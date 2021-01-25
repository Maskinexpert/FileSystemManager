package logging;

import java.io.FileWriter;
import java.io.IOException;

public class Logging {
  int timePad = 7;
  
  //Logger for actions that processes resource files
  public void actionLog(String consoleOutput, long startTime, String timeDate){
    try {
      int timeSpend = (int) (System.currentTimeMillis() - startTime);
      FileWriter fileWriteObj = new FileWriter("LoggingService.txt",true);
      String timeSpendPad = String.format("%1$-" + timePad + "s", timeSpend + "ms");
      fileWriteObj.write(timeDate + "; " + "Time elapsed: " + timeSpendPad + "   " + consoleOutput + "\n");
      fileWriteObj.close();
    } catch (IOException ioe){
    System.out.println(ioe);
    }
  }
  
  //Logger for user movements
  public void actionLog(String consoleOutput, String timeDate){
    try {
      FileWriter fileWriteObj = new FileWriter("LoggingService.txt",true);
      String timeSpendPad = String.format("%1$-" + timePad + "s", ".INFO.");
      fileWriteObj.write(timeDate + "; " + "Time elapsed: " + timeSpendPad + "   " + consoleOutput + "\n");
      fileWriteObj.close();
    } catch (IOException ioe){
    System.out.println(ioe);
    }
  }
  
  //Method for easier to read code and incase time scale has to be changed
  public long timeStart() {
    return System.currentTimeMillis();
  }
}