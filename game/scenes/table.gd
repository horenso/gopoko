extends Control


var table_id = null
var table_name = null

# Called when the node enters the scene tree for the first time.
func _ready():
	pass # Replace with function body.



func _on_LeaveButton_pressed():
	get_tree().change_scene("res://scenes/lobby.tscn")
