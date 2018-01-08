/**
 * Created by Suvajit on 29/09/17.
 */


import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

public class Eight_Puzzle_Astar {

    // Contains the code for implementing the 8 puzzle problem using A* algorithm.

    public static void main(String[] args)
    {
        int in;
        Scanner scan = new Scanner(System.in);

        Node_Astar[] states = new Node_Astar[4]; //array representing all the possible successive states from a particular node.

        Node_Astar goalNodeFound = new Node_Astar(); //object storing the goal node when found but null otherwise
        goalNodeFound = null; //Initializing as null in the beginning
        Stack stack = new Stack(); //Stores the position movements of the empty tile from Start state to Goal state
        Node_Astar current = new Node_Astar(); //current node taken into account
        LinkedList<ArrayList<?>> visited = new LinkedList<ArrayList<?>>(); //stores the nodes which have already been visited
        int count = 0; //count of total nodes expanded

        //Creating ArrayList for Initial State
        Node_Astar start = new Node_Astar(); //an ArrayList for Initial State
        ArrayList<Integer> initialState = new ArrayList<>();
        System.out.println("Enter the 8 numbers for the Initial State and 0 for the empty tile");

        for(int i = 0; i<9; i++) {
            in = scan.nextInt(); //Accepting input for Initial State
            initialState.add(in); //Adding input to arraylist
        }

        start.state = initialState;
        start.parent = null;
        start.move = null;
        start.priority = 0;
        start.distance = 0;

        //Creating ArrayList for Goal State
        Node_Astar goal = new Node_Astar(); //ArrayList for Goal State
        ArrayList<Integer> goalState = new ArrayList<>();
        System.out.println("Enter the 8 numbers for the Goal State and 0 for the empty tile");

        for(int i = 0; i<9; i++) {
            in = scan.nextInt(); //Accepting input for Goal State
            goalState.add(in); //Adding input to arraylist
        }

        goal.state = goalState;
        goal.parent = null;
        goal.distance = 0;
        goal.move = null;

        Comparator<Node_Astar> comparator = new Node_Compare_Astar(); //is an object of the class Node_Compare_Astar of the type Node_Astar which will allow to compare two nodes and arrange them based on the priorities in the Priority queue
        PriorityQueue<Node_Astar> pq = new PriorityQueue<Node_Astar>(100,comparator); //priority queue created to store the open list of nodes to be expanded in order of increasing(n)
        pq.add(start);
        visited.add(start.state);
        System.out.println("Nodes generated is as follows:");
        System.out.println("Starting Node: \n");//Output of starting node
        int num = 0;
        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++){
                System.out.print(start.state.get(num) + " ");
                num++;
            }
            System.out.println();
        }
        System.out.println("f(n): " + costFunctionLength(start,goal)+"\n"); //Output of f(n) for starting node

        while(!pq.isEmpty()){
            count++;
            current = pq.remove(); //current node based on priority queue
            states = findStates(current); //calling findStates to receive the states of successive nodes

            for(int i = 0; i<=3; i++){
                if(states[i] != null){
                    //checking if already present state is same as goal state
                    if (states[i].state.equals(goal.state)){
                        goalNodeFound = states[i];
                        break;
                    }
                    else{

                        if(!visited.contains(states[i].state)) //checking if state is already present in visited states
                        {
                            states[i].distance = current.distance + 1; //adding path cost
                            visited.add(states[i].state); //adding state to list of visited states
                            states[i].priority = costFunctionLength(states[i], goal); //receiving value of f(n)
                            pq.add(states[i]); //adding successive nodes to priority queue
                            System.out.println("Node: \n");//printing node generated
                            int num1 = 0;
                            for (int k = 0; k < 3; k++) {
                                for (int l = 0; l < 3; l++){
                                    System.out.print(states[i].state.get(num1) + " ");
                                num1++;
                            }
                            System.out.println();
                        }
                            System.out.println("f(n): " + states[i].priority+"\n");//printing value of f(n)

                        }
                    }
                }
            }

            if(goalNodeFound != null) //break the loop is goalnode is found
                break;

        }
        //printing goal node
        System.out.println("GoalNode: \n");
        int num2 = 0;
        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++){
                System.out.print(goal.state.get(num2) + " ");
                num2++;
            }
            System.out.println();
        }

        System.out.println("\nDirections of empty tile for best solution path"+"\n");

        //backtracking path of empty tile
        while (goalNodeFound.parent != null){
            if(goalNodeFound.move != null){
                stack.push(goalNodeFound.move);
            }
            goalNodeFound = goalNodeFound.parent;
        }

        while(!stack.isEmpty()){
            System.out.println(stack.pop()); //printing movement of empty tile

        }

        System.out.println("\n"+count + " Nodes expanded."); //printing value of nodes expanded
        System.out.println(visited.size() + " Nodes generated."); //printing value of nodes generated


    }

    private static Node_Astar[] findStates(Node_Astar state) {

        //finding possible successive states of current stae
        Node_Astar state1,state2,state3,state4;

        state1 = moveUp(state);
        state2 = moveDown(state);
        state3 = moveLeft(state);
        state4 = moveRight(state);

        Node_Astar[] states = {state1, state2, state3, state4};

        return states;
    }



    private static int costFunctionLength(Node_Astar node, Node_Astar goal) {


        int priority;
        int cnt = 0;
        int index;
        //Heuristic Function Calculation using Manhattan distance
        int row_node,col_node,row_goal,col_goal;
        for(int i=0; i<9; i++){

            if(node.state.get(i) != 0) {
                row_node = (int) Math.ceil((double) (i + 1) / 3.0);
                col_node = (i % 3) + 1;

                index = goal.state.indexOf(node.state.get(i));

                row_goal = (int) Math.ceil((double) (index + 1) / 3.0);
                col_goal = (index % 3) + 1;
                cnt = cnt + Math.abs(row_goal - row_node) + Math.abs(col_goal - col_node);
            }
        }
        //Calculating f(n) = g(n) + h(n);
        priority = node.distance + cnt;
        return priority;
    }


    private static Node_Astar moveUp(Node_Astar node) {
        //returning successive state if empty tile moves up
        int space = node.state.indexOf(0);
        ArrayList<Integer> childState;
        int temp;
        Node_Astar childNode = new Node_Astar();

        if (space > 2) {
            childState = (ArrayList<Integer>) node.state.clone();
            temp = childState.get(space-3);
            childState.set(space-3,0);
            childState.set(space,temp);
            childNode.state = childState;
            childNode.parent = node;
            childNode.distance = node.distance + 1;
            childNode.move = "UP";
            return childNode;
        }
        else{
            return null;
        }
    }

    private static Node_Astar moveDown(Node_Astar node) {
        //returning successive state if empty tile moves down
        int space = node.state.indexOf(0);
        ArrayList<Integer> childState;
        int temp;
        Node_Astar childNode = new Node_Astar();

        if (space <= 5) {
            childState = (ArrayList<Integer>) node.state.clone();
            temp = childState.get(space+3);
            childState.set(space+3,0);
            childState.set(space,temp);
            childNode.state = childState;
            childNode.parent = node;
            childNode.distance = node.distance + 1;
            childNode.move = "DOWN";
            return childNode;
        }
        else{
            return null;
        }
    }

    private static Node_Astar moveRight(Node_Astar node) {
        //returning successive state if empty tile moves right
        int space = node.state.indexOf(0);
        ArrayList<Integer> childState;
        int temp;
        Node_Astar childNode = new Node_Astar();

        if (space != 2 && space != 5 && space != 8) {
            childState = (ArrayList<Integer>) node.state.clone();
            temp = childState.get(space+1);
            childState.set(space+1,0);
            childState.set(space,temp);
            childNode.state = childState;
            childNode.parent = node;
            childNode.distance = node.distance + 1;
            childNode.move = "RIGHT";
            return childNode;
        }
        else{
            return null;
        }
    }

    private static Node_Astar moveLeft(Node_Astar node) {
        //returning successive state if empty tile moves left
        int space = node.state.indexOf(0);
        ArrayList<Integer> childState;
        int temp;
        Node_Astar childNode = new Node_Astar();

        if (space != 0 && space != 3 && space != 6) {
            childState = (ArrayList<Integer>) node.state.clone();
            temp = childState.get(space-1);
            childState.set(space-1,0);
            childState.set(space,temp);
            childNode.state = childState;
            childNode.parent = node;
            childNode.distance = node.distance + 1;
            childNode.move = "LEFT";
            return childNode;
        }
        else{
            return null;
        }
    }



}

