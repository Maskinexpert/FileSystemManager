//import input.*;
import manipulation.*;
import logging.*;
import fileTypes.*;
import java.util.Scanner;
import java.io.*;



public class FSM {
  
  static void listFiles (File fileDir) {
    File[] fileList = fileDir.listFiles();
    for (File fileName : fileList) {
      System.out.println(fileName.getName());
    }
  }
  
  static void filterFiles (File fileDir) {
    Scanner inputFilterObj = new Scanner(System.in);
    System.out.println("Choose a file extension:");
    int extenCount = 1;
    for (FileTypes exten : FileTypes.values()) {
      System.out.println(extenCount + ". " + exten); 
      extenCount++;
    }
    System.out.println(extenCount + ". Back");
    String filterExten;
    filterLoop: while (true) {
      String extenSel = (inputFilterObj.next()).toLowerCase();
      for (FileTypes extenLook : FileTypes.values()){
        if (extenSel.equals(extenLook.name())) {
          filterExten = extenLook.name();
          break filterLoop;
        } else if (extenSel.equals("back") || extenSel.equals("6")) {
          return;
        }
      }
      System.out.println("Please select one of the available file extensions by text");
    }
    File[] fileList = fileDir.listFiles(new FilenameFilter() {
      public boolean accept (File dir, String name) {
        return name.endsWith(filterExten);
      }  
    });
    for (File fileName : fileList) {
      System.out.println(fileName.getName());
    }
  }
  
  public static void main (String[] args) {
    File fileDir = null;
    try {
      fileDir = new File(System.getProperty("user.dir")+"\\resources");
    }
    catch (Exception e) {
      System.out.println("Program issue.");
      System.exit(0);
    }
    
    Scanner inputObj = new Scanner(System.in);
    System.out.println("What are we doing today?:");
    programLoop: while (true) {
      System.out.println("Main Menu: \n 1. List all files \n 2. Filter by extension \n 3. Manipulate text file \n 4. Exit");
      String sel = (inputObj.next()).toLowerCase();
      System.out.println();
      switch (sel) {
        case "list" :
        case "1" :
          listFiles(fileDir);
          System.out.println();
          break;
        case "filter" :
        case "2" :
          filterFiles(fileDir);
          System.out.println();
          break;
        case "manipulate" :
        case "3" :
          //Manipulation.mainLoop(file)
          break;
        case "exit" :
        case "4" :
          break programLoop;
        default :
          System.out.println("Please select one of the options");
        /*case 7:
          logging secret?*/
      }
    }
  }
}