/**
 * ******************************
 * Copyright 2015: smanna@cpp.edu
 * CS 241
 * PA3
 * ******************************
 * !!!WARNING!!! You are not allowed
 * to make any changes to this code.
 */

import java.io.File;
import java.util.Scanner;
import java.util.Arrays;

public class GenerateScore {

  private int score;

  public GenerateScore(String testFile, String textFolder) {
    score = 0;
    grade(testFile,textFolder);
  }

  // scoring 
  private void grade(String testFile, String textFolder) {

    try {
      Scanner answers = new Scanner(new File(testFile));
      File[] files = (new File(textFolder)).listFiles();
      Arrays.sort(files);
      Huffman compressor;
      String filename = "";
      int answerCompressed    = 0,
          answerDecompressed  = 0,
          attemptCompressed   = 0,
          attemptDecompressed = 0;
      Boolean grade = false;
      int cases = 0;

      //print header
      printBorders();
      System.out.printf("|%-12s|%-12s|%-12s|%-12s|%-12s|%-8s|","Filename","Attempt Comp",
                        "Attempt Decom","Answer Comp","Answer Decom","CORRECT?");

      //grade attempts
      for (File file: files) {

        if (file.getName().equals(".DS_Store")) continue; //ignore hidden files
        cases++;

        try{

          //set up answers
     filename = answers.next();
          if (!filename.equals(file.getName() + ".txt")) { //if exception causes out of order
            do {
              answers.nextLine();
              filename = answers.next();
            } while (!filename.equals(file.getName() + ".txt") && answers.hasNextLine());
          }
          answerCompressed = answers.nextInt();
          answerDecompressed = Integer.parseInt(answers.nextLine().trim());

          //set up student attempts
          compressor = new Huffman(file);
          attemptCompressed = compressor.compress().length();
          attemptDecompressed = compressor.decompress().length();


          //grade
          grade = (attemptCompressed == answerCompressed
                   && attemptDecompressed == answerDecompressed);
          if (grade) {
            score++;
          }

          //print attempts, answers, score
          printBorders();
          System.out.printf("|%-12s|%-12s|%-13s|%-12s|%-12s|%-8s|",file.getName(),
                            attemptCompressed,attemptDecompressed,
                            answerCompressed,answerDecompressed,grade);

        //catch student's exceptions
        } catch (NullPointerException b) {
          printBorders();
          System.out.printf("|%-12s|%-12s|%-13s|%-12s|%-12s|%-8s|",filename,
                            "null","null",
                            answerCompressed,answerDecompressed,grade);

        } catch (Exception c) {
          c.printStackTrace();
        }
      }

      //print final score
      printBorders();
      System.out.println("Score = " + score + "/" + cases);
      answers.close();

    } catch (java.io.FileNotFoundException a) {
      System.out.println("Answer file not found");
    }
  }

  private void printBorders() {
    System.out.print("\n|");
    for (int i = 0; i < 74; i++) System.out.print("-");
    System.out.print("|\n");
  }

  // main
  public static void main(String[] args) {
    String golden_file1,
           golden_file2;
    if (args.length < 2 || args[0].equals("test")) {
      golden_file1 = "data/test.txt";
      golden_file2 = "data/test";
    } else if (args[0].equals("eval")) {
      golden_file1 = "data/eval.txt";
      golden_file2 = "data/wordsEn.txt";
    } else {
      System.out.println("Unknown option: " + args[0]);
      System.exit(1);
      return;
    }
    new GenerateScore(golden_file1, golden_file2);

  }
}
