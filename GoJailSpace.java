import javax.swing.*;

/**
 * This class creates the goJailSpace to be implemented in GameBoard.
 *
 * @author William Wang, Aditya Kotak, Rahul Sarathy.
 * @version May 23, 2015
 * @author Period: 6
 * @author Assignment: MonopolyGame
 *
 * @author Sources: none
 */
public class GoJailSpace extends Space
{
    /**
     * This constructs the go to jail space on GameBoard.
     * 
     * @param g
     *            the GameBoard that will have the go to jail space
     */
    public GoJailSpace( GameBoard g, int x, int y, int o )
    {
        super( g, 30, x, y, o );
        name = "Go to Jail";
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
     * This moves a player to jail. It will subtract $200 from the player
     * because the player does not get the money it will collect once it passes
     * go.
     * 
     * @param p
     *            the player moving to jail
     */
    public void act( Player p, JTextArea text )
    {
        g.move( p, g.getJail() );
        p.setInJail( true );
//        p.payBank( 200 );
        text.append( "You have been sent to jail! You will not collect the $200 if you pass Go.\n" );
    }
}
