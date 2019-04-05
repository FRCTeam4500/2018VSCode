/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

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
    private int bufferLength = 3;
    private String[] previousBuffer = new String[bufferLength];
    private int i = 0;
    private FileOutputStream fos;
    private BufferedWriter bw;

    public enum LogEvent {
        INFO, WARNING, ERROR, EVENT;
    }

    public Logger() {
        f = new File("/home/lvuser/log.txt");
        initializeFile();
    }

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
            System.err.print("hi");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(LogEvent event, String message, Object source) {
        String formated = String.format("[%s - %s] %s\n", event.toString(), source.getClass().getName(), message);
        if (!Arrays.asList(previousBuffer).contains(formated)) {
            System.out.print(formated);
            try {
                bw.write(formated);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        previousBuffer[i] = formated;
        i = i < bufferLength-1 ? i+1 : 0;
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

    public void shutdown() {
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
