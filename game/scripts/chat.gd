extends Panel


# Called when the node enters the scene tree for the first time.
func _ready():
	pass  # Replace with function body.


func _on_MessageInput_gui_input(event):
	pass


func send_message(message: String):
	if message.empty():
		return
	$Rows/SendRow/MessageInput.text = ""
	$Rows/Messages.bbcode_text += "[color=#03d3fc][You] %s\n[/color]" % [message]


func _on_SendButton_pressed():
	send_message($Rows/SendRow/MessageInput.text)


func _on_MessageInput_text_entered(new_text):
	send_message(new_text)
