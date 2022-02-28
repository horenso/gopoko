**How to run the backend**

1) you need openjdk (if you don't have it already) it should be 14 or higher
2) there is file in the backend called `mvnw`, this is Maven, make it executable
3) run `./mvnw spring-boot:run`

**Communication**

The client and server communicate with each other by sending `JSON` messages between one active Websocket connection.
Every client message has at least two fields, `action` and `dest`.

`action` is either `send`, `subscribe` or `unsubscribe`. `send` sends a simple message to denote an action like sending
a chat message, performing an action in the game or joining a table. With `subscribe` the client signals that it is
interested in getting broadcast notifications for certain destinations. Examples are tables the player plays at, or the
list of tables in the lobby. The rest of the `JSON` depends on the `dest` field.

Messages from the server only have a `dest` field. In the client the script `websocket_handler.gd` converts these
incoming messages into Godot signals, that other nodes can subscribe to.

```
>> {"action": "send", "dest": "login", "username": "horenso"}
<< {"dest": "login", "success": true}
>> {"action": "subscribe", "dest": "lobby"}
```

What the server does with a subscription depends on the `dest` again, for the `lobby` (list of active games)
the server changes, starting by sending the entire list. In case of a game table the server may send the current state
of the game, but e.g no chat messages.

At this time the client follows the following pattern to waits for single notification messages:

```
func _login_request(username):
	var login_request = {"action": "send", "dest": "login", "username": username}
	$"/root/WebsocketHandler".connect("login", self, "_on_login_response", [], CONNECT_ONESHOT)
	$"/root/WebsocketHandler".send_request(login_request)


func _on_login_response(payload: Dictionary):
	...
```

`CONNECT_ONESHOT` means that the signal will be disconnected after its first emission.
