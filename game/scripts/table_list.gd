extends Tree

var root = null


func _ready():
	columns = 4
	set_hide_root(true)
	set_column_titles_visible(true)
	set_column_title(0, "ID")
	set_column_title(1, "Table Name")
	set_column_title(2, "Description")
	set_column_title(3, "Players")
	$"/root/WebsocketHandler".connect("table_update", self, "_update_tables")


func add_table(name: String, description: String, player_max: int, id: int):
	var new_child = create_item()
	new_child.set_text_align(0, TreeItem.ALIGN_CENTER)
	new_child.set_text_align(1, TreeItem.ALIGN_CENTER)
	new_child.set_text_align(2, TreeItem.ALIGN_CENTER)
	new_child.set_text_align(3, TreeItem.ALIGN_CENTER)
	new_child.set_text(0, str(id))
	new_child.set_text(1, name)
	new_child.set_text(2, description)
	new_child.set_text(3, "(0|%d)" % [player_max])


func update_tables(payload: Dictionary):
	# The lobby will be reworked anyway
	# but it would be nice to update the Tree Node instread of just
	# replacing all entries
	root = create_item()
	for table in payload["tables"]:
		add_table(table["name"], table["description"], table["playerMax"], table["id"])
