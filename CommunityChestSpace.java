import javax.swing.*;

/**
 * This class creates a community chest space that will be found on the physical
 * gameboard.
 *
 * @author William Wang, Aditya Kotak, Rahul Sarathy.
 * @version May 23, 2015
 * @author Period: 6
 * @author Assignment: MonopolyGame
 *
 * @author Sources: none
 */
public class CommunityChestSpace extends Space
{
    /**
     * This constructs a community chest space on the gameboard
     * 
     * @param g
     *            the gameboard this community chest space is to be built on
     * @param location
     *            the integer location in the gameboard
     */
    public CommunityChestSpace( GameBoard g, int location, int x, int y, int o )
    {
        super( g, location, x, y, o );
        name = "Community Chest";
        buyable = false;
        canHaveBuildings = false;
        if(myOrientation == 0)
        {
            goodLocs[0][0] = myX;
            goodLocs[0][1] = myY;
            goodLocs[1][0] = myX + 24;
            goodLocs[1][1] = myY;
            goodLocs[2][0] = myX;
            goodLocs[2][1] = myY + 26;
            goodLocs[3][0] = myX + 24;
            goodLocs[3][1] = myY + 26;
            goodLocs[4][0] = myX;
            goodLocs[4][1] = myY + 37;
            goodLocs[5][0] = myX + 24;
            goodLocs[5][1] = myY + 37;
        }
        else if (myOrientation == 1)
        {
            goodLocs[0][0] = myX + 10;
            goodLocs[0][1] = myY;
            goodLocs[1][0] = myX + 34;
            goodLocs[1][1] = myY;
            goodLocs[2][0] = myX + 10;
            goodLocs[2][1] = myY + 16;
            goodLocs[3][0] = myX + 34;
            goodLocs[3][1] = myY + 16;
            goodLocs[4][0] = myX + 10;
            goodLocs[4][1] = myY + 32;
            goodLocs[5][0] = myX + 34;
            goodLocs[5][1] = myY + 32;
        }
    }


    /**
     * This will call a player's draw community chest method which will execute
     * the method use in the community chest class.
     * 
     * @param p
     *            the player on this chance space
     */
    public void act( Player p, JTextArea text )
    {
        findNextAvailable( p );
        playersOnSpace.add( p );
        p.drawCC();
    }
}
