package com.example.lap10715.threadexecutorreview;

public class DownloadDecode implements Runnable{

    private DownloadTask mDownloadTask;

    public DownloadDecode(DownloadTask mDownloadTask) {
        this.mDownloadTask = mDownloadTask;
    }

    @Override
    public void run() {

    }
}
