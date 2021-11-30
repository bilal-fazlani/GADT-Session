package com.bilalfazlani
package ex3

enum Direction:
  case East, West, North, South
import Direction.*

enum Command:
  case Turn(direction: Direction)
  case Start
  case Stop
  case Chain(command1: Command, command2: Command)

import Command.*

extension (command1: Command)
  infix def ~>(command2: Command) = Chain(command1, command2)

object Robot{
  enum State:
    case Moving,Idle

  var currentState:State = State.Idle
  var direction: Option[Direction] = None

  def feed(command: Command):Unit = (currentState, command) match {
      case (_, Command.Chain(a,b)) =>
        feed(a)
        feed(b)
      case (State.Moving, Command.Stop) => 
        currentState = State.Idle
        println("robot stopped")
      case (State.Idle, Command.Start) =>
        currentState = State.Moving
        println("robot started")
      case (State.Idle, Command.Turn(dir)) =>
        direction = Some(dir)
        println(s"robot now facing $dir")
      case (_, x) => 
        Console.err.println(s"""invalid command for given state
        current state: $currentState
        command: $x""")
        sys.exit(1)
    }
}

@main
def run = 
  println("-- feed 1")
  Robot.feed(Turn(East) ~> Start ~> Stop)
  println("-- feed 2")
  Robot.feed(Turn(East) ~> Stop)
