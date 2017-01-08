import java.awt.event.*;
import java.util.*;

import javax.swing.*;


/**
 * 
 * This class is the main engine of the monopoly game. It uses all the objects
 * from our code and various data structures to run the game.
 *
 * @author William Wang, Aditya Kotak, Rahul Sarathy.
 * @version May 23, 2015
 * @author Period: 6
 * @author Assignment: MonopolyGame
 *
 * @author Sources: none
 */
public class GameBoard
{
    private Player currPlayer;

    private Bank bank;

    private Scanner scan;

    private Scanner pause = new Scanner( System.in );

    private Queue<Player> players;

    // Dies on board for rolling
    private Die d1, d2;

    // Storage of Chance Cards
    private LinkedList<Chance> ch;

    // Storage of Community Chest Cards
    private LinkedList<CommunityChest> cc;

    private int d1Val, d2Val;

    final int BOARD_SIZE = 40;

    private int numOfDoubles = 0;

    private ListNode2<Space> go;

    private ListNode2<Space> jail;

    private ListNode2<Space> goJail;

    private int[] boardArr = { 1, 2, 1, 3, 4, 1, 5, 1, 1, 6, 1, 7, 1, 1, 4, 1,
        2, 1, 1, 8, 1, 5, 1, 1, 4, 1, 1, 7, 1, 9, 1, 1, 2, 1, 4, 5, 1, 3, 1 };

    boolean checkHouse;

    boolean checkHotel;

    // List of Property Names
    private String[] propNames = { "Mediterranean", "Baltic", "Oriental",
        "Vermont", "Connecticut", "St. Charles", "States", "Virginia",
        "St. James", "Tennessee", "New York", "Kentucky", "Indiana",
        "Illinois", "Atlantic", "Ventnor", "Marvin Gardens", "Pacific",
        "North Carolina", "Pennsylvania", "Park", "Boardwalk" };

    // Different Types of Taxes
    private String[] taxNames = { "Income", "Luxury" };

    private int[] taxVals = { 200, 75 };

    // Different RailRoads
    private String[] railNames = { "Reading", "Pennsylvania", "B. & O.",
        "Short Line" };

    // Different Utility Spaces
    private String[] utilNames = { "Electric Company", "Water Works" };

    private int[] groupSize = { 2, 3, 3, 3, 3, 3, 3, 2 };

    private String[] chanceInfo = { "Advance to Go.",
        "Advance to Illinois Avenue.", "Advance to St. Charles Avenue.",
        "Advance to the nearest Utility.", "Advance to the nearest Railroad.",
        "Bank pays you dividend of $50", "Get Out of Jail Free Card",
        "Go back 3 spaces.", "Go to Jail.",
        "Pay $25 per house you own and $100 per hotel you own.",
        "Pay poor tax of $15.", "Take a trip to Reading Railroad.",
        "Take a walk on Boardwalk Avenue.", "Pay each player $50.",
        "Collect $150.", "Collect $100." };

    private String[] commInfo = { "Advance to Go.", "Collect $200.",
        "Pay $50.", "Collect $50.", "Get Out of Jail Free Card", "Go to Jail.",
        "Collect $50 from each player.", "Collect $100.", "Collect $20.",
        "Collect $10 from each player.", "Collect $125.", "Pay $100.",
        "Pay $150.", "Collect $25.",
        "Pay $40 per house you own and $115 per hotel you own.",
        "Collect $10.", "Collect $150." };

    private boolean canRollAgain = false;

    private JTextArea myText;

    // in jail loc: 24, 522
    private int[][] spaceLocs = { { 523, 522 }, { 473, 540 }, { 424, 522 },
        { 375, 540 }, { 327, 522 }, { 276, 522 }, { 227, 540 }, { 179, 522 },
        { 130, 540 }, { 80, 540 }, { 0, 522 }, { 0, 473 }, { 0, 424 },
        { 0, 374 }, { 0, 325 }, { 0, 275 }, { 0, 227 }, { 0, 177 }, { 0, 127 },
        { 0, 79 }, { 0, 0 }, { 80, 0 }, { 130, 0 }, { 179, 0 }, { 227, 0 },
        { 276, 0 }, { 327, 0 }, { 376, 0 }, { 424, 0 }, { 473, 0 }, { 523, 0 },
        { 540, 79 }, { 540, 127 }, { 523, 177 }, { 540, 227 }, { 523, 275 },
        { 523, 325 }, { 540, 374 }, { 523, 424 }, { 540, 473 } };

    private int[] orientation = { 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 1, 1, 1, 1, 1, 1, 1, 1,
        1 };


    /**
     * This constructs the gameboard and initialized several global variables
     * 
     * @param numOfHumanPlayers
     *            the players in this game
     * @param numOfComputers
     *            the computer players
     */
    public GameBoard(
        int numOfHumanPlayers,
        int numOfComputers,
        JTextArea text,
        ImageIcon[] symbols )
    {
        // instantiates dies
        d1 = new Die();
        d2 = new Die();
        // Players on board
        players = new LinkedList<Player>();
        // Instatiates players
        go = new ListNode2<Space>( new GoSpace( this,
            spaceLocs[0][0],
            spaceLocs[0][1],
            orientation[0] ), null, null );
        jail = createJail();
        goJail = createGoJail();
        bank = new Bank();
        for ( int i = 1; i <= numOfHumanPlayers; i++ )
        {
            Player p = new Player( "Player " + i, this, text, symbols[1 - 1] );
            players.add( p );
            go.getValue().addPlayer( p );
        }
        for ( int i = 1; i <= numOfComputers; i++ )
        {
            Player p = new AI( "Computer " + i, this, text, symbols[i - 1] );
            players.add( p );
            go.getValue().addPlayer( p );
        }

        ch = createChanceDeck();
        cc = createCCDeck();
        createBoard();
        myText = text;
    }


    /**
     * This method creates the gameboard for the monopoly game. It uses a basic
     * algorithm for calculating the prices for properties and will switch
     * between a property, railroad, community chest, tax, utility, chance, free
     * parking, jail, and a go to jail space to create the gameboard.
     */
    private void createBoard()
    {
        int ppos = 0;

        int rprice = 2;
        int sprice = 60;
        int hprice = 50;
        int mprice = 30;

        int group = 0;
        int side = 1;

        int pcount = 1;
        int tcount = 0;
        int rcount = 0;
        int ucount = 0;

        int posCount = 1;
        int or = 1;

        ListNode2<Space> node1 = go;
        ListNode2<Space> node2 = null;
        // iterates through the board and fills it out with different spaces to
        // replicate a monopoly board
        for ( int i = 0; i < boardArr.length; i++ )
        {

            int val = boardArr[i];
            switch ( val )
            {
                case 1:
                    node2 = createProperty( propNames[ppos],
                        i + 1,
                        group,
                        rprice,
                        sprice,
                        hprice * side,
                        mprice,
                        spaceLocs[posCount][0],
                        spaceLocs[posCount][1],
                        orientation[or] );
                    System.err.println( propNames[ppos] + " " + group );
                    if ( ppos == propNames.length - 1 )
                    {
                        node2.setNext( go );
                        go.setPrevious( node2 );
                    }
                    else
                    {
                        ppos++;
                    }
                    if ( pcount < groupSize[group] )
                    {
                        pcount++;
                    }
                    else
                    {
                        pcount = 1;
                        sprice += 40;
                        mprice += 20;
                        group++;
                        if ( group != 0 && group % 2 == 0 )
                        {
                            side++;
                        }
                    }
                    rprice += 2;
                    break;
                case 2:
                    node2 = createCC( i + 1,
                        spaceLocs[posCount][0],
                        spaceLocs[posCount][1],
                        orientation[or] );
                    break;
                case 3:
                    node2 = createTax( taxNames[tcount],
                        i + 1,
                        taxVals[tcount],
                        spaceLocs[posCount][0],
                        spaceLocs[posCount][1],
                        orientation[or] );
                    tcount++;
                    break;
                case 4:
                    node2 = createRailroad( railNames[rcount],
                        i + 1,
                        spaceLocs[posCount][0],
                        spaceLocs[posCount][1],
                        orientation[or] );
                    rcount++;
                    break;
                case 5:
                    node2 = createChance( i + 1,
                        spaceLocs[posCount][0],
                        spaceLocs[posCount][1],
                        orientation[or] );
                    break;
                case 6:
                    node2 = jail;
                    break;
                case 7:
                    node2 = createUtility( utilNames[ucount],
                        i + 1,
                        spaceLocs[posCount][0],
                        spaceLocs[posCount][1],
                        orientation[or] );
                    ucount++;
                    break;
                case 8:
                    node2 = createFree( spaceLocs[posCount][0],
                        spaceLocs[posCount][1],
                        orientation[or] );
                    break;
                case 9:
                    node2 = goJail;
                    break;
            }
            node1.setNext( node2 );
            node2.setPrevious( node1 );
            node1 = node2;
            posCount++;
        }
        ( (JailSpace)jail.getValue() ).setGoJail( goJail );
    }


    /**
     * This method creates a go to jail space.
     * 
     * @return a new go to jail space.
     */
    private ListNode2<Space> createGoJail()
    {
        return new ListNode2<Space>( new GoJailSpace( this,
            spaceLocs[30][0],
            spaceLocs[30][1],
            orientation[30] ), null, null );
    }


    /**
     * This method creates a free parking space
     * 
     * @param y
     * @param x
     * 
     * @return a new free parking space.
     */
    private ListNode2<Space> createFree( int x, int y, int o )
    {
        return new ListNode2<Space>( new FreeSpace( this, x, y, o ), null, null );
    }


    /**
     * This method creates a property space to be used in this gameboard
     * 
     * @param pname
     *            the name of the property
     * @param ploc
     *            the integer location of this property
     * @param pgroup
     *            the group of this property
     * @param rentBase
     *            the start rent of this property
     * @param space
     *            the amount of money a player must pay to buy this space
     * @param house
     *            the price to build a house on this property
     * @param mortgage
     *            the mortgage value of this property
     * @param y
     * @param x
     * @param o
     * @return the new property
     */
    private ListNode2<Space> createProperty(
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
        return new ListNode2<Space>( new PropertySpace( this,
            pname,
            ploc,
            pgroup,
            rentBase,
            space,
            house,
            mortgage,
            x,
            y,
            o ), null, null );
    }


    /**
     * This method creates a chance space
     * 
     * @param loc
     *            the integer location of this chance space
     * @param y
     * @param x
     * @return a new chance space.
     */
    private ListNode2<Space> createChance( int loc, int x, int y, int o )
    {
        return new ListNode2<Space>( new ChanceSpace( this, loc, x, y, o ),
            null,
            null );
    }


    /**
     * This method creates a utility space
     * 
     * @param loc
     *            the integer location of this community chest space
     * @param y
     * @param x
     * @return a new utility space.
     */
    private ListNode2<Space> createCC( int loc, int x, int y, int o )
    {
        return new ListNode2<Space>( new CommunityChestSpace( this,
            loc,
            x,
            y,
            o ), null, null );
    }


    /**
     * This method creates a tax space
     * 
     * @param name
     *            the name of this tax space
     * @param loc
     *            the integer location of this tax space
     * @param pay
     *            the amount needed to be paid at this tax space
     * @param y
     * @param x
     * @return a new tax space.
     */
    private ListNode2<Space> createTax(
        String name,
        int loc,
        int pay,
        int x,
        int y,
        int o )
    {
        return new ListNode2<Space>( new TaxSpace( this,
            name,
            loc,
            pay,
            x,
            y,
            o ), null, null );
    }


    /**
     * This method creates a railroad space
     * 
     * @param name
     *            the name of this railroad
     * @param loc
     *            the location of this railroad
     * @param y
     * @param x
     * @return a new railroad space.
     */
    private ListNode2<Space> createRailroad(
        String name,
        int loc,
        int x,
        int y,
        int o )
    {
        return new ListNode2<Space>( new RailroadSpace( this,
            name,
            loc,
            x,
            y,
            o ), null, null );
    }


    /**
     * This method creates a jail space
     * 
     * @return a new jail space.
     */
    private ListNode2<Space> createJail()
    {
        return new ListNode2<Space>( new JailSpace( this,
            myText,
            spaceLocs[10][0],
            spaceLocs[10][1],
            orientation[10] ), null, null );
    }


    /**
     * This method creates a utility space
     * 
     * @param name
     *            the name of the utility space
     * @param loc
     *            the integer location of this utility space
     * @param y
     * @param x
     * @return a new utility space
     */
    private ListNode2<Space> createUtility(
        String name,
        int loc,
        int x,
        int y,
        int o )
    {
        return new ListNode2<Space>( new UtilitySpace( this, name, loc, x, y, o ),
            null,
            null );
    }


    /**
     * This method creates a new chance deck
     * 
     * @return the new chance linkedlist
     */
    private LinkedList<Chance> createChanceDeck()
    {
        ch = new LinkedList<Chance>();
        for ( int i = 0; i < chanceInfo.length; i++ )
        {
            Chance c = new Chance( this, i, chanceInfo[i] );
            ch.add( c );
        }
        return ch;
    }


    /**
     * This method creates a new community chest deck
     * 
     * @return the new community chest linkedlist
     */
    private LinkedList<CommunityChest> createCCDeck()
    {
        cc = new LinkedList<CommunityChest>();
        for ( int i = 0; i < commInfo.length; i++ )
        {
            CommunityChest c = new CommunityChest( this, i, commInfo[i] );
            cc.add( c );
        }
        return cc;
    }


    public void startGUI( Player p, JTextArea text )
    {
        // Player p = players.poll();
        // currPlayer = p;
        text.append( "\n" );
        text.append( "It is " + p.getName() + "'s turn.\n" );
        if ( p.getMoney() <= 0 )
        {
            p.mortgage( p,
                p.getProperties(),
                p.getRailroads(),
                p.getUtilities() );
        }
        text.append( "Money: $" + p.getMoney() + "\n" );
        text.append( "Properties: " + p.getProperties() + "\n" );
        text.append( "Railroads: " + p.getRailroads() + "\n" );
        text.append( "Utilities: " + p.getUtilities() + "\n" );
        // players.add(p);
    }


    /**
     * This method starts the game and continues until there is only one player
     * left. It will let the player buy until it runs out of money and will
     * mortgage assets for the player if the player does not have enough money.
     */
    public void startGame()
    {
        while ( players.size() > 1 )
        {
            boolean hasMoney = true;
            Player p = players.remove();
            System.out.println();
            System.out.println( "It is " + p.getName() + "'s turn." );
            System.out.println( "Money: $" + p.getMoney() );
            System.out.println( "Properties: " + p.getProperties() );
            System.out.println( "Railroads: " + p.getRailroads() );
            System.out.println( "Utilities: " + p.getUtilities() );
            if ( !p.getInJail() )
            {
                System.out.println( "Roll the dice." );

                if ( p.isHuman )
                {
                    pause.nextLine();
                    // rollDie( p );
                    System.out.println();
                    // endTurnSeq( p );
                }
                else
                {
                    System.out.println();
                    // ( (AI)p ).run();
                    // pause.nextLine();
                }

            }
            else
            {
                if ( p.isHuman )
                {
                    boolean madeMove = false;
                    while ( !madeMove )
                    {
                        System.out.println();
                        System.out.println( "Choose an option" );
                        System.out.println( "   (1) Roll Dice" );
                        System.out.println( "   (2) Pay $50" );
                        System.out.println( "   (3) Use Get-Out-Of-Jail-Free Card" );
                        madeMove = ( (JailSpace)jail.getValue() ).actJail( p,
                            scan.next() );
                    }
                }
                else
                {
                    if ( ( (AI)p ).getHasJailFree() )
                    {
                        ( (JailSpace)jail.getValue() ).useCard( (AI)p );
                    }
                    else if ( ( (AI)p ).getMoney() > 50 )
                    {
                        ( (JailSpace)jail.getValue() ).payOutOfJail( (AI)p );
                    }
                    else
                    {
                        ( (JailSpace)jail.getValue() ).inJailRollDie( getDice(),
                            (AI)p );
                    }
                }
            }
            if ( p.getMoney() < 0 )
            {
                hasMoney = p.mortgage( p,
                    p.getProperties(),
                    p.getRailroads(),
                    p.getUtilities() );
            }
            if ( hasMoney )
            {
                players.add( p );
            }
            else
            {
                System.out.println( p.getName()
                    + " has gone bankrupt and has been removed from the game." );
            }

        }
    }


    /**
     * This method moves the player on the gameboard.
     * 
     * @param p
     *            the player who moves
     * @param numOfSpaces
     *            the number of spaces to be moved
     * @return the space that the player lands on
     */
    public ListNode2<Space> move( Player p, int numOfSpaces )
    {
        return p.move( numOfSpaces );
    }


    /**
     * This method moves the player to a given space
     * 
     * @param p
     *            the player to be moved
     * @param s
     *            the space to be moved to
     */
    public void move( Player p, ListNode2<Space> s )
    {
        int spos = s.getValue().getLoc();
        int move;
        if ( spos < p.getPos() )
        {
            move = BOARD_SIZE + spos - p.getPos();
        }
        else
        {
            move = spos - p.getPos();
        }
        move( p, move );
    }


    /**
     * This method draws a chance card
     * 
     * @return the chance card
     */
    public Chance drawChance()
    {
        int i = (int)( Math.random() * ch.size() );
        return ch.get( i );
    }


    /**
     * This method draws a community chest card
     * 
     * @return the community chest card
     */
    public CommunityChest drawCC()
    {
        int i = (int)( Math.random() * cc.size() );
        return cc.get( i );
    }


    /**
     * This method rolls the dice for a player and moves the player accordingly.
     * It will allow the player to do the action specified by that space. It
     * also makes sure that if there are doubles the player can roll again. If
     * there are three doubles, this player gets sent to jail.
     * 
     * @param p
     */
    public void rollDie( Player p, JTextArea text )
    {
        int d1Val = p.rollDie( d1 );
        int d2Val = p.rollDie( d2 );
        p.setMoveAmount( d1Val + d2Val );
        text.append( "You rolled a " + d1Val + " and a " + d2Val + ".\n" );
        while ( d1Val == d2Val )
        {
            numOfDoubles++;
            if ( numOfDoubles >= 3 )
            {
                move( p, jail );
                if ( p.getPrevPos() > jail.getValue().getLoc() )
                {
                    p.payBank( 200 );
                }
                text.append( "You rolled too many doubles! You have been sent to jail.\n" );
                jail.getValue().act( p, text );
                p.setInJail( true );
                numOfDoubles = 0;
                return;
            }
            else
            {
                ListNode2<Space> curr = move( p, d1Val + d2Val );
                text.append( "You moved to " + curr.getValue().getName()
                    + ".\n" );
                text.append( "\n" );
                curr.getValue().act( p, text );
                if ( p.getPrevPos() != goJail.getValue().getLoc() )
                {
                    text.append( "You rolled doubles! You may roll again.\n" );
                    canRollAgain = true;
                    // pause.nextLine();
                    // d1Val = p.rollDie( d1 );
                    // d2Val = p.rollDie( d2 );
                    // text.append( "You rolled a " + d1Val + " and a "
                    // + d2Val + ".\n" );
                }
                else
                {
                    numOfDoubles = 0;
                    return;
                }
            }
        }
        numOfDoubles = 0;
        ListNode2<Space> s = move( p, d1Val + d2Val );
        text.append( "You moved to " + s.getValue().getName() + ".\n" );
        pause.nextLine();
        p.spaceAction( s, text );
    }


    /**
     * This method gets the go space of the board.
     * 
     * @return the go space
     */
    public ListNode2<Space> getBoard()
    {
        return go;
    }


    /**
     * This method gets the go to jail space of the board
     * 
     * @return the go to jail space
     */
    public ListNode2<Space> getGoJail()
    {
        return goJail;
    }


    /**
     * This method gets the jail space of the board
     * 
     * @return the jail space
     */
    public ListNode2<Space> getJail()
    {
        return jail;
    }


    /**
     * This method returns the bank being used by the gameboard.
     * 
     * @return the bank
     */
    public Bank getBank()
    {
        return bank;
    }


    /**
     * This method returns the dice being used in the game
     * 
     * @return the two dice
     */
    public ArrayList<Die> getDice()
    {
        ArrayList<Die> d = new ArrayList<Die>();
        d.add( d1 );
        d.add( d2 );
        return d;
    }


    /**
     * This method gets the go space of the board.
     * 
     * @return the go space
     */
    public ListNode2<Space> getGo()
    {
        return go;
    }


    /**
     * This method returns a property based on a string. It will make sure that
     * this string is actually a property
     * 
     * @param name
     *            the string to be inputed by the user
     * @return the property of the string
     */
    public ListNode2<Space> getProperty( String name )
    {

        // name = checkIfStringIsAProp( name );
        ListNode2<Space> start = go;
        while ( !start.getValue().getName().equals( name ) )
        {
            start = start.getNext();
        }
        return start;

    }


    /**
     * This method returns a space based on a string. It will make sure that
     * this string is actually a space
     * 
     * @param name
     *            the string to be inputed by the user
     * @return the space of the string
     */
    public ListNode2<Space> getSpace( String name )
    {
        if ( checkIfStringIsSpace( name ) )
        {
            ListNode2<Space> start = go;
            while ( !start.getValue().getName().equals( name ) )
            {
                start = start.getNext();
            }
            return start;
        }
        else
        {
            myText.append( "That is not a property.\n" );
            return null;
        }

    }


    /**
     * This method checks if a string is a space on the gameboard
     * 
     * @param s
     *            the string to be checked
     * @return true if the string is a legitimate space
     */
    public boolean checkIfStringIsSpace( String s )
    {
        for ( String i : propNames )
        {
            if ( s.equalsIgnoreCase( i + " Avenue" ) )
            {
                return true;
            }
        }
        for ( String i : taxNames )
        {
            if ( s.equalsIgnoreCase( i + " Tax" ) )
            {
                return true;
            }
        }
        for ( String i : railNames )
        {
            if ( s.equalsIgnoreCase( i + " Railroad" ) )
            {
                return true;
            }
        }
        for ( String i : utilNames )
        {
            if ( s.equalsIgnoreCase( i + " Utility" ) )
            {
                return true;
            }
        }
        if ( s.equalsIgnoreCase( "Chance" )
            || s.equalsIgnoreCase( "Community Chest" )
            || s.equalsIgnoreCase( "Go" )
            || s.equalsIgnoreCase( "Free Parking" )
            || s.equalsIgnoreCase( "Jail" )
            || s.equalsIgnoreCase( "Go to Jail" ) )
        {
            return true;
        }
        return false;
    }


    /**
     * This method checks if a string is a property on the gameboard
     * 
     * @param s
     *            the string to be checked
     * @return true if the string is a legitimate property
     */
    public String checkIfStringIsAProp( String s )
    {
        boolean madeMove = false;
        while ( !madeMove )
        {
            for ( String i : propNames )
            {
                i = i + " Avenue";
                if ( s.equals( i ) )
                {
                    return s;
                }
            }
            System.out.println( "That is not a real property. Try Again." );
            s = scan.nextLine();

        }
        return s;
    }


    /**
     * This method gets the players in the game
     * 
     * @return the players active in the game
     */
    public Queue<Player> getPlayers()
    {
        // return players;
        Queue<Player> play = new LinkedList<Player>();
        for ( Player p : players )
        {
            play.add( p );
        }
        return play;
    }


    /**
     * This method allows the user to end his or her turn, buy a house, or buy a
     * hotel if allowed at the end of the turn.
     * 
     * @param p
     *            the player who just had his or her turn
     * @param text
     */
    public void endTurnSeq( Player p, JTextArea text )
    {
        print( p, text );
        // boolean madeMove = false;
        // while ( !madeMove )
        // {
        // String resp = scan.nextLine();
        // if ( resp.equals( "1" ) )
        // {
        // madeMove = true;
        // }
        // else if ( resp.equals( "2" ) && checkHouse == true )
        // {
        // p.buyHouse();
        // print( p, text );
        // }
        // else if ( resp.equals( "3" ) && checkHotel == true )
        // {
        // p.buyHotel();
        // print( p, text );
        // }
        // else if ( !resp.isEmpty() )
        // {
        // System.out.println(
        // "Sorry that is not a valid response. Please try again." );
        // }
        // }
    }


    /**
     * This method prints the statements for the endTurnSeq based on what is
     * allowed for that given player.
     * 
     * @param p
     *            the player whos turn just ended
     * @param text
     */
    public void print( Player p, JTextArea text )
    {
        checkHouse = false;
        checkHotel = false;

        text.append( "Please select what you would like to do:\n" );
        text.append( "(1) End turn\n" );
        for ( PropertySpace i : p.getProperties() )
        {
            if ( p.hasAllPropsInGroup( i ) )
            {
                checkHouse = true;
            }
        }
        if ( checkHouse )
        {
            text.append( "(2) Buy a House\n" );
        }
        for ( PropertySpace i : p.getProperties() )
        {
            if ( p.allPropsInGroupHave4Houses( i ) )
            {
                checkHotel = true;
            }
        }
        if ( checkHotel )
        {
            text.append( "(3) Buy a Hotel\n" );
        }
    }


    /**
     * gets the current player
     * 
     * @return the current player
     */
    public Player getCurrPlayer()
    {
        return currPlayer;
    }


    /**
     * gets the boolean variable for the houses
     * 
     * @return checkHouse
     */
    public boolean getOp2()
    {
        return checkHouse;
    }


    /**
     * Checks if a hotel is buyable for
     * 
     * @return checkHotel
     */
    public boolean getOp3()
    {
        return checkHotel;
    }


    /**
     * Checks whether a player can roll again
     * 
     * @return canRollAgain
     */
    public boolean getRollAgain()
    {
        return canRollAgain;
    }


    /**
     * New way to roll a die
     * 
     * @param p
     *            player
     * @param text
     *            text for GUI
     */
    public void altRollDie( Player p, JTextArea text )
    {
        int d1Val = p.rollDie( d1 );
        int d2Val = p.rollDie( d2 );
        p.setMoveAmount( d1Val + d2Val );
        text.append( p.getName() + " rolled a " + d1Val + " and a " + d2Val
            + ".\n" );
        if ( d1Val == d2Val )
        {
            numOfDoubles++;
            if ( numOfDoubles >= 3 )
            {
                move( p, jail );
                if ( p.getPrevPos() > jail.getValue().getLoc() )
                {
                    p.payBank( 200 );
                }
                text.append( p.getName() + " rolled too many doubles! "
                    + p.getName() + " have been sent to jail.\n" );
                jail.getValue().act( p, text );
                p.setInJail( true );
                numOfDoubles = 0;
                p.setRollAgain( false );
                return;
            }
            else
            {
                ListNode2<Space> curr = move( p, d1Val + d2Val );
                text.append( p.getName() + " moved to "
                    + curr.getValue().getName() + ".\n" );
                text.append( "\n" );
                // curr.getValue().act( p, text );
                if ( curr.equals( jail.getValue() ) )
                {
                    p.setRollAgain( true );
                    return;
                }
                else
                {
                    numOfDoubles = 0;
                    p.setRollAgain( false );
                    return;
                }
            }
        }
        numOfDoubles = 0;
        ListNode2<Space> s = move( p, d1Val + d2Val );
        text.append( p.getName() + " moved to " + s.getValue().getName()
            + ".\n" );
        p.setRollAgain( false );
        // p.spaceAction( s, text );
    }


    /**
     * Gets the properties owned
     * 
     * @return the properties
     */
    public String[] getPropNames()
    {
        return propNames;
    }
}
