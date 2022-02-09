extends Control

export var url = "ws://localhost:8080/"

enum State {
	WAITING_FOR_SERVER,
	NOT_LOGGED_IN,
	LOGGED_IN
}

var _client = WebSocketClient.new()
var state = State.WAITING_FOR_SERVER

func _ready():
	_client.connect("connection_closed", self, "_closed")
	_client.connect("connection_error", self, "_closed_with_error")
	_client.connect("connection_established", self, "_connected")
	_client.connect("data_received", self, "_on_data")

	var err = _client.connect_to_url(url)
	if err != OK:
		print("Unable to connect")
		# TODO: Try to reconnect

func _closed_with_error(_was_clean = false):
	print('Connection error, trying to reconnect')
	_client.connect("connection_error", self, "_closed_with_error")

func _closed(_was_clean = false):
	print("Closed, clean: ", _was_clean)
	set_process(false)

func _connected(_proto = ""):
	print("Connected to server")
	_client.get_peer(1).set_write_mode(WebSocketPeer.WRITE_MODE_TEXT)
	state = State.NOT_LOGGED_IN

func _on_data():
	var raw_data = _client.get_peer(1).get_packet().get_string_from_utf8()
	var r = parse_json(raw_data)
	print("Got data from server: ", r)
	match (r['destination']):
		'*':
			print('default concern')
		'login':
			if state == State.NOT_LOGGED_IN:
				if r['result'] != 'success':
					$LoginOverlay/LoginSection/ErrorLabel.text = r['result']
				else:
					state = State.LOGGED_IN
					$LoginOverlay/LoginSection/ErrorLabel.text = ''
					$LoginOverlay.visible = false
		'tableUpdate':
			$TableList.update_tables(r['tables'])

func _login(username):
	var login_payload = {
		'cmd': 'login',
		'username': username
	}
	var j = JSON.print(login_payload)
	print(j)
	_client.get_peer(1).put_packet(j.to_utf8())


func _process(_delta):
	_client.poll()


func _on_UsernameField_text_entered(new_text):
	self._login(new_text)


func _on_Button_pressed():
	var username = $LoginOverlay/LoginSection/UsernameField.text
	self._login(username)


func _on_Tree_item_activated():
	print($Tree.get_selected_column())
	print("sdf")


func _on_TableList_item_activated():
	pass # Replace with function body.
