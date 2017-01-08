import java.util.*;

import javax.swing.*;


/**
 * This class creates a Community Chest object which is a type of Card.
 *
 * @author William Wang, Aditya Kotak, Rahul Sarathy.
 * @version May 23, 2015
 * @author Period: 6
 * @author Assignment: MonopolyGame
 *
 * @author Sources: none
 */
public class CommunityChest extends Card
{
    /**
     * This constructs a Community Chest object which is a Card.
     * 
     * @param g
     *            the GameBoard this Community Chest card refers to
     * @param id
     *            the ID of this Community Chest card
     * @param info
     *            the command of this Community Chest card
     */
    public CommunityChest( GameBoard g, int id, String info )
    {
        super( g, id, info );

    }


    /**
     * A required method from the abstract Card class. This method has multiple
     * switch cases depending on the ID of the Community Chest card. It will do
     * the specified action using various methods from PropertySpace, GameBoard,
     * and Player to do the actual command of the Community Chest card.
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
                game.getBank().payPlayer( 200, p );
                break;
            case 2:
                p.payBank( 50 );
                break;
            case 3:
                game.getBank().payPlayer( 50, p );
                break;
            case 4:
                p.setHasJailFree( true );
                break;
            case 5:
                useJail = true;
                ListNode2<Space> jail = game.getJail();
                game.move( p, jail );
                p.setInJail( true );
                jail.getValue().act( p, text );
                break;
            case 6:
                Queue<Player> players = game.getPlayers();
                while ( players.size() > 0 )
                {
                    Player pl = players.remove();
                    pl.payPlayer( 50, p );
                }
                break;
            case 7:
                game.getBank().payPlayer( 100, p );
                break;
            case 8:
                game.getBank().payPlayer( 20, p );
                break;
            case 9:
                Queue<Player> play = game.getPlayers();
                while ( play.size() > 0 )
                {
                    Player pl = play.remove();
                    pl.payPlayer( 10, p );
                }
                break;
            case 10:
                game.getBank().payPlayer( 125, p );
                break;
            case 11:
                p.payBank( 100 );
                break;
            case 12:
                p.payBank( 150 );
                break;
            case 13:
                game.getBank().payPlayer( 25, p );
                break;
            case 14:
                p.payBank( p.getNumHouses() * 40 + p.getNumHotels() * 110 );
                break;
            case 15:
                game.getBank().payPlayer( 10, p );
                break;
            case 16:
                game.getBank().payPlayer( 150, p );
                break;
        }

    }

}
