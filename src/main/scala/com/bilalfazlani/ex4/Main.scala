package com.bilalfazlani
package ex4

type Idle
type Moving

enum Direction:
  case East, West, North, South
import Direction.*

enum Command[From, To]:
  case Turn(direction: Direction) extends Command[Idle, Idle]
  case Start extends Command[Idle, Moving]
  case Stop extends Command[Moving, Idle]
  case Chain[A, B, C](command1: Command[A, B], command2: Command[B, C])
      extends Command[A, C]

import Command.*

extension [A, B, C](command1: Command[A, B])
  infix def ~>(command2: Command[B, C]): Command[A, C] =
    Chain(command1, command2)

object Robot {
  enum State:
    case Moving,Idle
  var currentState:State = State.Idle
  var direction: Option[Direction] = None

  def feed[A,B](command: Command[A,B]):Unit = (currentState, command) match {
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
  Robot.feed(Turn(East) ~> Start ~> Stop ~> Start)
  println("--feed 2")
  Robot.feed(Turn(East) ~> Start ~> Stop)
