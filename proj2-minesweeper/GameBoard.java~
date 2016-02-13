import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//@SuppressWarnings("serial")
public class GameBoard extends JFrame{
 private GridLayout gameBoard;
 private Container container = new Container();
 private JButton[][] button = new JButton[10][10];
 private int i, j;
 
 public GameBoard(){
  super("Mine Sweeper");
  
  //Set up game board
  gameBoard = new GridLayout(10,10);
  
  //Set up content pane and its layout
  container = getContentPane();
  container.setLayout(gameBoard);
  setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
  
  //Set up buttons
  for(i=0; i<10; i++){
   for(j=0; j<10; j++){
    button[i][j] = new JButton();
    button[i][j].setPreferredSize(new Dimension(2,2));
    container.add(button[i][j]);
   }
  }
  
  
  //Set window size and display it
  setSize(500,500);
  setVisible(true);
 }
 
 private class ButtonHandler implements ActionListener{
  public void actionPerformed(ActionEvent event){
   //
  }
 }
}  //End of class GameBoard

class MyJButton extends JButton{
 private int row;
 private int col;
 
 public MyJButton(int r, int c){
  row = r;
  col = c;
 }
 
 public void setNumber(int r, int c){
  row = r;
  col = c;
 }
 
 public int getRow(){
  return row;
 }
 
 public int getCol(){
  return col;
 }
}
