extends Node2D

var seat_number = -1

signal take_seat

func _on_SitHereButton_pressed():
	emit_signal("take_seat", seat_number)


func set_seat_number(n: int):
	seat_number = n
