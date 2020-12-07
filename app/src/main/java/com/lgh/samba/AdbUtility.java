package com.lgh.samba;

import java.io.PrintWriter;

public class AdbUtility {

    private static Process process;
    private static PrintWriter printWriter;

    public static void exec(String command) {
        try {
            if (printWriter == null || process == null) {
                process = Runtime.getRuntime().exec("su");
                printWriter = new PrintWriter(process.getOutputStream());
            }
            printWriter.println(command);
            printWriter.flush();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void release() {
        if (printWriter != null) {
            process.destroy();
            printWriter.close();
            process = null;
            printWriter = null;
        }
    }
}
