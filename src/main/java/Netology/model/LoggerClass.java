package Netology.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;


public class LoggerClass {

    private static String loggerPath = "src/main/resources/log_server.log";
    private static int stringCount = 0;

    public LoggerClass() {

    }
    public synchronized static void WriteLog(String logMsg){
        try {
            FileWriter fileWriter = new FileWriter(loggerPath, true);
            Date currDate = new Date();
            stringCount++;
            String index = String.valueOf(stringCount);
            fileWriter.write(   index+ ". " + currDate + ":  " + logMsg + "\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

