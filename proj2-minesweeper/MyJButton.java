import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class MyJButton extends JButton
{
  private int row;
  private int col;
  private String name;
  private int state;
  private int bombState;  //1 - button have a bomb, 0 - no bomb
  private int enableState;
  private int leftClick;
  
  //Row and Column Info
  public void setNumber (int r, int c)
  {
    row = r;
    col = c;
  }
  public int getRow()
  {
    return row;
  }
  public int getCol(){
    return col;
  }
  
  //Name of Button
  public void setName(String n){
    name = n;
  }
  public String getName(){
    return name;
  }
  
  //Right Click State
  public void setState(int s){
    state = s;
  }
  public int getState(){
    return state;
  }
  
  //Bomb State
  public void setBomb(int bb){
    bombState = bb;
  }
  public int getBomb(){
    return bombState;
  }
  
  //Enable-Disable State
  public void setEnable(int i){
    enableState = i;
  }
  public int getEnable(){
    return enableState;
  }
  
  //Left Click State
  public void setLeftClick(int l){
    leftClick = l;
  }
  public int getLeftClick(){
    return leftClick;
  }
}