# CS143B
# Author:          Brent A. Dixon
# Getting Started: This project was written for an operating systems class taken at UCI. This program naively runs a OS simulation.
# Prerequisites:   These projects were compiled from the command line using Java 8. To compile and run this, run commands below in Linux terminal.
#                      ex: > javac Scheduler.java
#                          > java Scheduler
# Program Commands:
#                  init
#                  Restore the system to its initial state.
#
#                  cr <name> <priority>
#                  Creates a new process <name> at the priority level <priority>;
#                  <name> is a single character;
#                  <priority> can be 1 or 2 (0 is reserved for Init process).
#
#                  de <name>
#                  Destroy the process <name> and all of its descendants.
#
#                  req <name> <numberOfUnits>
#                  Requests the resource <name> which can be R1, R2, R3, or R4. 
#                  <numberOfUnits> gives the number of units of resource <name> to be requested.
#                  The numbers of units constituting each resource are as follows: 1 for R1, 2 for R2, 3 for R3, and 4 for R4.
#
#                  rel <name> <numberOfUnits> 
#                  Releases the resource <name>. 
#                  <numberOfUnits> gives the number of units of resource <name> to be released.
#
#                  to
#                  Causes currently running process to timeout and be placed at the end of the corresponding ready list.
#                  Loads the next process at the head of the ready list.
#
# Thank you!
