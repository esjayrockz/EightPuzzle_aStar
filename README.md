# EightPuzzle_aStar

•	I have used Java as my choice of programming language to solve the 8-puzzle using A* algorithm.
•	The heuristic used here in my program is Manhattan distance(sum of vertical and horizontal distance for each tile out of place).
•	The program accepts initial states as well as goal states as input from the user and generates the best solution path as output.
•	The initial states and goal states are stored in an ArrayList with “0” representing the empty tile.
•	A priority queue is created which stores the open list of nodes to be expanded in order of increasing(n).
•	f(n) is calculated as the sum of the path cost from the starting node and the Manhattan distance.
•	The lowest priority node as per its f(n) value is dequeued from the head of the priority node and checked if it is the goal node. If this happens, the execution of the program stops and the solution path is displayed.
•	If the above node is not the goal node, then it is expanded and its successors are generated and added to the priority queue.
•	Another ArrayList is created which stores the nodes which have already been visited and such nodes are not expanded if they are visited again with a higher f(n) value than its original f(n) value.
•	This process repeats until the goal node is encountered.
•	A stack is created which stores the position movements of the empty tile from Start state to Goal state.
