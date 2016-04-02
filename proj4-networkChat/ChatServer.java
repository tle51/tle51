/* CS342 Project 4
 * ChatServer, run this for server
 * 
 * Kevin Tang, Tan Le, Tsz Lam
 * 
 */ 

import java.io.*;
import java.net.*;
import java.util.ArrayList;


public class ChatServer {
  
  //Port to listem on 
  private static final int PORT = 10001;
  // List of all used user names
  private static ArrayList<String> users;
  //List of all user streams
  private static ArrayList<PrintWriter> user_stream_list;
  
  
  public static void main(String[] args) throws Exception {
    users = new ArrayList<String>();
    user_stream_list = new ArrayList<PrintWriter>();
    ServerSocket listener = new ServerSocket(PORT);
    InetAddress addr = InetAddress.getLocalHost();
    String serverAddress = addr.getHostAddress();
    try {
      System.out.println("CS342_Chat server is running...");
      System.out.println(serverAddress);
      while (true) {
        new user_handler(listener.accept()).start();
      }
    }
    catch(Exception e){
      System.out.println("Server failed to start");
      System.exit(-1);
    }
    finally {
      System.out.println("Shutting down server...");
      listener.close();
    }
  }
  
  //Create a thread for each user connected to server
  private static class user_handler extends Thread {
    private BufferedReader in;
    private PrintWriter out;
    private String user = "";
    private Socket the_socket;
    
    
    
    private user_handler(Socket socket) {
      the_socket = socket;
    }
    private boolean vertifie_name(String user){
      if (!users.contains(user)) {
        users.add(user);
        return true;            
      }  
      return false;
    }
    
    private void setup_stream() throws IOException{
      //Start streams form server to clients
      in = new BufferedReader(new InputStreamReader(
                                                    the_socket.getInputStream()));
      out = new PrintWriter(the_socket.getOutputStream(), true);
      
    }
    
    //Validate the name give by a client
    private void checking_name() throws IOException{
      while (true) {
        out.println("Vertified_Name");
        user = in.readLine();
        if (user == null) {
          return;
        }
        synchronized (users) {
          if(vertifie_name(user)){
            break; 
          }
        }
      }
      
    }
    //Notify client their name is now active
    private void give_user_ok() throws IOException{
      //HandShake
      out.println("Name_OK");
      //Add out stream to collection
      synchronized(user_stream_list){
        user_stream_list.add(out);
      }
      
    }
    //Notify others new user has joined
    private void new_user_joined() throws IOException{
      for (PrintWriter out_item : user_stream_list) { 
        out_item.println("Message "+ user + " has joined the chat."); 
      }
    }
    
    //Add new user to online list
    private void update_user_list() throws IOException{
      for (PrintWriter out_item : user_stream_list) {
        //Send null to clear text area
        out_item.println("null");
        
        for(int i = 0; i< users.size(); i++){
          out_item.println("Online" + users.get(i)); 
        }
      }
    }
    
    //Public messages
    private void public_write(String input) throws IOException{
      for (PrintWriter out_item : user_stream_list) { 
        out_item.println("Message " + user + " : " + input);
      } 
    }
    //Private messages
    private void private_write(String input) throws IOException{
      String[] parts = input.split(" ",3);
      if(parts.length != 3){
        out.println("Message ERROR: /private command syntax incorrect");
      }
      else{
        //tag message as private and send
        for (PrintWriter out_item : user_stream_list) {
          
          out_item.println("Private " + parts[1] +" "+ user + " : " + parts[2]);
        }
        
      }
      /* // Debug only
       for(String ret : parts){
       System.out.println(ret); 
       }
       */ 
    }
    //Notify others user has left
    private void a_user_has_left() throws IOException{
      for (PrintWriter out_item : user_stream_list) { 
        out_item.println("Message " + user + " has left chat.");
      } 
    }
    
    //Clean up
    private void remove_user()throws IOException{
      if (user != null) {
        synchronized(users){
          users.remove(user);
        }
      }
      if (out != null) {
        synchronized(user_stream_list){
          user_stream_list.remove(out);
        }
      }
      
      the_socket.close();
      a_user_has_left();
    }
    //Server interaction to user
    public void run() {
      try {
        setup_stream();
        
        //Ask for a user name from client, and check if name avaliable               
        checking_name();
        
        //Give user ok signal
        give_user_ok();
        
        //Notifiy all user new user had joined
        new_user_joined();
        
        //System.out.println(users);
        update_user_list();
        
        
        //Send message to all users or tag a private message and send then sort out from client
        //side
        while (true) {
          String input = in.readLine();
          if (input == null) {
            return;
          }
          //Send private message
          if(input.startsWith("/private")){
            private_write(input);
          }
          
          //Send message to all client
          else{
            public_write(input);
          }
          
        }
      } 
      catch (IOException e) {
        System.out.println(e);
      } 
      finally {
        //Remove user form list and no longer stream messages to them
        try{
          remove_user();
          update_user_list();
        }
        catch (Exception e){
          System.out.println(e); 
        }
      }
    }
  }
}