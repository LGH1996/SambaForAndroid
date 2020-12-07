package com.lgh.samba;

import android.content.Context;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class SambaService {
    private static SambaService sambaService;
    private final Context mContext;

    private SambaService(Context context) {
        this.mContext = context;
    }

    public static SambaService newInstance(Context context) {
        if (sambaService == null) {
            sambaService = new SambaService(context);
        }
        return sambaService;
    }

    public void initData(final boolean clearDir) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    String MAIN_DIR = mContext.getFilesDir().getAbsolutePath();

                    if (clearDir) {
                        File filesDir = new File(MAIN_DIR);
                        FileUtils.deleteQuietly(filesDir);
                    }

                    File binFile = new File(MAIN_DIR + File.separator + "bin");
                    if (!binFile.exists()) {
                        binFile.mkdirs();
                    }

                    File etcFile = new File(MAIN_DIR + File.separator + "etc");
                    if (!etcFile.exists()) {
                        etcFile.mkdirs();
                    }

                    File varFile = new File(MAIN_DIR + File.separator + "var");
                    if (!varFile.exists()) {
                        varFile.mkdirs();
                    }

                    File libFile = new File(MAIN_DIR + File.separator + "lib");
                    if (!libFile.exists()) {
                        libFile.mkdirs();
                    }

                    File etcSamba = new File(etcFile, "samba");
                    if (!etcSamba.exists()) {
                        etcSamba.mkdirs();
                    }

                    File varLock = new File(varFile, "lock");
                    if (!varLock.exists()) {
                        varLock.mkdirs();
                    }

                    File varLog = new File(varFile, "log");
                    if (!varLog.exists()) {
                        varLog.mkdirs();
                    }

                    File varTmp = new File(varFile, "tmp");
                    if (!varTmp.exists()) {
                        varTmp.mkdirs();
                    }


                    File nmbdFile = new File(binFile, "nmbd");
                    if (!nmbdFile.exists()) {
                        FileUtils.copyInputStreamToFile(mContext.getResources().openRawResource(R.raw.nmbd), nmbdFile);
                    }

                    File smbdFile = new File(binFile, "smbd");
                    if (!smbdFile.exists()) {
                        FileUtils.copyInputStreamToFile(mContext.getResources().openRawResource(R.raw.smbd), smbdFile);
                    }

                    File smbPasswdFile = new File(binFile, "smbpasswd");
                    if (!smbPasswdFile.exists()) {
                        FileUtils.copyInputStreamToFile(mContext.getResources().openRawResource(R.raw.smbpasswd), smbPasswdFile);
                    }


                    File testParmFile = new File(binFile, "testparm");
                    if (!testParmFile.exists()) {
                        FileUtils.copyInputStreamToFile(mContext.getResources().openRawResource(R.raw.testparm), testParmFile);
                    }

                    File smbConfFile = new File(etcSamba, "smb.conf");
                    if (!smbConfFile.exists()) {
                        FileUtils.copyInputStreamToFile(mContext.getResources().openRawResource(R.raw.smb), smbConfFile);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void start() {
        AdbUtility.exec("chmod 777 /data/data/com.lgh.samba/files/bin/nmbd");
        AdbUtility.exec("chmod 777 /data/data/com.lgh.samba/files/bin/smbd");
        AdbUtility.exec("chmod 777 /data/data/com.lgh.samba/files/bin/smbpasswd");
        AdbUtility.exec("chmod 777 /data/data/com.lgh.samba/files/bin/testparm");
        AdbUtility.exec("./data/data/com.lgh.samba/files/bin/nmbd");
        AdbUtility.exec("./data/data/com.lgh.samba/files/bin/smbd");
    }

    public void stop() {
        AdbUtility.exec("killall nmbd");
        AdbUtility.exec("killall smbd");
    }

    public void setUser(String user, final String password) {
        AdbUtility.exec("./data/data/com.lgh.samba/files/bin/smbpasswd -a " + user);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                AdbUtility.exec(password);
            }
        }, 1000);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                AdbUtility.exec(password);
            }
        }, 2000);
    }
}
