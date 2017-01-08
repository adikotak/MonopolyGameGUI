import javax.swing.*;

/**
 * This class creates a framework for a card to be built.
 *
 * @author William Wang, Aditya Kotak, Rahul Sarathy.
 * @version May 23, 2015
 * @author Period: 6
 * @author Assignment: MonopolyGame
 *
 * @author Sources: none
 */
public abstract class Card
{
    int cardID;

    String name;

    GameBoard game;

    String info;
    
    boolean useJail = false;


    /**
     * Constructs a card to be used in MonopolyGame.
     * 
     * @param g
     *            the GameBoard this card refers to
     * @param id
     *            the type of card based on the integer value
     * @param cinfo
     *            the command this card gives
     */
    public Card( GameBoard g, int id, String cinfo )
    {
        game = g;
        cardID = id;
        info = cinfo;
    }


    /**
     * This method returns the ID number of this card.
     * 
     * @return the card ID number
     */
    public int getID()
    {
        return cardID;
    }


    /**
     * This method returns the command in this card.
     * 
     * @return the command of this card
     */
    public String toString()
    {
        return info;
    }


    /**
     * This method will vary depending on whether the card drawn by a player is
     * a Community Chest or a Chance card. It will execute the specific function
     * of one of those two cards.
     * 
     * @param p
     *            the player who has drawn the card
     * @param id
     *            the ID number of the card
     */
    public abstract void use( Player p, int id, JTextArea text);
    
    public boolean getUseJail()
    {
        return useJail;
    }
}
