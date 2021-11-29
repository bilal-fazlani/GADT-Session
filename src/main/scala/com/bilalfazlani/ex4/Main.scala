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

def feed[A, B](commands: Command[A, B]) = println(commands)

def feedFull(commands: Command[Idle, Idle]) = println(commands)

@main
def run = 
  feed(Turn(East) ~> Start ~> Stop ~> Start)
  feedFull(Turn(East) ~> Start ~> Stop)
