package manipulation;
import java.util.Scanner;
import java.io.*;
import java.util.regex.*;
public class Manipulation {
  
  public void fileInfo (File file) {
    System.out.println("Filename: " + file.getName() + "\nSize(KB): " + (file.length()/1024));//getFileSizeKiloBytes(file));
    int countLine = 0;
    try (Scanner fileScanObjInfo = new Scanner(file)){
      while (fileScanObjInfo.hasNextLine()) {
        countLine++;
        fileScanObjInfo.nextLine();
      }
    } catch (IOException ioe){
      System.out.println(ioe.getMessage());
    }
    System.out.println("Lines of text: "+ countLine + "\n");

  }
  public void wordSearch (File file, String word) {
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
        System.out.println("\n"+word + " appears " + wordAmount + " time(s) in " + file.getName()+"\n");
      } else {
        System.out.println("\n"+word + " does not appear in " + file.getName()+"\n");
      }
    } catch (IOException ioe) {
      System.out.println(ioe.getMessage());
    }
  }
}