import java.util.*;

import javax.swing.*;


/**
 * This class creates the RailRoad Space on the GameBoard.
 *
 * @author William Wang, Aditya Kotak, Rahul Sarathy.
 * @version May 23, 2015
 * @author Period: 6
 * @author Assignment: MonopolyGame
 *
 * @author Sources: none
 */
public class RailroadSpace extends Space
{
    private Player owner;

    private Scanner in = new Scanner( System.in );

    private int mortgage;


    /**
     * Constructs a RailRoad space to be used in the Monopoly Game.
     * 
     * @param g
     *            The Gameboard this railroad will be used for.
     * @param rname
     *            The name of this RailRoad space.
     * @param rloc
     *            The integer location of this RailRoad space on the gameboard.
     */
    public RailroadSpace(
        GameBoard g,
        String rname,
        int rloc,
        int x,
        int y,
        int o )
    {
        super( g, rloc, x, y, o );
        name = rname + " Railroad";
        buyable = true;
        canHaveBuildings = false;
        mortgage = 100;
        isRailroad = true;
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
     * Returns a player who is the owner of this RailRoad space.
     * 
     * @return owner
     */
    public Player getOwner()
    {
        return owner;
    }


    /**
     * A required method since RailroadSpace extends Space that has the abstract
     * method act. This method enables Player p to purchase this railroad if
     * desired. This method will check if the player has enough money purchase
     * this property. If the owner is already set, then the Player p will pay
     * the owner the rent of this property based on the formula for multiplying
     * 50 to the number of railroad spaces owned by the owner.
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
            if ( p.getMoney() >= 200 )
            {
                if ( p.isHuman )
                {
                    while ( !madeMove )
                    {
                        text.append( "You may buy this railroad! Price: $"
                            + 200 + "\n" );
                        text.append( "Please select an option:\n" );
                        text.append( "(1) Buy Railroad\n" );
                        text.append( "(2) Don't Buy Railroad\n" );
                        String ans = in.nextLine();
                        if ( ans.equals( "1" ) )
                        {
                            text.append( "Congrats you've bought this railroad\n" );
                            p.payBank( 200 );
                            p.buyRail( this );
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
                            text.append( "You did not buy this railroad.\n" );
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
                    p.payBank( 200 );
                    p.buyRail( this );
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
                text.append( "Sorry, you do not have enough money to buy this railroad.\n" );
            }
        }
        else
        {
            if ( !p.equals( getOwner() ) )
            {
                int pay = p.payPlayer( 50 * ( getOwner().getRailNum() ), owner );
                text.append( "You paid " + owner.getName() + " $" + pay + ".\n" );
            }
        }
    }


    /**
     * Sets a player who is the owner of this RailRoad space.
     * 
     * @param p
     *            the Player who owns this railroad
     */
    public void setOwner( Player p )
    {
        owner = p;
    }


    /**
     * Gets the mortgage amount of this railroad space
     * 
     * @return the mortgage value
     */
    public int getMortgage()
    {
        return mortgage;
    }


    /**
     * This method returns the price for railroads
     * 
     * @return the price
     */
    public int getPrice()
    {
        return 200;
    }
}
