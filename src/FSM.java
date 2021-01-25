import java.util.Scanner;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import manipulation.*;
import logging.*;
import fileTypes.*;




public class FSM {
  //List all files in resources
  static void listFiles (File fileDir) {
    File[] fileList = fileDir.listFiles();
    for (File fileName : fileList) {
      System.out.println(fileName.getName());
    }
  }
  
  //Filter files
  static void filterFiles (File fileDir) {
    Logging logObj = new Logging();
    DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    LocalDateTime timeDateObj = LocalDateTime.now();
    String formattedTime = timeDateObj.format(timeFormat);
    //Log user location and ask for file extension
    String logOutput = ".....Filter Menu.....";
    logObj.actionLog(logOutput, formattedTime);
    Scanner inputFilterObj = new Scanner(System.in);
    System.out.println("Choose a file extension:");
    
    
    int extenCount = 1;
    for (FileTypes exten : FileTypes.values()) {
      System.out.println(extenCount + ". " + exten); 
      extenCount++;
    }
    System.out.println(extenCount + ". Back");
    String filterExten;
    //Continue to ask for extension until a valid extension is given or a request to go back
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
    //Start timer for operation, show results, and log time elapsed, result, and when the request was made 
    long startTime = logObj.timeStart();
    System.out.println("\nResult: ");
    File[] fileList = fileDir.listFiles(new FilenameFilter() {
      public boolean accept (File dir, String name) {
        return name.endsWith(filterExten);
      }  
    });
    for (File fileName : fileList) {
      System.out.println(fileName.getName());
    }
    logOutput = "Action: File Filter;    Output: \"Filter by " + filterExten + "\"";
    logObj.actionLog(logOutput, startTime, formattedTime);
  }
  
  //Choose how to manipulate the txt file
  static void manipulateChoice (File fileDir) {
    Logging logObj = new Logging();
    long startTime;
    DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    String formattedTime = (LocalDateTime.now()).format(timeFormat);
    
    //Log user location and return all txt files
    String logOutput = "..Manipulation Menu..";
    logObj.actionLog(logOutput, formattedTime);
    Scanner inputManipulateObj = new Scanner(System.in);
    File[] fileList = fileDir.listFiles(new FilenameFilter() {
      public boolean accept (File dir, String name) {
        return name.endsWith("txt");
      }  
    });
    //Continue to ask for a valid file to manipulate or an exit back to main manu
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
        } else if (textFileSel.equalsIgnoreCase("Back") || textFileSel.equals(String.valueOf(maniCount))) {
          return;
        }
      }
      System.out.println("Please be explicit in filename and extension\n");
    }
    System.out.println();
    
    //Ask for how to manipulate txt file, and log user interaction
    Manipulation maniObj = new Manipulation();
    String wordFind;
    manipulationLoop: while (true) {  
      System.out.println("What do you want to do with: " + filePath.getName() + "\n 1. File Info \n 2. Word Search \n 3. Back");
      String maniSel = inputManipulateObj.next();
      System.out.println();
      switch (maniSel) {
        case "1" :
          startTime = logObj.timeStart();
          logOutput = "Action: File Info;      Output: " + "\"" + maniObj.fileInfo(filePath) + "\"";
          logObj.actionLog(logOutput, startTime, formattedTime);
          break;
        case "2" :
          System.out.println("What word are you looking for?");
          wordFind = inputManipulateObj.next();
          startTime = logObj.timeStart();
          logOutput = "Action: Word Search;    Output: " + "\"" + maniObj.wordSearch(filePath,wordFind) + "\"";
          logObj.actionLog(logOutput, startTime, formattedTime);
          break;
        case "3" :
          break manipulationLoop;
        default :
          System.out.println("Please select one of the available selections\n");
      }
    }
  }
  
  public static void main (String[] args) {
    
    Logging logObj = new Logging();
    long startTime = logObj.timeStart();
    String logOutput = "";
    DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    String formattedTime = (LocalDateTime.now()).format(timeFormat);
    
    //Try to resources directory else terminate program
    File fileDir = null;
    try {
      fileDir = new File(System.getProperty("user.dir")+"\\resources");
    }
    catch (Exception e) {
      System.out.println("Program issue.");
      logOutput = "Program issue.";
      logObj.actionLog(logOutput, startTime, formattedTime);
      System.exit(0);
    }
    
    //Ask user for what to do, select which methods to use, and log all user interaction
    Scanner inputObj = new Scanner(System.in);
    logOutput = "....Program start....";
    logObj.actionLog(logOutput, startTime, formattedTime);
    System.out.println("What are we doing today?:");
    programLoop: while (true) {
      System.out.println("Main Menu: \n 1. List all files \n 2. Filter by extension \n 3. Manipulate text file \n 4. Exit");
      String sel = (inputObj.next()).toLowerCase();
      System.out.println();
      switch (sel) {
        case "list" :
        case "1" :
          formattedTime = (LocalDateTime.now()).format(timeFormat);
          startTime = logObj.timeStart();
          listFiles(fileDir);
          logOutput = "Action: File List;";
          logObj.actionLog(logOutput, startTime, formattedTime);
          System.out.println();
          break;
        case "filter" :
        case "2" :
          filterFiles(fileDir);
          System.out.println();
          formattedTime = (LocalDateTime.now()).format(timeFormat);
          logOutput = "......Main Menu......";
          logObj.actionLog(logOutput, formattedTime);
          break;
        case "manipulate" :
        case "3" :
          manipulateChoice(fileDir);
          formattedTime = (LocalDateTime.now()).format(timeFormat);
          logOutput = "......Main Menu......";
          logObj.actionLog(logOutput, formattedTime);
          break;
        case "exit" :
        case "4" :
          break programLoop;
        default :
          System.out.println("Please select one of the options");
      }
    }
    formattedTime = (LocalDateTime.now()).format(timeFormat);
    logOutput = ".....Program End.....\n";
    logObj.actionLog(logOutput, formattedTime); 
  }
}