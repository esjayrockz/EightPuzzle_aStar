import java.io.IOException;
import java.util.Scanner;
/**
 * Created by Suvajit on 27/10/17.
 */
public class NQueens {
    private static int N;//Number of Queens
    private static int queens_positions[]; //Row number of each of the queens

    private static int copy_queens_positions[]; //Temporary array used to find the heuristic of neighbours

    private static int restart_count = -1; //Number of random restarts
    private static int steps = 0; //Number of state changes

    /**
     * Method to generate a random initial state or random restart
     */
    private static void initialize() {

        for (int col = 0; col < N; col++) {
            int row = (int) (Math.random() * (N - 1));
            queens_positions[col] = row;

        }
        if (restart_count == -1) {
            System.out.println("\n Initial state");
            displayBoard(queens_positions);

        }
        restart_count++;

    }
/*
Method that calculate heuristic for a given N queens configuration
 */

    private static int getHeuristic(int queens_positions[]) {
        int heuristic = 0;
        for (int i = 0; i < N - 1; i++) {
            for (int j = i + 1; j < N; j++) {
                if (queens_positions[i] == queens_positions[j]) {
                    heuristic++;
                    continue;
                }
                if (Math.abs(i - j) == Math.abs(queens_positions[i] - queens_positions[j]))
                    heuristic++;
            }
        }
        return heuristic;
    }

/*
Display N queens board
 */

    private static void displayBoard(int[] queens_positions) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (queens_positions[j] == i)
                    System.out.print("Q" + "\t");
                else
                    System.out.print("-" + "\t");
            }
            System.out.println();
        }
    }

/*
Method to initialize a copy of the Queens_positions array to be used as a temporary array
 */

    private static void initQueensCopy() {
        for (int i = 0; i < N; i++) {
            copy_queens_positions[i] = queens_positions[i];
        }

    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of queens: ");
        N = scanner.nextInt();
        if (N == 1) {
            System.out.println("\nSolution:\nQ");
            return;
        }
        if (N < 4) {
            System.out.println("\nSolution does not exist");
            return;
        }
        queens_positions = new int[N];

        initialize();



        while (true) {

            //heuristic of current state
            int current_heuristic = getHeuristic(queens_positions);
            //if heuristic of current state is 0, return current state as
            //solution
            if (current_heuristic == 0) {
                System.out.println("\nSolution: \n");
                displayBoard(queens_positions);
                System.out.println("\nNumber of random_restarts:" + restart_count + "\nNo. of state changes:" + steps);
                return;
            }
            int board_heuristic[][] = new int[N][N];
            copy_queens_positions = new int[N];

            //get and store heuristics of all the neighbor states as well, in
            //board heuristic
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    initQueensCopy();
                    copy_queens_positions[i] = j;
                    board_heuristic[j][i] = getHeuristic(copy_queens_positions);
                }
            }

            int row_least_heuristic = 0;
            int column_least_heuristic = 0;

            //get and set the lowest heuristic position in the board of
            //heuristics: board_heuristic
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (board_heuristic[i][j] < board_heuristic[row_least_heuristic][column_least_heuristic]) {
                        row_least_heuristic = i;
                        column_least_heuristic = j;
                    }
                }
            }


//if the least heuristic of the neighbour states is less than the heuristic
//of the current state,that particular neighbour state becomes current state
            if (board_heuristic[row_least_heuristic][column_least_heuristic] < current_heuristic) {

                queens_positions[column_least_heuristic] = row_least_heuristic;
                steps++;
            }
            //else do a random restart
            else {
                steps = 0;
                initialize();
                if(restart_count>=1000) //Keeping maximum limit of restarts to 1000
                {
                    System.out.println("\nMore than 1000 restarts occured, solution state not possible with "+N+" queens");
                    return;
                }
            }
        }
    }
}