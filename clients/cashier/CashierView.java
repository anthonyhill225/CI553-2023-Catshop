package clients.cashier;
import catalogue.Basket;
import clients.SoundButton;
import middle.MiddleFactory;
import middle.OrderProcessing;
import middle.StockException;
import middle.StockReadWriter;
import javax.sound.sampled.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;


/**
 * View of the model
 * @author  M A Smith (c) June 2014  
 * 	amended by Anna Wilde
 * version 2.0
 */
public class CashierView implements Observer
{
  private static final int H = 300;       // Height of window pixels
  private static final int W = 400;       // Width  of window pixels
  
  private static final String CHECK  = "Check";
  private static final String BUY    = "Buy";
  private static final String BOUGHT = "Bought";
  private static final String DELETE = "Delete";
 
 


  private final JLabel      theAction  = new JLabel();
  private final JTextField  theInput   = new JTextField();
  private final JTextArea   theOutput  = new JTextArea();
  private final JScrollPane theSP      = new JScrollPane();
  private final JButton     theBtCheck = new JButton( CHECK );  //check if customer wants item 
  private final JButton     theBtBuy   = new JButton( BUY ); //buy item
  private final JButton     theBtBought= new JButton( BOUGHT ); //after item is check buy item
  private final JButton     theBtDelete= new JButton( DELETE ); //delete button
 
  
  
 

  private StockReadWriter theStock     = null;
  private OrderProcessing theOrder     = null;
  private CashierController cont       = null;
  
  /**
   * Construct the view
   * @param rpc   Window in which to construct
   * @param mf    Factor to deliver order and stock objects
   * @param x     x-coordinate of position of window on screen 
   * @param y     y-coordinate of position of window on screen  
   */
          
  public CashierView(  RootPaneContainer rpc,  MiddleFactory mf, int x, int y  )
  {
    try                                           // 
    {      
      theStock = mf.makeStockReadWriter();        // Database access
      theOrder = mf.makeOrderProcessing();        // Process order
    } catch ( Exception e )
    {
      System.out.println("Exception: " + e.getMessage() );
    }
    Container cp         = rpc.getContentPane();    // Content Pane
    Container rootWindow = (Container) rpc;         // Root Window
    cp.setLayout(null);                             // No layout manager
    rootWindow.setSize( W, H );                     // Size of Window
    rootWindow.setLocation( x, y );

    Font f = new Font("Monospaced",Font.PLAIN,12);  // Font f is

    theBtCheck.setBounds( 16, 25+60*0, 80, 40 );    // Check Button
    theBtCheck.addActionListener(                   // Call back code
      e -> cont.doCheck( theInput.getText() ) );
    cp.add( theBtCheck );                           //  Add to canvas
    theBtCheck.setBackground(Color.blue);             //change button background
    theBtCheck.setOpaque(true);                    
    theBtCheck.setBorderPainted(false);              //removes border
    theBtCheck.setForeground(Color.white);           //change text color 
    theBtCheck.addActionListener(new SoundButton("click.wav")); // click nosie
    
    

    theBtBuy.setBounds( 16, 25+60*1, 80, 40 );      // Buy button 
    theBtBuy.addActionListener(                     // Call back code
      e -> cont.doBuy() );
    cp.add( theBtBuy );                             //  Add to canvas
    theBtBuy.setBackground(Color.green);             //change button background
    theBtBuy.setOpaque(true);
    theBtBuy.setBorderPainted(false);
    theBtBuy.setForeground(Color.white);            // change text color to white
    theBtBuy.addActionListener(new SoundButton("click.wav")); //add sound effect when clicked
    
    //delet code 
    theBtDelete.setBounds(16, 25+60*3, 80, 40 ); //delte button / set location
    theBtDelete.addActionListener(        //call back code
        e -> {
            try {
                cont.doDelete();
            } catch (StockException e1) {
                //TODO auto generated catch block
                e1.printStackTrace();
            }

            });
    cp.add (theBtDelete );
    theBtDelete.setBackground(Color.red);             //change button background
    theBtDelete.setOpaque(true);
    theBtDelete.setBorderPainted(false);              //remove border
    theBtDelete.setForeground(Color.white);            //change text color 
    theBtDelete.addActionListener(new SoundButton("click.wav")); // click noise effect
    
  
    
    
    theBtBought.setBounds( 16, 25+60*2, 80, 40 );   // Clear Button
    theBtBought.addActionListener(                  // Call back code
      e -> cont.doBought() );
    cp.add( theBtBought );                          //  Add to canvas
    theBtBought.setBackground(Color.green);             //change button background
    theBtBought.setOpaque(true);
    theBtBought.setBorderPainted(false);
    theBtBought.setForeground(Color.white);  
    theBtBought.addActionListener(new SoundButton("click.wav"));
    
    

    theAction.setBounds( 110, 25 , 270, 20 );       // Message area
    theAction.setText( "" );                        // Blank
    cp.add( theAction );                            //  Add to canvas

    theInput.setBounds( 110, 50, 270, 40 );         // Input Area
    theInput.setText("");                           // Blank
    cp.add( theInput );                             //  Add to canvas

    theSP.setBounds( 110, 100, 270, 160 );          // Scrolling pane
    theOutput.setText( "" );                        //  Blank
    theOutput.setFont( f );                         //  Uses font  
    cp.add( theSP );                                //  Add to canvas
    theSP.getViewport().add( theOutput );           //  In TextArea
    rootWindow.setVisible( true );                  // Make visible
    theInput.requestFocus();                        // Focus is here
  }

  /**
   * The controller object, used so that an interaction can be passed to the controller
   * @param c   The controller
   */

  public void setController( CashierController c )
  {
    cont = c;
    
   
  }

  
  
  //reserve button 
  

  /**
   * Update the view
   * @param modelC   The observed model
   * @param arg      Specific args 
   */
  @Override
  public void update( Observable modelC, Object arg )
  {
    CashierModel model  = (CashierModel) modelC;
    String      message = (String) arg;
    theAction.setText( message );
    Basket basket = model.getBasket();
    if ( basket == null )
      theOutput.setText( "Customers order" );
    else
      theOutput.setText( basket.getDetails() );
    
    theInput.requestFocus();               // Focus is here
  }

}