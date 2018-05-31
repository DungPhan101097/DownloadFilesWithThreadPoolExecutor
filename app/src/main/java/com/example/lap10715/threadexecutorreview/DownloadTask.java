package com.example.lap10715.threadexecutorreview;

import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownloadTask implements Runnable {
    public static final int DOING = 1;
    public static final int START = 2;
    public static final int FINISH = 3;
    public static final int DOING_UPDATE_FILE_LEN = 4;
    private FileDownload fileDownload;
    private DownloadManager mDownloadManager;


    private Button mBtnDownloadFile;
    private TextView mTvDisplayUrl;
    private ProgressBar mPbProgress;

    public DownloadTask(FileDownload fileDownload) {
        this.fileDownload = fileDownload;
        mDownloadManager = DownloadManager.getmDownloadManager();
    }

    public void setUIItems(Button btnDowloadFile, TextView tvDisplayUrl,
                           ProgressBar pbProgress){
        this.mBtnDownloadFile = btnDowloadFile;
        this.mTvDisplayUrl = tvDisplayUrl;
        this.mPbProgress = pbProgress;
    }

    @Override
    public void run() {
        //doing
        mDownloadManager.handleState(this, START, 0);

        String msg;
        if (downloadFile()) {
            msg = "Your file in:  " + fileDownload.getDownloadedFile().getAbsolutePath();
        } else {
            msg = "Failed to download the file ";
        }

        //finish
        mDownloadManager.handleState(this,FINISH, 0);
    }

    public Button getmBtnDownloadFile() {
        return mBtnDownloadFile;
    }

    public TextView getmTvDisplayUrl() {
        return mTvDisplayUrl;
    }

    public ProgressBar getmPbProgress() {
        return mPbProgress;
    }

    public boolean downloadFile() {
        URLConnection connection = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;

        byte[] buffer = new byte[1024];

        try {
            connection = new URL(fileDownload.getUrl()).openConnection();
            int fileLen = connection.getContentLength();
            mDownloadManager.handleState(this, DOING_UPDATE_FILE_LEN, fileLen);

            outputStream = new BufferedOutputStream(new FileOutputStream(fileDownload.getDownloadedFile()));
            inputStream = connection.getInputStream();

            int numRead = -1;
            while ((numRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, numRead);

                mDownloadManager.handleState(this, DOING, numRead);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
