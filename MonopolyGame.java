import java.util.*;


/**
 * This is the main class of our Monopoly game. We use several data structures
 * and even more objects to create a comprehensive Monopoly game engine.
 *
 * @author William Wang, Aditya Kotak, Rahul Sarathy.
 * @version May 23, 2015
 * @author Period: 6
 * @author Assignment: MonopolyGame
 *
 * @author Sources: none
 */
public class MonopolyGame
{
    static Scanner scan;


    /**
     * The main method that runs the program.
     * 
     * @param args
     *            arguments
     */
    public static void main( String[] args )
    {
        MonopolyGUI gui = new MonopolyGUI();
    }
    // int numHumans = 2;
    // int numAI;
    //
    // scan = new Scanner( System.in );
    // System.out.println( "Welcome to Monopoly!" );
    // System.out.print( "Enter the number of human players (Max allowed: 6): "
    // );
    // numHumans = typeInt();
    // if ( numHumans > 6 || numHumans < 0 )
    // {
    // boolean madeMove = false;
    // while ( !madeMove )
    // {
    // System.out.println( "That is not an acceptable amount. Please try again."
    // );
    // numHumans = typeInt();
    // if ( numHumans <= 6 && numHumans > 0 )
    // {
    // madeMove = true;
    // }
    //
    // }
    // }
    // if ( numHumans != 6 )
    // {
    // System.out.print( "Enter the number of AI players (Max allowed: "
    // + ( 6 - numHumans ) + "): " );
    // numAI = typeInt();
    // if ( numAI > 6 - numHumans || ( numHumans + numAI == 1 ) || numAI < 0)
    // {
    // boolean madeMove = false;
    // while ( !madeMove )
    // {
    // System.out.println( "That is not an acceptable amount. Please try again."
    // );
    // numAI = typeInt();
    // if ( numAI <= 6 - numHumans && numAI > 0 )
    // {
    // madeMove = true;
    // }
    //
    // }
    // }
    // }
    // else
    // {
    // numAI = 0;
    // }
    // GameBoard g = new GameBoard( scan, numHumans, numAI );
    // g.startGame();
    // }
    //
    //
    // /**
    // * This method ensures that anything typed in is only an integer
    // *
    // * @return an integer
    // */
    // public static int typeInt()
    // {
    // boolean Move = false;
    // int num = -1;
    // while ( !Move )
    // {
    // String x = scan.nextLine();
    // try
    // {
    // num = Integer.parseInt( x );
    // Move = true;
    // }
    // catch ( Exception e )
    // {
    // System.out.println( "Please enter an integer." );
    // }
    // }
    // return num;
    // }
}
