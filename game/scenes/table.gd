extends Control


var table_id = null
var table_name = null

# Called when the node enters the scene tree for the first time.
func _ready():
	$"/root/WebsocketHandler".connect(table_str, self, "_on_opened_table", [], CONNECT_ONESHOT)
	$"/root/WebsocketHandler".send_request(sub_rquest)



func _on_LeaveButton_pressed():
	get_tree().change_scene("res://scenes/lobby.tscn")





func _on_opened_table(payload: Dictionary):
	print("Response to join: ", payload)
	if payload["success"]:
		get_tree().change_scene("res://scenes/table.tscn")
