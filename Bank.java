/**
 * This class creates a bank to be used in the monopoly game.
 *
 * @author William Wang, Aditya Kotak, Rahul Sarathy.
 * @version May 23, 2015
 * @author Period: 6
 * @author Assignment: MonopolyGame
 *
 * @author Sources: none
 */
public class Bank
{
    /**
     * This method pays a player a given amount of money.
     * 
     * @param amount
     *            the amount of money to be played to a player
     * @param p
     *            the player receiving the money
     * @return the transaction of money from the bank to the player
     */
    public int payPlayer( int amount, Player p )
    {
        return p.receiveMoney( amount );
    }


    /**
     * This method receives a given amount of money from a player.
     * 
     * @param amount
     *            the amount of money
     * @param p
     *            the player giving the money
     */
    public void receiveMoney( int amount, Player p )
    {

    }
}
