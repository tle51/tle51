public class Test{
  public static void main(String args[]){
    HugeUnsignedInteger one = new HugeUnsignedInteger("3794121054222720173140211063980246228235929141189113149936123925427953245210355568604802853069292468872036966962907773315648142328289827054827487435359111951943171534155985572981133750477217845231710260610778177964646474772291551256666302132700669970517916636957425277183875355589109526759426328171274530082944192454044512027646671573317170485578282599930560208626681428270880062142202489015300986303961289416763715495681106706543872401976213080257077781144512323989818310531244445846514237440000");
    HugeUnsignedInteger two = new HugeUnsignedInteger("410447653454359153");
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