import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


/**
 * @author arjunsm
 *
 */
public class TicTacToe {
public static void main(String args[]) throws Exception {
	
		/************** Initialize all the variables **************/
	
		Random rg1 = new Random(); // to randomly select the player which goes first
		int player = 0; // to denote the player
		int box; // the index chosen by the player in the current chance
		int starter = 0; // to keep track of who started the play
		int n=0; // to hold the command line argument (N)
		int row = 0; 
		int column = 0;
		int win_flag = 0; // used to identify if match had a result or ended in a draw
		int i;
		/********************************************************/
		
		if(args.length>0)
		{
			 n = Integer.parseInt(args[0]);
			 if(n<2 || n>10)
			 {
				 throw new Exception("Please use an integer value between 2-10 as an argument");
			 }
		}
		else
		{
			throw new Exception("Please enter a valid n as an argument");
		}
		
		/********** Populating the game entries and shuffling **********/
		
		ArrayList<Integer> list1 = new ArrayList<>();
		
		for(i=0;i<(n*n);i++)
		{
			list1.add(i);
		}

		Collections.shuffle(list1);
		
		/*************************************************************/
		
		/**Data structures to check if a row or a column or a diagonal is filled by the same player**/
		
		int count_row[] = new int[n];   
		int count_column[] = new int[n];
		int count_diagonol[] = new int[2];
		
		/****************************************************************************************/
		
		
		/********** Initially select a player randomly **********/
		
		boolean start_bool = rg1.nextBoolean();
		if(start_bool) //  random X condition
		{
			player = 2; // 2 denotes player X
			starter = 2;
			System.out.println("X goes first");
		}
		else // random O condition
		{
			player = 1; // 1 denotes a player O
			starter = 1;
			System.out.println("O goes first");
			
		}
		
		/********************************************************/
		
		
		for(i=0;i<(n*n);i++)
		{
			
			box = list1.get(i); // position which the player is going to play this time
			
			/****************** Result tracking ******************/
			
			row = row_tracking(player, box, n, count_row); 
			column = column_tracking(player, box, n, count_column);
			major_diagonal_tracking(player, box, n, count_diagonol);
			minor_diagonal_tracking(player, box, n, count_diagonol);
			
			/********************************************************/
			
			/** The condition checks if any row or column or diagonal is completely filled by the same player*/
			
			if(count_row[row] == n || count_column[column] == n || count_diagonol[0] == n || count_diagonol[1] == n || count_row[row] == -n || count_column[column] == -n || count_diagonol[0] == -n || count_diagonol[1] == -n)
			{
				win_flag = 1; // There is a winner for the game and the game has ended
				break;
			}
			/************************************************************************************************/
			
			/********** Assign player for next iteration **********/
			
			if(player == 1)
			{
				player = 2; // X is playing this iteration
			}
			else
			{
				player = 1; // O is playing this iteration
			}
		
		
			/******************************************************/
			
		}
		
		/******************** Print output *********************/
		
		print_output(starter, player, list1, n, i, win_flag);
		
		/********************************************************/
		
	}

/** The below function populates the result array to be printed and displays it on the screen ! 
 * It also prints who won the match based on the iteration the game ended **/

private static void print_output(int starter, int player, ArrayList<Integer> list1, int n, int i, int win_flag) {
	// TODO Auto-generated method stub
	
	char output[] = new char[n*n]; 
	store_output_array(starter, list1, n, i, output); // populates the boxes with the corresponding players
	print_output_array(n, output); // prints the final board
	if(win_flag == 1)
	{
		if(player == 2)
		{
			System.out.println("X won!");
		}
		else
		{
			System.out.println("O won!");
		}
	}
	else
	{
		System.out.println("It was a draw.");
	}
}

/** The below function prints the result on the screen! **/ 

private static void print_output_array(int n, char[] output) {
	int j;
	for(j=0;j<n*n;j++)
	{
		if((j+1) % n == 0)
		{
			System.out.println(output[j] + " ");
		}
		else 
		{
			System.out.print(output[j] + " ");
		}
	}
}

/** The below function populates the result array to be printed and displays it on the screen ! **/

private static void store_output_array(int starter, ArrayList<Integer> list1, int n, int i, char[] output) {
	int j;
	for(j=0;j<n*n;j++)
	{
		if(j>i)
		{
			output[list1.get(j)] = '#';
		}
		else if(starter % 2 == 0)
		{
			output[list1.get(j)] = 'X'; 
		}
		else
		{
			output[list1.get(j)] = 'O';
		}
		starter++;
	}
}


/** The below function checks if the current box is a "left" diagonal box. 
 * If it is, it increments the count_diagnol[left] for player X and decrements count_diagnol[left] for player O
 * @param player
 * @param box
 * @param n
 * @param count_diagonol
 */
private static void major_diagonal_tracking(int player, int box, int n, int[] count_diagonol) {
	if(player == 2 && box % (n+1) == 0)
	{
		count_diagonol[0]++;// Diagonal count incremented for corresponding row for X
	}
	else if(player == 1 && box % (n+1) == 0)
	{
		count_diagonol[0]--;// Diagonal count decremented for corresponding row for O
	}
}

/** The below function checks if the current box is a "right" diagonal box. 
 * If it is, it increments the count_diagnol[left] for player X and decrements count_diagnol[left] for player O
 * @param player
 * @param box
 * @param n
 * @param count_diagonol
 */

private static void minor_diagonal_tracking(int player, int box, int n, int[] count_diagonol) {
		
		if(box > 0 && box <= n *(n - 1) && box % (n-1) == 0)
		{	
			if(player == 2 )
			{
				count_diagonol[1]++;// Diagonal count incremented for corresponding row for X
			}
			else 
			{
				count_diagonol[1]--;// Diagonal count decremented for corresponding row for O
			}
		}
}

/** Finds the column 'c' the box belongs to
 * If it is player X, increment the count_column[c]
 * Else, decrement the count_column[c]
 */
private static int column_tracking(int player, int box, int n, int[] count_column) {
	int column = box % n;
	if(player == 2)
	{
		count_column[column]++;// Column count incremented for corresponding row for X
		return column;
	}
	else 
	{
		count_column[column]--;// Column count decremented for corresponding row for O
		return column;
	}
	
}

/** Finds the row 'r' the box belongs to
 * If it is player X, increment the count_row[r]
 * Else, decrement the count_row[r]
 */
private static int row_tracking(int player, int box, int n, int[] count_row) {
	int row = box / n;
	if(player == 2)
	{
		count_row[row]++;// Row count incremented for corresponding row for X
		return row;
	}
	else 
	{
		count_row[row]--;// Row count decremented for corresponding row for O
		return row;
	}
}



}
