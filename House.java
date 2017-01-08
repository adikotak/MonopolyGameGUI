import java.util.Scanner;


/**
 * This class creates a House object to be bought in the Player class.
 *
 * @author William Wang, Aditya Kotak, Rahul Sarathy.
 * @version May 23, 2015
 * @author Period: 6
 * @author Assignment: MonopolyGame
 *
 * @author Sources: none
 */
public class House
{
    private int houseNum;

    private int housePrice;

    private PropertySpace pSpace;

    private Player owner;

    Scanner in = new Scanner( System.in );

    GameBoard g;

    PropertySpace temp;


    /**
     * Constructs for a House object to be bought in the Player class.
     * 
     * @param arSize
     *            The size of the current number of houses on a given
     *            PropertySpace. Incremented by one in constructor.
     * @param price
     *            The price of a house on a given PropertySpace.
     */
    public House( int arSize, int price )
    {
        houseNum = arSize + 1;
        housePrice = price;
    }

    // public void buyHouse( Player p )
    // {
    // System.out.println(
    // "Please type the name of the property you would like to buy a house for."
    // );
    // System.out.println( "If you do not want to buy a house, type in \"quit\""
    // );
    // boolean move = false;
    // while ( !move )
    // {
    // String prop = in.nextLine();
    // temp = (PropertySpace)( g.getSpace( prop ).getValue() );
    // if ( temp.equals( "quit" ) || temp.equals( "Quit" ) )
    // {
    // move = true;
    // break;
    // }
    // if ( !p.hasAllPropsInGroup( temp ) || !p.hasProp( temp ) )
    // {
    // System.out.println( "Sorry, you cannot buy a house here." );
    // }
    // else if (p.allPropsInGroupHave4Houses( temp ))
    // {
    // System.out.println("You may buy hotels for this group!");
    // move = true;
    // break;
    // }
    // else
    // {
    // boolean anotherMove = false;
    // int num = typeInt();
    // while ( !anotherMove )
    // {
    // if ( num + houseNum > 4 || num < 1 )
    // {
    // System.out.println( "Sorry that exceeds the number of houses allowed" );
    // num = typeInt();
    // }
    // else
    // {
    // anotherMove = true;
    // break;
    // }
    //
    // }
    // System.out.println( "Congrats! You bought" + num
    // + "house(s) on: " + temp );
    // p.payBank( housePrice * num );
    // for (int i = 0; i<num; i++)
    // {
    // p.getCurProperty().addHouse();
    // }
    // if (p.allPropsInGroupHave4Houses( temp ))
    // {
    // System.out.println("You may now buy hotels for this group!");
    // }
    // move = true;
    //
    // }
    // }
    // }
    //
    // public void buyHotel(Player p)
    // {
    // System.out.println(
    // "Please type the name of the property you would like to buy a hotel for."
    // );
    // System.out.println( "If you do not want to buy a hotel, type in \"quit\""
    // );
    // boolean move = false;
    // while ( !move )
    // {
    // String prop = in.nextLine();
    // temp = (PropertySpace)( g.getSpace( prop ).getValue() );
    // if ( temp.equals( "quit" ) || temp.equals( "Quit" ) )
    // {
    // move = true;
    // break;
    // }
    // if ( !p.hasAllPropsInGroup( temp ) || !p.hasProp( temp ) ||
    // !p.allPropsInGroupHave4Houses( temp ) )
    // {
    // System.out.println( "Sorry, you cannot buy a hotel here." );
    // }
    // else
    // {
    // System.out.println( "Congrats! You bought a hotel on: " + temp );
    // p.payBank( housePrice * 5 );
    // p.getCurProperty().addHouse();
    // move = true;
    //
    // }
    // }
    // }
    //
    //
    // public int typeInt()
    // {
    // boolean anotherMove = false;
    // int num = -1;
    // while ( !anotherMove )
    // {
    // System.out.println(
    // "How many houses would you like to buy on this property?" );
    // try
    // {
    // num = Integer.parseInt( in.nextLine() );
    // anotherMove = true;
    // }
    // catch ( Exception e )
    // {
    // System.out.println( "Please enter an integer." );
    // }
    // }
    // return num;
    // }

}
