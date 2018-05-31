package com.example.lap10715.threadexecutorreview;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.example.lap10715.threadexecutorreview.DownloadTask.DOING;
import static com.example.lap10715.threadexecutorreview.DownloadTask.DOING_UPDATE_FILE_LEN;
import static com.example.lap10715.threadexecutorreview.DownloadTask.FINISH;
import static com.example.lap10715.threadexecutorreview.DownloadTask.START;


public class DownloadManager {

    private static DownloadManager mDownloadManager = null;
    private final BlockingQueue<Runnable> mDownloadWorkQueue;
    private final ThreadPoolExecutor mDownloadThreadPool;
    private Handler mMainHandler;

    private static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private static final int KEEP_ALIVE_TIME = 50;


    static {
        mDownloadManager = new DownloadManager();
    }

    private DownloadManager() {
        mDownloadWorkQueue = new LinkedBlockingDeque<>();
        mDownloadThreadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, CORE_POOL_SIZE,
                KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS, mDownloadWorkQueue);
        mMainHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                DownloadTask downloadTask = (DownloadTask)msg.obj;
                switch (msg.what) {
                    case START:
                         downloadTask.getmBtnDownloadFile().setBackgroundResource(R.drawable.download_btn_doing);
                        break;
                    case DOING:
                        downloadTask.getmBtnDownloadFile().setBackgroundResource(R.drawable.download_btn_doing);
                        downloadTask.getmPbProgress().setProgress(downloadTask.getmPbProgress().getProgress() + msg.arg1);
                        break;
                    case DOING_UPDATE_FILE_LEN:
                        downloadTask.getmBtnDownloadFile().setBackgroundResource(R.drawable.download_btn_doing);
                        downloadTask.getmPbProgress().setMax(msg.arg1);
                        break;
                    case FINISH:
                        downloadTask.getmBtnDownloadFile().setBackgroundResource(R.drawable.download_btn_finish);
                        //mTvDisplayUrl.setText();
                        break;
                    default:
                        break;
                }

            }
        };
    }

    public void runDownloadFile(Runnable task) {
        mDownloadThreadPool.execute(task);
    }

    public static DownloadManager getmDownloadManager() {
        return mDownloadManager;
    }

    public void handleState(DownloadTask downloadTask, int state, int update) {
        Message msg = mMainHandler.obtainMessage();
        msg.what = state;
        msg.arg1 = update;
        msg.obj = downloadTask;
        msg.sendToTarget();

    }


}
