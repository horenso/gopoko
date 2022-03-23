extends Control

onready var websocket_handler = $"/root/WebsocketHandler"
onready var chat_ui = $"ChatUI"
onready var spawn = $"Spawn"

var player_scene = load("res://scenes/player.tscn")
var empty_seat = preload("res://scenes/empty_seat.tscn")

# These must be set before initiating the scene
var table_id = null
var table_name = null


func _ready():
	websocket_handler.connect("chat_message", self, "_on_message_received")
	websocket_handler.connect("user_seated", self, "_on_player_took_seat")
	$Label.text = "%s (%s)" % [table_name, table_id]
	var sub_rquest = {"dest": "start_observing_table", "id": table_id}
	websocket_handler.send_request_with_callback(sub_rquest, self, "_on_start_observing_table", true)
	chat_ui.connect("message_sent", self, "_on_message_sent")


func _place_empty_seats(number_of_seats: int):
	for n in range(number_of_seats):
		var empty_seat_instance = empty_seat.instance()
		var seat_number = n + 1
		spawn.add_child(empty_seat_instance)
		empty_seat_instance.set_seat_number(seat_number)
		empty_seat_instance.position.x += n * 100
		empty_seat_instance.set_name("Seat%d" % [seat_number])
		empty_seat_instance.connect("take_seat", self, "_on_take_seat")


func _on_take_seat(seat_number):
	var take_seat_request = {"dest": "take_seat", "id": table_id, "seat": seat_number}
	print("Sit on %d" % [seat_number])
	websocket_handler.send_request(take_seat_request)


func _on_start_observing_table(payload: Dictionary):
	print("In _on_start_observing_table()")
	var seats = payload['seats']
	var players = payload['players']
	_place_empty_seats(seats)
	for player in players:
		var seat_number = player.get('seat')
		var username = player['username']
		_place_player(seat_number, username)


func _place_player(seat_number: int, username: String):
	var player_instance = load("res://scenes/player.tscn").instance()
	var seat_node = spawn.get_node("Seat%d" % [seat_number])
	var p = seat_node.position
	spawn.remove_child(seat_node)
	spawn.add_child(player_instance)
	player_instance.position += p
	player_instance.set_name("Player%d" % [seat_number])
	player_instance.set_username(username)


func _exit_tree():
	var unsub_request = {"dest": "stop_observing_table", "id": table_id}
	websocket_handler.send_request(unsub_request)


func _on_LeaveButton_pressed():
	get_tree().change_scene_to(load("res://scenes/lobby.tscn"))


func _on_message_sent(message: String):
	print("In table script, message: %s" % [message])
	var message_request = {"dest": "send_message", "id": table_id, "message": message}
	websocket_handler.send_request(message_request)


func _on_message_received(payload: Dictionary):
	var sender
	if payload["sender"] == websocket_handler.get_username():
		sender = "You"
	else:
		sender = payload["sender"]
	chat_ui.show_message(sender, payload["message"])


func _on_player_took_seat(payload: Dictionary):
	_place_player(payload["seat"], payload["username"])


func _on_player_left_seat(payload: Dictionary):
	var player_node = spawn.get_node("Player%d" % [payload["seat_number"]])
	spawn.remove_child(player_node)
