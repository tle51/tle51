/* Tan Le
 * CS 342
 * Programming Project 2 - MineSweeper
 * MyJButton Class - Control the button states
 * */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class MyJButton extends JButton
{
  private int row;
  private int col;
  private String name;
  private int state;
  private int bombState = 0;  //1 - button have a bomb, 0 - no bomb
  private int enableState;
  private int leftClick;
  private int normalSpace;  //1 - non-mine state, 0 - mine?
  private int visitedState;  //0 - not visited, 1 - visited
  private int numberState = 0;  //1 - a number
  private int flagState = 0;
  
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
  
  //Non-mine space
  public void setSpace(int i){
    normalSpace = i;
  }
  public int getSpace(){
    return normalSpace;
  }
  
  //DFS Visited State
  public void setVisited(int i){
    visitedState = i;
  }
  public int getVisited(){
    return visitedState;
  }
  
  //Number state (bomb adjacent)
  public void setNumber(int i){
    numberState = i;
  }
  public int getNumber(){
    return numberState;
  }
  
  //Flag state
  public void setFlag(int i){
    flagState = i;
  }
  public int getFlag(){
    return flagState;
  }
  
}