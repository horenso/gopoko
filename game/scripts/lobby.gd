extends Control

enum State { WAITING_FOR_SERVER, NOT_LOGGED_IN, LOGGED_IN }


func _login_request(username):
	var login_request = {"dest": "login", "username": username}
	$"/root/WebsocketHandler".connect("login", self, "_on_login_response", [], CONNECT_ONESHOT)
	$"/root/WebsocketHandler".send_request(login_request)


func _on_login_response(payload: Dictionary):
	if payload["result"] == "success":
		$LoginOverlay/LoginSection/ErrorLabel.text = ""
		$LoginOverlay.visible = false
	else:
		$LoginOverlay/LoginSection/ErrorLabel.text = payload["result"]


func _on_table_update(payload: Dictionary):
	$TableList.update_tables(payload['tables'])


func _on_UsernameField_text_entered(new_text):
	self._login_request(new_text)


func _on_Button_pressed():
	var username = $LoginOverlay/LoginSection/UsernameField.text
	self._login_request(username)


func _join_table(table_id: int):
	var join_request = {"dest": "join", "id": table_id}
	$"/root/WebsocketHandler".connect("join", self, "_on_join_response", [], CONNECT_ONESHOT)
	$"/root/WebsocketHandler".send_request(join_request)
	
func _on_join_response(payload: Dictionary):
	print('Response to join: ', payload)

func _on_TableList_item_activated():
	var table_name = $TableList.get_selected().get_text(0)
	print("Joining ", table_name)
	self._join_table(table_name.to_int())
