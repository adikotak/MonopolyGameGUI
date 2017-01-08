import java.util.*;

import javax.swing.*;


/**
 * This class creates a Player who will play this Monopoly game.
 *
 * @author William Wang, Aditya Kotak, Rahul Sarathy.
 * @version May 23, 2015
 * @author Period: 6
 * @author Assignment: MonopolyGame
 *
 * @author Sources: none
 */
public class Player
{
    private String name;

    private GameBoard myGame;

    private int cash;

    private boolean hasJailFree, inJail;

    private ArrayList<PropertySpace> properties;

    private ArrayList<RailroadSpace> rrspace;

    private ArrayList<UtilitySpace> uspace;

    private int numHouses, numHotels;

    private int position;

    private int prevPos;

    private int numMoved;

    private ListNode2<Space> currSpace;

    private Bank b;

    private int[] groupSize = { 2, 3, 3, 3, 3, 3, 3, 2 };

    private Scanner in = new Scanner( System.in );

    private PropertySpace temp;

    boolean isHuman;

    private JTextArea myText;

    ImageIcon mySym;

    boolean useJail;

    boolean canRollAgain;

    int myX, myY;


    /**
     * This constructor constructs the player object.
     * 
     * @param pname
     *            name for this Player (Player [number])
     * @param g
     *            the GameBoard this Player plays on
     */
    public Player( String pname, GameBoard g, JTextArea text, ImageIcon sym )
    {
        name = pname;
        myGame = g;
        b = g.getBank();
        cash = b.payPlayer( 1500, this );
        hasJailFree = false;
        inJail = false;
        properties = new ArrayList<PropertySpace>();
        rrspace = new ArrayList<RailroadSpace>();
        uspace = new ArrayList<UtilitySpace>();
        numHouses = 0;
        numHotels = 0;
        position = 0;
        prevPos = 0;
        currSpace = myGame.getBoard();
        isHuman = true;
        myText = text;
        mySym = sym;
        System.err.println( "Go X: " + myGame.getGo().getValue().myX );
        System.err.println( "Go Y: " + myGame.getGo().getValue().myY );
        System.err.println( myGame.getGo().getValue().getGoodLocs() );
        myGame.getGo().getValue().act( this, text );
    }


    /**
     * Rolls the die that will dictate how many steps this player moved.
     * 
     * @param d
     *            The die rolled to get the number of spaces needed to be moved.
     * @return the number the die rolled
     */
    public int rollDie( Die d )
    {
        d.roll();
        int dVal = d.getNumDots();
        return dVal;
    }


    /**
     * Sets the number needed to be moved by this Player
     * 
     * @param moved
     *            the integer value to be moved by this player
     */
    public void setMoveAmount( int moved )
    {
        numMoved = moved;
    }


    /**
     * This method gets the number of spaces moved by this player.
     * 
     * @return numMoved The steps moved by this player
     */
    public int getMoveAmount()
    {
        return numMoved;
    }


    /**
     * This method moves this player on the gameboard based on a certain number
     * of steps. If the player passes go, this player will be paid $200.
     * 
     * @param numOfSpaces
     *            the integer value for the number of spaces this player needs
     *            to be moved on the gameboard
     * @return currSpace the space where this player landed after moving
     *         numOfSpaces
     */
    public ListNode2<Space> move( int numOfSpaces )
    {
        prevPos = position;
        currSpace.getValue().getPlayerList().remove( this );
        position += numOfSpaces;
        if ( position >= myGame.BOARD_SIZE )
        {
            position -= myGame.BOARD_SIZE;
            if ( ( prevPos != myGame.getGoJail().getValue().getLoc() && ( numOfSpaces > 0 ) )
                && !getInJail() )
            {
                b.payPlayer( 200, this );
                myText.append( "You've been paid $200 for passing GO!\n" );
            }

        }
        ListNode2<Space> start = currSpace;

        for ( int i = 0; i < numOfSpaces; i++ )
        {
            ListNode2<Space> next = start.getNext();
            if ( next.getValue().getLoc() == position )
            {
                currSpace = start.getNext();
                break;
            }
            else
            {
                start = next;
            }
        }
        return currSpace;
    }


    /**
     * This method calls the "act" method of any given space by using the
     * getValue method of ListNode2<Space>.
     * 
     * @param s
     *            a ListNode2<Space> that will be used to call that space's act
     *            method.
     * @param text
     */
    public void spaceAction( ListNode2<Space> s, JTextArea text )
    {
        myText = text;
        Space sp = s.getValue();
        sp.act( this, text );
    }


    /**
     * Draws a chance card for this Player to execute.
     */
    public void drawChance()
    {
        Chance c = myGame.drawChance();
        myText.append( "You drew a Chance card!\n" );
        myText.append( c.toString() + "\n" );
        myText.append( "\n" );
        if ( c.getID() == 8 )
        {
            useJail = true;
        }
        c.use( this, c.getID(), myText );

    }


    /**
     * Draws a community chest card for this player to execut.
     */
    public void drawCC()
    {
        CommunityChest c = myGame.drawCC();
        myText.append( "You drew a Community Chest card!\n" );
        myText.append( c.toString() + "\n" );
        myText.append( "\n" );
        if ( c.getID() == 5 )
        {
            useJail = true;
        }
        c.use( this, c.getID(), myText );
    }


    /**
     * This method buys a property for this Player.
     * 
     * @param s
     *            the PropertySpace being bought by this player.
     */
    public void buyProp( PropertySpace s )
    {
        properties.add( s );
    }


    /**
     * This method gets the number of properties owned by this player.
     * 
     * @return the number of properties owned by this player.
     */
    public int getPropNum()
    {
        return properties.size();
    }


    /**
     * This method buys a railroad space for this Player.
     * 
     * @param s
     *            the RailRoad Space being bought by this player.
     */
    public void buyRail( RailroadSpace s )
    {
        rrspace.add( s );
    }


    /**
     * This method gets the number of railroads owned by this player.
     * 
     * @return the number of railroads owned by this player.
     */
    public int getRailNum()
    {
        return rrspace.size();
    }


    /**
     * This method buys a utility space for this Player.
     * 
     * @param s
     *            the Utility Space being bought by this player.
     */
    public void buyUtil( UtilitySpace s )
    {
        uspace.add( s );
    }


    /**
     * This method gets the number of utilities owned by this player.
     * 
     * @return the number of utilities owned by this player.
     */
    public int getUtilNum()
    {
        return uspace.size();
    }


    /**
     * Part of the mortgage method, this method sells a building owned by this
     * player.
     * 
     * @param pr
     *            the PropertySpace where a building will be removed from.
     */
    public void sellBuilding( PropertySpace pr )
    {
        pr.removeHouse();
        b.payPlayer( pr.getHousePrice() / 2, this );
    }


    /**
     * Part of the mortgage method, this method sells a property owned by this
     * player.
     * 
     * @param p
     *            The player who is the owner of the property
     * @param pr
     *            The property to be sold.
     */
    public void sellProperty( Player p, PropertySpace pr )
    {
        p.getProperties().remove( pr );
        pr.setOwner( null );
        b.payPlayer( pr.getMortgage(), p );
    }


    /**
     * Part of the mortgage method, this method sells a railroad owned by this
     * player.
     * 
     * @param p
     *            The player who is the owner of the railroad space
     * @param r
     *            The railroad to be sold
     */
    public void sellRailroad( Player p, RailroadSpace r )
    {
        p.getRailroads().remove( r );
        r.setOwner( null );
        b.payPlayer( r.getMortgage(), p );
    }


    /**
     * Part of the mortgage method, this method sells a utility owned by this
     * player.
     * 
     * @param p
     *            The player who is the owner of the utility space
     * @param u
     *            The utility space to be sold
     */
    private void sellUtility( Player p, UtilitySpace u )
    {
        p.getUtilities().remove( u );
        u.setOwner( null );
        b.payPlayer( u.getMortgage(), p );

    }


    /**
     * This method will sell hotels, then houses, then utilities, then
     * railroads, and then properties until this player has non negative money.
     * 
     * @param p
     *            the Player who will have it's assets sold.
     * @param properties
     *            the properties of this player
     * @param railroads
     *            the railroads of this player
     * @param utilities
     *            the utilities of this player
     * @return true if selling the assets has brought the player to positive
     *         amount of money, false if not.
     */
    public boolean mortgage(
        Player p,
        ArrayList<PropertySpace> properties,
        ArrayList<RailroadSpace> railroads,
        ArrayList<UtilitySpace> utilities )
    {
        for ( int i = 0; i < getProperties().size(); i++ )
        {
            if ( getProperties().get( i ).hasHotel() )
            {
                p.sellBuilding( getProperties().get( i ) );
                myText.append( "Sold hotel on "
                    + getProperties().get( i ).getName() + "\n" );
                myText.append( "Money: $" + p.getMoney() + "\n" );
                if ( p.getMoney() > 0 )
                {
                    return true;
                }
            }
        }
        for ( int i = 0; i < getProperties().size(); i++ )
        {
            while ( getProperties().get( i ).hasHouses() )
            {
                p.sellBuilding( getProperties().get( i ) );
                myText.append( "Sold house on "
                    + getProperties().get( i ).getName() + "\n" );
                myText.append( "Money: $" + p.getMoney() + "\n" );
                if ( p.getMoney() > 0 )
                {
                    return true;
                }
            }
        }
        for ( int i = 0; i < p.getUtilNum(); i++ )
        {
            myText.append( "Sold " + p.getUtilities().get( i ).getName() + "\n" );
            p.sellUtility( this, p.getUtilities().get( i ) );
            i--;
            myText.append( "Money: $" + p.getMoney() + "\n" );
            if ( p.getMoney() > 0 )
            {
                return true;
            }
        }
        for ( int i = 0; i < p.getRailNum(); i++ )
        {
            myText.append( "Sold " + p.getRailroads().get( i ).getName() + "\n" );
            p.sellRailroad( this, p.getRailroads().get( i ) );
            i--;
            myText.append( "Money: $" + p.getMoney() + "\n" );
            if ( p.getMoney() > 0 )
            {
                return true;
            }
        }
        for ( int i = 0; i < p.getPropNum(); i++ )
        {
            myText.append( "Sold " + p.getProperties().get( i ).getName()
                + "\n" );
            p.sellProperty( this, p.getProperties().get( i ) );
            i--;
            myText.append( "Money: $" + p.getMoney() + "\n" );
            if ( p.getMoney() > 0 )
            {
                return true;
            }
        }
        return false;
    }


    /**
     * This method adds money to this player's total money
     * 
     * @param money
     *            the amount of money to be added to this player's total money
     * @return the total money of this player after adding the money from the
     *         param money
     */
    public int receiveMoney( int money )
    {
        cash += money;
        return cash;
    }


    /**
     * This method pays another player using this player's money.
     * 
     * @param money
     *            The amount of money to be given to the other player.
     * @param p
     *            the Player who this player will pay.
     * @return The total money left for this player.
     */
    public int payPlayer( int money, Player p )
    {
        cash -= money;
        p.receiveMoney( money );
        return money;
    }


    /**
     * This method removes money from this player's total cash.
     * 
     * @param money
     *            the amount of money to be removed.
     */
    public void payBank( int money )
    {
        cash -= money;
    }


    /**
     * This method gets the current position of this player
     * 
     * @return The integer position of this player on the gameboard
     */
    public int getPos()
    {
        return position;
    }


    /**
     * This method gets the previous position of this player.
     * 
     * @return The integer value of this player's previous position on the
     *         gameboard
     */
    public int getPrevPos()
    {
        return prevPos;
    }


    /**
     * This method either sets this player's standing in jail or removes this
     * person's standing in jail
     * 
     * @param isInJail
     *            a boolean condition that makes the inJail boolean variable to
     *            its value
     */
    public void setInJail( boolean isInJail )
    {
        inJail = isInJail;
    }


    /**
     * Gets whether or not this player is in jail.
     * 
     * @return inJail the boolean variable that is true if this player is in
     *         jail.
     */
    public boolean getInJail()
    {
        return inJail;
    }


    /**
     * Returns whether or not this player has a get out of jail free card
     * 
     * @return hasJailFree the boolean variable that is true if this player has
     *         a get out of jail free card
     */
    public boolean getHasJailFree()
    {
        return hasJailFree;
    }


    /**
     * This method gets the current space of this player
     * 
     * @return currSpace which is the current space of this player
     */
    public ListNode2<Space> getCurrSpace()
    {
        return currSpace;
    }


    /**
     * Gets the name of this player
     * 
     * @return the player's name
     */
    public String getName()
    {
        return name;
    }


    /**
     * Sets whether or not this player has a get out of jail free card
     * 
     * @param hasCard
     *            a boolean variable for if this player has a get out of jail
     *            free card
     */
    public void setHasJailFree( boolean hasCard )
    {
        hasJailFree = hasCard;
    }


    /**
     * Gets the amount of money this player has.
     * 
     * @return the amount of money this player has
     */
    public int getMoney()
    {
        return cash;
    }


    /**
     * This method gets all the properties of this player.
     * 
     * @return the properties owned by this player
     */
    public ArrayList<PropertySpace> getProperties()
    {
        return properties;
    }


    /**
     * This method gets all the railroads of this player.
     * 
     * @return the railroads owned by this player
     */
    public ArrayList<RailroadSpace> getRailroads()
    {
        return rrspace;
    }


    /**
     * This method gets all the utilities of this player.
     * 
     * @return the utilities owned by this player
     */
    public ArrayList<UtilitySpace> getUtilities()
    {
        return uspace;
    }


    /**
     * This method checks whether this player has all properties in a specifics
     * property group.
     * 
     * @param propertySpace
     *            the property to be used to identify the property group
     * @return true if this player owns all the properties of the specified
     *         property group
     */
    public boolean hasAllPropsInGroup( PropertySpace propertySpace )
    {
        int group = propertySpace.getGroup();
        int gSize = groupSize[group];
        int count = 0;
        for ( int i = 0; i < properties.size(); i++ )
        {
            if ( properties.get( i ).getGroup() == group )
            {
                count++;
            }
        }
        return gSize == count;
    }


    /**
     * Checks through all the properties of this player to return true or false
     * if a specified property is owned by this player.
     * 
     * @param s
     *            The property in question
     * @return true is this player owns the property
     */
    public boolean hasProp( PropertySpace s )
    {
        for ( PropertySpace i : properties )
        {
            if ( i.equals( s ) )
            {
                return true;
            }
        }
        return false;
    }


    /**
     * This method gets the number of houses this player owns.
     * 
     * @return The number of houses this player owns
     */
    public int getNumHouses()
    {
        return numHouses;
    }


    /**
     * This method gets the number of hotels this player owns.
     * 
     * @return The number of hotels this player owns
     */
    public int getNumHotels()
    {
        return numHotels;
    }


    /**
     * This method sets the number of houses this player owns to a specified
     * number.
     * 
     * @param num
     *            the number of houses this player should own
     */
    public void setNumHouses( int num )
    {
        numHouses = num;
    }


    /**
     * This method sets the number of hotels this player owns to a specified
     * number.
     * 
     * @param num
     *            the number of hotels this player should own
     */
    public void setNumHotels( int num )
    {
        numHotels = num;
    }


    /**
     * This method gets the current property space of this player
     * 
     * @return the current property space of this player
     */
    public PropertySpace getCurProperty()
    {
        return (PropertySpace)currSpace.getValue();
    }


    /**
     * This method checks that if all properties in a specified property group
     * has four houses
     * 
     * @param propertySpace
     *            the propertyspace that will be used for the property group
     * @return true if all properties in the specified group have 4 houses.
     */
    public boolean allPropsInGroupHave4Houses( PropertySpace propertySpace )
    {
        int group = propertySpace.getGroup();
        int gSize = groupSize[group];
        int count = 0;
        for ( int i = 0; i < properties.size(); i++ )
        {
            if ( properties.get( i ).getGroup() == group )
            {
                if ( properties.get( i ).getNumOfHouses() == 4
                    || properties.get( i ).hasHotel() )
                {
                    count++;
                }
            }
        }
        return gSize == count;
    }


    /**
     * This method will buy ask the user if he or she wants to buy a house on a
     * specified property. Allows the user to quit buying a house if he or she
     * types in "quit." This method will check if the player is allowed to buy a
     * house on the property. If he or she is allowed to do so, this method will
     * prompt the user to choose the number of houses to be added. If the number
     * will cause the total number to exceed 4 or if the number itself is 0 or
     * less, this method will prompt the user to try again.
     */
    public void buyHouse()
    {
        if ( isHuman )
        {
            String prop = "";
            System.out.println( "Please type the name of the property you would like to buy a house for." );
            System.out.println( "If you do not want to buy a house, type in \"quit\"" );
            boolean move = false;
            while ( !move )
            {
                prop = in.nextLine();
                if ( prop.equals( "quit" ) || prop.equals( "Quit" ) )
                {
                    move = true;
                    break;
                }
                temp = (PropertySpace)( myGame.getProperty( prop ).getValue() );
                if ( !hasAllPropsInGroup( temp ) || !hasProp( temp ) )
                {
                    System.out.println( "Sorry, you cannot buy a house here. Try again." );
                }
                else if ( allPropsInGroupHave4Houses( temp ) )
                {
                    System.out.println( "You must buy a hotel here!" );
                    move = true;
                    break;
                }
                else
                {
                    boolean anotherMove = false;
                    int num = typeInt( temp );
                    if ( num == -10 )
                    {
                        anotherMove = true;
                    }
                    while ( !anotherMove )
                    {
                        if ( num + temp.getNumOfHouses() > 4 || num < 1 )
                        {
                            System.out.println( "Sorry that isn't allowed" );
                            num = typeInt( temp );
                            if ( num == -10 )
                            {
                                anotherMove = true;
                                break;
                            }
                        }
                        else
                        {
                            if ( getMoney() < num * temp.getHousePrice() )
                            {
                                System.out.println( "Sorry you do not have enough money to buy "
                                    + num + " house(s)." );
                                num = typeInt( temp );
                                if ( num == -10 )
                                {
                                    anotherMove = true;
                                    break;
                                }
                            }
                            else
                            {
                                System.out.println( "This will cost you: $"
                                    + ( num * temp.getHousePrice() ) );
                                anotherMove = true;
                                break;
                            }
                        }

                    }
                    if ( num > 0 )
                    {
                        payBank( ( temp.getHousePrice() * num ) );
                        System.out.println( "Congrats! You bought " + num
                            + " house(s) on: " + temp );
                        for ( int i = 0; i < num; i++ )
                        {
                            temp.addHouse();
                        }
                        System.out.println( "You now have: "
                            + temp.getNumOfHouses() + " houses on "
                            + temp.getName() );
                        if ( allPropsInGroupHave4Houses( temp ) )
                        {
                            System.out.println( "You may now buy hotels for this group!" );
                        }
                        System.out.println( "Money: $" + getMoney() );
                        move = true;
                    }
                    else
                    {
                        move = true;
                        break;
                    }
                }
            }
        }
        else
        {
            temp = (PropertySpace)getCurrSpace().getValue();
            payBank( ( temp.getHousePrice() ) );
            temp.addHouse();
            if ( allPropsInGroupHave4Houses( temp ) )
            {
                System.out.println( "You may now buy hotels for this group!" );
                System.out.println( "Each hotel costs: $"
                    + temp.getHousePrice() );
            }
            System.out.println();
            System.out.println( "Money: $" + getMoney() );
        }
    }


    /**
     * This method buys a hotel for this player on a given property that this
     * player can choose. This method checks whether or not a hotel can be built
     * on the property based on whether the entire group has 4 houses on each
     * and also allows the user to quit buying a hotel if he or she chooses.
     */
    public void buyHotel()
    {
        System.out.println( "Please type the name of the property you would like to buy a hotel for." );
        System.out.println( "If you do not want to buy a hotel, type in \"quit\"" );
        boolean move = false;
        while ( !move )
        {
            String prop = in.nextLine();
            if ( prop.equals( "quit" ) || prop.equals( "Quit" ) )
            {
                move = true;
                break;
            }
            temp = (PropertySpace)( myGame.getProperty( prop ).getValue() );
            if ( !hasAllPropsInGroup( temp ) || !hasProp( temp )
                || !allPropsInGroupHave4Houses( temp ) || temp.hasHotel() )
            {
                System.out.println( "Sorry, you cannot buy a hotel here. Try again." );
            }
            else
            {
                if ( getMoney() >= temp.getHousePrice() )
                {
                    System.out.println( "Congrats! You bought a hotel on: "
                        + temp );
                    payBank( temp.getHousePrice() );
                    temp.addHouse();
                    System.out.println( "Money: $" + getMoney() );
                    move = true;
                }
                else
                {
                    System.out.println( "Sorry you do not have enough money to buy a hotel here." );
                    System.out.println( "Either quit or try another property." );
                }

            }
        }
    }


    /**
     * This method is used in buyHouse where the user must type in an integer
     * for the number of houses he or she would like to buy. It uses a try catch
     * exception to ensure any input is only an integer of the word "quit" which
     * will be used to quit buying a house.
     * 
     * @return an integer
     */
    public int typeInt( PropertySpace s )
    {
        boolean anotherMove = false;
        int num = -1;
        while ( !anotherMove )
        {
            System.out.println( "How many houses would you like to buy on this property?" );
            System.out.println( "Each house costs: $" + s.getHousePrice() );
            System.out.println( "If you do not want to buy a house, type in \"quit\"" );
            String x = in.nextLine();
            if ( x.equals( "quit" ) || x.equals( "Quit" ) )
            {
                return -10;
            }
            try
            {
                num = Integer.parseInt( x );
                anotherMove = true;
            }
            catch ( Exception e )
            {
                System.out.println( "Please enter an integer or quit." );
            }
        }
        return num;
    }


    public void run( JTextArea text )
    {
        myGame.altRollDie( this, text );
        text.append( "\n" );
        getCurrSpace().getValue().findNextAvailable( this );
        // if(!getCurrSpace().getValue().buyable)
        // {
        // spaceAction( getCurrSpace(), text );
        // }

    }


    /**
     * This method prints the end sequence
     * 
     * @param text
     *            the text field it will write to
     */
    public void end( JTextArea text )
    {
        myGame.endTurnSeq( this, text );

    }


    /**
     * This method calculates the net worth of the player
     * 
     * @return the net worth
     */
    public int calculateWin()
    {
        int win = 0;
        for ( PropertySpace p : properties )
        {
            win += p.getPrice() + p.getHousePrice() * p.getNumOfHouses();
        }
        for ( RailroadSpace r : rrspace )
        {
            win += r.getPrice();
        }
        for ( UtilitySpace u : uspace )
        {
            win += u.getPrice();
        }
        win += getMoney();
        return win;
    }


    /**
     * This method allows players to possibly roll again.
     * 
     * @param again
     *            a boolean condition, true if the player can roll again.
     */
    public void setRollAgain( boolean again )
    {
        canRollAgain = again;
    }


    /**
     * Gets the x value
     * 
     * @return x value
     */
    public int getX()
    {
        return myX;
    }


    /**
     * Set the x value
     * 
     * @param x
     *            value
     */
    public void setX( int x )
    {
        myX = x;
    }


    /**
     * Gets the y value
     * 
     * @return y value
     */
    public int getY()
    {
        return myY;
    }


    /**
     * Set the y value
     * 
     * @param y
     *            value
     */
    public void setY( int y )
    {
        myY = y;
    }


    /**
     * Gets the symbol of the player
     * 
     * @return the symbol
     */
    public ImageIcon getSymbol()
    {
        return mySym;
    }


    /**
     * Sets the symbol of the player
     * 
     * @param symbol
     *            the symbol
     */
    public void setSymbol( ImageIcon symbol )
    {
        mySym = symbol;
    }
}
