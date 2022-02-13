extends Panel


func _ready():
	$Rows/UsernameField.grab_focus()


func _on_UsernameField_text_entered(new_text):
	self._login_request(new_text)


func _on_Button_pressed():
	var username = $Rows/UsernameField.text
	self._login_request(username)


func _login_request(username):
	var login_request = {"action": "send", "dest": "login", "username": username}
	$"/root/WebsocketHandler".connect("login", self, "_on_login_response", [], CONNECT_ONESHOT)
	$"/root/WebsocketHandler".send_request(login_request)


func _on_login_response(payload: Dictionary):
	$"/root/WebsocketHandler".disconnect("login", self, "_on_login_response")
	if payload["success"]:
		get_tree().change_scene("res://scenes/lobby.tscn")
	else:
		$Rows/ErrorLabel.text = payload["error"]
