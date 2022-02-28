extends Panel

onready var messages = $"Rows/Messages"
onready var message_input = $"Rows/SendRow/MessageInput"

signal message_sent


func send_message(message: String):
	if message.empty():
		return
	message_input.text = ""
	emit_signal("message_sent", message)


func show_message(sender: String, message: String):
	messages.bbcode_text += "[color=#03d3fc][%s] %s\n[/color]" % [sender, message]


func _on_SendButton_pressed():
	send_message(message_input.text)


func _on_MessageInput_text_entered(new_text):
	send_message(new_text)
