public class Test{
  public static void main(String args[]){
    HugeUnsignedInteger one = new HugeUnsignedInteger("1034563456345634563456345656");
    HugeUnsignedInteger two = new HugeUnsignedInteger("1534634563456345643563456456");
//    HugeUnsignedInteger three = new HugeUnsignedInteger("3242359292923939548");
//    HugeUnsignedInteger four = new HugeUnsignedInteger("12345");
//    HugeUnsignedInteger five = new HugeUnsignedInteger("12334");
    
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
    
    //Division
    //System.out.println(1423234/23238);
    //System.out.println(9999/111);
    System.out.println("Division: " + one.division(two));
    
    //Modulus
    //System.out.println(3627272327%227272723);
    //System.out.println(0%10);
//    try{
//      System.out.println(one.subtraction(three));
//      System.out.println(four.subtraction(five));
//    }
//    catch(SubtractionException e){
//      System.err.println("Negative");
//    }
    System.out.println("Modulus: " + one.modulus(two));
    
    //Relational Operations
    System.out.println("Equal: " + one.equalTo(two));
    System.out.println("Greater: " + one.greaterThan(two));
    System.out.println("Less: " + one.lessThan(two));
   
    
  } 
}