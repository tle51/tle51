public class Test{
  public static void main(String args[]){
    HugeUnsignedInteger one = new HugeUnsignedInteger("9999999999");
    HugeUnsignedInteger two = new HugeUnsignedInteger("2222222222");
    
    //Addition
    System.out.println("Add: " + one.addition(two));
    
    //Subtraction
    try{
      System.out.println("Subtract: " + one.subtraction(two));
    }
    catch(SubtractionException e){
      System.err.println("Subtraction result is a negative number");
    }
    
    //Multiplication
    //System.out.println(56 / 10 % 10);
    System.out.println("Multiply: " + one.multiplication(two));
    
  } 
}