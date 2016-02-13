import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class MyJButton extends JButton
{
  private int row;
  private int col;
  
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
}