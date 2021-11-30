package com.bilalfazlani
package ex5

type Idle
type Moving

enum Direction:
  case East, West, North, South
import Direction.*

sealed trait Command[Before, After]

case object Start extends Command[Idle, Moving]
case class Turn[A](direction: Direction) extends Command[A, A]
case object Stop extends Command[Moving, Idle]
case class Chain[A, B, C](command1: Command[A, B], command2: Command[B, C])
    extends Command[A, C]

extension [A, B](command1: Command[A, B])
  infix def ~>[C](command2: Command[B, C]): Command[A, C] =
    Chain(command1, command2)

object Robot {
  enum State:
    case Moving,Idle
  var currentState:State = State.Idle
  var direction: Option[Direction] = None

  def feed[A,B](command: Command[A,B]):Unit = (currentState, command) match {
      case (_, Chain(a,b)) =>
        feed(a)
        feed(b)
      case (State.Moving, Stop) => 
        currentState = State.Idle
        println("robot stopped")
      case (State.Idle, Start) =>
        currentState = State.Moving
        println("robot started")
      case (_, Turn(dir)) =>
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