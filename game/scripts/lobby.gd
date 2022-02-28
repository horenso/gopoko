extends Control

onready var websocket_handler = $"/root/WebsocketHandler"
onready var table_list = $"TableList"

const Table = preload("res://scenes/table.tscn")


func _ready():
	_update_table_list()


func _update_table_list():
	var sub_rquest = {"dest": "get_table_list"}
	websocket_handler.send_request_with_callback(sub_rquest, self, "_on_table_list_update", true)


func _on_table_list_update(payload: Dictionary):
	table_list.update_tables(payload)


func _on_TableList_item_activated():
	var selected = $TableList.get_selected()
	var table_id = selected.get_text(0)
	var table_name = selected.get_text(1)
	print("Joining ", table_name)
	self._open_table(table_id.to_int(), table_name)


func _open_table(table_id: int, table_name: String):
	var table_scene = load("res://scenes/table.tscn")
	var table_instance = table_scene.instance()
	# I want to assign some properties now:
	table_instance.table_id = table_id
	table_instance.table_name = table_name

	var root = get_tree().root
	var lobby = root.get_node("Lobby")
	root.add_child(table_instance)
	get_tree().set_current_scene(table_instance)
	lobby.queue_free()


func _on_NewTableButton_pressed():
	var request = {"dest": "create_table"}
	websocket_handler.send_request(request)


func _on_UpdateListButton_pressed():
	_update_table_list()
