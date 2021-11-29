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

def feed[A, B](commands: Command[A, B]) = println(commands)

def feedFull(commands: Command[Idle, Idle]) = println(commands)

@main
def run = 
  feed(Turn(East) ~> Start ~> Stop ~> Turn(East))
  feedFull(Start ~> Turn(East) ~> Stop)