import manipulation.*;
//import logging.*;
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
      String extenSel = inputFilterObj.next();
      for (FileTypes extenLook : FileTypes.values()){
        if (extenSel.equalsIgnoreCase(extenLook.name())) {
          filterExten = extenLook.name();
          break filterLoop;
        } else if (extenSel.equals("back") || extenSel.equals("6")) {
          return;
        }
      }
      System.out.println("Please select one of the available file extensions by text\n");
    }
    System.out.println("\nResult: ");
    File[] fileList = fileDir.listFiles(new FilenameFilter() {
      public boolean accept (File dir, String name) {
        return name.endsWith(filterExten);
      }  
    });
    for (File fileName : fileList) {
      System.out.println(fileName.getName());
    }
  }
  
  static void manipulateChoice (File fileDir) {
    Scanner inputManipulateObj = new Scanner(System.in);
    File[] fileList = fileDir.listFiles(new FilenameFilter() {
      public boolean accept (File dir, String name) {
        return name.endsWith("txt");
      }  
    });
    System.out.println("Choose a file to manipulate");
    File filePath;
    chooseFileLoop: while (true) {
      int maniCount = 1;
      for (File fileName : fileList) {
        System.out.println(maniCount + ". " + fileName.getName());
        maniCount++;
      }
      System.out.println(maniCount + ". Back");
      String textFileSel = inputManipulateObj.next();
      for (File fileName : fileList){
        if (textFileSel.equalsIgnoreCase(fileName.getName())){
          filePath = fileName;
          break chooseFileLoop;
        } else if (textFileSel.equalsIgnoreCase("Back")) {
          return;
        }
      }
      System.out.println("Please be explicit in filename and extension\n");
    }
    System.out.println();
    Manipulation maniObj = new Manipulation();
    String wordFind;
    manipulationLoop: while (true) {  
      System.out.println("What do you want to do with: " + filePath.getName() + "\n 1. File Info \n 2. Word Search \n 3. Back");
      String maniSel = inputManipulateObj.next();
      System.out.println();
      switch (maniSel) {
        case "1" :
          maniObj.fileInfo(filePath);
          break;
        case "2" :
          System.out.println("What word are you looking for?");
          wordFind = inputManipulateObj.next();
          maniObj.wordSearch(filePath,wordFind);
          break;
        case "3" :
          break manipulationLoop;
        default :
          System.out.println("Please select one of the available selections\n");
      }
    }
  }
  
  public static void main (String[] args) {
    File fileDir = null;
    try {
      fileDir = new File(System.getProperty("user.dir")+"\\resources");
    }
    catch (Exception e) {
      //Logging.crash(e);
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
          manipulateChoice(fileDir);
          break;
        case "exit" :
        case "4" :
          break programLoop;
        default :
          System.out.println("Please select one of the options");
      }
    }
  }
}