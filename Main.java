import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.io.IOException;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class Main {
  
  public static void main(String[] args) throws IOException, IllegalCalculatorInput {
    JACC jacc = new JACC();   
    jacc.startCalculator();


    // JACCTest jt = new JACCTest();
    // int runs = 0;
    // int nans = 0;
    // BufferedWriter writer = new BufferedWriter(new FileWriter("out.txt"));
    
    
    
    // while (jt.corr && runs < 5) {
    //   String [] inf = jt.variableTest(false);
    //   writer.write(inf[3] + "\n");
    //   writer.write(inf[2] + "\n");
    //   writer.write(inf[0] + "\n");
    //   runs++;
    // }
    // writer.close();
    
    // long totalTime = 0;
    // int runs = 10;
    // for(int i = 0; i < runs; i++) {
    //   boolean corr = false;
    //   long startTime = 0;
    //   long elapsedTime = 0;

    //   try {
    //     File readFile = new File("out.txt");
    //       Scanner myReader = new Scanner(readFile);
    //       startTime = System.currentTimeMillis();
    //       while (myReader.hasNextLine()) {
    //         jacc.parseString(myReader.nextLine());
    //         double parseVal = jacc.parseString(myReader.nextLine());
    //         double corrVal = Double.parseDouble(myReader.nextLine());
    //         if (parseVal == corrVal || (Double.isNaN(corrVal) && Double.isNaN(parseVal))) {
    //           corr = true;
    //         } else {
    //           corr = false;
    //         }
            
    //       }
    //       elapsedTime = System.currentTimeMillis() - startTime;
    //       myReader.close();
    //   } catch (FileNotFoundException e) {
    //     System.out.println("An error occurred.");
    //     e.printStackTrace();
    //   }
      
    //   totalTime += elapsedTime;
    //   System.out.println(elapsedTime);
    //   System.out.println(jacc.timeTokenizeing);
    //   System.out.println(jacc.timeFuncParsing);
    //   System.out.println(jacc.timeElementaryParsing);
    //   jacc.timeTokenizeing = 0;
    //   jacc.timeFuncParsing = 0;
    //   jacc.timeElementaryParsing = 0;
    // }
    // System.out.println(totalTime/runs);
    


    // System.out.println(nans);
    // System.out.println(jt.divByZeros);
    // 1630, 1596, 1650, 1613, 1616
    // 1025

    // boolean corr = true;
    // int[] allowedFunc = {0,1,2,3,4,5};
    // int runs = 0;
    // String[] inf;
    // while (corr && runs < 100000) {
    //   inf = jt.elementaryTest(true);
    //   corr = jt.corr;
    //   if (Double.parseDouble(inf[0]) != 0) {
    //     System.out.println(inf[2]);
    //     System.out.println(inf[1]);
    //     System.out.println(inf[0]);
    //     System.out.println(corr);
    //     System.out.println(runs);
    //   }
    //   runs++;
      
      
    // }
    // inf = jt.elementaryTest(true);
    // System.out.println(inf[2]);
    // System.out.println(inf[1]);
    // System.out.println(inf[0]);

    // JACC jacc = new JACC();
    // jacc.startCalculator();
    
  }
}