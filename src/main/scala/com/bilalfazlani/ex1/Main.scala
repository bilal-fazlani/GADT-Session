package com.bilalfazlani
package ex1

enum Direction:
  case East, West, North, South

enum Command:
  case Turn(direction: Direction)
  case Start
  case Stop

import Direction.*
import Command.*

object Robot{
  enum State:
    case Moving,Idle

  var currentState:State = State.Idle
  var direction: Option[Direction] = None

  def feed(command: Command) = (currentState, command) match {
      case (State.Moving, Command.Stop) => 
        currentState = State.Idle
        println("robot stopped")
      case (State.Idle, Command.Start) =>
        currentState = State.Moving
        println("robot started")
      case (State.Idle, Command.Turn(dir)) =>
        direction = Some(dir)
        println(s"robot now facing $dir")
      case _ => 
        Console.err.println("invalid command for given state")
        sys.exit(1)
    }
}

@main
def run = 
  Robot.feed(Command.Start)
  Robot.feed(Command.Turn(Direction.North))
  Robot.feed(Command.Stop)