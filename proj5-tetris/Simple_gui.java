/** Tan Le (tle51), Tsz Lam (tlam22), Kevin Tang (ktang20)
  * CS 342 - Project 5: Tetris
  * Simple_gui class - Set up all the different components inside the window. Include menu,
  *                    score, and game grid.
  */

import java.awt.GridLayout;
import java.awt.event.*;
//import javax.swing.JLabel;
import javax.swing.*;
//import java.awt.Color;
//import javax.swing.border.Border;
//import java.io.*;
import java.awt.*;

public class Simple_gui extends JFrame{
  //Grid Dimension
  private static final int height = 20;
  private static final int width = 10;
  //Panels
  private JPanel main_panel = new JPanel();
  private JPanel game_panel = new JPanel();
  private JPanel side_panel = new JPanel();
  private JPanel score_panel = new JPanel();
  private JLabel total_score = new JLabel();
  private JPanel line_panel = new JPanel();
  private JLabel total_line = new JLabel();
  private JPanel level_panel = new JPanel();
  private JLabel total_level = new JLabel();
  private JPanel clock_panel = new JPanel();
  private JLabel total_clock = new JLabel();
  private JPanel next_block_panel = new JPanel();
  private JLabel next_block_label = new JLabel();
  //Menu
  private JMenuBar menu_bar = new JMenuBar();
  private JMenu game_menu = new JMenu("Game");
  private JMenu help_menu = new JMenu("Help");
  private JMenuItem restart_menu = new JMenuItem("Restart");
  private JMenuItem quit_menu = new JMenuItem("Quit");
  private JMenuItem about_menu = new JMenuItem("About");
  private JMenuItem help_menu_item = new JMenuItem("Help");
  //Game grid
  private inner_gui game_gui = new inner_gui(width,height);
  private Timer info_timer = new Timer(1, new InfoHandler());
  
  //Constructor
  public Simple_gui(){
    //Window Title
    super("Tetris");
    
    //Initialize and start score timer
    info_timer.start();
    
    //Set up panels
    main_panel.setLayout(new BorderLayout());
    game_panel.setLayout(new BorderLayout());
    side_panel.setLayout(new BoxLayout(side_panel, BoxLayout.PAGE_AXIS));
    //Game panel
    //game_gui = new inner_gui(width,height);
    game_gui.setFocusable(true);
    game_panel.add(game_gui, BorderLayout.CENTER);
    game_panel.setPreferredSize(new Dimension(280,500));
    //Score panel
    score_panel.setLayout(new BorderLayout());
    score_panel.setPreferredSize(new Dimension(300, 50));
    score_panel.add(new Label("Score:"), BorderLayout.NORTH);
    score_panel.add(total_score, BorderLayout.CENTER);
    score_panel.setBorder(BorderFactory.createLineBorder(Color.black));
    //Line panel
    line_panel.setLayout(new BorderLayout());
    line_panel.setPreferredSize(new Dimension(300, 50));
    line_panel.add(new Label("Line Cleared:"),BorderLayout.NORTH);
    line_panel.add(total_line, BorderLayout.CENTER);
    line_panel.setBorder(BorderFactory.createLineBorder(Color.black));
    //Level panel
    level_panel.setLayout(new BorderLayout());
    level_panel.setPreferredSize(new Dimension(300, 50));
    level_panel.add(new Label("Level:"), BorderLayout.NORTH);
    level_panel.add(total_level, BorderLayout.CENTER);
    level_panel.setBorder(BorderFactory.createLineBorder(Color.black));
    //Clock panel
    clock_panel.setLayout(new BorderLayout());
    clock_panel.setPreferredSize(new Dimension(300, 50));
    clock_panel.add(new Label("Time:"), BorderLayout.NORTH);
    clock_panel.add(total_clock, BorderLayout.CENTER);
    clock_panel.setBorder(BorderFactory.createLineBorder(Color.black));
    //Next block panel
    next_block_panel.setLayout(new BorderLayout());
    next_block_panel.setPreferredSize(new Dimension(300, 50));
    next_block_panel.add(new Label("Next Block:"), BorderLayout.NORTH);
    next_block_panel.add(next_block_label, BorderLayout.CENTER);
    next_block_panel.setBorder(BorderFactory.createLineBorder(Color.black));
    //Side panel
    side_panel.setPreferredSize(new Dimension(80,500));
    side_panel.add(next_block_panel);
    side_panel.add(clock_panel);
    side_panel.add(level_panel);
    side_panel.add(score_panel);
    side_panel.add(line_panel);
    //Add game panel and score panel to the main panel
    main_panel.add(game_panel, BorderLayout.CENTER);
    main_panel.add(side_panel, BorderLayout.WEST);
    
    //Set up menu bar
    menu_bar.add(game_menu);
    menu_bar.add(help_menu);
    game_menu.add(restart_menu);
    game_menu.add(quit_menu);
    help_menu.add(help_menu_item);
    help_menu.add(about_menu);
    setJMenuBar(menu_bar);
     
    //restart_menu_item action listner
    restart_menu.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent arg0){
        
        game_gui.reset_game();
      }
    });
    
    //quit_menu action listener
    quit_menu.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent arg0){
        System.exit(0);
        
      }      
    });
    
    //help_menu action listner
    about_menu.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent arg0){
        JOptionPane.showMessageDialog( null,
                                      "CS342 Project 5 Tetris\n Tsz Lam\n Tan Le\n Kevin Tang\n","About",JOptionPane.INFORMATION_MESSAGE );
        
      }     
      
    });
    
    //about_menu action listener
    help_menu_item.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent arg0){
        JOptionPane.showMessageDialog( null,
                                      "Controls:\n"+
                                      " Left_arrow - move piece left\n" +
                                      " Right_arrow - move piece right\n" +
                                      " Z - rotate left\n" +
                                      " X - rotate right\n" +
                                      " Down Arrow - soft drop\n"+
                                      " Space - hard drop\n"+
                                      " C - pause game\n"+
                                      " R - reset game\n\n"+
                                      "Game Menu:\n"+
                                      "Restart - Restart the current game and start a new one\n"+
                                      "Quit - Quit the game\n\n"+
                                      "Help Menu:\n"+
                                      "Help - Open a window that explain the functionality of the game\n"+
                                      "About - Give information about the project\n\n"+  
                                      "Objective:\n"+
                                      "Complete full solid lines (no gaps).\n"+ 
                                      "When you make a line it disappears and all the blocks shift accordingly.\n" + 
                                      "If you let the blocks reach the top of the Tetris board, you lose.\n"+
                                      "You cannot win a Tetris game, though you can attempt to get the high score.\n"+
                                      "Source: http://playfreetetris.com/\n","Help",JOptionPane.INFORMATION_MESSAGE );
        
      }     
      
    });
    
    //Set up game window   
    this.add(main_panel);
    this.setSize(360,495);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
    this.setResizable(false);
  }  //End of constructor
  
  //Handler for updating score
  private class InfoHandler implements ActionListener{
    public void actionPerformed(ActionEvent e){
      //Update Score
      int current_score = game_gui.update_score();
      String score_string = Integer.toString(current_score);
      total_score.setText(score_string);
      
      //Update Line Cleared
      int game_line = game_gui.update_line();
      String line_string = Integer.toString(game_line);
      total_line.setText(line_string);
      
      //Update Current Level
      int game_level = game_gui.update_level();
      String level_string = Integer.toString(game_level);
      total_level.setText(level_string);
      
      //Update Clock
      int current_clock = game_gui.update_clock();
      String clock_string = Integer.toString(current_clock);
      total_clock.setText(clock_string);
      
      //Update Next Block
      String[] block_string = {"Empty", "Line", "Square", "L", "J", "T", "Z", "S"};
      int next_block = game_gui.get_next_block();
      next_block_label.setText(block_string[next_block]);
    }
  }
  
  public static void main(String[] args) {
    new Simple_gui();
  }
  
}
