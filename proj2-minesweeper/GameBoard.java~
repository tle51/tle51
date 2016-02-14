import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameBoard extends JFrame implements ActionListener{
  private Container container = new Container();
  private MyJButton[][] button = new MyJButton[10][10];
  private int i, j;
  //Menu
  private JMenuBar menuBar;
  private JMenu gameMenu, helpMenu;
  private JMenuItem resetMenu;
  private JMenuItem scoreMenu;
  private JMenuItem exitMenu;
  private JMenuItem helpSubMenu;
  private JMenuItem aboutMenu;
  //Panels
  private JPanel mainPanel = new JPanel();
  private JPanel topPanel = new JPanel();
  private JPanel gridPanel = new JPanel();
  //Timer panel
  private JPanel minePanel = new JPanel();
  private JPanel resetPanel = new JPanel();
  private JPanel timePanel = new JPanel();
  //Other buttons
  private Button resetButton;
  
  public GameBoard(){
    //Window Title
    super("Mine Sweeper");
    
    //Set up grid and top section
    mainPanel.setLayout(new BorderLayout());
    gridPanel.setLayout(new GridLayout(10,10));
    mainPanel.add(topPanel, BorderLayout.NORTH);
    mainPanel.add(gridPanel,BorderLayout.CENTER);
    //Split top section into three parts
    topPanel.setLayout(new BorderLayout());
    topPanel.add(minePanel, BorderLayout.WEST);
    topPanel.add(resetPanel, BorderLayout.CENTER);
    topPanel.add(timePanel, BorderLayout.EAST);
    
    //Reset Button
    resetButton = new Button("Reset");
    resetButton.setName("reset");
    resetPanel.add(resetButton);
    resetButton.addActionListener(new ResetHandler());
    
    //------------TESTING-----------------
    Button mineButton = new Button("Mine");
    Button timeButton = new Button("Time");
    minePanel.add(mineButton);
    timePanel.add(timeButton);
    
    //Set up content pane and its layout
    //container = getContentPane();
    //container.setLayout(gameBoard);
    
    //Set up the menu bar
    menuBar = new JMenuBar();
    gameMenu = new JMenu("Game");
    helpMenu = new JMenu("Help");
    resetMenu = new JMenuItem("Reset");
    scoreMenu = new JMenuItem("Top Ten");
    exitMenu = new JMenuItem("Exit");
    exitMenu.addActionListener(new ExitHandler());
    helpSubMenu = new JMenuItem("Help");
    helpSubMenu.addActionListener(new HelpHandler());
    aboutMenu = new JMenuItem("About");
    aboutMenu.addActionListener(new AboutHandler());
    menuBar.add(gameMenu);
    menuBar.add(helpMenu);
    gameMenu.add(resetMenu);
    gameMenu.add(scoreMenu);
    gameMenu.add(exitMenu);
    helpMenu.add(helpSubMenu);
    helpMenu.add(aboutMenu);
    setJMenuBar(menuBar);
    
    //Set up buttons
    for(i=0; i<10; i++){
      for(j=0; j<10; j++){
        button[i][j] = new MyJButton();
        button[i][j].setNumber(i,j);
        button[i][j].setPreferredSize(new Dimension(2,2));
        //button[i][j].addMouseListener(new MouseClickHandler());
        button[i][j].addActionListener(this);
        //container.add(button[i][j]);
        gridPanel.add(button[i][j]);
      }
    }
    
    //Set window size and display it
    add(mainPanel);  //add main panel to frame
    setSize(500,500);
    setVisible(true);
    setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
  }  //End of constructor
  
  //------------------ACTION-HANDLERS-------------------------
  //Handle what the button will do when pressed
  public void actionPerformed(ActionEvent event){
    MyJButton temp = (MyJButton) event.getSource();
    int row = temp.getRow();
    int col = temp.getCol();
    button[row][col].setEnabled(false);
    JOptionPane.showMessageDialog(this, "you pressed: " + row + "," + col);
  }
  
  //Reset button handler
  private class ResetHandler implements ActionListener{
    public void actionPerformed(ActionEvent event){
      Button temp = (Button) event.getSource();
      resetButton.setEnabled(false);
      JOptionPane.showMessageDialog(null, "you pressed: reset");
    }
  }
  
  //Exit from menu
  private class ExitHandler implements ActionListener{
    public void actionPerformed(ActionEvent event){
      System.exit(0);
    }
  }
  
  //About drop down menu
  private class AboutHandler implements ActionListener{
    public void actionPerformed(ActionEvent event){
      JOptionPane.showMessageDialog(null, 
                                    "Development Team: Tan Le (tle51), Valentin Kormanov (vkorma2)\n"
                                      +"Course: CS342\n"
                                      +"Programming Project 2: Mine Sweeper", "About", JOptionPane.INFORMATION_MESSAGE); //JOptionPane.PLAIN_MESSAGE (can use this as well for no icon)
    }
  }
  
  //Help drop down menu
  private class HelpHandler implements ActionListener{
    public void actionPerformed(ActionEvent event){
      JOptionPane.showMessageDialog(null, 
                                    "HOW TO PLAY:\n"
                                      +"The objective of this game is to clear the board without triggering the mines.\n"
                                      +"In order to find where the mines are located, look at the numbers on the board. The number indicates\n"
                                      +"the number ofmines that are adjacent to that number.\n\n"
                                      +"CONTROL:\n"
                                      +"- Left Mouse Click: Reveal the square\n"
                                      +"- Right Mouse Click: Mark the square", "Help", JOptionPane.PLAIN_MESSAGE);
    }
  }
  
  //Handle what the button will do with different mouse click
  private class MouseClickHandler extends MouseAdapter{
    public void mouseClicked (MouseEvent e){
      if (SwingUtilities.isLeftMouseButton(e)){
        
      }
    }
  }
  //------------------------------------------------------------
  
}  //End of class GameBoard

