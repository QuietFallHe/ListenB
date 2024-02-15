package com.quietfall.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.quietfall.MusicPlayActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MusicService extends Service {
    private MediaPlayer player;
    private Timer timer;
    private static final String TAG = "qf";
    public static final int HANDLER_WHAT = 0;
    public static final int HANDLER_WHAT_END = 1;
    private final Object mediaPlayerLock = new Object(); // 创建一个锁对象
    public static final String ACTION_PLAYBACK_COMPLETED = "com.example.MusicService.ACTION_PLAYBACK_COMPLETED";


    public MusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player = new MediaPlayer();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MusicController();
    }


    public class MusicController extends Binder implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {
        /**
         * 开始播放url
         *
         * @param url
         */
        public void play(String url) {
            synchronized (mediaPlayerLock) { // 使用synchronized关键字同步代码块
                try {
                    if (player != null) {
                        player.reset();
                    }
                    Log.d(TAG, "play: "+url);

                    Uri uri = Uri.parse(url);
                    Map<String, String> headers = new HashMap<>();
                    // 设置headers...
                    headers.put("Referer", "https://www.bilibili.com");
                    headers.put("Sec-Fetch-Mode", "no-cors");
                    headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36");

                    player.setDataSource(getApplicationContext(), uri, headers);

                    player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.start();
                            addTimer();
                        }
                    });

                    player.prepareAsync(); // 异步准备播放，避免阻塞主线程
                    player.setOnCompletionListener(this);
                    player.setOnErrorListener(this);
                } catch (IOException e) {
                    Log.e(TAG, "MusicService的play出错");
                    e.printStackTrace();
                }
            }


        }

        /**
         * 定时返回数据
         */
        private void addTimer() {
            if (timer == null) {
                timer = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        if (player == null) {
                            return;
                        }
                        //单位：ms
                        int duration = player.getDuration();
                        int currentPosition = player.getCurrentPosition();
                        Bundle bundle = new Bundle();
                        bundle.putInt("duration", duration);
                        bundle.putInt("currentPosition", currentPosition);
                        MusicPlayActivity.handler.obtainMessage(HANDLER_WHAT, bundle).sendToTarget();
                        //   Log.d(TAG, "MusicService的AddTimer的run: " + currentPosition/1000);
                    }
                };
                timer.schedule(task, 5, 500);
            }

        }

        public void pause() {
            synchronized (mediaPlayerLock) {
                if (player.isPlaying()) {
                    player.pause();
                }
            }
        }

        public void continuePlay() {
            synchronized (mediaPlayerLock) {
                if (!player.isPlaying()) {
                    player.start();
                }
            }
        }

        public void seekTo(int progress) {
            synchronized (mediaPlayerLock) {
                if (player != null) {
                    player.seekTo(progress);
                }
            }
        }

        /**
         * 暂停
         */
        /*public void pause() {
            player.pause();
        }
*/
        /**
         * 继续播放
         */
       /* public void continuePlay() {
            player.start();
        }*/

        /**
         * 进度条定位
         *
         * @param
         */        /*public void seekTo(int progress) {
            player.seekTo(progress);
        }*/
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            Intent intent = new Intent(ACTION_PLAYBACK_COMPLETED);
            sendBroadcast(intent);
            MusicPlayActivity.handler.obtainMessage(HANDLER_WHAT_END,null).sendToTarget();
            String url = MusicPlayActivity.nextUrl;
            Log.d(TAG, "onCompletion: " + url);

            play(url);


        }

        @Override
        public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
            return true;
        }
       /* public void reset()
        {
            player.stop();
            player = new MediaPlayer();
        }*/


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player == null) {
            return;
        }
        if (player.isPlaying()) {
            player.stop();
        }
        player.release();
        player = null;
    }
}