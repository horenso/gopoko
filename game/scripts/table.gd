extends Control

onready var websocket_handler = $"/root/WebsocketHandler"
onready var chat_ui = $"ChatUI"

# These must be set before initiating the scene
var table_id = null
var table_name = null


func _ready():
	websocket_handler.connect("chat_message", self, "_on_message_received")
	$Label.text = "%s (%s)" % [table_name, table_id]
	var sub_rquest = {"dest": "start_observing_table", "id": table_id}
	websocket_handler.send_request(sub_rquest)
	chat_ui.connect("message_sent", self, "_on_message_sent")


func _exit_tree():
	var unsub_rquest = {"dest": "stop_observing_table", "id": table_id}
	websocket_handler.send_request(unsub_rquest)


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
