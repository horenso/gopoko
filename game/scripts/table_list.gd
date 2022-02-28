extends Tree

var root = null
onready var websocket_handler = $"/root/WebsocketHandler"


func _ready():
	columns = 3
	set_hide_root(true)
	set_column_titles_visible(true)
	set_column_title(0, "ID")
	set_column_title(1, "Table Name")
	set_column_title(2, "Players")


func add_table(id: int, name: String):
	var new_child = create_item()
	new_child.set_text_align(0, TreeItem.ALIGN_CENTER)
	new_child.set_text_align(1, TreeItem.ALIGN_CENTER)
	new_child.set_text_align(2, TreeItem.ALIGN_CENTER)
	new_child.set_text(0, str(id))
	new_child.set_text(1, name)
	new_child.set_text(2, "(0|?)")


func update_tables(payload: Dictionary):
	# The lobby will be reworked anyway
	# but it would be nice to update the Tree Node instread of just
	# replacing all entries
	clear()
	root = create_item()
	for table in payload["tables"]:
		add_table(table["id"], table["name"])
