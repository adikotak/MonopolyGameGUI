/**
 * This class represents a node of a doubly-linked list to help create the
 * gameboard.
 *
 * @author William Wang, Aditya Kotak, Rahul Sarathy.
 * @version May 23, 2015
 * @author Period: 6
 * @author Assignment: MonopolyGame
 *
 * @author Sources: none
 */
public class ListNode2<E>
{
    private E value;

    private ListNode2<E> previous;

    private ListNode2<E> next;


    /**
     * Constructs a ListNode2 which is representation of a node of a
     * doubly-linked list
     * 
     * @param initValue
     *            the integer value of the listnode2 on the board
     * @param initPrevious
     *            the previous listnode2's integer value
     * @param initNext
     *            the next listnode2's integer value
     */
    public ListNode2(
        E initValue,
        ListNode2<E> initPrevious,
        ListNode2<E> initNext )
    {
        value = initValue;
        previous = initPrevious;
        next = initNext;
    }


    /**
     * This method gets the value of listNode2
     * 
     * @return the value of listNode2
     */
    public E getValue()
    {
        return value;
    }


    /**
     * This method gets the value of the previous listNode2
     * 
     * @return the value of the previous listNode2
     */
    public ListNode2<E> getPrevious()
    {
        return previous;
    }


    /**
     * This method gets the value of the next listNode2
     * 
     * @return the value of the next listNode2
     */
    public ListNode2<E> getNext()
    {
        return next;
    }


    /**
     * This method sets the value of the current listNode2
     * 
     * @param theNewValue
     *            the new value for listNode2
     */
    public void setValue( E theNewValue )
    {
        value = theNewValue;
    }


    /**
     * This method sets the value of the previous listNode2
     * 
     * @param theNewPrev
     *            the new previous value for the previous listNode2
     */
    public void setPrevious( ListNode2<E> theNewPrev )
    {
        previous = theNewPrev;
    }


    /**
     * This method sets the value of the next listNode2
     * 
     * @param theNewNext
     *            the new next value for the next listNode2
     */
    public void setNext( ListNode2<E> theNewNext )
    {
        next = theNewNext;
    }


    /**
     * This method gives a string representation of the value of a listnode2
     * 
     * @return a string representation for the value of a listnode2
     */
    public String toString()
    {
        return getValue().toString();
    }
}
