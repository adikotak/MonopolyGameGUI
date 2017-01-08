import java.util.ArrayList;

import javax.swing.JTextArea;


/**
 * This class creates a JailSpace object to be used in the Monopoly game.
 *
 * @author William Wang, Aditya Kotak, Rahul Sarathy.
 * @version May 23, 2015
 * @author Period: 6
 * @author Assignment: MonopolyGame
 *
 * @author Sources: none
 */
public class JailSpace extends Space
{
    private ArrayList<Player> playersInJail;

    private int tries;

    private boolean inJail;

    private boolean rolledDoubles;

    private ArrayList<Die> d;

    private JTextArea myText;

    private int inJailX = 24, inJailY = 522;

    private int[][] inJailLocs = new int[6][2];


    /**
     * Constructs the JailSpace to be used in GameBoard.
     * 
     * @param g
     *            The GameBoard that will have this JailSpace
     */
    public JailSpace( GameBoard g, JTextArea text, int x, int y, int o )
    {
        super( g, 10, x, y, o );
        name = "Jail";
        buyable = false;
        canHaveBuildings = false;
        playersInJail = new ArrayList<Player>();
        d = g.getDice();
        tries = 0;
        inJail = false;
        rolledDoubles = false;
        myText = text;
        goodLocs[0][0] = myX;
        goodLocs[0][1] = myY;
        goodLocs[1][0] = myX;
        goodLocs[1][1] = myY + 15;
        goodLocs[2][0] = myX;
        goodLocs[2][1] = myY + 30;
        goodLocs[3][0] = myX;
        goodLocs[3][1] = myY + 45;
        goodLocs[4][0] = myX;
        goodLocs[4][1] = myY + 60;
        goodLocs[5][0] = myX + 24;
        goodLocs[5][1] = myY + 60;

        inJailLocs[0][0] = inJailX + 2;
        inJailLocs[0][1] = inJailY + 3;
        inJailLocs[1][0] = inJailX + 28;
        inJailLocs[1][1] = inJailY + 3;
        inJailLocs[2][0] = inJailX + 2;
        inJailLocs[2][1] = inJailY + 21;
        inJailLocs[3][0] = inJailX + 28;
        inJailLocs[3][1] = inJailY + 21;
        inJailLocs[4][0] = inJailX + 2;
        inJailLocs[4][1] = inJailY + 39;
        inJailLocs[5][0] = inJailX + 28;
        inJailLocs[5][1] = inJailY + 39;
    }


    /**
     * This method adds a player to jail.
     * 
     * @param p
     *            The player to be moved into jail
     */
    public void addToJail( Player p )
    {
        playersInJail.add( p );
    }


    /**
     * This method lets a player roll two dice in order to try to be released
     * from jail if doubles are rolled.
     * 
     * @param dice
     *            the two die in an arraylist to be used to remove the player in
     *            jail
     * @param p
     *            the player in jail
     * @return true if the player has made a move
     */
    public boolean inJailRollDie( ArrayList<Die> dice, Player p )
    {
        Die d1 = dice.get( 0 );
        Die d2 = dice.get( 1 );
        d1.roll();
        d2.roll();
        myText.append( "You rolled a " + d1.getNumDots() + " and a "
            + d2.getNumDots() + ".\n" );

        tries++;

        if ( d1.getNumDots() == d2.getNumDots() )
        {
            myText.append( "You rolled doubles! You are free from Jail!\n" );
            rolledDoubles = true;
            p.setInJail( false );
            removeFromJail( p );
            ListNode2<Space> curr = p.move( d1.getNumDots() + d2.getNumDots() );
            myText.append( "You moved to " + curr.getValue().getName() + ".\n" );
            curr.getValue().act( p, myText );
        }
        else if ( tries == 3 )
        {
            myText.append( "You served your sentence and paid $50! You are free from Jail!\n" );
            p.payBank( 50 );
            p.setInJail( false );
            removeFromJail( p );
            ListNode2<Space> curr = p.move( d1.getNumDots() + d2.getNumDots() );
            myText.append( "You moved to " + curr.getValue().getName() + ".\n" );
            curr.getValue().act( p, myText );
        }
        return true;
    }


    /**
     * This method allows a player to pay $50 to be removed from jail. This
     * method makes sure the player has enough money.
     * 
     * @param p
     *            the player in jail
     * @return true if the player has paid out of jail.
     */
    public boolean payOutOfJail( Player p )
    {
        if ( p.getMoney() > 50 )
        {
            myText.append( "You paid $50! You are free from Jail!\n" );
            p.payBank( 50 );
            p.setInJail( false );
            removeFromJail( p );
            return true;
        }
        else
        {
            myText.append( "You do not have enough money.\n" );
            return false;
        }

    }


    /**
     * This method allows a user to use a get out of jail free card to get
     * removed from jail. This method checks if the player has the get out of
     * jail free card.
     * 
     * @param p
     *            the player in jail
     * @return true if the player used the get out of jail free card
     */
    public boolean useCard( Player p )
    {
        if ( p.getHasJailFree() )
        {
            myText.append( "You used a Get-Out-Of-Jail-Free card!\n" );
            p.setHasJailFree( false );
            p.setInJail( false );
            removeFromJail( p );
            return true;
        }
        else
        {
            myText.append( "You do not have a Get-Out-Of-Jail-Free card.\n" );
            return false;
        }
    }


    /**
     * This removes the player from the jail space
     * 
     * @param p
     *            the player in jail
     */
    public void removeFromJail( Player p )
    {
        playersInJail.remove( p );
    }


    /**
     * This method moves a player to jail and sets his or her standing with
     * jail.
     * 
     * @param p
     *            the player in question
     */
    public void act( Player p, JTextArea text )
    {
        myText = text;
        if ( g.getGoJail().getValue().getLoc() == p.getPrevPos() || p.useJail )
        {
            findNextAvailableJail(p);
            addToJail( p );
            p.setInJail( true );
            System.err.println( "I'm in jail" );
        }
        else
        {
            findNextAvailable(p);
            playersOnSpace.add( p );
            p.setInJail( false );
            System.err.println( "I'm not in jail" );
        }
    }


    /**
     * This method allows three switch cases for the player to choose which
     * option to use in order to get out of jail
     * 
     * @param p
     *            the player in jail
     * @param option
     *            the string inputed in by the player
     * @return true if the person is out of jail
     */
    public boolean actJail( Player p, String option )
    {
        boolean madeMove;
        switch ( option )
        {
            case "1":
                madeMove = inJailRollDie( d, p );
                break;
            case "2":
                madeMove = payOutOfJail( p );
                break;
            case "3":
                madeMove = useCard( p );
                break;
            default:
                System.out.println( "Invalid Option" );
                madeMove = false;
                break;
        }
        return madeMove;
    }


    /**
     * This method uses the a space to set the goJailSpace.
     * 
     * @param goJail
     *            the space that refers to the goJailSpace
     */
    public void setGoJail( ListNode2<Space> goJail )
    {
        goJail = g.getGoJail();
    }
    
    public void findNextAvailableJail(Player p)
    {
        for(int i = 0; i < inJailLocs.length; i++)
        {
            int x = inJailLocs[i][0];
            int y = inJailLocs[i][1];
            if(!playerHasLocJail(x, y))
            {
                p.setX( x );
                p.setY( y );
                return;
            }
        }
    }
    
    private boolean playerHasLocJail( int x, int y )
    {
        for(int i = 0; i < playersInJail.size(); i++)
        {
            Player p = playersInJail.get( i );
            if(p.getX() == x && p.getY() == y)
            {
                return true;
            }
        }
        return false;
    }
}
