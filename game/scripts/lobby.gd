extends Control


func _ready():
	var sub_rquest = {"action": "subscribe", "dest": "table_list_updates"}
	$"/root/WebsocketHandler".connect("table_list_update", self, "_on_table_list_update")
	$"/root/WebsocketHandler".send_request(sub_rquest)


func _on_table_list_update(payload: Dictionary):
	$TableList.update_tables(payload)


func _on_TableList_item_activated():
	var selected = $TableList.get_selected()
	var table_id = selected.get_text(0)
	var table_name = selected.get_text(1)
	print("Joining ", table_name)
	self._open_table(table_id.to_int(), table_name)


func _open_table(table_id: int, table_name: String):
	var table_str = "table_%d" % [table_id]
	var sub_rquest = {"action": "subscribe", "dest": table_str}
	var table_scene = load("res://scenes/table.tscn").instance()
	table_scene.table_id = table_id
	table_scene.table_name = table_name
	get_tree().change_scene_to(table_scene)
