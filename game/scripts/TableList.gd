extends Tree

var root = null

func _ready():
	columns = 3
	set_hide_root(true)
	set_column_titles_visible(true)
	set_column_title(0, 'Table Name')
	set_column_title(1, 'Description')
	set_column_title(2, 'Players')

func add_table(name: String, description: String, player_max: int):
	var new_child = create_item()
	new_child.set_text_align(0, TreeItem.ALIGN_CENTER)
	new_child.set_text_align(1, TreeItem.ALIGN_CENTER)
	new_child.set_text_align(2, TreeItem.ALIGN_CENTER)
	new_child.set_text(0, name)
	new_child.set_text(1, description)
	new_child.set_text(2, '(0|%d)' % [player_max])

func update_tables(table_infos):
	# The lobby will be reworked anyway
	# but it would be nice to update the Tree Node instread of just
	# replacing all entries
	root = create_item()
	for table in table_infos:
		add_table(table['name'], table['description'], table['playerMax'])

func _on_TableList_item_activated():
	print(get_selected().get_text(0))
