import java.util.ArrayList;

import javax.swing.JTextArea;


/**
 * This class creates a Go space which is the first space on the GameBoard.
 *
 * @author William Wang, Aditya Kotak, Rahul Sarathy.
 * @version May 23, 2015
 * @author Period: 6
 * @author Assignment: MonopolyGame
 *
 * @author Sources: none
 */
public class GoSpace extends Space
{
    private Bank b;


    /**
     * This constructs the Go space on the GameBoard.
     * 
     * @param g
     *            the game board that has this go space
     */
    public GoSpace( GameBoard g, int x, int y, int o )
    {
        super( g, 0, x, y, o );
        name = "Go";
        buyable = false;
        canHaveBuildings = false;
        b = g.getBank();
        goodLocs[0][0] = myX;
        goodLocs[0][1] = myY;
        goodLocs[1][0] = myX + 24;
        goodLocs[1][1] = myY;
        
        goodLocs[2][0] = myX;
        goodLocs[2][1] = myY + 15;
        goodLocs[3][0] = myX + 24;
        goodLocs[3][1] = myY + 15;
        
        goodLocs[4][0] = myX;
        goodLocs[4][1] = myY + 30;
        goodLocs[5][0] = myX + 24;
        goodLocs[5][1] = myY + 30;
    }


    /**
     * This method prints out when a player is on this go space. It does not pay
     * a player because that is done in the player move method.
     * 
     * @param p
     *            the player on this go space
     */
    public void act( Player p, JTextArea text )
    {
        findNextAvailable( p );
        playersOnSpace.add( p );
    }
}
