import java.util.*;

import javax.swing.*;


/**
 * This class creates a PropertySpace for use in the Monopoly game. It has
 * numerous objects needed for a fully functioning Monopoly game.
 *
 * @author William Wang, Aditya Kotak, Rahul Sarathy.
 * @version May 23, 2015
 * @author Period: 6
 * @author Assignment: MonopolyGame
 *
 * @author Sources: none
 */
public class PropertySpace extends Space
{
    private int houseCount;

    private ArrayList<House> houses;

    private ArrayList<House> hotels;

    private int rentPrice;

    private int spacePrice;

    private int housePrice;

    private int mortgagePrice;

    private boolean hasHouses;

    private boolean hasHotel;

    private Player owner;

    private int group;

    private Scanner in = new Scanner( System.in );

    private JTextArea myText;

    private int numHouses, numHotels;


    /**
     * Constructs a PropertySpace.
     * 
     * @param g
     *            The GameBoard this PropertySpace uses to use GameBoard's
     *            methods.
     * @param pname
     *            The name of this property.
     * @param ploc
     *            The integer location of this property on a GameBoard
     *            LinkedList.
     * @param pgroup
     *            The group of this Property. This will correspond to the color
     *            groups known in the traditional monopoly.
     * @param rentBase
     *            The rent a player must pay to the owner when there are no
     *            houses built on this property.
     * @param space
     *            The rent a player must pay to the owner when there are no
     *            houses built on this space.
     * @param house
     *            The price to build a house on this property.
     * @param mortgage
     *            The amount of money the owner will receive if he or she
     *            chooses to mortgage this property.
     */
    public PropertySpace(
        GameBoard g,
        String pname,
        int ploc,
        int pgroup,
        int rentBase,
        int space,
        int house,
        int mortgage,
        int x,
        int y,
        int o )
    {
        super( g, ploc, x, y, o );
        name = pname + " Avenue";
        buyable = true;
        canHaveBuildings = true;
        group = pgroup;
        rentPrice = rentBase;
        spacePrice = space;
        housePrice = house;
        mortgagePrice = mortgage;
        hasHouses = false;
        houses = new ArrayList<House>();
        numHouses = 0;
        numHotels = 0;
        if ( myOrientation == 0 )
        {
            goodLocs[0][0] = myX;
            goodLocs[0][1] = myY;
            goodLocs[1][0] = myX + 24;
            goodLocs[1][1] = myY;
            goodLocs[2][0] = myX;
            goodLocs[2][1] = myY + 20;
            goodLocs[3][0] = myX + 24;
            goodLocs[3][1] = myY + 20;
            goodLocs[4][0] = myX;
            goodLocs[4][1] = myY + 40;
            goodLocs[5][0] = myX + 24;
            goodLocs[5][1] = myY + 40;
        }
        else if ( myOrientation == 1 )
        {
            goodLocs[0][0] = myX + 6;
            goodLocs[0][1] = myY;
            goodLocs[1][0] = myX + 30;
            goodLocs[1][1] = myY;
            goodLocs[2][0] = myX + 6;
            goodLocs[2][1] = myY + 16;
            goodLocs[3][0] = myX + 30;
            goodLocs[3][1] = myY + 16;
            goodLocs[4][0] = myX + 6;
            goodLocs[4][1] = myY + 32;
            goodLocs[5][0] = myX + 30;
            goodLocs[5][1] = myY + 32;
        }

    }


    /**
     * Gets the owner of this property.
     * 
     * @return the owner of this property
     */
    public Player getOwner()
    {
        return owner;
    }


    /**
     * A required method since ProeprtySpace extends Space that has the abstract
     * method act. This method enables Player p to purchase this space if
     * desired. This method will check if the player has enough money purchase
     * this property and also checks if this transaction means an entire block
     * of properties is owned by a single owner. If the owner is already set,
     * then the Player p will pay the owner the rent of this property based on
     * the getRent() method.
     * 
     * @param p
     *            The Player who lands on this property space.
     */
    public void act( Player p, JTextArea text )
    {
        findNextAvailable( p );
        myText = text;
        boolean madeMove = false;
        playersOnSpace.add( p );

        if ( getOwner() == null )
        {
            if ( p.getMoney() >= spacePrice )
            {
                if ( p.isHuman )
                {
                    while ( !madeMove )
                    {
                        text.append( "You may buy this property! Price: $"
                            + spacePrice + "\n" );
                        text.append( "Please select an option:\n" );
                        text.append( "(1) Buy Property\n" );
                        text.append( "(2) Don't Buy Property\n" );
                        String ans = in.nextLine();
                        if ( ans.equals( "1" ) )
                        {
                            text.append( "Congrats you've bought this property!\n" );
                            p.payBank( spacePrice );
                            p.buyProp( this );
                            setOwner( p );
                            text.append( "Money: $" + p.getMoney() + "\n" );
                            text.append( "Properties: " + p.getProperties()
                                + "\n" );
                            text.append( "Railroads: " + p.getRailroads()
                                + "\n" );
                            text.append( "Utilities: " + p.getUtilities()
                                + "\n" );
                            if ( p.hasAllPropsInGroup( this ) )
                            {
                                text.append( "You may now buy houses on this property block!\n" );
                                text.append( "These properties are now double rent!\n" );
                            }
                            madeMove = true;
                        }
                        else if ( ans.equals( "2" ) )
                        {
                            text.append( "You did not buy this property.\n" );
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
                    p.payBank( spacePrice );
                    p.buyProp( this );
                    setOwner( p );
                    text.append( "Money: $" + p.getMoney() + "\n" );
                    text.append( "Properties: " + p.getProperties() + "\n" );
                    text.append( "Railroads: " + p.getRailroads() + "\n" );
                    text.append( "Utilities: " + p.getUtilities() + "\n" );
                    if ( p.hasAllPropsInGroup( this ) )
                    {
                        text.append( "You may now buy houses on this property block!\n" );
                        text.append( "These properties are now double rent!\n" );
                    }
                    madeMove = true;
                }
            }
            else
            {
                text.append( "Sorry, you do not have enough money to buy this property.\n" );
            }
        }
        else
        {
            if ( !p.equals( getOwner() ) )
            {

                int pay = p.payPlayer( getRent( owner ), owner );
                text.append( "You paid " + owner.getName() + " $" + pay + ".\n" );
            }
        }
    }


    /**
     * Gets the house price for this PropertySpace.
     * 
     * @return housePrice
     */
    public int getHousePrice()
    {
        return housePrice;

    }


    /**
     * Adds a house to this property through the use of an ArrayList. Checks if
     * adding a house will exceed 5 houses and denies the player from buying a
     * house. If the house being bought is the 5th house on this PropertySpace,
     * this method will add a Hotel to this property.
     */
    public void addHouse()
    {
        houseCount++;
        numHouses++;
        if ( houseCount > 5 )
        {
            myText.append( "You already have the maximum amount of buildings on this property!\n" );
            houseCount--;
        }
        else if ( houseCount == 5 )
        {
            numHouses = 0;
            numHotels = 1;
            hasHotel = true;
            getOwner().setNumHouses( getOwner().getNumHouses() - 4 );
            getOwner().setNumHotels( getOwner().getNumHotels() + 1 );
            // add some code to implement hotels
        }
        else
        {
            houses.add( new House( houses.size(), housePrice ) );
            getOwner().setNumHouses( getOwner().getNumHouses() + 1 );
        }
        hasHouses = true;
    }


    /**
     * Removes a house on this PropertySpace. This method checks if the amount
     * of houses is five. If it is so, it will subtract the total number of
     * hotels of the owner by 1 and add 4 to the number of houses because a
     * hotel is merely 5 houses. If there are no houses on this property to
     * remove, it will tell the user that removing a house is not allowed.
     */
    public void removeHouse()
    {
        if ( houseCount == 5 )
        {
            numHotels = 0;
            hasHotel = false;
            getOwner().setNumHouses( getOwner().getNumHouses() + 4 );
            getOwner().setNumHotels( getOwner().getNumHotels() - 1 );
            houseCount--;
            numHouses = 4;
        }
        else if ( houseCount <= 0 )
        {
            myText.append( "There are no houses to sell.\n" );
        }
        else
        {
            houses.remove( 0 );
            getOwner().setNumHouses( getOwner().getNumHouses() - 1 );
            houseCount--;
            numHouses--;
        }
        if ( houseCount == 0 )
        {
            hasHouses = false;
        }
    }


    /**
     * Returns the number of houses on this PropertySpace in order to ensure the
     * limit does not get exceeded.
     * 
     * @return houseCount
     */
    public int getNumOfHouses()
    {
        return houseCount;
    }


    /**
     * Returns the original rent of this PropertySpace to be used in the main
     * getRent() method;
     * 
     * @return rentPrice
     */
    public int getOrgRent()
    {
        return rentPrice;
    }


    /**
     * Sets the owner of this PropertySpace to Player p
     * 
     * @param p
     *            The Player who has bought this PropertySpace
     */
    public void setOwner( Player p )
    {
        owner = p;
    }


    /**
     * Returns the group number of this PropertySpace which is equivalent of a
     * color from the original game.
     * 
     * @return group
     */
    public int getGroup()
    {
        return group;
    }


    /**
     * Gets the rent of this PropertySpace based on how many houses are bought
     * on this PropertySpace.
     * 
     * @param p
     *            The Player who owns this property.
     * @return An integer value representing the rent of this property.
     */
    public int getRent( Player p )
    {
        if ( p.hasAllPropsInGroup( this ) )
        {
            if ( houseCount == 0 )
            {
                return getOrgRent() * 2;
            }
            else if ( houseCount == 1 )
            {
                return ( getOrgRent() * 5 );
            }
            else if ( houseCount == 2 )
            {
                return getOrgRent() * 10;
            }
            else if ( houseCount == 3 )
            {
                return ( getOrgRent() * 20 );
            }
            else if ( houseCount == 4 )
            {
                return getOrgRent() * 25;
            }
            else
            {
                return getOrgRent() * 40;
            }
        }
        else
        {
            return getOrgRent();
        }

    }


    /**
     * Checks whether this PropertySpace has a hotel built on it.
     * 
     * @return hasHotel
     */
    public boolean hasHotel()
    {
        return hasHotel;
    }


    /**
     * Checks whether this PropertySpace has a house built on it.
     * 
     * @return hasHotel
     */
    public boolean hasHouses()
    {
        return hasHouses;
    }


    /**
     * Gets the mortgage value of this PropertySpace
     * 
     * @return the mortgage value of this property
     */
    public int getMortgage()
    {
        return mortgagePrice;
    }


    /**
     * This method gets the price of the property
     * 
     * @return the price of the property
     */
    public int getPrice()
    {
        return spacePrice;
    }


    /**
     * This method returns a string representation of a space
     * 
     * @return a string representation of a space
     */
    public String toString()
    {
        return super.toString() + " (" + numHouses + " houses, " + numHotels
            + " hotels)";
    }
}
