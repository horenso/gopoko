extends Sprite

const path_fmt = "res://img/cards/%s-%s.png"

enum Suit { diamonds, clubs, hearts, spades }
enum Value { two, three, four, five, six, seven, eight, nine, ten, jack, queen, king, ace }

export(Suit) var suit = Suit.clubs
export(Value) var value = Value.ace


func _ready():
	update_texture()


func update_texture():
	var texture = load(path_fmt % [Value.keys()[value], Suit.keys()[suit]])
	set_texture(texture)


func _on_CallButton_pressed():
	value = (value + 1) % 13
	print(Value.keys()[value])
	update_texture()


func _on_RaiseButton_pressed():
	suit = (suit + 1) % 4
	print(Suit.keys()[suit])
	update_texture()
