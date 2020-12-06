package com.lgh.samba;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    String MAIN_DIR = getFilesDir().getAbsolutePath();

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
                        FileUtils.copyInputStreamToFile(getResources().openRawResource(R.raw.nmbd), nmbdFile);
                    }

                    File smbdFile = new File(binFile, "smbd");
                    if (!smbdFile.exists()) {
                        FileUtils.copyInputStreamToFile(getResources().openRawResource(R.raw.smbd), smbdFile);
                    }

                    File smbPasswdFile = new File(binFile, "smbpasswd");
                    if (!smbPasswdFile.exists()) {
                        FileUtils.copyInputStreamToFile(getResources().openRawResource(R.raw.smbpasswd), smbPasswdFile);
                    }


                    File testParmFile = new File(binFile, "testparm");
                    if (!testParmFile.exists()) {
                        FileUtils.copyInputStreamToFile(getResources().openRawResource(R.raw.testparm), testParmFile);
                    }

                    File smbConfFile = new File(etcSamba, "smb.conf");
                    if (!smbConfFile.exists()) {
                        FileUtils.copyInputStreamToFile(getResources().openRawResource(R.raw.smb), smbConfFile);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        TextView button = findViewById(R.id.but);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i("LGH_LOG", AdbUtility.exec("su"));
                AdbUtility.exec("chmod 777 /data/data/com.lgh.samba/files/bin/nmbd");
                AdbUtility.exec("chmod 777 /data/data/com.lgh.samba/files/bin/smbd");
                AdbUtility.exec("chmod 777 /data/data/com.lgh.samba/files/bin/smbpasswd");
                AdbUtility.exec("chmod 777 /data/data/com.lgh.samba/files/bin/testparm");
                AdbUtility.exec("chmod 777 /data/data/com.lgh.samba/files/bin/up.sh");
                AdbUtility.exec("sh /data/data/com.lgh.samba/files/bin/up.sh");
//                AdbUtility.exec("./data/data/com.lgh.samba/files/bin/smbd");
                Toast.makeText(MainActivity.this, "ok", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
