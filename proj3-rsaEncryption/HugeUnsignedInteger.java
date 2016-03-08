/** Tan Le (tle51) & Janki Patel (jpate53)
  * CS 342 - Project 3: RSA Encryption/Decryption
  * Huge-Unsigned-Integer Class - Hold very large unsigned integer values and include operations
  */
import java.io.*;
import java.lang.*;

public class HugeUnsignedInteger{
  private String value;
  private int[] arr;
  private int numDigit;
  private int i, j;
  
  //Constructor
  public HugeUnsignedInteger(String input){
    value = input;
    toArray();
  }
  
  //Covert value to array
  public void toArray(){
    numDigit = value.length();  //Count number of digits
    arr = new int[numDigit];  //Dynamically allocate array space
    
    //Store value to array
    StringReader strRead = new StringReader(value);
    try{
      for(i=numDigit-1; i>=0; i--){
        char c = (char) strRead.read();
        arr[i] = Character.getNumericValue(c);
      }
    }
    catch(IOException e){
      System.err.println(e);
    }
  }
  
  //Addition Operation
  public String addition(HugeUnsignedInteger value2){
    String result = "";
    int tempSize;
    int tempCount;
    //Check for the larger array size of the two operands
    if(numDigit > value2.numDigit){
      tempSize = numDigit;
    }
    else{
      tempSize = value2.numDigit;
    }
    //Initialize temp array to zero
    int[] tempArr = new int[tempSize+1];
    for(i=0; i<tempSize+1; i++){
      tempArr[i] = 0;
    }
    //Add two large int array together
    j=0;
    while(j < value2.numDigit || j < numDigit){
      //If end of array1 -> copy rest of array2 contents
      if(j >= numDigit){
        tempArr[j] = tempArr[j] + value2.arr[j];
      }
      //If end of array2 -> copy rest of array1 contents
      else if(j >= value2.numDigit){
        tempArr[j] = tempArr[j] + arr[j];
      }
      else{
        int tempAdd = arr[j] + value2.arr[j];
        //Addition result is less than 10
        if(tempAdd < 10){
          int tempAdd2 = tempArr[j] + tempAdd;
          if(tempAdd2 < 10){
            tempArr[j] = tempAdd2;
          }
          else{
            tempArr[j] = tempArr[j] + (tempAdd2 - 10);
            tempArr[j+1] = tempArr[j+1] + 1;
          }
        }
        else{
          tempArr[j] = tempArr[j] + (tempAdd - 10);
          tempArr[j+1] = tempArr[j+1] + 1;
        }
      }
      j++;
    }
    //If there is an extra leading zero in the tempArr
    if(tempArr[tempSize] == 0){
      //Remove the extra space
      tempCount = tempSize;
    }
    else{
      tempCount = tempSize+1;
    }
    //Convert int array to string
    for(i=tempCount-1; i>=0; i--){
      result = result + Integer.toString(tempArr[i]);
    }
    
    return result;
  }
  
  //Subtraction Operation
  public String subtraction(HugeUnsignedInteger value2) throws SubtractionException{
    String result = "";
    int tempSize;
    int tempCount;
    //Check for the larger array of the two operands
    if(numDigit > value2.numDigit){
      tempSize = numDigit;
    }
    else{
      tempSize = value2.numDigit;
    }
    //Copy array1 to tempArr
    int[] tempArr = new int[tempSize+1];
    for(i=0; i<tempSize+1; i++){
      if(i < numDigit){
        tempArr[i] = arr[i];
      }
      //Set remaining spaces of tempArr to 0
      else{
        tempArr[i] = 0;
      }
    }
    //Subtract two large int array together
    j=0;
    while(j < value2.numDigit){
      int tempSub = tempArr[j] - value2.arr[j];
      //Normal subtraction
      if(tempSub >= 0){
        tempArr[j] = tempSub;
      }
      //Need to borrow
      else{
        tempArr[j] = (tempArr[j] + 10) - value2.arr[j];
        tempArr[j+1] = tempArr[j+1] - 1;
      }
      j++;
    }
    //Check if negative number
    if(tempArr[tempSize] < 0){
      throw new SubtractionException("Number is negative");
    }
    else{
      if(tempArr[numDigit-1] == 0 && numDigit-1 != 0){
        tempCount = numDigit-1;
      }
      else{
        tempCount = numDigit;
      }
      for(i=tempCount-1; i>=0; i--){
        result = result + Integer.toString(tempArr[i]);
      }
    }
    return result;
  }
  
  //Multiplication Operation
  public String multiplication(HugeUnsignedInteger value2){
    String result = "";
    int tempSize;  //Size of product1
    int tempCount;  //Size for result string
    int productSize;  //Size of product2
    int[] tempArr;
    int[] productArr;
    //Determine the larger digit number
    if(numDigit >= value2.numDigit){
      tempSize = numDigit;
      //Copy array1 to tempArr
      tempArr = new int[tempSize];
      for(i=0; i<tempSize; i++){
        tempArr[i] = arr[i];
      }
      //Copy array2 to productArr
      productSize = value2.numDigit;
      productArr = new int[value2.numDigit];
      for(i=0; i< value2.numDigit; i++){
        productArr[i] = value2.arr[i];
      }
    }
    else{
      tempSize = value2.numDigit;
      //Copy array2 to tempArr
      tempArr = new int[tempSize];
      for(i=0; i<tempSize; i++){
        tempArr[i] = value2.arr[i];
      }
      //Copy array1 to productArr
      productSize = numDigit;
      productArr = new int[numDigit];
      for(i=0; i<numDigit; i++){
        productArr[i] = arr[i];
      }
    }
    //Initialize resultArr
    int[] resultArr = new int[tempSize+productSize];
    for(i=0; i<tempSize+productSize; i++){
      resultArr[i] = 0;
    }
    //Multiply two array (tempArr x productArr)
    for(i=0; i<productSize; i++){  //Go thru productArr
      for(j=0; j<tempSize; j++){  //Go thru tempArr
        int tempProduct = productArr[i] * tempArr[j];
        if(tempProduct < 10){
          int tempAddition = resultArr[i+j] + tempProduct;
          if(tempAddition < 10){
            resultArr[i+j] = tempAddition;
          }
          else{
            resultArr[i+j] = tempAddition - 10;
            resultArr[i+j+1] = resultArr[i+j+1] + 1;
          }
        }
        else{
          //Check if need to carry
          int tempAdd = resultArr[i+j] + (tempProduct % 10);
          if(tempAdd < 10){
            resultArr[i+j] = tempAdd;
          }
          else{
            resultArr[i+j] = tempAdd - 10;
            resultArr[i+j+1] = resultArr[i+j+1] + 1;
          }
          int tempAdd2 = resultArr[i+j+1] + (tempProduct / 10 % 10);
          if(tempAdd2 < 10){
            resultArr[i+j+1] = tempAdd2;
          }
          else{
            resultArr[i+j+1] = tempAdd2 - 10;
            resultArr[i+j+2] =  resultArr[i+j+2] + 1;
          }
          
//          for(int x = 0; x<tempSize+productSize; x++){
//            if(resultArr[x] > 10){
//              resultArr[x] = resultArr[x] - 10;
//              resultArr[x+1] = resultArr[x+1] + 1;
//            }
//          }
//          //Check if need to carry
//          int tempAdd = resultArr[i+j] + (tempProduct % 10);
//          int tempAdd2 = resultArr[i+j+1] + (tempProduct / 10 % 10);
//          resultArr[i+j] = tempAdd;
//          resultArr[i+j+1] = tempAdd2;
//          
//          for(int x = 0; x<tempSize+productSize; x++){
//            if(resultArr[x] > 10){
//              resultArr[x] = resultArr[x] - 10;
//              resultArr[x+1] = resultArr[x+1] + 1;
//            }
//          }
          
        }
      }
    }
    //Remove leading zero
    if(resultArr[tempSize+productSize-1] == 0){
      tempCount = tempSize+productSize-1;
    }
    else{
      tempCount = tempSize+productSize;
    }
    //Convert int array to string
    for(i=tempCount-1; i>=0; i--){
      result = result + Integer.toString(resultArr[i]);
    }
    return result;
  }
  
  //Division Operation
  public String division(HugeUnsignedInteger value2){
    String result = "";
    
    return result;
  }
  
  //------------Test----------------
  public int getDigit(){
    return numDigit;
  }
  public void printValue(){
    System.out.println(value);
    for(i=0; i<numDigit; i++){
      System.out.print(arr[i]);
    }
  }
  //--------------------------------

}  //End of class

 //Subtraction Exception
  class SubtractionException extends Exception{
    public SubtractionException(String msg){
      super(msg);
    }
  }