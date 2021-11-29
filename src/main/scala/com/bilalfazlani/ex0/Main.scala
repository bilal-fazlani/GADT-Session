package com.bilalfazlani
package ex0

enum Command:
  case Start, Turn, Stop

enum Direction:
  case East, West, North, South

case class Order(command: Command, direction: Option[Direction] = None)

def feed(command: Order) = println(command)

@main
def run = feed(Order(Command.Turn, Some(Direction.East)))
