import javax.swing.*;


/**
 * This class creates a taxspace to be used in the gameboard
 *
 * @author William Wang, Aditya Kotak, Rahul Sarathy.
 * @version May 23, 2015
 * @author Period: 6
 * @author Assignment: MonopolyGame
 *
 * @author Sources: none
 */
public class TaxSpace extends Space
{
    private int myPay;


    /**
     * This constructs the tax space
     * 
     * @param g
     *            the gameboard in which this taxspace will be used in
     * @param tname
     *            the name of this tax space
     * @param tloc
     *            the integer location of this taxspace
     * @param pay
     *            the amount of money needed to be paid in this space
     */
    public TaxSpace( GameBoard g, String tname, int tloc, int pay, int x, int y, int o )
    {
        super( g, tloc, x, y, o );
        name = tname + " Tax";
        loc = tloc;
        buyable = false;
        canHaveBuildings = false;
        myPay = pay;
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
     * This method gets the amount of money needed to be paid if landed on this
     * taxspace
     * 
     * @return the amount of money to be paid
     */
    public int payTax()
    {
        return myPay;
    }


    /**
     * This method makes the player pay if landed on this space
     * 
     * @param p
     *            the player on this space
     */
    public void act( Player p, JTextArea text )
    {
        findNextAvailable( p );
        playersOnSpace.add( p );
        p.payBank( myPay );
        text.append( "You paid a $" + myPay + " tax!\n" );
        text.append( "Money: $" + p.getMoney() + "\n" );
        text.append( "Properties: " + p.getProperties() + "\n" );
        text.append( "RailRoads: " + p.getRailroads() + "\n" );
        text.append( "Utilities: " + p.getUtilities() + "\n" );
    }

}
