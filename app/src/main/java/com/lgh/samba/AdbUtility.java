package com.lgh.samba;

import java.io.IOException;
import java.io.PrintWriter;

public class AdbUtility {
    public static void exec(String command) {
        try {
            Process process = Runtime.getRuntime().exec("su");
            PrintWriter printWriter = new PrintWriter(process.getOutputStream());
            printWriter.println(command);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
