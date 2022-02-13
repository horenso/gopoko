extends Node

const URL = "ws://localhost:8080/"

var _client = WebSocketClient.new()
var is_connected: bool = false
var is_logged_in: bool = false
var _token = null

# Signals nodes can subscribe to, each signal corresponse to a path
# for the "dest" field in the response

# warning-ignore:unused_signal
signal login(payload)
# warning-ignore:unused_signal
signal table_update(payload)
# warning-ignore:unused_signal
signal table_open(payload)


func send_request(request: Dictionary):
	var payload = JSON.print(request)
	print("Sending payload: ", payload)
	if is_connected:
		_client.get_peer(1).put_packet(payload.to_utf8())
	# TODO: What happens when we aren"t connected?
	# Maybe save requests in a queue? Or forget about them?


func _ready():
	_client.connect("connection_closed", self, "_closed")
	_client.connect("connection_error", self, "_closed_with_error")
	_client.connect("connection_established", self, "_connected")
	_client.connect("data_received", self, "_on_data")

	var err = _client.connect_to_url(URL)
	if err == OK:
		is_connected = true
	else:
		print("Unable to connect to server")
		# TODO: Try to reconnect


func _process(_delta):
	if is_connected:
		_client.poll()


func _closed_with_error(_was_clean = false):
	print("Closed with error, clean: ", _was_clean)


func _closed(_was_clean = false):
	print("Closed, clean: ", _was_clean)
	set_process(false)


func _connected(_proto = ""):
	print("Connected to server")
	_client.get_peer(1).set_write_mode(WebSocketPeer.WRITE_MODE_TEXT)


func _on_data():
	var raw_data = _client.get_peer(1).get_packet().get_string_from_utf8()
	var response = parse_json(raw_data)
	print("Got data from server: ", response)
	var dest = response["dest"]
	if self.has_signal(dest):
		emit_signal(dest, response)
	else:
		print("Response is not a signal: ", response["dest"])
