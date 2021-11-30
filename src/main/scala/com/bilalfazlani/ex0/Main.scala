package com.bilalfazlani
package ex0

enum Command:
  case Start, Turn, Stop

enum Direction:
  case East, West, North, South

case class Order(command: Command, direction: Option[Direction] = None)

object Robot{
  enum State:
    case Moving,Idle

  var currentState:State = State.Idle
  var direction: Option[Direction] = None

  def feed(command: Order) = (currentState, command) match {
      case (State.Moving, Order(Command.Stop, None)) => 
        currentState = State.Idle
        println("robot stopped")
      case (State.Idle, Order(Command.Start, None)) =>
        currentState = State.Moving
        println("robot started")
      case (State.Idle, Order(Command.Turn, Some(dir))) =>
        direction = Some(dir)
        println(s"robot now facing $dir")
      case (_, x) => 
        Console.err.println(s"""invalid order for given state
        current state: $currentState
        order: $x""")
        sys.exit(1)
    }
}


@main
def run = 
  Robot.feed(Order(Command.Start, Some(Direction.East)))
  Robot.feed(Order(Command.Turn, Some(Direction.East)))
  Robot.feed(Order(Command.Stop, None))
