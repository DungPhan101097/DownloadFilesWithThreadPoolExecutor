package com.example.lap10715.threadexecutorreview;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 1;

    private Button btnDownFile1;
    private Button btnDownFile2;
    private Button btnDownFile3;
    private TextView url1;
    private TextView url2;
    private TextView url3;
    private ProgressBar pbFile1;
    private ProgressBar pbFile2;
    private ProgressBar pbFile3;
    private TextView tvDisplayUrl1;
    private TextView tvDisplayUrl2;
    private TextView tvDisplayUrl3;

    private FileDownload[] fileDownloads = {
            new FileDownload("icon_android_java.jpg","https://drive.google.com/uc?export=download&id=0B1rVEnAlVmVvWGxQNmxGRFBXSEU"),
            new FileDownload("application_life_cycle.pdf", "https://drive.google.com/uc?export=download&id=0B1rVEnAlVmVvRndfV3RNN1pvOVU"),
            new FileDownload("database_android.pdf","https://drive.google.com/uc?export=download&id=0B1rVEnAlVmVvRndfV3RNN1pvOVU")};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "Please provide permission!", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
            }
        } else {
            initViews();
            initData();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initViews();
                    initData();
                } else {
                    Toast.makeText(this, "Application can't access any data!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }

        }
    }

        private void initViews() {
            btnDownFile1 = findViewById(R.id.btn_downfile_1);
            btnDownFile2 = findViewById(R.id.btn_downfile_2);
            btnDownFile3 = findViewById(R.id.btn_downfile_3);

            url1 = findViewById(R.id.tv_file_name_1);
            url2 = findViewById(R.id.tv_file_name_2);
            url3 = findViewById(R.id.tv_file_name_3);

            pbFile1 = findViewById(R.id.pb_progress_file_1);
            pbFile2 = findViewById(R.id.pb_progress_file_2);
            pbFile3 = findViewById(R.id.pb_progress_file_3);

            tvDisplayUrl1 = findViewById(R.id.tv_display_url_1);
            tvDisplayUrl2 = findViewById(R.id.tv_display_url_2);
            tvDisplayUrl3 = findViewById(R.id.tv_display_url_3);
        }

        private void initData() {
            url1.setText(fileDownloads[0].getFileName());
            url2.setText(fileDownloads[1].getFileName());
            url3.setText(fileDownloads[2].getFileName());


            btnDownFile1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnDownFile1.setBackgroundResource(R.drawable.download_btn_idle);

                    DownloadTask downloadTask = new DownloadTask(fileDownloads[0]);
                    downloadTask.setUIItems(btnDownFile1, tvDisplayUrl1,
                            pbFile1);
                    DownloadManager.getmDownloadManager().runDownloadFile(downloadTask);

                }
            });
            btnDownFile2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnDownFile2.setBackgroundResource(R.drawable.download_btn_idle);

                    DownloadTask downloadTask = new DownloadTask(fileDownloads[1]);
                    downloadTask.setUIItems(btnDownFile2, tvDisplayUrl2,
                            pbFile2);
                    DownloadManager.getmDownloadManager().runDownloadFile(downloadTask);

                }
            });
            btnDownFile3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnDownFile3.setBackgroundResource(R.drawable.download_btn_idle);

                    DownloadTask downloadTask = new DownloadTask(fileDownloads[2]);
                    downloadTask.setUIItems(btnDownFile3, tvDisplayUrl3,
                            pbFile3);
                    DownloadManager.getmDownloadManager().runDownloadFile(downloadTask);
                }
            });

        }

    }
