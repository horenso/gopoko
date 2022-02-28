extends Node


const URL = "ws://localhost:8080/"

var _client = WebSocketClient.new()
var is_connected: bool = false

var _username = null
var _token = null

signal connected
signal disconnected

signal chat_message


func _ready():
	_client.connect("connection_closed", self, "_closed")
	_client.connect("connection_error", self, "_closed_with_error")
	_client.connect("connection_established", self, "_connected")
	_client.connect("data_received", self, "_on_receive")


func init_connection(username: String, token: String):
	# Save the token for reconnects
	_username = username
	_token = token
	var username_header = 'username: %s' % [username]
	var token_header = 'token: %s' % [token]
	var err = _client.connect_to_url(URL, [], false, [username_header, token_header])
	if err == OK:
		is_connected = true
	else:
		print("unable to connect to server...")
		# TODO: Try to reconnect


func send_request_with_callback(request: Dictionary, node: Node, fn: String, one_shot: bool):
	if not is_connected:
		print("not connected...")
		return
		# TODO: What happens when we aren"t connected?
		# Maybe save requests in a queue? Or forget about them?
	var dest = request['dest']
	if not has_signal(dest):
		print("created signal ", dest)
		add_user_signal(dest)
	if one_shot:
		connect(dest, node, fn, [], CONNECT_ONESHOT)
	else:
		connect(dest, node, fn)
		
	send_request(request)

func send_request(request: Dictionary):
	if not is_connected:
		print("not connected...")
		return
	var payload = JSON.print(request)
	_client.get_peer(1).put_packet(payload.to_utf8())
	print(">> ", payload)

func _on_receive():
	var raw_data = _client.get_peer(1).get_packet().get_string_from_utf8()
	var response: Dictionary = JSON.parse(raw_data).result
	print("<< ", JSON.print(response))
	var dest = response["dest"]
	if has_signal(dest):
		emit_signal(dest, response)
	else:
		print("Signal %s doesn't exist." % [dest])


func _process(_delta):
	if is_connected:
		_client.poll()


func _closed_with_error(_was_clean = false):
	print("Closed with error, clean: ", _was_clean)
	emit_signal("disconnected")


func _closed(_was_clean = false):
	print("Closed, clean: ", _was_clean)
	emit_signal("disconnected")


func _connected(_proto = ""):
	print("Connected to server")
	_client.get_peer(1).set_write_mode(WebSocketPeer.WRITE_MODE_TEXT)
	emit_signal("connected")
