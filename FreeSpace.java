import javax.swing.*;

/**
 * This class creates a free parking space on GameBoard.
 *
 * @author William Wang, Aditya Kotak, Rahul Sarathy.
 * @version May 23, 2015
 * @author Period: 6
 * @author Assignment: MonopolyGame
 *
 * @author Sources: none
 */
public class FreeSpace extends Space
{
    /**
     * This constructs the free parking space on GameBoard.
     * 
     * @param g
     *            the GameBoard in which this free parking space will be created
     */
    public FreeSpace( GameBoard g, int x, int y, int o)
    {
        super( g, 20, x, y, o );
        name = "Free Parking";
        buyable = false;
        canHaveBuildings = false;
        goodLocs[0][0] = myX + 10;
        goodLocs[0][1] = myY;
        goodLocs[1][0] = myX + 34;
        goodLocs[1][1] = myY;
        goodLocs[2][0] = myX + 10;
        goodLocs[2][1] = myY + 26;
        goodLocs[3][0] = myX + 34;
        goodLocs[3][1] = myY + 26;
        goodLocs[4][0] = myX + 10;
        goodLocs[4][1] = myY + 37;
        goodLocs[5][0] = myX + 34;
        goodLocs[5][1] = myY + 37;
    }


    /**
     * This method does nothing because nothing is supposed to happen on the
     * free parking space.
     * 
     * @param p
     *            the player on this free parking space
     */
    public void act( Player p, JTextArea text )
    {
        findNextAvailable( p );
        playersOnSpace.add( p );
    }

}
