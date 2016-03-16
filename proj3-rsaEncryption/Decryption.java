/** Tan Le (tle51) & Janki Patel (jpate53)
 * CS 342 - Project 3: RSA Encryption/Decryption
 * Encryption Class - Encrypt a block file using a public key
 */

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class Decryption{
    private HugeUnsignedInteger d;
    private HugeUnsignedInteger n;
    private int i, blockSize;
    private String privateName;
    private File privateFile;  //Private key file
    private String eName;
    private String dName;
    private File eFile;
    private File dFile;
    
    //Constructor
    public Decryption(String filePath, int block, String savePath, String ePath){
        privateName = filePath.concat(".txt");
        dName = savePath.concat(".txt");
        dFile = new File(dName);  //Decrypted File
        eName = ePath.concat(".txt");
        eFile = new File(eName); //Encrypted File
        privateFile = new File(privateName);
        blockSize = block;
        System.out.println("---Inside Decryption Class---");  //Test
        readXML();
        decrypt();
        System.out.print("");  //Test
    }
    
    //Read XML File
    public void readXML(){
        try{
            DocumentBuilderFactory docBuilder = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = docBuilder.newDocumentBuilder();
            Document doc = builder.parse(privateFile);
            NodeList nList = doc.getElementsByTagName("rsakey");
            String tempD, tempN;
            for(i=0; i<nList.getLength(); i++){
                Node node = nList.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element elementValue = (Element) node;
                    //Get e value
                    tempD = elementValue.getElementsByTagName("dvalue").item(0).getTextContent();
                    d = new HugeUnsignedInteger(tempD);
                    //Get n value
                    tempN = elementValue.getElementsByTagName("nvalue").item(0).getTextContent();
                    n = new HugeUnsignedInteger(tempN);
                    
                    System.out.println("D: " + d.value);  //Test
                    System.out.println("N: " + n.value);  //Test
                }
            }
        }
        catch(Exception e){
            System.err.println(e);
        }
    }
    
    //Decrpyt the file
    public void decrypt(){
        String tempString = "";
        HugeUnsignedInteger inputNumber;
        HugeUnsignedInteger outputNumber;
        HugeUnsignedInteger tempNumber;
        HugeUnsignedInteger fVal;
        HugeUnsignedInteger expVal;
        HugeUnsignedInteger dd;
        HugeUnsignedInteger one = new HugeUnsignedInteger("1");
        HugeUnsignedInteger two = new HugeUnsignedInteger("2");
        //Read each block
        try{
            BufferedReader fRead2 = new BufferedReader(new FileReader(eFile));
            
            //Output File
            if(dFile.exists()){
                dFile.delete();
            }
            dFile.createNewFile();
            BufferedWriter fWrite = new BufferedWriter(new FileWriter(dFile, true));
            
            //int c;
            while((tempString = fRead2.readLine()) != null){
                //Convert to HugeUnsignedInteger
                //inputNumber = new HugeUnsignedInteger(tempString);
                expVal = new HugeUnsignedInteger(tempString);
                System.out.println("Decrpytion Input: " + tempString);  //Test
                System.out.println("D: " + d.value);  //Test
                System.out.println("N: " + n.value);  //Test
                //C=M^d mod n
                //P = C^x * (exp^2 % n)^d % n
                fVal = new HugeUnsignedInteger("1");
                dd = new HugeUnsignedInteger(d.value);
                //System.out.println(d.value);
                while(dd.equalTo(one) == 0){  //d != 1
                    //          //Check if d value is even or odd
                    //          if(d.modulus(two).equals("1")){  //Odd
                    //            //c * c
                    //            String cResult = fVal.multiplication(inputNumber);
                    //            fVal = new HugeUnsignedInteger(cResult);
                    //            //exp * exp
                    //            String expResult = expVal.multiplication(expVal);
                    //            expVal = new HugeUnsignedInteger(expResult);
                    //            //exp % n
                    //            expResult = expVal.modulus(n);
                    //            expVal = new HugeUnsignedInteger(expResult);
                    //            try{
                    //            //d-1
                    //              String newD = dd.subtraction(one);
                    //              dd = new HugeUnsignedInteger(newD);
                    //            }
                    //            catch(SubtractionException ee){
                    //              System.err.println(ee);
                    //            }
                    //          }
                    //          else{  //Even
                    //            //c * 1
                    //            String cResult = fVal.multiplication(one);
                    //            fVal = new HugeUnsignedInteger(cResult);
                    //            //exp * exp
                    //            String expResult = expVal.multiplication(expVal);
                    //            expVal = new HugeUnsignedInteger(expResult);
                    //            //exp % n
                    //            expResult = expVal.modulus(n);
                    //            expVal = new HugeUnsignedInteger(expResult);
                    //          }
                    //          String divideD = dd.division(two);
                    //          dd = new HugeUnsignedInteger(divideD);
                    
                    if(dd.modulus(two).equals("1")){  //Odd
                        String fResult = fVal.multiplication(expVal);
                        fVal = new HugeUnsignedInteger(fResult);
                        //d-1
                        try{
                            String newD = dd.subtraction(one);
                            dd = new HugeUnsignedInteger(newD);
                        }
                        catch(SubtractionException ee){
                            //System.out.println("HERE");
                            System.err.println(ee);
                        }
                    }
                    //exp * exp
                    //System.out.println(expVal.value);
                    //String expResult1 = expVal.multiplication(expVal);
                    expVal = new HugeUnsignedInteger(expVal.multiplication(expVal));
                    //exp % n
                    //expResult1 = expVal.modulus(n);
                    expVal = new HugeUnsignedInteger(expVal.modulus(n));
                    
                    //String divideD = dd.division(two);
                    dd = new HugeUnsignedInteger(dd.division(two));
                }
                //When d is equal to 1
                //System.out.println(fVal.value);
                //String expResult = expVal.multiplication(fVal);
                expVal = new HugeUnsignedInteger(expVal.multiplication(fVal));
                //String resultVal = expVal.modulus(n);
                outputNumber = new HugeUnsignedInteger(expVal.modulus(n));
                System.out.println("Decryption Output: " + outputNumber.value);  //Test
                
                //Insert leading zeros
                String outputLen = outputNumber.value;
                while(outputLen.length() < blockSize*2)
                {
                    outputLen = "0" + outputLen;
                }
                //Odd number that is greater than total block size
                HugeUnsignedInteger overCount = new HugeUnsignedInteger(Integer.toString(outputLen.length()));
                HugeUnsignedInteger tt2 = new HugeUnsignedInteger("2");
                if(overCount.modulus(tt2).equals("1")){  //Odd length
                  outputLen = "0" + outputLen;
                }
                
                //Write to file
                fWrite.write(outputLen);
                fWrite.newLine();
                tempString = "";
            }
            fWrite.close();
            fRead2.close();
        }
        catch(IOException e){
            System.err.println(e);
        }
        
    }
    
    //-----Test------
    //  public static void main(String[] args){
    //    Decryption dd = new Decryption("rsakey2.txt");
    //  }
    
}