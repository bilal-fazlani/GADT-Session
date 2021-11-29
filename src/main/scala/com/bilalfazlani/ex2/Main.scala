package com.bilalfazlani
package ex2

enum Direction:
  case East, West, North, South

enum Command:
  case Turn(direction: Direction)
  case Start
  case Stop
  case Chain(command1: Command, command2: Command)

import Direction.*
import Command.*

def feed(commands: Command) = println(commands)

@main
def run = feed(Chain(Chain(Turn(East), Start), Stop))
