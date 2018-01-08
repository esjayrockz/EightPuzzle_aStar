/**
 * Created by Suvajit on 30/09/17.
 */

import java.util.ArrayList;

public class Node_Astar {

    /*
    Contains code to define the data members variables of an object.
    This object will represent each node of the 8 puzzle problem i.e. each state and its
    representation of things such as the path cost of the state, its priority etc.
    */

    String name;
    ArrayList<Integer> state;
    Node_Astar parent;
    int distance;
    String move;
    public int priority;

    public Node_Astar(String name){


        this.name = name;


    }

    public Node_Astar(){


    }
    public String getName(){


        return this.name;


    }

}
