package manipulation;
import java.util.Scanner;
import java.io.*;
import java.util.regex.*;
public class Manipulation {
  
  //Display Name, Size, and line count for selected file. Then return these 3 in lastOutput to be logged in FSM.java
  public String fileInfo (File file) {
    String firstOutput = "Filename: " + file.getName();
    System.out.println(firstOutput);
    String secondOutput = "Size(KB): " + (file.length()/1024);
    System.out.println(secondOutput);
    int countLine = 0;
    String thirdOutput = "";
    try (Scanner fileScanObjInfo = new Scanner(file)){
      while (fileScanObjInfo.hasNextLine()) {
        countLine++;
        fileScanObjInfo.nextLine();
      }
      thirdOutput = "Lines of text: "+ countLine;
      System.out.println(thirdOutput + "\n");
    } catch (IOException ioe){
      thirdOutput = ioe.getMessage(); 
      System.out.println(thirdOutput);
    }
    String lastOutput = firstOutput + "; " + secondOutput + "; " + thirdOutput;
    return lastOutput;

  }
  
  
  //Scan selected txt for word, word occurences, and display and return result to be logged
  public String wordSearch (File file, String word) {
    String lastOutput = "";
    try (Scanner fileScanObjSearch = new Scanner(file)){
      int wordAmount = 0;
      fileScanObjSearch.useDelimiter(" ");
      String words;
      Pattern pattern = Pattern.compile("\\b"+word.toLowerCase()+"\\b", Pattern.CASE_INSENSITIVE);
      while (fileScanObjSearch.hasNext()) {
        words = (fileScanObjSearch.next()).toLowerCase();
        Matcher matcher = pattern.matcher(words);     
        if (matcher.find()) {
          wordAmount++;
        }
      }
      if (wordAmount > 0){
        lastOutput = word + " appears " + wordAmount + " time(s) in " + file.getName();
        System.out.println("\n"+lastOutput+"\n");
      } else {
        lastOutput = word + " does not appear in " + file.getName();
        System.out.println("\n"+lastOutput+"\n");
      }
    } catch (IOException ioe) {
      lastOutput = ioe.getMessage();
      System.out.println(lastOutput);
    }
    return lastOutput;
  }
}