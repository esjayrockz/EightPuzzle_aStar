/**
 * Created by Suvajit on 30/09/17.
 */
import java.util.Comparator;

public class Node_Compare_Astar implements Comparator<Node_Astar>{

    /*•	Node_Compare_Astar.java is an implementation to the Comparator class
    in Java which helps to prioritize the nodes in the priority queue.
     */


    @Override
    public int compare(Node_Astar node1, Node_Astar node2) //Compares different nodes based on priority values
    {
        if (node1.priority > node2.priority){
            return 1;
        }
        if (node1.priority < node2.priority){
            return -1;
        }

        return 0;
    }
}
