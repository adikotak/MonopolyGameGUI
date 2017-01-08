import java.util.*;

import javax.swing.*;


/**
 * This class creates a Chance object which is a type of Card
 *
 * @author William Wang, Aditya Kotak, Rahul Sarathy.
 * @version May 23, 2015
 * @author Period: 6
 * @author Assignment: MonopolyGame
 *
 * @author Sources: none
 */
public class Chance extends Card
{
    /**
     * This constructs a Chance object which is a Card.
     * 
     * @param g
     *            the GameBoard this Chance card refers to
     * @param id
     *            the id of this Chance card
     * @param info
     *            the command of this Chance card
     */
    public Chance( GameBoard g, int id, String info )
    {
        super( g, id, info );
    }


    /**
     * A required method from the abstract Card class. This method has multiple
     * switch cases depending on the ID of the chance card. It will do the
     * specified action using various methods from PropertySpace, GameBoard, and
     * Player to do the actual command of the Chance card.
     */
    public void use( Player p, int cid, JTextArea text )
    {
        int id = cid;
        switch ( id )
        {
            case 0:
                ListNode2<Space> go = game.getGo();
                game.move( p, go );
                go.getValue().act( p, text );
                break;
            case 1:
                ListNode2<Space> s = game.getSpace( "Illinois Avenue" );
                game.move( p, s );
                s.getValue().act( p, text );
                break;
            case 2:
                ListNode2<Space> sp = game.getSpace( "St. Charles Avenue" );
                game.move( p, sp );
                sp.getValue().act( p, text );
                break;
            case 3:
                ListNode2<Space> util = p.getCurrSpace();
                while ( !util.getNext()
                    .getValue()
                    .getName()
                    .contains( "Utility" ) )
                {
                    util = util.getNext();
                }
                game.move( p, util.getNext() );
                text.append( "You landed on: "
                    + util.getNext().getValue().getName() + "\n" );
                util.getNext().getValue().act( p, text );
                break;
            case 4:
                ListNode2<Space> rail = p.getCurrSpace();
                while ( !rail.getNext()
                    .getValue()
                    .getName()
                    .contains( "Railroad" ) )
                {
                    rail = rail.getNext();
                }
                game.move( p, rail.getNext() );
                text.append( "You landed on: "
                    + rail.getNext().getValue().getName() + "\n" );
                rail.getNext().getValue().act( p, text );
                break;
            case 5:
                game.getBank().payPlayer( 50, p );
                break;
            case 6:
                p.setHasJailFree( true );
                break;
            case 7:
                ListNode2<Space> curLoc = p.getCurrSpace();
                for ( int i = 0; i < 3; i++ )
                {
                    curLoc = curLoc.getPrevious();
                }
                p.move( -3 );
                text.append( "You landed on: " + curLoc.getValue().getName()
                    + "\n" );
                curLoc.getValue().act( p, text );
                break;
            case 8:
                useJail = true;
                ListNode2<Space> jail = game.getJail();
                game.move( p, jail );
                p.setInJail( true );
                jail.getValue().act( p, text );
                break;
            case 9:
                p.payBank( p.getNumHouses() * 25 + p.getNumHotels() * 100 );
                break;
            case 10:
                p.payBank( 15 );
                break;
            case 11:
                ListNode2<Space> readrail = game.getSpace( "Reading Railroad" );
                game.move( p, readrail );
                readrail.getValue().act( p, text );
                break;
            case 12:
                ListNode2<Space> boardwalk = game.getSpace( "Boardwalk Avenue" );
                game.move( p, boardwalk );
                boardwalk.getValue().act( p, text );
                break;
            case 13:
                Queue<Player> players = game.getPlayers();
                while ( players.size() > 0 )
                {
                    Player pl = players.remove();
                    p.payPlayer( 50, pl );
                }
                break;
            case 14:
                game.getBank().payPlayer( 150, p );
                break;
            case 15:
                game.getBank().payPlayer( 100, p );
                break;
        }
    }
}
