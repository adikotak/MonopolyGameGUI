import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.*;

import java.util.*;


/**
 * This is the class that runs the main GUI engine from animations to GUI text
 * and buttons.
 *
 * @author William Wang, Aditya Kotak, Rahul Sarathy.
 * @version May 23, 2015
 * @author Period: 6
 * @author Assignment: MonopolyGame
 *
 * @author Sources: none
 */
public class MonopolyGUI
{
    private GameBoard myGame;

    private JFrame frame;

    private JPanel buttonBox;

    private ArrayList<JButton> buttonArray;

    private JButton button1, button2, button3, button4, button5, button6;

    private MyPlayerListener playerListener;

    private boolean chooseHumans = true;

    private int numHumans, numAI;

    private JLayeredPane board;

    private JPanel log;

    private JTextArea display;

    private JScrollPane scroll;

    private JButton roll;

    private MyRollListener rollListener;

    private MyEndTurnListener endListener;

    private MyBuyHouseListener houseListener;

    private MyBuyHotelListener hotelListener;

    private MyBuyPropListener propListener;

    private MyBuyRailListener railListener;

    private MyBuyUtilListener utilListener;

    private Queue<Player> players;

    private Player curr;

    private JButton confirm;

    private MyRollJailListener rollJail;

    private MyPayJailListener payJail;

    private MyUseCardListener useCard;

    private JTextField field;

    private JButton enter, quit;

    private MyEnterHouseListener enterHouseListener;

    private MyEnterHotelListener enterHotelListener;

    private PropertySpace currP;

    private MyBuyNumHousesListener buyHouse;

    private JButton playAgain;

    private MyPlayAgainListener playAgainListener;

    private MyQuitBuyListener quitBuy;

    private MyRefuseListener refuse;

    ImageIcon p1 = new ImageIcon( "monopoly1.jpg" );

    JLabel pLabel1 = new JLabel( p1 );

    ImageIcon p2 = new ImageIcon( "monopoly2.jpg" );

    JLabel pLabel2 = new JLabel( p2 );

    ImageIcon p3 = new ImageIcon( "monopoly3.jpg" );

    JLabel pLabel3 = new JLabel( p3 );

    ImageIcon p4 = new ImageIcon( "monopoly4.jpg" );

    JLabel pLabel4 = new JLabel( p4 );

    ImageIcon p5 = new ImageIcon( "monopoly5.jpg" );

    JLabel pLabel5 = new JLabel( p5 );

    ImageIcon p6 = new ImageIcon( "monopoly6.jpg" );

    JLabel pLabel6 = new JLabel( p6 );

    private ImageIcon[] symbols = { p1, p2, p3, p4, p5, p6 };

    JLabel propLabel = new JLabel();

    Queue<JLabel> imq = new LinkedList<JLabel>();


    // private JButton cheat = new JButton("Cheat");
    //
    // private MyCheatListener cheatListener = new MyCheatListener();

    /**
     * This constructs the GUI board
     */
    public MonopolyGUI()
    {
        // cheat.addActionListener( cheatListener );
        frame = new JFrame( "Monopoly" );
        frame.setSize( 1230, 675 );
        frame.setResizable( false );
        display = new JTextArea( 37, 50 );
        display.setEditable( false );
        display.setLineWrap( true );
        display.setWrapStyleWord( true );
        DefaultCaret caret = (DefaultCaret)display.getCaret();
        caret.setUpdatePolicy( DefaultCaret.ALWAYS_UPDATE );
        scroll = new JScrollPane( display );
        scroll.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED );
        scroll.setAutoscrolls( true );
        display.append( "Welcome to Monopoly!\n" );
        display.append( "How many human players are playing? " );
        log = new JPanel();
        log.setLayout( new BorderLayout() );
        buttonBox = new JPanel();
        buttonArray = new ArrayList<JButton>();
        button1 = new JButton( "1" );
        button2 = new JButton( "2" );
        button3 = new JButton( "3" );
        button4 = new JButton( "4" );
        button5 = new JButton( "5" );
        button6 = new JButton( "6" );
        button1.setActionCommand( "1" );
        button2.setActionCommand( "2" );
        button3.setActionCommand( "3" );
        button4.setActionCommand( "4" );
        button5.setActionCommand( "5" );
        button6.setActionCommand( "6" );
        playerListener = new MyPlayerListener();
        button1.addActionListener( playerListener );
        button2.addActionListener( playerListener );
        button3.addActionListener( playerListener );
        button4.addActionListener( playerListener );
        button5.addActionListener( playerListener );
        button6.addActionListener( playerListener );
        buttonBox.add( button1 );
        buttonBox.add( button2 );
        buttonBox.add( button3 );
        buttonBox.add( button4 );
        buttonBox.add( button5 );
        buttonBox.add( button6 );
        buttonArray.add( button1 );
        buttonArray.add( button2 );
        buttonArray.add( button3 );
        buttonArray.add( button4 );
        buttonArray.add( button5 );
        buttonArray.add( button6 );
        log.add( BorderLayout.NORTH, scroll );
        log.add( BorderLayout.SOUTH, buttonBox );
        board = new JLayeredPane();
        board.setPreferredSize( new Dimension( 600, 600 ) );
        board.setLocation( 0, 0 );
        frame.getContentPane().add( BorderLayout.WEST, board );
        ImageIcon ic = new ImageIcon( "monopoly board pic.jpg" );
        JLabel label = new JLabel( ic );
        label.setLocation( 0, 0 );
        label.setSize( board.getPreferredSize() );
        // ImageIcon ic2 = new ImageIcon( "monopoly-tokens1.jpg" );
        // JLabel label2 = new JLabel( ic2 );
        // label2.setLocation( 0, 0 );
        // label2.setSize( board.getPreferredSize() );
        // for(int i = 0; i < symbols.length; i++)
        // {
        // symbols[i].setLocation( 0, i*20 );
        // symbols[i].setSize(24, 15);
        // board.add( symbols[i], JLayeredPane.PALETTE_LAYER );
        // }
        board.add( label, JLayeredPane.DEFAULT_LAYER );
        // board.add( label2, JLayeredPane.PALETTE_LAYER );
        // board.setVisible( true );
        // board.add( cheat );

        frame.getContentPane().add( BorderLayout.EAST, log );

        roll = new JButton( "Roll Dice" );
        rollListener = new MyRollListener();
        roll.addActionListener( rollListener );

        endListener = new MyEndTurnListener();
        houseListener = new MyBuyHouseListener();
        hotelListener = new MyBuyHotelListener();
        propListener = new MyBuyPropListener();
        railListener = new MyBuyRailListener();
        utilListener = new MyBuyUtilListener();
        confirm = new JButton( "OK" );
        confirm.addActionListener( endListener );

        rollJail = new MyRollJailListener();
        payJail = new MyPayJailListener();
        useCard = new MyUseCardListener();

        field = new JTextField( 25 );
        field.setEditable( true );
        quitBuy = new MyQuitBuyListener();
        enter = new JButton( "Enter" );
        quit = new JButton( "Quit" );
        enterHouseListener = new MyEnterHouseListener();
        enterHotelListener = new MyEnterHotelListener();
        buyHouse = new MyBuyNumHousesListener();
        quit.addActionListener( quitBuy );

        playAgain = new JButton( "Play Again" );
        playAgainListener = new MyPlayAgainListener();
        playAgain.addActionListener( playAgainListener );

        refuse = new MyRefuseListener();
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setVisible( true );
    }


    /**
     * Gets the text area for the display
     * 
     * @return the text area
     */
    public JTextArea getTextArea()
    {
        return display;
    }


    /**
     * This sub class listens to a player
     *
     * @author William Wang, Aditya Kotak, Rahul Sarathy.
     * @version May 23, 2015
     * @author Period: 6
     * @author Assignment: MonopolyGame
     *
     * @author Sources: none
     */
    public class MyPlayerListener implements ActionListener
    {
        /**
         * Adds players to the game
         */
        public void actionPerformed( ActionEvent e )
        {
            if ( chooseHumans )
            {
                numHumans = Integer.parseInt( e.getActionCommand() );
                display.append( numHumans + "\n" );
                if ( numHumans < 6 )
                {
                    int maxAI = 6 - numHumans;
                    buttonBox.setVisible( false );
                    for ( JButton b : buttonArray )
                    {
                        if ( Integer.parseInt( b.getActionCommand() ) > maxAI )
                        {
                            buttonBox.remove( b );
                        }
                    }
                    display.append( "How many AI players are playing? " );
                    log.add( BorderLayout.SOUTH, buttonBox );
                    buttonBox.setVisible( true );
                    chooseHumans = false;
                }
                else
                {
                    myGame = new GameBoard( numHumans, 0, display, symbols );
                    players = myGame.getPlayers();
                    buttonBox.setVisible( false );
                    buttonBox.removeAll();
                    button1.removeActionListener( playerListener );
                    button2.removeActionListener( playerListener );
                    button3.removeActionListener( playerListener );
                    button4.removeActionListener( playerListener );
                    button5.removeActionListener( playerListener );
                    button6.removeActionListener( playerListener );

                    System.err.println( "A " + players.size() );
                    curr = players.peek();
                    System.err.println( "B " + players.size() );
                    myGame.startGUI( curr, display );
                    if ( curr.isHuman )
                    {
                        buttonBox.add( roll );
                    }
                    else
                    {
                        ( (AI)curr ).run( display );
                        JLabel curIm = imq.remove();
                        curIm.setLocation( curr.getX(), curr.getY() );
                        imq.add( curIm );
                        board.repaint();
                        buttonBox.add( confirm );
                    }
                    buttonBox.setVisible( true );
                    Queue<Player> s = myGame.getPlayers();
                    int i = 0;
                    while ( !s.isEmpty() )
                    {
                        System.err.println( "Create1" );

                        Player p = s.remove();
                        ImageIcon icon = symbols[i];
                        JLabel lb = new JLabel( icon );
                        imq.add( lb );
                        board.add( lb, JLayeredPane.PALETTE_LAYER );
                        lb.setLocation( p.getX(), p.getY() );
                        lb.setSize( 24, 15 );
                        i++;
                    }
                    return;
                }

            }
            else
            {
                numAI = Integer.parseInt( e.getActionCommand() );
                display.append( numAI + "\n" );
                myGame = new GameBoard( numHumans, numAI, display, symbols );
                players = myGame.getPlayers();
                buttonBox.setVisible( false );
                buttonBox.removeAll();
                button1.removeActionListener( playerListener );
                button2.removeActionListener( playerListener );
                button3.removeActionListener( playerListener );
                button4.removeActionListener( playerListener );
                button5.removeActionListener( playerListener );
                button6.removeActionListener( playerListener );

                System.err.println( "C " + players.size() );
                curr = players.peek();
                System.err.println( "D " + players.size() );
                myGame.startGUI( curr, display );
                if ( curr.isHuman )
                {
                    buttonBox.add( roll );
                }
                else
                {
                    ( (AI)curr ).run( display );
                    JLabel curIm = imq.remove();
                    curIm.setLocation( curr.getX(), curr.getY() );
                    imq.add( curIm );
                    board.repaint();
                    buttonBox.add( confirm );
                }
                buttonBox.setVisible( true );
                Queue<Player> s = myGame.getPlayers();
                int i = 0;
                while ( !s.isEmpty() )
                {
                    System.err.println( "Create2" );

                    Player p = s.remove();
                    ImageIcon icon = symbols[i];
                    JLabel lb = new JLabel( icon );
                    imq.add( lb );
                    board.add( lb, JLayeredPane.PALETTE_LAYER );
                    lb.setLocation( p.getX(), p.getY() );
                    lb.setSize( 24, 15 );
                    i++;
                }
            }
        }
    }


    /**
     * This sub class listens to a roll
     *
     * @author William Wang, Aditya Kotak, Rahul Sarathy.
     * @version May 23, 2015
     * @author Period: 6
     * @author Assignment: MonopolyGame
     *
     * @author Sources: none
     */
    public class MyRollListener implements ActionListener
    {
        /**
         * rolls the dice and moves the player
         */
        public void actionPerformed( ActionEvent e )
        {
            curr.run( display );
            JLabel curIm = imq.remove();
            curIm.setLocation( curr.getX(), curr.getY() );
            imq.add( curIm );
            board.repaint();
            buttonBox.setVisible( false );
            buttonBox.removeAll();
            if ( curr.getCurrSpace().getValue().buyable )
            {
                if ( curr.getCurrSpace().getValue().canHaveBuildings )
                {
                    if ( ( (PropertySpace)curr.getCurrSpace().getValue() ).getOwner() == null )
                    {
                        if ( curr.getMoney() > ( (PropertySpace)curr.getCurrSpace()
                            .getValue() ).getPrice() )
                        {
                            button1.addActionListener( propListener );
                            button2.addActionListener( refuse );
                            buttonBox.add( button1 );
                            buttonBox.add( button2 );
                            display.append( "You may buy this property! Price: $"
                                + ( (PropertySpace)curr.getCurrSpace()
                                    .getValue() ).getPrice() + "\n" );
                            display.append( "Please select an option:\n" );
                            display.append( "(1) Buy Property\n" );
                            display.append( "(2) Don't Buy Property\n" );
                            buttonBox.setVisible( true );
                            return;
                        }
                    }
                    else
                    {
                        if ( !curr.equals( ( (PropertySpace)curr.getCurrSpace()
                            .getValue() ).getOwner() ) )
                        {
                            int pay = curr.payPlayer( ( (PropertySpace)curr.getCurrSpace()
                                .getValue() ).getRent( ( (PropertySpace)curr.getCurrSpace()
                                .getValue() ).getOwner() ),
                                ( (PropertySpace)curr.getCurrSpace().getValue() ).getOwner() );
                            display.append( "You paid "
                                + ( (PropertySpace)curr.getCurrSpace()
                                    .getValue() ).getOwner().getName() + " $"
                                + pay + ".\n" );
                        }
                    }
                    if ( curr.canRollAgain )
                    {
                        display.append( "You rolled doubles! You may roll again.\n" );
                        buttonBox.add( roll );
                        buttonBox.setVisible( true );
                        return;
                    }
                    else
                    {
                        curr.end( display );
                        button1.addActionListener( endListener );
                        buttonBox.add( button1 );
                        if ( myGame.getOp2() )
                        {
                            boolean has = false;
                            for ( int i = 0; i < button2.getActionListeners().length; i++ )
                            {
                                if ( button2.getActionListeners()[i].equals( houseListener ) )
                                {
                                    has = true;
                                }
                            }
                            if ( !has )
                            {
                                button2.addActionListener( houseListener );
                            }
                            buttonBox.add( button2 );
                        }
                        if ( myGame.getOp3() )
                        {
                            boolean has = false;
                            for ( int i = 0; i < button3.getActionListeners().length; i++ )
                            {
                                if ( button3.getActionListeners()[i].equals( hotelListener ) )
                                {
                                    has = true;
                                }
                            }
                            if ( !has )
                            {
                                button3.addActionListener( hotelListener );
                            }
                            buttonBox.add( button3 );
                        }
                        buttonBox.setVisible( true );
                        return;
                    }
                }
                else if ( curr.getCurrSpace().getValue().isRailroad )
                {
                    if ( ( (RailroadSpace)curr.getCurrSpace().getValue() ).getOwner() == null )
                    {
                        if ( curr.getMoney() > 200 )
                        {
                            button1.addActionListener( railListener );
                            button2.addActionListener( refuse );
                            buttonBox.add( button1 );
                            buttonBox.add( button2 );
                            display.append( "You may buy this railroad! Price: $"
                                + 200 + "\n" );
                            display.append( "Please select an option:\n" );
                            display.append( "(1) Buy Railroad\n" );
                            display.append( "(2) Don't Buy Railroad\n" );
                            buttonBox.setVisible( true );
                            return;
                        }
                    }
                    else
                    {
                        if ( !curr.equals( ( (RailroadSpace)curr.getCurrSpace()
                            .getValue() ).getOwner() ) )
                        {
                            int pay = curr.payPlayer( 50 * ( ( (RailroadSpace)curr.getCurrSpace()
                                .getValue() ).getOwner().getRailNum() ),
                                ( (RailroadSpace)curr.getCurrSpace().getValue() ).getOwner() );
                            display.append( "You paid "
                                + ( (RailroadSpace)curr.getCurrSpace()
                                    .getValue() ).getOwner().getName() + " $"
                                + pay + ".\n" );
                        }
                        if ( curr.canRollAgain )
                        {
                            display.append( "You rolled doubles! You may roll again.\n" );
                            buttonBox.add( roll );
                            buttonBox.setVisible( true );
                            return;
                        }
                        else
                        {
                            curr.end( display );
                            button1.addActionListener( endListener );
                            buttonBox.add( button1 );
                            if ( myGame.getOp2() )
                            {
                                boolean has = false;
                                for ( int i = 0; i < button2.getActionListeners().length; i++ )
                                {
                                    if ( button2.getActionListeners()[i].equals( houseListener ) )
                                    {
                                        has = true;
                                    }
                                }
                                if ( !has )
                                {
                                    button2.addActionListener( houseListener );
                                }
                                buttonBox.add( button2 );
                            }
                            if ( myGame.getOp3() )
                            {
                                boolean has = false;
                                for ( int i = 0; i < button3.getActionListeners().length; i++ )
                                {
                                    if ( button3.getActionListeners()[i].equals( hotelListener ) )
                                    {
                                        has = true;
                                    }
                                }
                                if ( !has )
                                {
                                    button3.addActionListener( hotelListener );
                                }
                                buttonBox.add( button3 );
                            }
                            buttonBox.setVisible( true );
                            return;
                        }
                    }
                }
                else if ( curr.getCurrSpace().getValue().isUtility )
                {
                    if ( ( (UtilitySpace)curr.getCurrSpace().getValue() ).getOwner() == null )
                    {
                        if ( curr.getMoney() > 150 )
                        {
                            button1.addActionListener( utilListener );
                            button2.addActionListener( refuse );
                            buttonBox.add( button1 );
                            buttonBox.add( button2 );
                            display.append( "You may buy this utility! Price: $"
                                + 150 + "\n" );
                            display.append( "Please select an option:\n" );
                            display.append( "(1) Buy Utility\n" );
                            display.append( "(2) Don't Buy Utility\n" );
                            buttonBox.setVisible( true );
                            return;
                        }
                    }
                    else
                    {
                        if ( !curr.equals( ( (UtilitySpace)curr.getCurrSpace()
                            .getValue() ).getOwner() ) )
                        {
                            if ( ( (UtilitySpace)curr.getCurrSpace().getValue() ).getOwner()
                                .getUtilNum() == 1 )
                            {
                                int pay = curr.payPlayer( curr.getMoveAmount() * 4,
                                    ( (UtilitySpace)curr.getCurrSpace()
                                        .getValue() ).getOwner() );
                                display.append( "You paid "
                                    + ( (UtilitySpace)curr.getCurrSpace()
                                        .getValue() ).getOwner().getName()
                                    + " $" + pay + ".\n" );
                            }
                            else if ( ( (UtilitySpace)curr.getCurrSpace()
                                .getValue() ).getOwner().getUtilNum() == 2 )
                            {
                                int pay = curr.payPlayer( curr.getMoveAmount() * 10,
                                    ( (UtilitySpace)curr.getCurrSpace()
                                        .getValue() ).getOwner() );
                                display.append( "You paid "
                                    + ( (UtilitySpace)curr.getCurrSpace()
                                        .getValue() ).getOwner().getName()
                                    + " $" + pay + ".\n" );
                            }
                        }
                        if ( curr.canRollAgain )
                        {
                            display.append( "You rolled doubles! You may roll again.\n" );
                            buttonBox.add( roll );
                            buttonBox.setVisible( true );
                            return;
                        }
                        else
                        {
                            curr.end( display );
                            button1.addActionListener( endListener );
                            buttonBox.add( button1 );
                            if ( myGame.getOp2() )
                            {
                                boolean has = false;
                                for ( int i = 0; i < button2.getActionListeners().length; i++ )
                                {
                                    if ( button2.getActionListeners()[i].equals( houseListener ) )
                                    {
                                        has = true;
                                    }
                                }
                                if ( !has )
                                {
                                    button2.addActionListener( houseListener );
                                }
                                buttonBox.add( button2 );
                            }
                            if ( myGame.getOp3() )
                            {
                                boolean has = false;
                                for ( int i = 0; i < button3.getActionListeners().length; i++ )
                                {
                                    if ( button3.getActionListeners()[i].equals( hotelListener ) )
                                    {
                                        has = true;
                                    }
                                }
                                if ( !has )
                                {
                                    button3.addActionListener( hotelListener );
                                }
                                buttonBox.add( button3 );
                            }
                            buttonBox.setVisible( true );
                            return;
                        }
                    }
                }
            }
            else
            {
                curr.getCurrSpace().getValue().act( curr, display );
            }
            if ( curr.canRollAgain )
            {
                display.append( "You rolled doubles! You may roll again.\n" );
                buttonBox.add( roll );
                buttonBox.setVisible( true );
                return;
            }
            else
            {
                curr.end( display );
                button1.addActionListener( endListener );
                buttonBox.add( button1 );
                if ( myGame.getOp2() )
                {
                    boolean has = false;
                    for ( int i = 0; i < button2.getActionListeners().length; i++ )
                    {
                        if ( button2.getActionListeners()[i].equals( houseListener ) )
                        {
                            has = true;
                        }
                    }
                    if ( !has )
                    {
                        button2.addActionListener( houseListener );
                    }
                    buttonBox.add( button2 );
                }
                if ( myGame.getOp3() )
                {
                    boolean has = false;
                    for ( int i = 0; i < button3.getActionListeners().length; i++ )
                    {
                        if ( button3.getActionListeners()[i].equals( hotelListener ) )
                        {
                            has = true;
                        }
                    }
                    if ( !has )
                    {
                        button3.addActionListener( hotelListener );
                    }
                    buttonBox.add( button3 );
                }
                buttonBox.setVisible( true );
                return;
            }
        }
    }


    /**
     * This sub class ends a turn and presents options
     *
     * @author William Wang, Aditya Kotak, Rahul Sarathy.
     * @version May 23, 2015
     * @author Period: 6
     * @author Assignment: MonopolyGame
     *
     * @author Sources: none
     */
    public class MyEndTurnListener implements ActionListener
    {
        /**
         * Presents the necessary options
         */
        public void actionPerformed( ActionEvent e )
        {
            // System.err.println( "End1" );
            if ( curr.getMoney() <= 0 )
            {
                curr.mortgage( curr,
                    curr.getProperties(),
                    curr.getRailroads(),
                    curr.getUtilities() );
            }
            if ( curr.getMoney() > 0 )
            {
                players.remove();
                System.err.println( "E " + players.size() );
                players.add( curr );
                System.err.println( "F " + players.size() );
                buttonBox.setVisible( false );
                buttonBox.removeAll();
                button1.removeActionListener( endListener );
                button2.removeActionListener( endListener );
                button2.removeActionListener( houseListener );
                button3.removeActionListener( hotelListener );
                System.err.println( "G " + players.size() );
                curr = players.peek();

                System.err.println( "H " + players.size() );
                myGame.startGUI( curr, display );
                if ( curr.getMoney() <= 0 )
                {
                    if ( curr.isHuman )
                    {
                        numHumans--;
                    }
                    players.remove();
                    button1.removeActionListener( endListener );
                    button2.removeActionListener( endListener );
                    display.append( curr.getName() + " lost! " + curr.getName()
                        + " has been removed from the game.\n" );
                    if ( numHumans == 0 || players.size() < 2 )
                    {
                        buttonBox.setVisible( false );
                        buttonBox.removeAll();
                        buttonBox.add( playAgain );
                        buttonBox.setVisible( true );
                        int win = players.peek().calculateWin();
                        Player winner = players.remove();
                        while ( players != null )
                        {
                            if ( players.peek().calculateWin() > win )
                            {
                                winner = players.peek();
                            }
                            players.remove();
                        }
                        display.append( winner.getName() + " has won!" );
                    }
                    return;
                }
                if ( curr.isHuman )
                {
                    if ( !curr.getInJail() )
                    {
                        // System.err.println( "End2" );
                        buttonBox.add( roll );
                        // System.err.println( "End3" );
                    }
                    else
                    {
                        button1.addActionListener( rollJail );
                        button2.addActionListener( payJail );
                        button3.addActionListener( useCard );
                        buttonBox.add( button1 );
                        buttonBox.add( button2 );
                        buttonBox.add( button3 );
                        display.append( "\n" );
                        display.append( "Please select an option:\n" );
                        display.append( "   (1) Roll Dice\n" );
                        display.append( "   (2) Pay $50\n" );
                        display.append( "   (3) Use Get-Out-Of-Jail-Free Card\n" );
                    }
                }
                else
                {
                    // System.err.println( "End4" );
                    ( (AI)curr ).run();
                    JLabel curIm = imq.remove();
                    curIm.setLocation( curr.getX(), curr.getY() );
                    imq.add( curIm );
                    board.repaint();
                    buttonBox.add( confirm );
                    // System.err.println( "End5" );
                }
                // System.err.println( "End6" );
                buttonBox.setVisible( true );
            }
            else
            {
                players.remove();
                button1.removeActionListener( endListener );
                button2.removeActionListener( endListener );
                display.append( curr.getName() + " lost! " + curr.getName()
                    + " has been removed from the game.\n" );
                if ( players.size() < 2 )
                {
                    buttonBox.setVisible( false );
                    buttonBox.removeAll();
                    buttonBox.add( playAgain );
                    buttonBox.setVisible( true );
                    display.append( players.peek().getName() + " has won!" );
                }
            }

        }
    }


    /**
     * This sub class allows buying houses
     *
     * @author William Wang, Aditya Kotak, Rahul Sarathy.
     * @version May 23, 2015
     * @author Period: 6
     * @author Assignment: MonopolyGame
     *
     * @author Sources: none
     */
    public class MyBuyHouseListener implements ActionListener
    {
        /**
         * Checks if buying houses is allowed and implements buying houses.
         */
        public void actionPerformed( ActionEvent e )
        {
            display.append( "Please type the name of the property you would like to buy a house for and press Enter.\n" );
            display.append( "If you do not want to buy a house, press Quit.\n" );
            buttonBox.setVisible( false );
            buttonBox.removeAll();
            button1.removeActionListener( endListener );
            button2.removeActionListener( houseListener );
            button3.removeActionListener( hotelListener );
            enter.removeActionListener( enterHotelListener );
            boolean has = false;
            for ( int i = 0; i < enter.getActionListeners().length; i++ )
            {
                if ( enter.getActionListeners()[i].equals( enterHouseListener ) )
                {
                    has = true;
                }
            }
            if ( !has )
            {
                enter.addActionListener( enterHouseListener );
            }
            buttonBox.add( field );
            buttonBox.add( enter );
            buttonBox.add( quit );
            buttonBox.setVisible( true );
        }
    }


    /**
     * This sub class allows player to buy hotels
     *
     * @author William Wang, Aditya Kotak, Rahul Sarathy.
     * @version May 23, 2015
     * @author Period: 6
     * @author Assignment: MonopolyGame
     *
     * @author Sources: none
     */
    public class MyBuyHotelListener implements ActionListener
    {
        /**
         * Helps to buy hotels
         */
        public void actionPerformed( ActionEvent e )
        {
            display.append( "Please type the name of the property you would like to buy a hotel for and press Enter.\n" );
            display.append( "If you do not want to buy a hotel, press Quit.\n" );
            buttonBox.setVisible( false );
            buttonBox.removeAll();
            button1.removeActionListener( endListener );
            button2.removeActionListener( houseListener );
            button3.removeActionListener( hotelListener );
            enter.removeActionListener( enterHouseListener );
            boolean has = false;
            for ( int i = 0; i < enter.getActionListeners().length; i++ )
            {
                if ( enter.getActionListeners()[i].equals( enterHotelListener ) )
                {
                    has = true;
                }
            }
            if ( !has )
            {
                enter.addActionListener( enterHotelListener );
            }
            buttonBox.add( field );
            buttonBox.add( enter );
            buttonBox.add( quit );
            buttonBox.setVisible( true );
        }
    }


    /**
     * This sub class allows properties to be bought
     *
     * @author William Wang, Aditya Kotak, Rahul Sarathy.
     * @version May 23, 2015
     * @author Period: 6
     * @author Assignment: MonopolyGame
     *
     * @author Sources: none
     */
    public class MyBuyPropListener implements ActionListener
    {
        /**
         * allows properties to be bought
         */
        public void actionPerformed( ActionEvent e )
        {
            // System.err.println( "BuyProp1" );
            display.append( curr.getName() + " bought "
                + curr.getCurrSpace().getValue().getName() + ".\n" );
            curr.payBank( curr.getCurrSpace().getValue().getPrice() );
            curr.buyProp( (PropertySpace)curr.getCurrSpace().getValue() );
            ( (PropertySpace)curr.getCurrSpace().getValue() ).setOwner( curr );
            display.append( "Money: $" + curr.getMoney() + "\n" );
            display.append( "Properties: " + curr.getProperties() + "\n" );
            display.append( "Railroads: " + curr.getRailroads() + "\n" );
            display.append( "Utilities: " + curr.getUtilities() + "\n" );
            if ( curr.hasAllPropsInGroup( (PropertySpace)curr.getCurrSpace()
                .getValue() ) )
            {
                display.append( "You may now buy houses on this property block!\n" );
                display.append( "These properties are now double rent!\n" );
            }
            // curr.end( display );
            buttonBox.setVisible( false );
            buttonBox.removeAll();
            button1.removeActionListener( propListener );
            button2.removeActionListener( refuse );
            if ( curr.canRollAgain )
            {
                display.append( curr.getName() + " rolled doubles! "
                    + curr.getName() + " may roll again.\n" );
                buttonBox.add( roll );
            }
            else
            {
                curr.end( display );
                button1.addActionListener( endListener );
                buttonBox.add( button1 );
                if ( myGame.getOp2() )
                {
                    // System.err.println( "BuyProp2" );

                    boolean has = false;
                    for ( int i = 0; i < button2.getActionListeners().length; i++ )
                    {
                        if ( button2.getActionListeners()[i].equals( houseListener ) )
                        {
                            has = true;
                        }
                    }
                    if ( !has )
                    {
                        button2.addActionListener( houseListener );
                    }
                    buttonBox.add( button2 );
                    // System.err.println( "BuyProp3" );
                }
                // System.err.println( "BuyProp4" );
                if ( myGame.getOp3() )
                {
                    // System.err.println( "BuyProp5" );
                    boolean has = false;
                    for ( int i = 0; i < button3.getActionListeners().length; i++ )
                    {
                        if ( button3.getActionListeners()[i].equals( hotelListener ) )
                        {
                            has = true;
                        }
                    }
                    if ( !has )
                    {
                        button3.addActionListener( hotelListener );
                    }
                    buttonBox.add( button3 );
                    // System.err.println( "BuyProp6" );
                }
                // System.err.println( "BuyProp7" );
            }
            buttonBox.setVisible( true );
        }
    }


    /**
     * This sub class allows railroads to be bought
     *
     * @author William Wang, Aditya Kotak, Rahul Sarathy.
     * @version May 23, 2015
     * @author Period: 6
     * @author Assignment: MonopolyGame
     *
     * @author Sources: none
     */
    public class MyBuyRailListener implements ActionListener
    {
        /**
         * allows railroads to be bought
         */
        public void actionPerformed( ActionEvent e )
        {
            // System.err.println( "BuyRail1" );
            display.append( curr.getName() + " bought "
                + curr.getCurrSpace().getValue().getName() + ".\n" );
            curr.payBank( curr.getCurrSpace().getValue().getPrice() );
            curr.buyRail( (RailroadSpace)curr.getCurrSpace().getValue() );
            ( (RailroadSpace)curr.getCurrSpace().getValue() ).setOwner( curr );
            display.append( "Money: $" + curr.getMoney() + "\n" );
            display.append( "Properties: " + curr.getProperties() + "\n" );
            display.append( "Railroads: " + curr.getRailroads() + "\n" );
            display.append( "Utilities: " + curr.getUtilities() + "\n" );
            // curr.end( display );
            buttonBox.setVisible( false );
            buttonBox.removeAll();
            button1.removeActionListener( railListener );
            button2.removeActionListener( refuse );
            if ( curr.canRollAgain )
            {
                display.append( curr.getName() + " rolled doubles! "
                    + curr.getName() + " may roll again.\n" );
                buttonBox.add( roll );
            }
            else
            {
                curr.end( display );
                button1.addActionListener( endListener );

                buttonBox.add( button1 );
                if ( myGame.getOp2() )
                {
                    // System.err.println( "BuyProp2" );

                    boolean has = false;
                    for ( int i = 0; i < button2.getActionListeners().length; i++ )
                    {
                        if ( button2.getActionListeners()[i].equals( houseListener ) )
                        {
                            has = true;
                        }
                    }
                    if ( !has )
                    {
                        button2.addActionListener( houseListener );
                    }
                    buttonBox.add( button2 );
                    // System.err.println( "BuyProp3" );
                }
                // System.err.println( "BuyProp4" );
                if ( myGame.getOp3() )
                {
                    // System.err.println( "BuyProp5" );
                    boolean has = false;
                    for ( int i = 0; i < button3.getActionListeners().length; i++ )
                    {
                        if ( button3.getActionListeners()[i].equals( hotelListener ) )
                        {
                            has = true;
                        }
                    }
                    if ( !has )
                    {
                        button3.addActionListener( hotelListener );
                    }
                    buttonBox.add( button3 );
                    // System.err.println( "BuyProp6" );
                }
                // System.err.println( "BuyProp7" );
            }
            buttonBox.setVisible( true );
        }
    }


    /**
     * This sub class allows utilities to be bought
     *
     * @author William Wang, Aditya Kotak, Rahul Sarathy.
     * @version May 23, 2015
     * @author Period: 6
     * @author Assignment: MonopolyGame
     *
     * @author Sources: none
     */
    public class MyBuyUtilListener implements ActionListener
    {
        /**
         * helps to buy utilities.
         */
        public void actionPerformed( ActionEvent e )
        {
            // System.err.println( "BuyUtil1" );
            display.append( curr.getName() + " bought "
                + curr.getCurrSpace().getValue().getName() + ".\n" );
            curr.payBank( curr.getCurrSpace().getValue().getPrice() );
            curr.buyUtil( (UtilitySpace)curr.getCurrSpace().getValue() );
            ( (UtilitySpace)curr.getCurrSpace().getValue() ).setOwner( curr );
            display.append( "Money: $" + curr.getMoney() + "\n" );
            display.append( "Properties: " + curr.getProperties() + "\n" );
            display.append( "Railroads: " + curr.getRailroads() + "\n" );
            display.append( "Utilities: " + curr.getUtilities() + "\n" );
            // curr.end( display );
            buttonBox.setVisible( false );
            buttonBox.removeAll();
            button1.removeActionListener( utilListener );
            button2.removeActionListener( refuse );
            System.err.println( curr.canRollAgain );
            if ( curr.canRollAgain )
            {
                display.append( curr.getName() + " rolled doubles! "
                    + curr.getName() + " may roll again.\n" );
                buttonBox.add( roll );
            }
            else
            {
                curr.end( display );
                button1.addActionListener( endListener );

                buttonBox.add( button1 );
                if ( myGame.getOp2() )
                {
                    // System.err.println( "BuyProp2" );

                    boolean has = false;
                    for ( int i = 0; i < button2.getActionListeners().length; i++ )
                    {
                        if ( button2.getActionListeners()[i].equals( houseListener ) )
                        {
                            has = true;
                        }
                    }
                    if ( !has )
                    {
                        button2.addActionListener( houseListener );
                    }
                    buttonBox.add( button2 );
                    // System.err.println( "BuyProp3" );
                }
                // System.err.println( "BuyProp4" );
                if ( myGame.getOp3() )
                {
                    // System.err.println( "BuyProp5" );
                    boolean has = false;
                    for ( int i = 0; i < button3.getActionListeners().length; i++ )
                    {
                        if ( button3.getActionListeners()[i].equals( hotelListener ) )
                        {
                            has = true;
                        }
                    }
                    if ( !has )
                    {
                        button3.addActionListener( hotelListener );
                    }
                    buttonBox.add( button3 );
                    // System.err.println( "BuyProp6" );
                }
                // System.err.println( "BuyProp7" );
            }
            buttonBox.setVisible( true );
        }
    }


    /**
     * This sub class allows players to roll out of jail
     *
     * @author William Wang, Aditya Kotak, Rahul Sarathy.
     * @version May 23, 2015
     * @author Period: 6
     * @author Assignment: MonopolyGame
     *
     * @author Sources: none
     */
    public class MyRollJailListener implements ActionListener
    {
        /**
         * helps roll in jail
         */
        public void actionPerformed( ActionEvent e )
        {
            ( (JailSpace)myGame.getJail().getValue() ).inJailRollDie( myGame.getDice(),
                curr );
            buttonBox.setVisible( false );
            buttonBox.removeAll();
            button1.removeActionListener( rollJail );
            button2.removeActionListener( payJail );
            button3.removeActionListener( useCard );
            button1.addActionListener( endListener );
            button1.addActionListener( endListener );
            buttonBox.add( button1 );
            if ( myGame.getOp2() )
            {
                boolean has = false;
                for ( int i = 0; i < button2.getActionListeners().length; i++ )
                {
                    if ( button2.getActionListeners()[i].equals( houseListener ) )
                    {
                        has = true;
                    }
                }
                if ( !has )
                {
                    button2.addActionListener( houseListener );
                }
                buttonBox.add( button2 );
            }
            if ( myGame.getOp3() )
            {
                boolean has = false;
                for ( int i = 0; i < button3.getActionListeners().length; i++ )
                {
                    if ( button3.getActionListeners()[i].equals( hotelListener ) )
                    {
                        has = true;
                    }
                }
                if ( !has )
                {
                    button3.addActionListener( hotelListener );
                }
                buttonBox.add( button3 );
            }
            buttonBox.setVisible( true );
            curr.end( display );
        }
    }


    /**
     * This sub class allows players to pay out of jail
     *
     * @author William Wang, Aditya Kotak, Rahul Sarathy.
     * @version May 23, 2015
     * @author Period: 6
     * @author Assignment: MonopolyGame
     *
     * @author Sources: none
     */
    public class MyPayJailListener implements ActionListener
    {
        /**
         * implements paying out of jail.
         */
        public void actionPerformed( ActionEvent e )
        {
            if ( curr.getMoney() > 50 )
            {
                display.append( "You paid $50! You are free from Jail!\n" );
                curr.payBank( 50 );
                curr.setInJail( false );
                ( (JailSpace)myGame.getJail().getValue() ).removeFromJail( curr );
                buttonBox.setVisible( false );
                buttonBox.removeAll();
                button1.removeActionListener( rollJail );
                button2.removeActionListener( payJail );
                button3.removeActionListener( useCard );
                button1.addActionListener( endListener );
                buttonBox.add( button1 );
                if ( myGame.getOp2() )
                {
                    boolean has = false;
                    for ( int i = 0; i < button2.getActionListeners().length; i++ )
                    {
                        if ( button2.getActionListeners()[i].equals( houseListener ) )
                        {
                            has = true;
                        }
                    }
                    if ( !has )
                    {
                        button2.addActionListener( houseListener );
                    }
                    buttonBox.add( button2 );
                }
                if ( myGame.getOp3() )
                {
                    boolean has = false;
                    for ( int i = 0; i < button3.getActionListeners().length; i++ )
                    {
                        if ( button3.getActionListeners()[i].equals( hotelListener ) )
                        {
                            has = true;
                        }
                    }
                    if ( !has )
                    {
                        button3.addActionListener( hotelListener );
                    }
                    buttonBox.add( button3 );
                }
                buttonBox.setVisible( true );
                curr.end( display );
            }
            else
            {
                display.append( "You do not have enough money.\n" );
            }
        }
    }


    /**
     * This sub class allows players to use a card to leave jail
     *
     * @author William Wang, Aditya Kotak, Rahul Sarathy.
     * @version May 23, 2015
     * @author Period: 6
     * @author Assignment: MonopolyGame
     *
     * @author Sources: none
     */
    public class MyUseCardListener implements ActionListener
    {
        /**
         * this allows a player to use a card out of jail.
         */
        public void actionPerformed( ActionEvent e )
        {
            // ( (JailSpace)myGame.getJail().getValue() ).useCard( curr );
            if ( curr.getHasJailFree() )
            {
                display.append( curr.getName()
                    + " used a Get-Out-Of-Jail-Free card!\n" );
                curr.setHasJailFree( false );
                curr.setInJail( false );
                ( (JailSpace)myGame.getJail().getValue() ).removeFromJail( curr );
                buttonBox.setVisible( false );
                buttonBox.removeAll();
                button1.removeActionListener( rollJail );
                button2.removeActionListener( payJail );
                button3.removeActionListener( useCard );
                button1.addActionListener( endListener );
                buttonBox.add( button1 );
                if ( myGame.getOp2() )
                {
                    boolean has = false;
                    for ( int i = 0; i < button2.getActionListeners().length; i++ )
                    {
                        if ( button2.getActionListeners()[i].equals( houseListener ) )
                        {
                            has = true;
                        }
                    }
                    if ( !has )
                    {
                        button2.addActionListener( houseListener );
                    }
                    buttonBox.add( button2 );
                }
                if ( myGame.getOp3() )
                {
                    boolean has = false;
                    for ( int i = 0; i < button3.getActionListeners().length; i++ )
                    {
                        if ( button3.getActionListeners()[i].equals( hotelListener ) )
                        {
                            has = true;
                        }
                    }
                    if ( !has )
                    {
                        button3.addActionListener( hotelListener );
                    }
                    buttonBox.add( button3 );
                }
                buttonBox.setVisible( true );
                buttonBox.setVisible( true );
                curr.end( display );
            }
            else
            {
                display.append( "You do not have a Get-Out-Of-Jail-Free card.\n" );
            }

        }
    }


    /**
     * This sub class allows players to enter in which property they want to buy
     * a house for
     *
     * @author William Wang, Aditya Kotak, Rahul Sarathy.
     * @version May 23, 2015
     * @author Period: 6
     * @author Assignment: MonopolyGame
     *
     * @author Sources: none
     */
    public class MyEnterHouseListener implements ActionListener
    {
        /**
         * helps in buying houses
         */
        public void actionPerformed( ActionEvent e )
        {
            if ( !field.getText().equals( "" ) )
            {
                for ( int i = 0; i < myGame.getPropNames().length; i++ )
                {
                    if ( ( myGame.getPropNames()[i] + " Avenue" ).equals( field.getText() ) )
                    {
                        currP = (PropertySpace)( myGame.getProperty( field.getText() ).getValue() );
                        if ( currP.getOwner().equals( curr ) )
                        {
                            if ( curr.hasAllPropsInGroup( currP ) )
                            {
                                field.setText( "" );
                                int maxHouses = 4 - currP.getNumOfHouses();
                                buttonBox.setVisible( false );
                                button1.removeActionListener( endListener );
                                button2.removeActionListener( houseListener );
                                buttonBox.removeAll();
                                for ( JButton b : buttonArray )
                                {
                                    if ( Integer.parseInt( b.getActionCommand() ) <= maxHouses )
                                    {
                                        b.addActionListener( buyHouse );
                                        buttonBox.add( b );
                                    }
                                }
                                // quit.removeActionListener( endListener );
                                // quit.addActionListener( quitBuy );
                                buttonBox.add( quit );
                                buttonBox.setVisible( true );
                                display.append( "How many houses would you like to buy?\n" );
                                return;
                            }
                            else
                            {
                                display.append( "You do not have all properties in this group yet.\n" );
                                return;
                            }
                        }
                        else
                        {
                            display.append( "You do not own " + currP.getName()
                                + ".\n" );
                            return;
                        }
                    }
                }
                display.append( "That is not a property. Please try again.\n" );
                return;
            }
            else
            {
                display.append( "Please enter a property name.\n" );
                return;
            }
        }
    }


    /**
     * This sub class allows players to enter in the number of houses they want
     * to buy
     *
     * @author William Wang, Aditya Kotak, Rahul Sarathy.
     * @version May 23, 2015
     * @author Period: 6
     * @author Assignment: MonopolyGame
     *
     * @author Sources: none
     */
    public class MyBuyNumHousesListener implements ActionListener
    {
        /**
         * helps in buying houses
         */
        public void actionPerformed( ActionEvent e )
        {
            int numHouses = Integer.parseInt( e.getActionCommand() );
            if ( curr.getMoney() > numHouses * currP.getHousePrice() )
            {
                curr.payBank( numHouses * currP.getHousePrice() );
                for ( int i = 0; i < numHouses; i++ )
                {
                    currP.addHouse();
                }
                buttonBox.setVisible( false );
                buttonBox.removeAll();
                button1.removeActionListener( buyHouse );
                button2.removeActionListener( buyHouse );
                button3.removeActionListener( buyHouse );
                button4.removeActionListener( buyHouse );
                button1.addActionListener( endListener );
                buttonBox.add( button1 );

                if ( myGame.getOp2() )
                {
                    boolean has = false;
                    for ( int i = 0; i < button2.getActionListeners().length; i++ )
                    {
                        if ( button2.getActionListeners()[i].equals( houseListener ) )
                        {
                            has = true;
                        }
                    }
                    if ( !has )
                    {
                        button2.addActionListener( houseListener );
                    }
                    buttonBox.add( button2 );
                }
                if ( myGame.getOp3() )
                {
                    boolean has = false;
                    for ( int i = 0; i < button3.getActionListeners().length; i++ )
                    {
                        if ( button3.getActionListeners()[i].equals( hotelListener ) )
                        {
                            has = true;
                        }
                    }
                    if ( !has )
                    {
                        button3.addActionListener( hotelListener );
                    }
                    buttonBox.add( button3 );
                }

                display.append( "You bought " + numHouses + " house(s) on "
                    + currP.getName() + " for $" + numHouses
                    * currP.getHousePrice() + ".\n" );
                display.append( "You now have " + currP.getNumOfHouses()
                    + " houses on " + currP.getName() + ".\n" );
                display.append( "Money: $" + curr.getMoney() + "\n" );
                if ( curr.allPropsInGroupHave4Houses( currP ) )
                {
                    display.append( "You may now buy hotels for this group!\n" );
                    buttonBox.setVisible( false );
                    button3.addActionListener( hotelListener );
                    buttonBox.add( button3 );
                    buttonBox.setVisible( true );
                }
                buttonBox.setVisible( true );
                curr.end( display );
            }
            else
            {
                display.append( "You do not have enough money to buy "
                    + numHouses + " house(s).\n" );
            }
        }
    }


    // public class MyCheatListener implements ActionListener
    // {
    // public void actionPerformed( ActionEvent e )
    // {
    //
    // }
    // }

    /**
     * This class restarts the game
     *
     * @author William Wang, Aditya Kotak, Rahul Sarathy.
     * @version May 23, 2015
     * @author Period: 6
     * @author Assignment: MonopolyGame
     *
     * @author Sources: none
     */
    public class MyPlayAgainListener implements ActionListener
    {
        /**
         * restarts the game when someone is bankrupt
         */
        public void actionPerformed( ActionEvent e )
        {
            chooseHumans = true;
            display.setText( "" );
            display.append( "Welcome to Monopoly!\n" );
            display.append( "How many human players are playing? " );
            buttonBox.setVisible( false );
            buttonBox.removeAll();
            button1.addActionListener( playerListener );
            button2.addActionListener( playerListener );
            button3.addActionListener( playerListener );
            button4.addActionListener( playerListener );
            button5.addActionListener( playerListener );
            button6.addActionListener( playerListener );
            buttonBox.add( button1 );
            buttonBox.add( button2 );
            buttonBox.add( button3 );
            buttonBox.add( button4 );
            buttonBox.add( button5 );
            buttonBox.add( button6 );
            buttonBox.setVisible( true );
        }
    }


    /**
     * This sub class allows players to quit buying houses or hotels
     *
     * @author William Wang, Aditya Kotak, Rahul Sarathy.
     * @version May 23, 2015
     * @author Period: 6
     * @author Assignment: MonopolyGame
     *
     * @author Sources: none
     */
    public class MyQuitBuyListener implements ActionListener
    {
        /**
         * Helps in quitting buying.
         */
        public void actionPerformed( ActionEvent e )
        {
            curr.end( display );
            buttonBox.setVisible( false );
            buttonBox.removeAll();
            button1.removeActionListener( buyHouse );
            button2.removeActionListener( buyHouse );
            button3.removeActionListener( buyHouse );
            button4.removeActionListener( buyHouse );
            // quit.removeActionListener(quitBuy);
            // quit.addActionListener( endListener );
            button1.addActionListener( endListener );
            buttonBox.add( button1 );
            if ( myGame.getOp2() )
            {
                boolean has = false;
                for ( int i = 0; i < button2.getActionListeners().length; i++ )
                {
                    if ( button2.getActionListeners()[i].equals( houseListener ) )
                    {
                        has = true;
                    }
                }
                if ( !has )
                {
                    button2.addActionListener( houseListener );
                }
                buttonBox.add( button2 );
            }
            if ( myGame.getOp3() )
            {
                boolean has = false;
                for ( int i = 0; i < button3.getActionListeners().length; i++ )
                {
                    if ( button3.getActionListeners()[i].equals( hotelListener ) )
                    {
                        has = true;
                    }
                }
                if ( !has )
                {
                    button3.addActionListener( hotelListener );
                }
                buttonBox.add( button3 );
            }

            buttonBox.setVisible( true );
        }
    }


    /**
     * This sub class makes sure only when there is enough money can something
     * be bought
     *
     * @author William Wang, Aditya Kotak, Rahul Sarathy.
     * @version May 23, 2015
     * @author Period: 6
     * @author Assignment: MonopolyGame
     *
     * @author Sources: none
     */
    public class MyRefuseListener implements ActionListener
    {
        /**
         * Checks if things can be bought
         */
        public void actionPerformed( ActionEvent e )
        {
            curr.end( display );
            buttonBox.setVisible( false );
            buttonBox.removeAll();
            button1.removeActionListener( propListener );
            button1.removeActionListener( railListener );
            button1.removeActionListener( utilListener );
            button2.removeActionListener( refuse );
            button1.addActionListener( endListener );
            buttonBox.add( button1 );
            if ( myGame.getOp2() )
            {
                boolean has = false;
                for ( int i = 0; i < button2.getActionListeners().length; i++ )
                {
                    if ( button2.getActionListeners()[i].equals( houseListener ) )
                    {
                        has = true;
                    }
                }
                if ( !has )
                {
                    button2.addActionListener( houseListener );
                }
                buttonBox.add( button2 );
            }
            if ( myGame.getOp3() )
            {
                boolean has = false;
                for ( int i = 0; i < button3.getActionListeners().length; i++ )
                {
                    if ( button3.getActionListeners()[i].equals( hotelListener ) )
                    {
                        has = true;
                    }
                }
                if ( !has )
                {
                    button3.addActionListener( hotelListener );
                }
                buttonBox.add( button3 );
            }
            buttonBox.setVisible( true );
        }
    }


    /**
     * This sub class allows players to enter in which property they want to buy
     * a hotel for
     *
     * @author William Wang, Aditya Kotak, Rahul Sarathy.
     * @version May 23, 2015
     * @author Period: 6
     * @author Assignment: MonopolyGame
     *
     * @author Sources: none
     */
    public class MyEnterHotelListener implements ActionListener
    {
        /**
         * Helps in buying hotels
         */
        public void actionPerformed( ActionEvent e )
        {
            if ( !field.getText().equals( "" ) )
            {
                for ( int i = 0; i < myGame.getPropNames().length; i++ )
                {
                    if ( ( myGame.getPropNames()[i] + " Avenue" ).equals( field.getText() ) )
                    {
                        currP = (PropertySpace)( myGame.getProperty( field.getText() ).getValue() );
                        if ( currP.getOwner().equals( curr ) )
                        {
                            if ( curr.allPropsInGroupHave4Houses( currP ) )
                            {
                                if ( !currP.hasHotel() )
                                {
                                    if ( curr.getMoney() > currP.getHousePrice() )
                                    {
                                        curr.payBank( currP.getHousePrice() );

                                        currP.addHouse();

                                        buttonBox.setVisible( false );
                                        buttonBox.removeAll();
                                        button1.addActionListener( endListener );
                                        buttonBox.add( button1 );

                                        if ( myGame.getOp2() )
                                        {
                                            boolean has = false;
                                            for ( int j = 0; j < button2.getActionListeners().length; j++ )
                                            {
                                                if ( button2.getActionListeners()[i].equals( houseListener ) )
                                                {
                                                    has = true;
                                                }
                                            }
                                            if ( !has )
                                            {
                                                button2.addActionListener( houseListener );
                                            }
                                            buttonBox.add( button2 );
                                        }
                                        if ( myGame.getOp3() )
                                        {
                                            boolean has = false;
                                            for ( int j = 0; j < button3.getActionListeners().length; j++ )
                                            {
                                                if ( button3.getActionListeners()[i].equals( hotelListener ) )
                                                {
                                                    has = true;
                                                }
                                            }
                                            if ( !has )
                                            {
                                                button3.addActionListener( hotelListener );
                                            }
                                            buttonBox.add( button3 );
                                        }

                                        display.append( "You bought a hotel on "
                                            + currP.getName()
                                            + " for $"
                                            + currP.getHousePrice() + ".\n" );
                                        display.append( "Money: $"
                                            + curr.getMoney() + "\n" );
                                        buttonBox.setVisible( true );
                                        curr.end( display );
                                    }
                                    else
                                    {
                                        display.append( "You do not have enough money to buy a hotel.\n" );
                                    }
                                }
                                else
                                {
                                    display.append( "You already have a hotel on this property.\n" );
                                }
                                return;
                            }
                            else
                            {
                                display.append( "Not all properties in this group have 4 houses yet.\n" );
                                return;
                            }
                        }
                        else
                        {
                            display.append( "You do not own " + currP.getName()
                                + ".\n" );
                            return;
                        }
                    }
                }
                display.append( "That is not a property. Please try again.\n" );
                return;
            }
            else
            {
                display.append( "Please enter a property name.\n" );
                return;
            }
        }
    }
}