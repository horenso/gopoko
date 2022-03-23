extends Sprite

onready var username_label = $"UsernameLabel"

func set_username(username: String):
	username_label.text = username
