/* Tan Le
 * CS 342
 * Programming Project 2 - MineSweeper
 * GameBoard Class - Set up the game board and include the function of the game
 * */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameBoard extends JFrame{
  private Container container = new Container();
  private MyJButton[][] button = new MyJButton[10][10];
  private int i, j;
  private int state = 0;
  private int mineRemain = 10;  //Used for displaying mine label
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
  private JButton resetButton;
  private JLabel timerLLabel, timerMLabel, timerRLabel;
  private JLabel mineLLabel, mineMLabel, mineRLabel;
  //Images
  private ImageIcon smileButton = new ImageIcon("images/smile_button.gif");
  private ImageIcon smileButtonPressed = new ImageIcon("images/smile_button_pressed.gif");
  private ImageIcon headO = new ImageIcon("images/head_o.gif");
  private ImageIcon bombPressed = new ImageIcon("images/button_bomb_pressed.gif");
  private ImageIcon buttonPressed = new ImageIcon("images/button_pressed.gif");
  private ImageIcon headGlasses = new ImageIcon("images/head_glasses.gif");
  private ImageIcon iconArray[];
  private ImageIcon rightClickArray[];
  private ImageIcon numberArray[];
  //Timer
  private Timer timeClock = new Timer(1000, new TimerHandler());
  private int timeCount = 0;  //timer
  
  public GameBoard(){
    //Window Title
    super("Mine Sweeper");
    
    //Set up grid and top section
    mainPanel.setLayout(new BorderLayout());
    gridPanel.setLayout(new GridLayout(10,10));
    timePanel.setLayout(new BorderLayout());
    minePanel.setLayout(new BorderLayout());
    mainPanel.add(topPanel, BorderLayout.NORTH);
    mainPanel.add(gridPanel,BorderLayout.CENTER);
    //Split top section into three parts
    topPanel.setLayout(new BorderLayout());
    topPanel.add(minePanel, BorderLayout.WEST);
    topPanel.add(resetPanel, BorderLayout.CENTER);
    topPanel.add(timePanel, BorderLayout.EAST);
    
    //Reset Button
    resetButton = new JButton(smileButton);
    resetButton.setName("reset");
    resetButton.setBorder(null);
    resetPanel.add(resetButton);
    resetButton.addMouseListener(new ResetHandler());
    
    //Number Array Images (array index stored 1 less than actual number)
    String numName[] = {"images/button_1.gif","images/button_2.gif","images/button_3.gif","images/button_4.gif",
                        "images/button_5.gif","images/button_6.gif","images/button_7.gif","images/button_8.gif"};
    numberArray = new ImageIcon[numName.length];
    for(i = 0; i < numName.length; i++){
      numberArray[i] = new ImageIcon(numName[i]);
    }
    
    //Right Click Images
    String iconName[] = {"images/button_normal.gif","images/button_flag.gif","images/button_question.gif"};
    rightClickArray = new ImageIcon[iconName.length];
    for(i = 0; i < iconName.length; i++){
      rightClickArray[i] = new ImageIcon(iconName[i]);
    }
    
    //Timer Image Icons
    String names[] = {"images/countdown_0.gif","images/countdown_1.gif","images/countdown_2.gif","images/countdown_3.gif",
                      "images/countdown_4.gif", "images/countdown_5.gif","images/countdown_6.gif","images/countdown_7.gif",
                      "images/countdown_8.gif","images/countdown_9.gif"};
    iconArray = new ImageIcon[names.length];
    for(i = 0; i < names.length; i++){
      iconArray[i] = new ImageIcon(names[i]);
    }
    
    //Timer Label
    timerLLabel = new JLabel(iconArray[0]);
    timerMLabel = new JLabel(iconArray[0]);
    timerRLabel = new JLabel(iconArray[0]);
    timePanel.add(timerLLabel,BorderLayout.WEST);
    timePanel.add(timerMLabel,BorderLayout.CENTER);
    timePanel.add(timerRLabel,BorderLayout.EAST);
    //Start the timer
    
    //Mine Label
    mineLLabel = new JLabel(iconArray[0]);
    mineMLabel = new JLabel(iconArray[0]);
    mineRLabel = new JLabel(iconArray[0]);
    minePanel.add(mineLLabel,BorderLayout.EAST);
    minePanel.add(mineMLabel,BorderLayout.CENTER);
    minePanel.add(mineRLabel,BorderLayout.WEST);

    //Set up the menu bar
    menuBar = new JMenuBar();
    gameMenu = new JMenu("Game");
    helpMenu = new JMenu("Help");
    resetMenu = new JMenuItem("Reset");
    resetMenu.setMnemonic(KeyEvent.VK_R);
    scoreMenu = new JMenuItem("Top Ten");
    scoreMenu.setMnemonic(KeyEvent.VK_T);
    exitMenu = new JMenuItem("Exit");
    exitMenu.addActionListener(new ExitHandler());
    exitMenu.setMnemonic(KeyEvent.VK_X);
    helpSubMenu = new JMenuItem("Help");
    helpSubMenu.addActionListener(new HelpHandler());
    helpSubMenu.setMnemonic(KeyEvent.VK_L);
    aboutMenu = new JMenuItem("About");
    aboutMenu.addActionListener(new AboutHandler());
    aboutMenu.setMnemonic(KeyEvent.VK_A);
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
        button[i][j].setIcon(rightClickArray[0]);
        button[i][j].setNumber(i,j);
        button[i][j].setState(0);  //State of the right click
        button[i][j].setBomb(0);  //Initialize bomb state to normal (no bomb)
        button[i][j].setEnable(1);  //Enabled button
        button[i][j].setLeftClick(1);  //Enable left clicking
        button[i][j].setVisited(0);  //Mark all button to unvisited
        button[i][j].setNumber(0);
        button[i][j].setBorder(null);
        button[i][j].setFocusable(false);
        button[i][j].setContentAreaFilled(false);
        button[i][j].addMouseListener(new MouseClickHandler());
        button[i][j].addActionListener(new ButtonHandler());
        gridPanel.add(button[i][j]);
      }
    }
    
    //Add mine
    randomizeBomb();
    setNumberState();
    
    //Set window size and display it
    add(mainPanel);  //add main panel to frame
    setSize(185,275);
    setResizable(false);  //disable window resizing
    setVisible(true);
    setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
  }  //End of constructor
  
  //------------------ACTION-HANDLERS-------------------------
  //Handle what the button(game button) will do when pressed [ANYTHING TO DO WITH THE GRID SQUARE]
  private class ButtonHandler implements ActionListener{
    public void actionPerformed(ActionEvent event){
      MyJButton temp = (MyJButton) event.getSource();
      //temp.setEnabled(false);
      
      //Bomb Space
      if(temp.getBomb() == 1 && temp.getLeftClick() == 1){
        button[temp.getRow()][temp.getCol()].setEnable(0);
        temp.setIcon(bombPressed);
        
//        //Check winning condition
//        if(checkWin() == 1){
//          JOptionPane.showMessageDialog(null, "You Win!"); 
//          timeClock.stop();
//        }
        
      }
      //Number or normal Space (left clicking)
      else{
        //Check adjacent space around clicked location
        if(temp.getLeftClick() == 1){
          //addNumber(temp.getRow(), temp.getCol());
          autoClear(temp.getRow(), temp.getCol());
        }
      }
      
      //Start the clock
      if(checkWin() == 0){
        //timerRLabel.setIcon(iconArray[1]);  //set label to 001
        timeClock.start();  //start the timer
      }
    }
  }
  
  //Reset button handler
  private class ResetHandler extends MouseAdapter{
    public void mousePressed(MouseEvent event){
      //JButton temp = (JButton) event.getSource();
      resetButton.setIcon(smileButtonPressed);
      //resetButton.setEnabled(false);
      //JOptionPane.showMessageDialog(null, "you pressed: reset");  
    }
    public void mouseReleased(MouseEvent event){
      //JButton temp = (JButton) event.getSource();
      if(checkWin() == 0){
        resetButton.setIcon(smileButton);
      }
      else{
        resetButton.setIcon(headGlasses);
      }
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
  
  //Handle what the button(on grid) will do with different mouse click [RIGHT CLICK]
  private class MouseClickHandler extends MouseAdapter{
    //Pressed State
    public void mousePressed(MouseEvent event){
      MyJButton temp = (MyJButton) event.getSource();
      //Right Clicking
      if(SwingUtilities.isRightMouseButton(event)){  
        if(temp.getEnable() == 1){  //Can only right click if square is still enabled
          state = temp.getState();
          state++;
          temp.setState(state);
          //Restart back to default state
          if(state > 2){
            temp.setState(0);
            state = 0;
            button[temp.getRow()][temp.getCol()].setLeftClick(1);
            //button[temp.getRow()][temp.getCol()].setNumber(0); //DFS - auto clear can clear this button
            temp.setIcon(rightClickArray[temp.getState()]);
          }
          else{
            button[temp.getRow()][temp.getCol()].setLeftClick(0);
            //button[temp.getRow()][temp.getCol()].setNumber(1);  //DFS - auto clear can't clear this button (locked)
            temp.setIcon(rightClickArray[temp.getState()]);
          }
        }
      }
      else{  //left click
        resetButton.setIcon(headO);
      }
    }
    //Released State
    public void mouseReleased(MouseEvent event){
      if(checkWin() == 0){
        resetButton.setIcon(smileButton);
      }
      else{
        resetButton.setIcon(headGlasses);
      }
    }
  }
  
  //Handler to count the time 
  private class TimerHandler implements ActionListener{
    public void actionPerformed(ActionEvent event){
      String timeString;
      int timeLength;
      
      timeCount++;
      timeString = Integer.toString(timeCount);
      char arr[]= timeString.toCharArray();  // 123 => arr[0]=1, arr[1]=2, arr[2]=3
      timeLength = timeString.length();
      
      //Single digit
      if(timeLength == 1){
        timerRLabel.setIcon(iconArray[timeCount]);
      }
      else if(timeLength == 2){
        timerMLabel.setIcon(iconArray[Integer.parseInt(String.valueOf(arr[0]))]);
        timerRLabel.setIcon(iconArray[Integer.parseInt(String.valueOf(arr[1]))]);                              
      }
      else if(timeLength == 3){
        //If hit max time, stop the clock
        if(timeCount > 999){
          timeClock.stop();
        }
        else{
          timerLLabel.setIcon(iconArray[Integer.parseInt(String.valueOf(arr[0]))]);
          timerMLabel.setIcon(iconArray[Integer.parseInt(String.valueOf(arr[1]))]);
          timerRLabel.setIcon(iconArray[Integer.parseInt(String.valueOf(arr[2]))]);
        }
      }
    }
  }
  
  //------------------------------------------------------------
  
  //Randomize Bomb Location
  public void randomizeBomb(){
    int mineCount = 10;
    while(mineCount != 0){
      int randX = (int)(Math.random() * 10);  //Random number from 0-9
      int randY = (int)(Math.random() * 10);
      
      if(button[randX][randY].getBomb() == 0){
        button[randX][randY].setBomb(1);
        mineCount--;
      }
    }
  }
  
  //Add number
  public void addNumber(int row, int col){
    int mineCount = 0;
    int downRow = row - 1;
    int upRow = row + 1;
    int downCol = col -1;
    int upCol = col + 1;
    
    //Northeast
    if(downRow >= 0 && downCol >= 0){
      if(button[downRow][downCol].getBomb() == 1){
        mineCount++;
      }
    }
    //North
    if(downRow >= 0){
      if(button[downRow][col].getBomb() == 1){
        mineCount++;
      }
    }
    //Northwest
    if(downRow >= 0 && upCol < 10){
      if(button[downRow][upCol].getBomb() == 1){
        mineCount++;
      }
    }
    //West
    if(downCol >= 0){
      if(button[row][downCol].getBomb() == 1){
        mineCount++;
      }
    }
    //East
    if(upCol < 10){
      if(button[row][upCol].getBomb() == 1){
        mineCount++;
      }
    }
    //Southwest
    if(upRow < 10 && downCol >= 0){
      if(button[upRow][downCol].getBomb() == 1){
        mineCount++;
      }
    }
    //South
    if(upRow < 10){
      if(button[upRow][col].getBomb() == 1){
        mineCount++;
      }
    }
    //Southeast
    if(upRow < 10 && upCol < 10){
      if(button[upRow][upCol].getBomb() == 1){
        mineCount++;
      }
    }
    
    //Set number on space
    if(mineCount > 0){  //Number space
      button[row][col].setEnable(0);
      button[row][col].setNumber(1);  //?????????????????? (DFS)
      button[row][col].setIcon(numberArray[mineCount-1]);
    }
    else if(mineCount == 0 && (button[row][col].getBomb() == 0)){  //Empty space
      button[row][col].setEnable(0);
      button[row][col].setIcon(buttonPressed);
    }
    
    //Check winning condition
    if(checkWin() == 1){  //won
      //countingTime = 0;
      timeClock.stop();
      resetButton.setIcon(headGlasses);
      JOptionPane.showMessageDialog(null, "You Win!\n" + "Time: " + timeCount);
    }
    
  }
  
  //Reset game
  
  //Auto clear (DFS)
  public void autoClear(int row, int col){
    int mineCount = 0;
    int downRow = row - 1;
    int upRow = row + 1;
    int downCol = col -1;
    int upCol = col + 1;
    
    if(button[row][col].getVisited() == 0 && button[row][col].getLeftClick() == 1){
      if(button[row][col].getNumber() == 1){  //adjacent space have a bomb
        button[row][col].setVisited(1);
        addNumber(row,col);
      }
      else{
        addNumber(row,col);
        button[row][col].setVisited(1);
        //Northwest
        if(downRow >= 0 && downCol >= 0){
          if(button[downRow][downCol].getBomb() == 0){
            autoClear(downRow, downCol);
          }
        }
        //North
        if(downRow >= 0){
          if(button[downRow][col].getBomb() == 0){
            autoClear(downRow, col);
          }
        }
        //Northeast
        if(downRow >= 0 && upCol < 10){
          if(button[downRow][upCol].getBomb() == 0){
            autoClear(downRow, upCol);
          }
        }
        //West
        if(downCol >= 0){
          if(button[row][downCol].getBomb() == 0){
            autoClear(row, downCol);
          }
        }
        //East
        if(upCol < 10){
          if(button[row][upCol].getBomb() == 0){
            autoClear(row, upCol);
          }
        }
        //Southwest
        if(upRow < 10 && downCol >= 0){
          if(button[upRow][downCol].getBomb() == 0){
            autoClear(upRow, downCol);
          }
        }
        //South
        if(upRow < 10){
          if(button[upRow][col].getBomb() == 0){
            autoClear(upRow, col);
          }
        }
        //Southeast
        if(upRow < 10 && upCol < 10){
          if(button[upRow][upCol].getBomb() == 0){
            autoClear(upRow, upCol);
          }
        }
      } 
    }
  }
  
  //set number state
  public void setNumberState(){
    for(i=0;i<10;i++){
      for(j=0;j<10;j++){
        int downRow = i - 1;
        int upRow = i + 1;
        int downCol = j -1;
        int upCol = j + 1;
        int mineCount = 0;
        //Northeast
        if(downRow >= 0 && downCol >= 0){
          if(button[downRow][downCol].getBomb() == 1){
            mineCount++;
          }
        }
        //North
        if(downRow >= 0){
          if(button[downRow][j].getBomb() == 1){
            mineCount++;
          }
        }
        //Northwest
        if(downRow >= 0 && upCol < 10){
          if(button[downRow][upCol].getBomb() == 1){
            mineCount++;
          }
        }
        //West
        if(downCol >= 0){
          if(button[i][downCol].getBomb() == 1){
            mineCount++;
          }
        }
        //East
        if(upCol < 10){
          if(button[i][upCol].getBomb() == 1){
            mineCount++;
          }
        }
        //Southwest
        if(upRow < 10 && downCol >= 0){
          if(button[upRow][downCol].getBomb() == 1){
            mineCount++;
          }
        }
        //South
        if(upRow < 10){
          if(button[upRow][j].getBomb() == 1){
            mineCount++;
          }
        }
        //Southeast
        if(upRow < 10 && upCol < 10){
          if(button[upRow][upCol].getBomb() == 1){
            mineCount++;
          }
        }
        
        //Set number on space
        if(mineCount > 0){  //Number space
          button[i][j].setNumber(1);  //?????????????????? (DFS)
        }
      } 
    }
  }
  
  //Check if any non-bomb spaces are left
  public int checkWin(){
    for(i=0;i<10;i++){
      for(j=0;j<10;j++){
        //Found a non-bomb button
        if(button[i][j].getBomb() == 0){
          if(button[i][j].getEnable() == 1){
            return 0;
          }
        }
      } 
    }
    return 1;  //all safe button are cleared -> winning condition
  }
  
  //Change the number of the mine counter label
  public void countMineLabel(){
    
  }
  
  //Game end
  
}  //End of class GameBoard

