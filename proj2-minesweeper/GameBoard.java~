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
  private JMenuItem menuItem;
  //Panels
  private JPanel mainPanel = new JPanel();
  private JPanel topPanel = new JPanel();
  private JPanel gridPanel = new JPanel();
  //Timer panel
  private JPanel minePanel = new JPanel();
  private JPanel resetPanel = new JPanel();
  private JPanel timePanel = new JPanel();
  
  
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
    
    //------------TESTING-----------------
    Button resetButton = new Button("Reset");
    resetPanel.add(resetButton);
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
    menuBar.add(gameMenu);
    menuBar.add(helpMenu);
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
  }
  
  //Handle what the button will do when pressed
  public void actionPerformed(ActionEvent event){
    MyJButton temp = (MyJButton) event.getSource();
    int row = temp.getRow();
    int col = temp.getCol();
    button[row][col].setEnabled(false);
    JOptionPane.showMessageDialog(this, "you pressed: " + row + "," + col);
  }
  
  //Handle what the button will do with different mouse click
  private class MouseClickHandler extends MouseAdapter{
    public void mouseClicked (MouseEvent e){
      if (SwingUtilities.isLeftMouseButton(e)){
        
      }
    }
  }
  
}  //End of class GameBoard

