/** Tan Le (tle51), Tsz Lam (tlam22), Kevin Tang (ktang20)
  * CS 342 - Project 5: Tetris
  * inner_gui class - Control the game grid. Include keyboard functions.
  */

import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.JLabel;
import javax.swing.*;
import java.awt.Color;  
import javax.swing.border.Border;

/** Control:
  * Move Left/Right: Left/Right Arrow Key
  * Rotate Left: Z
  * Rotate Right: X
  * Hard Drop: Space
  * Soft Drop: Down Arrow Key
  * Pause: C
  * Reset: R
  */ 

/*Panel inside frame*/
class inner_gui extends JPanel{
  private information[][] squares;
  private Border border;
  //Game info
  private int total_line_cleared = 0;
  private int line_level = 0;  //Once clear 10 or more -> increase level
  private int total_score = 0;
  private int current_line_cleared;
  private double current_level = 1;
  private int game_clock = 0;
  private double delay;
  private Timer game_timer;
  private shape tetris_moving;
  public boolean game_over = false;  //Indicate when the game is over (stop spawning)
  private boolean action_ok = true;  //Can rotate or move tetris piece
  public boolean game_paused = false;  //Game pause state
  private int rand = -1;  //Set to -1 for initial state
  private int prev_rand;
  private Color[] colors = {Color.BLUE, Color.RED, Color.CYAN, Color.YELLOW, Color.MAGENTA, Color.GRAY, Color.GREEN, Color.BLUE};
  private Timer clock_timer = new Timer(1000, new ClockHandler());
  
  //Constructor
  public inner_gui(int width, int height){
    squares = new information[height][width];
    this.setLayout(new GridLayout(height,width));
    border = BorderFactory.createLineBorder(Color.BLACK,1);
    add_labels(width,height);
    
    //Add key listener for left and right key
    this.addKeyListener(new KeyHandler());
    
    //Spawn tetromino
    spawn_tetris();
    clock_timer.start();
 
  }
  
  //Spawn tetrominos
  private void spawn_tetris(){
    //Spawn next block if not game over
    if(game_over == false){
      //Check if can clear line
      current_line_cleared = line_clear();
      total_line_cleared += current_line_cleared;
      line_level += current_line_cleared;
      
      if(current_line_cleared != 0){
        move_down_all();
      }
      
      //Check if can level up
      if(line_level >= 10){
        current_level++;
        line_level = 0;
      }
      
      System.out.println("Current Line cleared: "+current_line_cleared);
      System.out.println("Total Line cleared: "+total_line_cleared);
      System.out.println("Line Level: " +line_level);
      System.out.println("Current Level: "+current_level);
      
      //Update score if atleast one line is cleared
      int current_score = add_score(current_line_cleared, (int)current_level);
      total_score += current_score;
            
      //spawn_ready = false;
      action_ok = true;
      //Randomize a shape (1 - 7)
      if(rand == -1){
      rand = (int)(Math.random() * 7 +1);
      }
      shape new_tetris = new shape(rand);
      prev_rand = rand;  //Save previous rand vale for color
      //Randomize the next shape
      rand = (int)(Math.random() * 7 +1);
      
      //Set to global shape
      tetris_moving = new_tetris;
      
      //Color in coordinate on grid
      XY[] tt = tetris_moving.get_shape_cord();
      
      //Check for game over 
      for(int i=0; i<tt.length; i++){
        //Spawn out of bound
        if(tt[i].y_c < 0){
          game_over = true;
          game_timer.stop();
          clock_timer.stop();
          action_ok = false;
          break;
        }
        
        //Initial spawn collide with existing block
        if(squares[tt[i].y_c][tt[i].x_c].inused == true){
          game_over = true;
          game_timer.stop();
          clock_timer.stop();
          action_ok = false;
          break;
        }
      }
      
      //If not game over -> spawn block
      if(game_over == false){
        //Initalize inused state
        for(int i=0; i<tt.length; i++){
          squares[tt[i].y_c][tt[i].x_c].inused = false;
        }
        
        for(int i=0; i<tt.length; i++){
          squares[tt[i].y_c][tt[i].x_c].setBackground(colors[prev_rand]);
        }
        
        //Call Timer
        timer();
      }
      //If Game over
      else{
        JOptionPane.showMessageDialog(null, "Game Over");
      }
    }
  }
  
  //Pause the game
  private void pause_game(){
    //Switch pause state
    if(game_paused == true){
      game_paused = false;
    }
    else{
      game_paused = true;
    }
    
    //Pause the game
    if(game_paused == true){
      //Stop tetromino move down
      game_timer.stop();
      //Pause time
      clock_timer.stop();
      //Prevent user from controlling
      action_ok = false;
    }
    //Unpause the game
    else{
      game_timer.start();
      clock_timer.start();
      action_ok = true;
    } 
  }
  
  //Reset the game board
  public void reset_game(){
    //Stop timer
    game_timer.stop();
    
    //Clear the whole board
    for(int i=0; i<10; i++){
      for(int j=0; j<20; j++){
        squares[j][i].setBackground(Color.WHITE);
        squares[j][i].inused = false;
      }
    }
    
    //Reset global variables
    total_line_cleared = 0;
    total_score = 0;
    line_level = 0;
    current_level = 1;
    game_clock = 0;
    rand = -1;
    game_over = false;
    action_ok = true;
    game_paused = false;
    
    //Spawn the first piece
    spawn_tetris();
    clock_timer.start();
  }
  
  //Return line cleared
  private int line_clear(){
    int cc = 0;
    boolean clear_ok = true;
    
    for(int i=19; i >= 0 ; i--){
      for(int j=0; j < 10; j++){
        if(squares[i][j].inused == false){
          clear_ok = false;
          break;
        }
      }
      
      //Check if can clear
      if(clear_ok == true){  
        for(int j=0; j < 10; j++){
          squares[i][j].setBackground(Color.white);
          squares[i][j].inused = false;
        }
        cc++;
      }
      clear_ok = true;
    }
    return cc;
  }
  
  //Return score
  private int add_score(int line_cleared,int level){
    if (line_cleared == 0){
      return 0;
    }
    the_score weeb = new the_score();
    weeb.update_score(line_cleared,level);
    return weeb.get_score();
  }
  
  //Get Score
  public int update_score(){
    int new_score = total_score;
    return new_score;
  }
  
  //Get Current Level
  public int update_level(){
    return (int)current_level;
  }
  
  //Get Line Cleared
  public int update_line(){
    return total_line_cleared;
  }
  
  //Get Clock Time
  public int update_clock(){
    return game_clock;
  }
  
  //Get Next Block
  public int get_next_block(){
    return rand;
  }
  
  //move_down,add
  private void move_down_all(){
    int line_clr = 0;
    for(int i=19; i>=0; i--){
      //Check if current row is empty
      if(line_empty(i)){
        line_clr++;
      }
      else{
        for(int j=0; j<10; j++){
          //Shift if space is an existing block by number of line cleared
          if(squares[i][j].inused == true){
            //squares[i+line_clr][j] = squares[i][j];
            squares[i+line_clr][j].setBackground(squares[i][j].getBackground());
            squares[i+line_clr][j].inused = true;
            if(line_clr > 0){
              squares[i][j].setBackground(Color.WHITE);
              squares[i][j].inused = false;
            }
          }
        }
      }
    }
  }
  
  private boolean line_empty(int row){
    for(int i=0; i < 10; i++){
      if(squares[row][i].inused == true){
        return false; 
      }
    }
    return true;
  }
  
  //Initialize game grid
  private void add_labels(int width, int height){
    for(int i=0; i < height; i++){
      for(int j=0; j < width; j++){
        squares[i][j] = new information(" ",JLabel.CENTER);
        squares[i][j].setOpaque(true);
        squares[i][j].setBackground(Color.white);
        squares[i][j].setSize(500,500);
        squares[i][j].setBorder(border);
        squares[i][j].inused = false;  //Tetromino that is at the bottom -> static block (true)
        this.add(squares[i][j]);
      }
    }
  }
  
  //Timer
  private void timer(){
    //Calculate the delay
    delay = (50 - (current_level * 2)) / 60;
    delay = delay * 1000;  //Convert to milisecond
    
    //Set up timer with delay
    game_timer = new Timer((int)delay, new TimerHandler());
    
    //Start the timer
    game_timer.start();
  }
  
  //Handler to implement game clock
  private class ClockHandler implements ActionListener{
    public void actionPerformed(ActionEvent e){
      game_clock++;
    }
  }
  
  //Handler to control tetrominos down movement (timer)
  private class TimerHandler implements ActionListener{
    public void actionPerformed(ActionEvent event){
      //Initialize state
      boolean state = true;
        
      //Get tetromino current coordinate
      XY[] temp_pos = tetris_moving.get_shape_cord();
        
      //Check if move down is possible
      for(int i = 0; i<temp_pos.length; i++){
        //Check if at bottom
        if(temp_pos[i].y_c + 1 > 19){
          //Set tetrominos to be inused
          for(i = 0; i<temp_pos.length; i++){
            squares[temp_pos[i].y_c][temp_pos[i].x_c].inused = true;
          }
          
          //Stop timer
          game_timer.stop();
          state = false;  
          action_ok = false;
          
          //Spawn next block
          spawn_tetris();
          
          return;
        }
        
        //Check if collide with other tetrominos
        if(squares[temp_pos[i].y_c + 1][temp_pos[i].x_c].inused == true){
          //Set tetrominos to be inused
          for(i = 0; i<temp_pos.length; i++){
            squares[temp_pos[i].y_c][temp_pos[i].x_c].inused = true;
          }
          
          //Stop timer
          game_timer.stop();
          state = false;
          action_ok = false;
          
          //Spawn next Block
          spawn_tetris();
          
          return;
        }        
      }
      
      if(state == true){
        //Clear previous position color
        for(int i=0; i<temp_pos.length; i++){
          squares[temp_pos[i].y_c][temp_pos[i].x_c].setBackground(Color.white);
        }
        
        //Move down one and update coordinate
        tetris_moving.set_shape_cord(tetris_moving.move_down_one());
        temp_pos = tetris_moving.get_shape_cord();
        
        //Color in block
        for(int i=0; i<temp_pos.length; i++){
          squares[temp_pos[i].y_c][temp_pos[i].x_c].setBackground(colors[prev_rand]);
        }
      }
    }
  }
  
  //Handler for Keyboard input
  private class KeyHandler implements KeyListener{
    @Override
    public void keyPressed(KeyEvent e){
      //Left arrow key input
      if(e.getKeyCode() == KeyEvent.VK_LEFT){
        boolean temp_state = true;
        
        //Get moving shape coordinate
        XY[] shape_pos = tetris_moving.get_shape_cord();
        
        if(action_ok == true){
          game_timer.stop();
          //Check if it is at the edge of screen
          for(int i=0; i<shape_pos.length; i++){
            if(shape_pos[i].x_c == 0){
              temp_state = false;
              game_timer.start();
              return;
            }
          }
          
          //Check for collision
          for(int i=0; i<shape_pos.length; i++){
            //If collided
            if(squares[shape_pos[i].y_c][shape_pos[i].x_c-1].inused == true){
              temp_state = false;
              game_timer.start();
              return;
            }
          }
          
          //Move all blocks to the left
          for(int i=0; i<shape_pos.length; i++){
            shape_pos[i].x_c = shape_pos[i].x_c - 1;
          }
          
          //Check if move left is possible
          if(temp_state == true){
            //Clear previous position color
            for(int i=0; i<shape_pos.length; i++){
              squares[shape_pos[i].y_c][shape_pos[i].x_c+1].setBackground(Color.white);
              squares[shape_pos[i].y_c][shape_pos[i].x_c+1].inused = false;
            }
            
            //Move left and update coordinate
            tetris_moving.set_shape_cord(shape_pos);
            shape_pos = tetris_moving.get_shape_cord();
            
            //Color in new position
            for(int i=0; i<shape_pos.length; i++){
              squares[shape_pos[i].y_c][shape_pos[i].x_c].setBackground(colors[prev_rand]);
            }
          }
          game_timer.start();
        }        
      }
      //Right arrow key input
      else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
        boolean temp_state = true;
        
        //Get moving shape coordinate
        XY[] shape_pos = tetris_moving.get_shape_cord();
        
        if(action_ok == true){
          game_timer.stop();
          //Check if it is at the edge of screen
          for(int i=0; i<shape_pos.length; i++){
            if(shape_pos[i].x_c == 9){
              temp_state = false;
              game_timer.start();
              return;
            }
          }
          
          //Check for collision
          for(int i=0; i<shape_pos.length; i++){
            //If collided
            if(squares[shape_pos[i].y_c][shape_pos[i].x_c+1].inused == true){
              temp_state = false;
              game_timer.start();
              return;
            }
          }
          
          //Move all blocks to the right
          for(int i=0; i<shape_pos.length; i++){
            shape_pos[i].x_c = shape_pos[i].x_c + 1;
          }
          
          //Check if move right is possible
          if(temp_state == true){  //Can move right
            //Clear previous position color
            for(int i=0; i<shape_pos.length; i++){
              squares[shape_pos[i].y_c][shape_pos[i].x_c-1].setBackground(Color.white);
              squares[shape_pos[i].y_c][shape_pos[i].x_c-1].inused = false;
            }
            
            //Move right and update coordinate
            tetris_moving.set_shape_cord(shape_pos);
            shape_pos = tetris_moving.get_shape_cord();
            
            //Color in block
            for(int i=0; i<shape_pos.length; i++){
              squares[shape_pos[i].y_c][shape_pos[i].x_c].setBackground(colors[prev_rand]);
            }
          }
          game_timer.start();
        }
      }
      //Rotate Left
      else if(e.getKeyCode() == KeyEvent.VK_Z){
        boolean temp_state = true;
        boolean rotate_ok = true;
        XY[] prev_shape = tetris_moving.get_shape_cord();
        
        if(action_ok == true){
          game_timer.stop();
          //Get moving shape rotated coordinate
          shape temp_shape = tetris_moving; //Copy moving tetris
          temp_shape.set_shape_cord(temp_shape.rotate_left());  //Set rotated coordinate
          XY[] shape_pos = temp_shape.get_shape_cord();
          
          
          //Check if rotating left is possible
          for(int i=0; i<shape_pos.length; i++){
            //Check if out of bound
            if(shape_pos[i].x_c < 0 || shape_pos[i].x_c > 9 || shape_pos[i].y_c > 19 || shape_pos[i].y_c < 0){
              temp_state = false;
              rotate_ok = false;
            }
            if(rotate_ok == false){
              for(i=0; i<shape_pos.length; i++){
                shape_pos[i].y_c = prev_shape[i].y_c;
                shape_pos[i].x_c = prev_shape[i].x_c;
              }
              game_timer.start();
              return;
            }
          } //End of for loop
          
          //Check for collision
          for(int i=0; i<shape_pos.length; i++){
            if(squares[shape_pos[i].y_c][shape_pos[i].x_c].inused == true){
              rotate_ok = false;
              temp_state = false;
            }
          }
          //Restore to original form
          if(rotate_ok == false){
            for(int i=0; i<shape_pos.length; i++){
              shape_pos[i].y_c = prev_shape[i].y_c;
              shape_pos[i].x_c = prev_shape[i].x_c;
            }
            game_timer.start();
            return;
          }

          //Rotating is possible
          if(temp_state == true){
            //Clear previous position color
            for(int i=0; i<prev_shape.length; i++){
              squares[prev_shape[i].y_c][prev_shape[i].x_c].setBackground(Color.white);
              squares[prev_shape[i].y_c][prev_shape[i].x_c].inused = false;
            }
            
            //Rotate tetromino and update coordinate
            tetris_moving.set_shape_cord(shape_pos);
            shape_pos = tetris_moving.get_shape_cord();
            
            //Color in new location
            for(int i=0; i<shape_pos.length; i++){
              squares[shape_pos[i].y_c][shape_pos[i].x_c].setBackground(colors[prev_rand]);
            }
          }
          game_timer.start();
        }      
      }  //End of Rotate Left
      //Rotate Right
      else if(e.getKeyCode() == KeyEvent.VK_X){
        boolean temp_state = true;
        boolean rotate_ok = true;
        XY[] prev_shape = tetris_moving.get_shape_cord();
        
        if(action_ok == true){
          game_timer.stop();
          //Get moving shape rotated coordinate
          shape temp_shape = tetris_moving; //Copy moving tetris
          temp_shape.set_shape_cord(temp_shape.rotate_right());  //Set rotated coordinate
          XY[] shape_pos = temp_shape.get_shape_cord();
          
          //Check if rotating left is possible
          for(int i=0; i<shape_pos.length; i++){
            //Check if out of bound
            if(shape_pos[i].x_c < 0 || shape_pos[i].x_c > 9 || shape_pos[i].y_c > 19 || shape_pos[i].y_c < 0){
              temp_state = false;
              rotate_ok = false;
            }
            if(rotate_ok == false){
              for(i=0; i<shape_pos.length; i++){
                shape_pos[i].y_c = prev_shape[i].y_c;
                shape_pos[i].x_c = prev_shape[i].x_c;
              }
              game_timer.start();
              return;
            }
          } //End of for loop
          
          //Check for collision
          for(int i=0; i<shape_pos.length; i++){
            if(squares[shape_pos[i].y_c][shape_pos[i].x_c].inused == true){
              rotate_ok = false;
              temp_state = false;
            }
          }
          //Restore to original form
          if(rotate_ok == false){
            for(int i=0; i<shape_pos.length; i++){
              shape_pos[i].y_c = prev_shape[i].y_c;
              shape_pos[i].x_c = prev_shape[i].x_c;
            }
            game_timer.start();
            return;
          }
          
          //Rotating is possible
          if(temp_state == true){
            //Clear previous position color
            for(int i=0; i<prev_shape.length; i++){
              squares[prev_shape[i].y_c][prev_shape[i].x_c].setBackground(Color.white);
              squares[prev_shape[i].y_c][prev_shape[i].x_c].inused = false;
            }
            
            //Rotate tetromino and update coordinate
            tetris_moving.set_shape_cord(shape_pos);
            shape_pos = tetris_moving.get_shape_cord();
            
            //Color in new location
            for(int i=0; i<shape_pos.length; i++){
              squares[shape_pos[i].y_c][shape_pos[i].x_c].setBackground(colors[prev_rand]);
            }
          }
          game_timer.start();  
        } 
      } //End of Rotate Right
      //Hard Drop
      else if(e.getKeyCode() == KeyEvent.VK_SPACE){
        boolean temp_state = true;
        XY[] prev_shape = tetris_moving.get_shape_cord();
        XY[] shape_pos;
        
        if(action_ok == true){
          //Go down as far as possible
          while(true){
            //Calculate one move down
            shape temp_shape = tetris_moving;
            temp_shape.set_shape_cord(temp_shape.move_down_one());
            shape_pos = temp_shape.get_shape_cord();  //Get move down coordinate
            
            //Check if hit bottom
            for(int i=0; i<shape_pos.length; i++){
              if(shape_pos[i].y_c > 19){
                temp_state = false;
                break;
              }
              //Check if hit existing block
              else{
                if(squares[shape_pos[i].y_c][shape_pos[i].x_c].inused == true){
                  temp_state = false;
                  break;
                }
              }
            }
            
            //Shift back if next move is invalid
            if(temp_state == false){
              for(int i=0; i<shape_pos.length; i++){
                shape_pos[i].y_c = shape_pos[i].y_c - 1;
              }
              break;
            }
          }
          
          //Clear previous position color
          for(int i=0; i<prev_shape.length; i++){
            squares[prev_shape[i].y_c][prev_shape[i].x_c].setBackground(Color.white);
            squares[prev_shape[i].y_c][prev_shape[i].x_c].inused = false;
          }
          
          //Update new coordinate
          tetris_moving.set_shape_cord(shape_pos);
          shape_pos = tetris_moving.get_shape_cord();
          
          //Color in new coordinate
          for(int i=0; i<shape_pos.length; i++){
            squares[shape_pos[i].y_c][shape_pos[i].x_c].setBackground(colors[prev_rand]);
            squares[shape_pos[i].y_c][shape_pos[i].x_c].inused = true;
            action_ok = false;
          }
          
          //Spawn new block
          game_timer.stop();
          spawn_tetris();
        }
        
      }  //End of Hard Drop
      //Soft Drop
      else if(e.getKeyCode() == KeyEvent.VK_DOWN){
        game_timer.stop();
        boolean temp_state = true;
        boolean bottom_state = false;
        XY[] prev_shape = tetris_moving.get_shape_cord();
        
        //Get move down coordinate
        if(action_ok == true){
          shape temp_shape = tetris_moving;
          temp_shape.set_shape_cord(temp_shape.move_down_one());
          XY[] shape_pos = temp_shape.get_shape_cord();
          
          //Check if hit bottom
          for(int i=0; i<shape_pos.length; i++){
            if(shape_pos[i].y_c > 19){
              temp_state = false;
              bottom_state = true;
              break;
            }
            //Check if hit existing block
            else{
              if(squares[shape_pos[i].y_c][shape_pos[i].x_c].inused == true){
                temp_state = false;
                bottom_state = true;
                break;
              }
            }
          }
          
          //Shift back if next move is invalid
          if(temp_state == false){
            for(int i=0; i<shape_pos.length; i++){
              shape_pos[i].y_c = shape_pos[i].y_c - 1;
              //Check if at the bottom
              if(shape_pos[i].y_c == 19){
                bottom_state = true;
              }
            }
          }
          
          //Clear previous position color
          for(int i=0; i<prev_shape.length; i++){
            squares[prev_shape[i].y_c][prev_shape[i].x_c].setBackground(Color.white);
            squares[prev_shape[i].y_c][prev_shape[i].x_c].inused = false;
          }
          
          //Update new coordinate
          tetris_moving.set_shape_cord(shape_pos);
          shape_pos = tetris_moving.get_shape_cord();
          
          //Color in new coordinate
          for(int i=0; i<shape_pos.length; i++){
            squares[shape_pos[i].y_c][shape_pos[i].x_c].setBackground(colors[prev_rand]);
          }
          
          //If soft drop at bottom -> lock the piece
          if(bottom_state == true){
            action_ok = false;
            for(int i=0; i<shape_pos.length; i++){
              squares[shape_pos[i].y_c][shape_pos[i].x_c].inused = true;
            }
            
            //Spawn new block
            spawn_tetris();
          }
          else{
            game_timer.start();
          }
        }
      }  //End of Soft Drop
      //Pause/Unpause Game
      else if(e.getKeyCode() == KeyEvent.VK_C){
        pause_game();
      }
      //Reset game
      else if(e.getKeyCode() == KeyEvent.VK_R){
        reset_game();
      }
    }  //End of keyPressed
    
    @Override
    public void keyReleased(KeyEvent e){
    } 
    
    @Override
    public void keyTyped(KeyEvent e){
    }
  }
  
  
}