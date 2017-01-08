import java.util.*;

import javax.swing.JTextArea;


/**
 * This abstract class creates the framework for every space to be used in the
 * gameboard
 *
 * @author William Wang, Aditya Kotak, Rahul Sarathy.
 * @version May 23, 2015
 * @author Period: 6
 * @author Assignment: MonopolyGame
 *
 * @author Sources: none
 */
public abstract class Space
{
    String name;

    int loc;

    int myX, myY;

    boolean buyable;

    boolean canHaveBuildings;

    boolean isRailroad;

    boolean isUtility;

    ArrayList<Player> playersOnSpace;

    GameBoard g;

    int[][] goodLocs = new int[6][2];

    int myOrientation;


    /**
     * This constructs the space object
     * 
     * @param game
     *            the gameboard the space will be created on
     * @param sloc
     *            the integer location of the space
     */
    public Space(
        GameBoard game,
        int sloc,
        int coordX,
        int coordY,
        int orientation )
    {
        loc = sloc;
        g = game;
        myX = coordX;
        myY = coordY;
        myOrientation = orientation;
        playersOnSpace = new ArrayList<Player>();
    }


    /**
     * This method adds a player to this space using the arraylist of players on
     * this space
     * 
     * @param p
     *            the player on this space
     */
    public void addPlayer( Player p )
    {
        playersOnSpace.add( p );
    }


    /**
     * This method removes a player from this space using the arraylist of
     * players on this space
     * 
     * @param p
     *            the player on this space
     */
    public void removePlayer( Player p )
    {
        playersOnSpace.remove( playersOnSpace.indexOf( p ) );
    }


    /**
     * This method gets the name of this space
     * 
     * @return the name of this space
     */
    public String getName()
    {
        return name;

    }


    /**
     * This method gets the integer location of this space on the gameboard.
     * 
     * @return the integer location of this space on the gameboard
     */
    public int getLoc()
    {
        return loc;
    }


    /**
     * This method gets the total players on this space
     * 
     * @return all the players on this space
     */
    public ArrayList<Player> getPlayerList()
    {
        return playersOnSpace;
    }


    /**
     * This method offers the framework for the other spaces to use.
     * 
     * @param p
     *            the player on this space
     * @param text
     */
    public abstract void act( Player p, JTextArea text );


    /**
     * This method gets the name of this space
     * 
     * @return the name of this space
     */
    public String toString()
    {
        return getName();

    }


    /**
     * Checks if the space is a railroad
     * 
     * @return true if railroad
     */
    public boolean isRailroad()
    {
        return isRailroad;
    }


    /**
     * Checks if the space is a utility
     * 
     * @return true if utility
     */
    public boolean isUtility()
    {
        return isUtility;
    }


    /**
     * Gets the price for a space
     * 
     * @return the price
     */
    public int getPrice()
    {
        return 0;
    }


    /**
     * Gets the locations that are buyable
     * 
     * @return buyable locs
     */
    public int[][] getGoodLocs()
    {
        return goodLocs;
    }


    /**
     * Finds the next space available
     * 
     * @param p
     *            player moving
     */
    public void findNextAvailable( Player p )
    {
        for ( int i = 0; i < goodLocs.length; i++ )
        {
            int x = goodLocs[i][0];
            int y = goodLocs[i][1];
            if ( !playerHasLoc( x, y ) )
            {
                p.setX( x );
                p.setY( y );
                return;
            }
        }
    }


    /**
     * Checks if the player is at a given location
     * 
     * @param x
     *            x coordinate
     * @param y
     *            y coordinate
     * @return true if occupied
     */
    private boolean playerHasLoc( int x, int y )
    {
        for ( int i = 0; i < playersOnSpace.size(); i++ )
        {
            Player p = playersOnSpace.get( i );
            if ( p.getX() == x && p.getY() == y )
            {
                return true;
            }
        }
        return false;
    }
}
