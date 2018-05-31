package com.example.lap10715.threadexecutorreview;

import android.os.Environment;

import java.io.File;
import java.net.URL;

public class FileDownload {
    private String fileName;
    private String url;
    private File downloadedFile;

    public FileDownload(String fileName, String url) {
        this.fileName = fileName;
        this.url = url;
        downloadedFile = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS),
                fileName);
    }

    public String getFileName() {
        return fileName;
    }

    public String getUrl() {
        return url;
    }

    public File getDownloadedFile() {
        return downloadedFile;
    }
}