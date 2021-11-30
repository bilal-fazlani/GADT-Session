package com.bilalfazlani
package ex2

enum Direction:
  case East, West, North, South

enum Command:
  case Turn(direction: Direction)
  case Start
  case Stop
  case Chain(command1: Command, command2: Command)

object Chain{
  def apply(command1: Command, command2: Command) = 
    
    Command.Chain(command1, command2)
}

import Direction.*
import Command.*

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
  println("--feed 1")
  Robot.feed(Chain(Chain(Turn(East), Start), Stop))
  println("--feed 2")
  Robot.feed(Chain(Chain(Start, Start), Stop))
