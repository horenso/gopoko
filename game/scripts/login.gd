extends Panel

onready var websocket_handler = $"/root/WebsocketHandler"
onready var username_field = $"Rows/UsernameField"
onready var password_field = $"Rows/PasswordField"
onready var error_label = $"Rows/ErrorLabel"
onready var http_request = $"Rows/HTTPRequest"

const URL = "http://localhost:8080"


func _ready():
	username_field.grab_focus()

func _on_Button_pressed():
	$HTTPRequest.request("http://www.mocky.io/v2/5185415ba171ea3a00704eed")

func _on_request_completed(result, response_code, headers, body):
	var json = JSON.parse(body.get_string_from_utf8())
	print(json.result)
	print(headers)
	print(result)


func _on_RegisterButton_pressed():
	var username = username_field.text
	var password = password_field.text
	var register_request = {"username": username, "password": password}
	http_request.connect("request_completed", self, "_on_register_result", [], CONNECT_ONESHOT)	
	http_request.request(
		URL + "/users",
		["content-type: application/json"],
		false,
		HTTPClient.METHOD_POST,
		JSON.print(register_request)
	)


func _on_LoginButton_pressed():
	var username = username_field.text
	var password = password_field.text
	var login_request = { "username": username, "password": password }
	http_request.connect("request_completed", self, "_on_login_response", [], CONNECT_ONESHOT)	
	http_request.request(
		URL + "/users",
		["content-type: application/json"],
		false,
		HTTPClient.METHOD_GET,
		JSON.print(login_request)
	)


func _on_register_result(result, response_code, headers, body):
	if response_code == HTTPClient.RESPONSE_CREATED:
		error_label.text = "Registered successfully!"
	else:
		var json_result = JSON.parse(body.get_string_from_utf8()).result
		error_label.text = json_result["message"]


func _on_login_response(result, response_code, headers, body):
	var json_result = JSON.parse(body.get_string_from_utf8()).result
	if response_code == HTTPClient.RESPONSE_OK:
		websocket_handler.connect("connected", self, "_on_connected")
		websocket_handler.init_connection(json_result["token"])
	else:
		error_label.text = json_result["message"]


func _on_connected():
	get_tree().change_scene("res://scenes/lobby.tscn")
