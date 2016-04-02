/* CS342 Project 4
 * ChatClient, run this for client chat box
 * 
 * Kevin Tang, Tan Le, Tsz Lam
 * 
 */ 

import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
/* Commands for chat box:
 *
 * /Exit --- exit client
 * 
 * /private message_recevier_name the_message_goes_here --- sends private message to a specified user 
 * 
 */ 

public class ChatClient {
  
  private BufferedReader in;
  private String serverAddress;
  private PrintWriter out;
  private Socket socket;
  private String user_name = "";
  //Basic gui components
  private JFrame frame = new JFrame("CS342 Chat");
  private JTextArea messageArea = new JTextArea(10, 40);
  private JTextArea user_online_area = new JTextArea(30,10);
  private JTextField textField = new JTextField(40);
  //Drop down list (chat options)
  private String[] chatChoices = {"All", "Private"};
  private JComboBox<String> chatOption = new JComboBox<>(chatChoices);
  //GUI Layout
  private JPanel onlinePanel = new JPanel();
  private JPanel messagePanel = new JPanel();
  private JPanel textPanel = new JPanel();
  private JLabel onlineText = new JLabel("Online Users");
  private JLabel userName = new JLabel(" ");
  private JButton sendButton = new JButton("Send");
  //Menu Items
  private JMenuBar menuBar;
  private JMenu chatMenu, helpMenu;
  private JMenuItem aboutMenu, exitMenu, helpMenuItem;
  
  public ChatClient() {
    //Gui details
    set_up_gui();
    
    
    // Add Listeners to textfield ,send texts to server
    textField.addActionListener(new ActionListener() {
      
      public void actionPerformed(ActionEvent e) {
        //Exit if user types in Exit
        if(textField.getText().equals("/Exit")){
          try{
            out.close();
            in.close();
            socket.close();
            System.exit(0);
          }
          catch(IOException d){
            
          }
        }
        //Send message to server
        out.println(textField.getText());
        textField.setText("");
      }
    });
  }
  private void set_up_gui(){
    //Quick GUI 
    //Message box for user to input message
    //frame.getContentPane().add(textField, "South");
    frame.getContentPane().add(textPanel, "South");
    textPanel.setLayout(new BorderLayout());
    textPanel.add(textField, BorderLayout.CENTER);
    //Chat Options (drop down list)
    textPanel.add(chatOption, BorderLayout.WEST);
    chatOption.addActionListener(new chatHandler());
    //Send Button
    textPanel.add(sendButton, BorderLayout.EAST);
    sendButton.addActionListener(new sendHandler());
     
    //Users that are online
    //frame.getContentPane().add(new JScrollPane(user_online_area),"West");
    frame.getContentPane().add(onlinePanel, "West");
    onlinePanel.setLayout(new BorderLayout());
    onlinePanel.add(new JScrollPane(user_online_area), BorderLayout.SOUTH);
    onlinePanel.add(onlineText, BorderLayout.NORTH);
    
    //Displey messages from other users
    //frame.getContentPane().add(new JScrollPane(messageArea), "Center");
    frame.getContentPane().add(messagePanel, "Center");
    messagePanel.setLayout(new BorderLayout());
    messagePanel.add(new JScrollPane(messageArea), BorderLayout.CENTER);
    messagePanel.add(userName, BorderLayout.NORTH);
    
    //Set up menu
    menuBar = new JMenuBar();
    chatMenu = new JMenu("Chat");
    helpMenu = new JMenu("Help");
    exitMenu = new JMenuItem("Exit");
    exitMenu.addActionListener(new exitHandler());
    helpMenuItem = new JMenuItem("Help");
    helpMenuItem.addActionListener(new helpHandler());
    aboutMenu = new JMenuItem("About");
    aboutMenu.addActionListener(new aboutHandler());
    frame.setJMenuBar(menuBar);
    menuBar.add(chatMenu);
    menuBar.add(helpMenu);
    chatMenu.add(exitMenu);
    helpMenu.add(helpMenuItem);
    helpMenu.add(aboutMenu);
    
    //GUI window setup
    frame.pack();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    textField.setEditable(false);
    messageArea.setEditable(false);
    user_online_area.setEditable(false);
    
  }
  
  //Get server IP
  private void getServerIP() {
    serverAddress = JOptionPane.showInputDialog(
                                                frame,
                                                "IP Address of server:",
                                                "CS342 Chat",
                                                JOptionPane.QUESTION_MESSAGE);
  }
  
  //Get user name
  private void getUserInfo() {
    user_name = "";
    while(user_name.equals("")){
      user_name = JOptionPane.showInputDialog(
                                              frame,
                                              "User Name:",
                                              "Enter User Name",
                                              JOptionPane.QUESTION_MESSAGE);
      user_name = user_name.replaceAll("\\s+",""); 
    }
  }
  
  private void setup_stream() throws IOException{
    //Read and write to or from server, open streams
    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    out = new PrintWriter(socket.getOutputStream(), true); 
  }
  
  private void vertifie_name(String line) throws IOException{
    while(line.startsWith("Vertified_Name")) {
      getUserInfo();
      out.println(user_name);
      line = in.readLine();
    }
    if (line.startsWith("Name_OK")) {
      textField.setEditable(true);
      messageArea.append("Connected to server with IP: " + serverAddress+ "\n");
    }
    else{
      System.out.println("Vertification of name failed");
      System.out.println("Program exit...");
      System.exit(-1);
      
    }
  }
  
  //Communicate with the server
  private void client_and_server(String line) throws IOException{
    while(true){
      
      //Public messages 
      if (line.startsWith("Message")) {
        messageArea.append(line.substring(8) + "\n");
      }
      //Private messages
      else if(line.startsWith("Private")){
        //Split
        String[] parts = line.split(" ",4);
        if(parts.length != 4){
          System.out.println("Failed to recieved private message");
        }
        else{
          if(user_name.equals(parts[1]) || user_name.equals(parts[2])){
            messageArea.append("[To - "+ parts[1]+ "] " + parts[2] +" "+ parts[3] +"\n");
          }
        }
      }
      //Clear text field for online list (for updating)
      else if(line.startsWith("null")){
        user_online_area.setText("");
      }
      //Add new user to online list (insert all users who are currently online)
      else if(line.startsWith("Online")){
        user_online_area.append(line.substring(6) + "\n");
      }
      line = in.readLine();       
    }
    
  }
  
  //Talk to server
  private void run() throws IOException {
    //Try and connect if fail exit program
    getServerIP();
    try{
      socket = new Socket();
      //Wait 3 sec before timeout
      socket.connect(new InetSocketAddress(serverAddress,10001),3000);
      
      setup_stream();
      
      //Read instruction from server
      String line = in.readLine();
      //User must enter name and server must approve,
      //Server sents back vertification, must happen or program exit 
      vertifie_name(line);
      
      
      //Receive from server
      client_and_server(line);
    }
    //If connection is lost/ cannot be made to server at anytime terminate the program
    catch(Exception se){
      System.out.println(se);
      System.out.println("Error connecting to host...");
      System.out.println("Program Exit...");
      System.exit(1); 
      
    }
  }
  
  //Handler for the send button
  private class sendHandler implements ActionListener{
    public void actionPerformed(ActionEvent e){
      String inputText = textField.getText();
      out.println(inputText);
      textField.setText("");
    }
  }
  
  //Handler for help menu
  private class helpHandler implements ActionListener{
    public void actionPerformed(ActionEvent e){
      JOptionPane.showMessageDialog(frame,
                                    "To chat to all users in the chat room, select the 'All' option " 
                                    + "from the drop down list to the left of the text box.\n"
                                    + "To send a private chat to a specific person, select the 'Private' option or " 
                                    + "use this format: /private <user name> <message>", "Help", JOptionPane.PLAIN_MESSAGE);
    }
  }
  
  //Handler for about menu
  private class aboutHandler implements ActionListener{
    public void actionPerformed(ActionEvent e){
      JOptionPane.showMessageDialog(frame, 
                                    "Development Team: Tan Le, Tsz Lam, & Kevin Tang\n"
                                    +"Course: CS342\n"
                                      +"Programming Project 4: Networked Chat Application", "About", JOptionPane.INFORMATION_MESSAGE);
    }
  }
  
  //Handler for exiting from menu
  private class exitHandler implements ActionListener{
    public void actionPerformed(ActionEvent e){
      System.exit(0);
    }
  }
  
  //Handler for Chat Option
  private class chatHandler implements ActionListener{
    public void actionPerformed(ActionEvent e){
      JComboBox cb = (JComboBox)e.getSource();
      String option = (String)cb.getSelectedItem();
      
      //If All is selected -> set textfield to be default
      if(option.equals("All")){
        textField.setText("");
      }
      else if(option.equals("Private")){
        String toUser = JOptionPane.showInputDialog(frame, 
                                                    "Send private message to:", 
                                                    "Private Message", 
                                                    JOptionPane.PLAIN_MESSAGE);
        if(toUser == null){
          textField.setText("");
        }
        else{
          textField.setText("/private " + toUser + " ");
        }
      }
    }
  }
  
  public static void main(String[] args) throws Exception {
    ChatClient user = new ChatClient();
    user.run();
  }
}