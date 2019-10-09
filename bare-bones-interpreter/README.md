# Bare Bones Interpreter

Bare Bones is a simple language which we will implement an interpreter for.

## Language
Bare Bones has three commands, a control loop and support for positive integers.

### Commands

''''
clear name; //sets variable 'name' to 0
incr name; //increments variable 'name' by 1
decr name; //decrements variable 'name' by 1
''''

Variables need not be declared before use (default value 0).

### Control loop
Bare Bones supports while control loop, with nesting.
''''
while name not 0 do;
...
...
end;

## Implementation

