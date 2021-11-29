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

def feed(commands: Command) = println(commands)

@main
def run = feed(Command.Start)