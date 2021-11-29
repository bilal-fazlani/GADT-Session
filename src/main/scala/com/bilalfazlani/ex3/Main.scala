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

def feed(commands: Command) = println(commands)

@main
def run = feed(Turn(East) ~> Start ~> Stop)
