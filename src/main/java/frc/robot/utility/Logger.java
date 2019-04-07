package frc.robot.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;

/**
 * Add your docs here.
 */
public class Logger {

    private File f;
    private FileOutputStream fos;
    private BufferedWriter bw;

    // If false, calls to Logger.write will do nothing. This should be the case in competition
    private boolean debugEnabled = true;

    // How many messages to keep track of before overwriting with a new one
    // This is used to avoid spaming the same message. If it is {x}, the last {x} messages will be compared to the new one
    private int bufferLength = 3;
    // Keeps track of the past ${bufferLength} messages (avoids printing repeats)
    private String[] previousBuffer = new String[bufferLength];
    // Used to keep track of the index for previousBuffer
    private int i = 0;

    public enum LogEvent {
        INFO, WARNING, ERROR, EVENT;
    }

    /**
     * Initializes the Logger class with the default path of /home/lvuser/log.txt
     */
    public Logger() {
        f = new File("/home/lvuser/log.txt");
        initializeFile();
    }

    /**
     * Initializes the Logger class and saves the logs to the path of ${path}
     * @param path The location (on the RIO) to store the log. This uses Linux's file system.
     */
    public Logger(String path) {
        f = new File(path);
        initializeFile();
    }

    private void initializeFile() {
        f.delete();
        try {
            f.createNewFile();
            fos = new FileOutputStream(f);
            bw = new BufferedWriter(new OutputStreamWriter(fos));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * <p>Writes a message to the console and log file. Messages are in the form of</p>
     * <p>[event - className] message </p>
     * If INFO, "Hello World", and this are passed, the following will print
     * <p>[INFO - frc.robot.utility.Logger] Hello world</p>
     * @param event The context of the message. This is up to the users preference
     * @param message The body of the message to be displayed
     * @param source The class that is calling the function. Generally, it will be "this"
     */
    public void write(LogEvent event, String message, Object source) {
        // if (debugEnabled) {
            String formated = String.format("[%s - %s] %s\n", event.toString(), source.getClass().getName(), message);
    
            if (!Arrays.asList(previousBuffer).contains(formated)) { // Makes sure the same message wasn't recently printed
                System.out.print(formated);
                try {
                    bw.write(formated);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            previousBuffer[i] = formated;
            // Increments the buffer index, unless it is greater then the length, then reset to 0
            i = i < bufferLength-1 ? i+1 : 0;
        // }
    }

    public void writeHeader(String header) {
        String sectionHeader = "========================================";
        int n = (sectionHeader.length() / 2) - (header.length() / 2);
        String message = String.format("%" + n + "s\n", header);
        System.out.println(sectionHeader);
        System.out.println(message);
        System.out.println(sectionHeader);
        try {
            bw.write("\n");
            bw.write(sectionHeader + "\n");
            bw.write(message);
            bw.write(sectionHeader + "\n");
            bw.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDebugMode(boolean debugEnabled) {
        this.debugEnabled = debugEnabled;
    }

    public void shutdown() {
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
