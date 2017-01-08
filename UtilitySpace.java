import java.util.*;

import javax.swing.*;


/**
 * This class creates the UtilitySpace object for the Monopoly game.
 *
 * @author William Wang, Aditya Kotak, Rahul Sarathy.
 * @version May 23, 2015
 * @author Period: 6
 * @author Assignment: MonopolyGame
 *
 * @author Sources: none
 */
public class UtilitySpace extends Space
{
    private Player owner;

    private Scanner in = new Scanner( System.in );

    private int mortgage;


    /**
     * Constructs the UtilitySpace to be used in the Monopoly game.
     * 
     * @param g
     *            The gameboard for which UtilitySpace is for.
     * @param uname
     *            The name of this UtilitySpace.
     * @param uloc
     *            The integer location of this UtilitySpace on the gameboard.
     */
    public UtilitySpace(
        GameBoard g,
        String uname,
        int uloc,
        int x,
        int y,
        int o )
    {
        super( g, uloc, x, y, o );
        name = uname + " Utility";
        buyable = true;
        canHaveBuildings = false;
        mortgage = 75;
        isUtility = true;
        if ( myOrientation == 0 )
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
        else if ( myOrientation == 1 )
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
     * Returns the player who is the owner of this UtilitySpace.
     * 
     * @return owner
     */
    public Player getOwner()
    {
        return owner;
    }


    /**
     * A required method since UtilitySpace extends Space that has the abstract
     * method act. This method enables Player p to purchase this utility if
     * desired. This method will check if the player has enough money purchase
     * this utility. If the owner is already set, then the Player p will pay the
     * owner the rent of this property based on the formula for multiplying
     * either 4 or 10 to the number rolled by the player to land on this
     * UtilitySpace depending on how many Utilities the owner owns.
     * 
     * @param p
     *            The Player who lands on this railroad space.
     */
    public void act( Player p, JTextArea text )
    {
        findNextAvailable( p );
        boolean madeMove = false;
        playersOnSpace.add( p );

        if ( getOwner() == null )
        {
            if ( p.getMoney() >= 150 )
            {
                if ( p.isHuman )
                {
                    while ( !madeMove )
                    {
                        text.append( "You may buy this utility! Price: $" + 150
                            + "\n" );
                        text.append( "Please select an option:\n" );
                        text.append( "(1) Buy Utility\n" );
                        text.append( "(2) Don't Buy Utility\n" );
                        String ans = in.nextLine();
                        if ( ans.equals( "1" ) )
                        {
                            text.append( "Congrats you've bought this utility.\n" );
                            p.payBank( 150 );
                            p.buyUtil( this );
                            setOwner( p );
                            text.append( "Money: $" + p.getMoney() + "\n" );
                            text.append( "Properties: " + p.getProperties()
                                + "\n" );
                            text.append( "Railroads: " + p.getRailroads()
                                + "\n" );
                            text.append( "Utilities: " + p.getUtilities()
                                + "\n" );
                            madeMove = true;
                        }
                        else if ( ans.equals( "2" ) )
                        {
                            text.append( "You did not buy this utility.\n" );
                            madeMove = true;
                        }
                        // else
                        // {
                        // System.out.println(
                        // "Sorry, that is not a valid option. Please try again."
                        // );
                        // }
                    }
                }
                else
                {
                    text.append( p.getName() + " bought " + getName() + ".\n" );
                    p.payBank( 150 );
                    p.buyUtil( this );
                    setOwner( p );
                    text.append( "Money: $" + p.getMoney() + "\n" );
                    text.append( "Properties: " + p.getProperties() + "\n" );
                    text.append( "Railroads: " + p.getRailroads() + "\n" );
                    text.append( "Utilities: " + p.getUtilities() + "\n" );
                    madeMove = true;
                }

            }
            else
            {
                text.append( "Sorry, you do not have enough money to buy this utility.\n" );
            }
        }
        else
        {
            if ( !p.equals( getOwner() ) )
            {
                if ( getOwner().getUtilNum() == 1 )
                {
                    int pay = p.payPlayer( p.getMoveAmount() * 4, owner );
                    text.append( "You paid " + owner.getName() + " $" + pay
                        + ".\n" );
                }
                else if ( getOwner().getUtilNum() == 2 )
                {
                    int pay = p.payPlayer( p.getMoveAmount() * 10, owner );
                    text.append( "You paid " + owner.getName() + " $" + pay
                        + ".\n" );
                }
            }
        }
    }


    /**
     * Sets the owner of this UtilitySpace to Player p
     * 
     * @param p
     *            The Player who owns this UtilitySpace
     */
    public void setOwner( Player p )
    {
        owner = p;
    }


    /**
     * Gets the mortgage value of this utilityspace
     * 
     * @return the mortgage value of this utilityspace
     */
    public int getMortgage()
    {
        return mortgage;
    }


    /**
     * This method gets the price for utilities
     * 
     * @return the price
     */
    public int getPrice()
    {
        return 150;
    }
}
