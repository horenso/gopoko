extends Control

# These must be set before initiating the scene
var table_id = null
var table_id_str = null
var table_name = null


# Called when the node enters the scene tree for the first time.
func _ready():
	$Label.text = '%s (%s)' % [table_name, table_id_str]
#	var sub_rquest = {"action": "subscribe", "dest": _table_id_str}
#	$"/root/WebsocketHandler".connect(_table_id_str, self, "_on_opened_table", [], CONNECT_ONESHOT)
#	$"/root/WebsocketHandler".send_request(sub_rquest)


func _on_LeaveButton_pressed():
	get_tree().change_scene_to(load("res://scenes/lobby.tscn"))
