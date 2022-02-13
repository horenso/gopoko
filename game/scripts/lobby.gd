extends Control

enum State { WAITING_FOR_SERVER, NOT_LOGGED_IN, LOGGED_IN }


func _ready():
	var sub_rquest = {"action": "subscribe", "dest": "lobby"}
	$"/root/WebsocketHandler".connect("table_update", self, "_on_table_update")
	$"/root/WebsocketHandler".send_request(sub_rquest)


func _on_table_update(payload: Dictionary):
	$TableList.update_tables(payload)


func _on_TableList_item_activated():
	var selected = $TableList.get_selected()
	var table_id = selected.get_text(0)
	var table_name = selected.get_text(1)
	print("Joining ", table_name)
	self._open_table(table_id.to_int(), table_name)


# Requests and responses:


func _open_table(table_id: int, table_name: String):
	var table_str = "table_%d" % [table_id]
	var sub_rquest = {"action": "subscribe", "dest": table_str}
	var table_scene = load("res://scenes/table.tscn").instance()
	table_scene.table_id = table_id
	$"/root/WebsocketHandler".connect(table_str, self, "_on_opened_table", [], CONNECT_ONESHOT)
	$"/root/WebsocketHandler".send_request(sub_rquest)


func _on_opened_table(payload: Dictionary):
	print("Response to join: ", payload)
	if payload["success"]:
		get_tree().change_scene("res://scenes/table.tscn")
