import java.util.*;

import javax.swing.*;


/**
 * The AI class defines a Player that does everything a Player would do in real
 * time, except automatically.
 *
 * @author William Wang, Aditya Kotak, Rahul Sarathy.
 * @version May 23, 2015
 * @author Period: 6
 * @author Assignment: MonopolyGame
 *
 * @author Sources: none
 */
public class AI extends Player
{
    private String name;

    private Bank b;

    private GameBoard myGame;

    private int cash;

    private ArrayList<PropertySpace> properties;

    private ArrayList<RailroadSpace> rrspace;

    private ArrayList<UtilitySpace> uspace;

    private boolean hasJailFree, inJail;

    private int numHouses, numHotels;

    private int position, prevPos;

    private ListNode2<Space> currSpace;

    private JTextArea myText;


    /**
     * Constructor for an AI.
     * 
     * @param name
     *            name for this AI (Computer [number])
     * @param g
     *            the GameBoard this AI plays on
     */
    public AI( String name, GameBoard g, JTextArea text, ImageIcon sym )
    {
        super( name, g, text, sym );
        inJail = false;
        myText = text;
        myGame = g;
        b = myGame.getBank();
        isHuman = false;
    }


    /**
     * Method that automatically runs through all the tasks a player does,
     * complete with choice.
     */
    public void run()
    {
        if ( !getInJail() )
        {
            int numOfDoubles = 0;
            ArrayList<Die> dice = myGame.getDice();
            dice.get( 0 ).roll();
            dice.get( 1 ).roll();
            int d1Val = dice.get( 0 ).getNumDots();
            int d2Val = dice.get( 1 ).getNumDots();
            setMoveAmount( d1Val + d2Val );
            myText.append( getName() + " rolled a " + d1Val + " and a " + d2Val
                + ".\n" );
            while ( d1Val == d2Val )
            {
                numOfDoubles++;
                if ( numOfDoubles >= 3 )
                {
                    myGame.move( this, myGame.getJail() );
                    if ( getPrevPos() > myGame.getJail().getValue().getLoc() )
                    {
                        payBank( 200 );
                    }
                    myText.append( getName() + " rolled too many doubles! "
                        + getName() + " has been sent to jail.\n" );
                    myGame.getJail().getValue().act( this, myText );
                    setInJail( true );
                    numOfDoubles = 0;
                    return;
                }
                else
                {
                    ListNode2<Space> curr = myGame.move( this, d1Val + d2Val );
                    myText.append( getName() + " moved to "
                        + curr.getValue().getName() + ".\n" );
                    myText.append( "\n" );
                    curr.getValue().act( this, myText );
                    if ( getPrevPos() != myGame.getGoJail().getValue().getLoc() )
                    {
                        myText.append( getName() + " rolled doubles! "
                            + getName() + " may roll again.\n" );
                        myText.append( "\n" );
                        dice.get( 0 ).roll();
                        dice.get( 1 ).roll();
                        d1Val = dice.get( 0 ).getNumDots();
                        d2Val = dice.get( 1 ).getNumDots();
                        myText.append( getName() + " rolled a " + d1Val
                            + " and a " + d2Val + ".\n" );
                    }
                    else
                    {
                        numOfDoubles = 0;
                        return;
                    }
                }
            }
            numOfDoubles = 0;
            ListNode2<Space> s = myGame.move( this, d1Val + d2Val );
            myText.append( getName() + " moved to " + s.getValue().getName()
                + ".\n" );
            spaceAction( s, myText );
            for ( int i = getProperties().size() - 1; i >= 0; i-- )
            {
                if ( allPropsInGroupHave4Houses( getProperties().get( i ) ) )
                {
                    AIbuyHotel( getProperties().get( i ), myText );
                }
            }
            for ( int i = getProperties().size() - 1; i >= 0; i-- )
            {
                if ( hasAllPropsInGroup( getProperties().get( i ) ) )
                {
                    AIbuyHouse( getProperties().get( i ), myText );
                }
            }
        }
        else
        {
            if ( getHasJailFree() )
            {
                myText.append( getName()
                    + " used a Get-Out-Of-Jail-Free card!\n" );
                setHasJailFree( false );
                setInJail( false );
                ( (JailSpace)myGame.getJail().getValue() ).removeFromJail( this );
            }
            else if ( getMoney() > 50 )
            {
                myText.append( getName() + " paid $50! " + getName()
                    + " is free from Jail!\n" );
                payBank( 50 );
                setInJail( false );
                ( (JailSpace)myGame.getJail().getValue() ).removeFromJail( this );
            }
            else
            {
                ( (JailSpace)myGame.getJail().getValue() ).inJailRollDie( myGame.getDice(),
                    this );
            }
        }

    }


    /**
     * This method buys houses for the AI. It budgets 75% of the current amount
     * of money to be used for buying houses.
     * 
     * @param s
     *            the property for which the house is being bought for.
     */
    public void AIbuyHouse( PropertySpace s, JTextArea text )
    {
        if ( s.getNumOfHouses() < 4 && !allPropsInGroupHave4Houses( s )
            && !s.hasHotel() )
        {
            int hPrice = s.getHousePrice();
            int temp = getMoney();
            int numToBeBought = 0;
            while ( temp > ( getMoney() / 4 ) )
            {
                if ( temp > hPrice )
                {
                    temp -= hPrice;
                    numToBeBought++;
                }
                else
                {
                    break;
                }
            }
            if ( numToBeBought + s.getNumOfHouses() > 4 )
            {
                numToBeBought = 4 - s.getNumOfHouses();
            }
            for ( int i = 0; i < numToBeBought; i++ )
            {
                payBank( hPrice );
                s.addHouse();
                numHouses++;
            }
            if ( numToBeBought > 0 )
            {
                text.append( getName() + " bought: " + numToBeBought
                    + " house(s) on " + s.getName() + "\n" );
            }
            if ( allPropsInGroupHave4Houses( s ) )
            {
                text.append( getName()
                    + " may now buy hotels for this group!\n" );
            }
        }
    }


    /**
     * This method buys a hotel for the AI as long as the AI has enough money
     * and there is not a hotel on the property space.
     * 
     * @param s
     *            the property space for which a hotel is being bought.
     */
    public void AIbuyHotel( PropertySpace s, JTextArea text )
    {
        int hPrice = s.getHousePrice();
        if ( getMoney() > hPrice && !s.hasHotel() )
        {
            payBank( hPrice );
            s.addHouse();
            text.append( getName() + " bought a hotel on " + s.getName() + "\n" );
        }
    }

}
