 
/**
  * The {@code the_score} class is responsible keeping
  * track of the current score of the game
  */
public class the_score {
  /*Score for game*/
 private int total_score;
  
/**
  * Create the score.
  */
  public the_score(){
    total_score = 0;
  }
/**
  * Set score to 0.
  */
  public void score_clear(){
    total_score = 0;
  }
  
/**
  * Set score to the specified value.
  * @param n, score to update.
  */
  public void set_score(int n){
   total_score  = n;
  }
  
/**
  * Caculate the score base on the state of the game.
  * @param multiplyer, number of lines cleared at once.
  * @param level, current level of the game.
  */
  public void update_score(int multiplyer, int level){
    switch(multiplyer){
      case 1:
        total_score += 40 * level;
        break;
      case 2:
        total_score += 100 * level;
        break;
      case 3:
        total_score += 300 * level;
        break;
      default:
        total_score += 1200 * level;
      
    }
    
  }
  
/**
  * Get the score.
  * @return the current score.
  */
  public int get_score(){
     return  total_score;
    }
  
  
}
