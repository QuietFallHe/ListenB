package com.quietfall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.quietfall.database.ListSqliteHelper;
import com.quietfall.entity.Music;
import com.quietfall.service.MusicService;
import com.quietfall.utils.HttpUtils;

import java.util.Random;

public class MusicPlayActivity extends AppCompatActivity implements View.OnClickListener {
    private MusicService.MusicController mMusicController;
    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            mMusicController = (MusicService.MusicController) iBinder;

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    private Intent intent;

    private static final String TAG = "qf";
    private static String mTitle;
    private static String mUpName;
    private static String mCover;
    private static Long mDuration;
    private static String mBv;
    private static Long mCid;
    private static String mFid;
    private static String mUrl;

    private TextView tv_title;
    private TextView tv_up_name;
    private static ImageView iv_cover;
    private TextView title_title;
    private TextView tv_duration;
    private static TextView tv_time;
    private ImageView iv_play;
    private static ListSqliteHelper mHelper;
    public static SeekBar sb_progress;
    private static int randomId;
    //是否绑定服务
    private boolean isBind = false;
    //是否正在播放
    private boolean isPlay = false;
    //是否第一次播放
    private boolean first = true;
    public static String nextUrl = null;
    public static final String ACTION_NEXT = "com.quietfall.MusicPlayActivity.ACTION_NEXT";
    public static final String ACTION_LAST = "com.quietfall.MusicPlayActivity.ACTION_LAST";
    // 定位下一首
    private static final int LOCATE_NEXT = 0;
    //随机定位
    private static final int LOCATE_RANDOM = 1;
    //单曲循环
    private static final int LOCATE_REPEAT = 2;
    public static int LOCATE_MODE = LOCATE_NEXT;
    @SuppressLint("HandlerLeak")
    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == MusicService.HANDLER_WHAT) {
                Bundle bundle = (Bundle) msg.obj;
                int duration = bundle.getInt("duration");
                int progress = bundle.getInt("currentPosition");
                sb_progress.setMax(duration);
                sb_progress.setProgress(progress);
                int min = progress / 1000 / 60;
                int sec = progress / 1000 % 60;

                String sSec = sec < 10 ? "0" + sec : String.valueOf(sec);
                tv_time.setText(min + ":" + sSec);

            }
            if (msg.what == MusicService.HANDLER_WHAT_END) {
                sb_progress.setProgress(0);

            }


        }
    };
    //广播接收器
    private BroadcastReceiver completionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(MusicService.ACTION_PLAYBACK_COMPLETED)) {
                setNext(LOCATE_MODE);
                init();
                setNextUrl(MusicPlayActivity.LOCATE_MODE);
            }
            if (intent.getAction().equals(ACTION_NEXT)) {
                Log.d(TAG, "onReceive: ");
                mMusicController.play(nextUrl);
                setNext(LOCATE_NEXT);
                init();
                if (!isPlay) {
                    isPlay = true;
                    first =false;
                    iv_play.setImageResource(R.drawable.pause);
                }
                setNextUrl(LOCATE_MODE);
            }
            if (intent.getAction().equals(ACTION_LAST))
            {
                mUrl = lastUrl;
                mMusicController.play(lastUrl);
                init();
                if (!isPlay) {
                    isPlay = true;
                    first =false;
                    iv_play.setImageResource(R.drawable.pause);
                }
                setNextUrl(LOCATE_MODE);
            }
        }
    };


// 在需要加载图片的地方调用


    private static int mId;
    private ImageView iv_mode;
    private ImageView iv_next;
    private ImageView iv_last;
    private String lastUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_play);
        /*=============
         * 获取控件
         */
        sb_progress = findViewById(R.id.sb_progress);
        iv_play = findViewById(R.id.iv_play);
        title_title = findViewById(R.id.title_title);
        tv_title = findViewById(R.id.tv_title);
        tv_up_name = findViewById(R.id.tv_up_name);
        iv_cover = findViewById(R.id.iv_cover);
        tv_duration = findViewById(R.id.tv_duration);
        tv_time = findViewById(R.id.tv_time);
        iv_mode = findViewById(R.id.iv_mode);
        iv_next = findViewById(R.id.iv_next);
        iv_last = findViewById(R.id.iv_last);

        /*=============
         * 设置控件
         */
        iv_play.setOnClickListener(this);
        iv_mode.setOnClickListener(this);
        iv_next.setOnClickListener(this);
        iv_last.setOnClickListener(this);
        sb_progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                Log.d(TAG, "onStopTrackingTouch: " + progress / 1000);
                mMusicController.seekTo(progress);

            }
        });


        /*=============
         * 获取数据
         */
        intent = getIntent();
        mFid = intent.getStringExtra("fid");
        mId = intent.getIntExtra("id", 1);
        mTitle = intent.getStringExtra("title");
        mUpName = intent.getStringExtra("up_name");
        mCover = intent.getStringExtra("cover");
        Log.d(TAG, "MusicPlayActivity接受到cover数据: " + mCover);
        mDuration = intent.getLongExtra("duration", 0L);
        mBv = intent.getStringExtra("bv");
        mCid = intent.getLongExtra("cid", 0L);
        Log.d(TAG, "MusicPlayActivity接受到bv数据: " + mBv);
        Log.d(TAG, "MusicPlayActivity接受到cid数据: " + mCid);
        /*=============
         * 初始化
         */

        init();
        //绑定服务
        bind();
        //注册广播接收器
        IntentFilter filter = new IntentFilter();
        filter.addAction(MusicService.ACTION_PLAYBACK_COMPLETED);
        filter.addAction(ACTION_NEXT);
        filter.addAction(ACTION_LAST);

        registerReceiver(completionReceiver, filter);

        // setNextUrl(LOCATE_NEXT);
    }

    /**
     * 初始化
     */

    private void init() {
        Long min = mDuration / 60;
        Long sec = mDuration % 60;
        String sSec = sec < 10 ? "0" + sec : String.valueOf(sec);
        tv_duration.setText(min + ":" + sSec);

        title_title.setText(mTitle);
        tv_title.setText(mTitle);
        tv_up_name.setText(mUpName);

        Glide.with(this).load(mCover).listener(new RequestListener<String, GlideDrawable>() {

            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                Log.d(TAG, "Glide监听onException: ");
                e.printStackTrace();
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                Log.d(TAG, "Glide监听onResourceReady:");
                return false;
            }
        }).into(iv_cover);


    }

    /**
     * 绑定服务
     */
    public void bind() {
        if (!isBind) {
            Intent intent = new Intent(this, MusicService.class);
            bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
            isBind = true;
        }

    }

    /**
     * 解绑服务
     */
    public void unBind() {
        if (isBind) {
            unbindService(mServiceConnection);
            isBind = false;
        }
    }

    private void setNext(int mode) {
        if (mode == LOCATE_NEXT) {
            mId = mId + 1;
            Music music = mHelper.musicQueryById(mFid, mId);
           // Log.d(TAG, "run: " + music);
            if (music.id == null) {
                mId = 1;
            }
            music = mHelper.musicQueryById(mFid, mId);
            mTitle = music.title;
            mUpName = music.upName;
            mCover = music.cover;
            mDuration = music.duration;
            mBv = music.bv;
            mCid = music.cid;
        }
        if (mode == LOCATE_REPEAT) {
            return;
        }
        if (mode == LOCATE_RANDOM) {
            mId = randomId;
            Music music = mHelper.musicQueryById(mFid, randomId);
            mUpName = music.upName;
            mCover = music.cover;
            mDuration = music.duration;
            mBv = music.bv;
            mCid = music.cid;
        }
        mUrl = nextUrl;

    }


    /**
     * 设置下一首歌曲的源地址url
     *
     * @return
     */
    public static void setNextUrl(int mode) {

        new Thread() {
            @Override
            public void run() {
                super.run();
                if (mode == LOCATE_NEXT) {

                    Music music = mHelper.musicQueryById(mFid, mId + 1);
                    Log.d(TAG, "run: " + music);
                    if (music.id == null) {
                        music = mHelper.musicQueryById(mFid, 1);
                    }
                    String url = HttpUtils.getLinkByBvAndCid(music.bv, music.cid);
                    nextUrl = url;
                    return;
                }
                if (mode == LOCATE_REPEAT) {
                    nextUrl = mUrl;
                    return;
                }
                if (mode == LOCATE_RANDOM) {
                    Long count = HttpUtils.getCountById(mFid);
                    Random random = new Random(); // 创建一个Random对象
                    int lowerBound = 1; // 下限
                    int upperBound = Math.toIntExact(count); // 上限

                    // 生成并打印一个[lowerBound, upperBound]之间的随机整数
                    int randomInt = random.nextInt(upperBound - lowerBound + 1) + lowerBound;
                    randomId = randomInt;
                    Music music = mHelper.musicQueryById(mFid, randomId);
                    String url = HttpUtils.getLinkByBvAndCid(music.bv, music.cid);
                    nextUrl = url;
                }
            }
        }.start();
    }

    interface OnUrlSetListener {
        void onUrlSet();
    }

    private void setNextUrl(int mode, OnUrlSetListener listener) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                if (mode == LOCATE_NEXT) {

                    Music music = mHelper.musicQueryById(mFid, mId + 1);
                    Log.d(TAG, "run: " + music);
                    if (music.id == null) {
                        music = mHelper.musicQueryById(mFid, 1);
                    }
                    String url = HttpUtils.getLinkByBvAndCid(music.bv, music.cid);
                    nextUrl = url;

                } else if (mode == LOCATE_REPEAT) {
                    nextUrl = mUrl;

                } else if (mode == LOCATE_RANDOM) {
                    Long count = HttpUtils.getCountById(mFid);
                    Random random = new Random(); // 创建一个Random对象
                    int lowerBound = 1; // 下限
                    int upperBound = Math.toIntExact(count); // 上限

                    // 生成并打印一个[lowerBound, upperBound]之间的随机整数
                    int randomInt = random.nextInt(upperBound - lowerBound + 1) + lowerBound;
                    randomId = randomInt;
                    Music music = mHelper.musicQueryById(mFid, randomId);
                    String url = HttpUtils.getLinkByBvAndCid(music.bv, music.cid);
                    nextUrl = url;

                }
                listener.onUrlSet();
            }

        }.start();


    }

    /**
     * 设置上一首歌曲的url
     */
    private void setLastUrl(OnUrlSetListener listener)
    {
        new Thread(){
            @Override
            public void run() {
                super.run();
                Long count = HttpUtils.getCountById(mFid);
                --mId;

                Music music = mHelper.musicQueryById(mFid,mId);
                if (mId == 0)
                {
                    mId = Math.toIntExact(count);
                }
                music = mHelper.musicQueryById(mFid,mId);
                String url = HttpUtils.getLinkByBvAndCid(music.bv,music.cid);
                lastUrl = url;
                mTitle = music.title;
                mUpName = music.upName;
                mCover = music.cover;
                mDuration = music.duration;
                mBv = music.bv;
                mCid = music.cid;
                listener.onUrlSet();

            }
        }.start();


    }


    public void back(View view) {
        onBackPressed();
    }

    /**
     * 播放音乐
     * 1.如果是第一次播放，则开始播放，取消第一次播放的状态，并设置正在播放状态
     * 2.如果不是第一次播放（暂停后播放），将图标设置为暂停，并设置正在播放状态
     * 3.如果正在播放，将图标设置为播放，取消正在播放状态
     */
    private void playMusic() {
        if (first) {
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    mUrl = HttpUtils.getLinkByBvAndCid(mBv, mCid);
                    setNextUrl(LOCATE_MODE);
                    //Log.d(TAG, "MusicPlayActivity的onClick视频原链接 " + mUrl);

                    mMusicController.play(mUrl);
                }
            }.start();
            first = false;
            iv_play.setImageResource(R.drawable.pause);
            isPlay = true;
            return;
        }
        if (isPlay) {
            iv_play.setImageResource(R.drawable.play);
            isPlay = false;
            mMusicController.pause();
        } else {
            iv_play.setImageResource(R.drawable.pause);
            isPlay = true;
            mMusicController.continuePlay();

        }
        Log.d(TAG, "playMusic: ");

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_play) {
            playMusic();
            return;
        }
        if (id == R.id.iv_mode) {
            if (LOCATE_MODE == LOCATE_NEXT) {
                //实现单曲循环的功能
                iv_mode.setImageResource(R.drawable.repeat);
                LOCATE_MODE = LOCATE_REPEAT;
                setNextUrl(LOCATE_REPEAT);
                return;
            }
            if (LOCATE_MODE == LOCATE_REPEAT) {
                //实现随机播放的功能
                iv_mode.setImageResource(R.drawable.random);
                LOCATE_MODE = LOCATE_RANDOM;
                setNextUrl(LOCATE_RANDOM);
                return;
            }
            if (LOCATE_MODE == LOCATE_RANDOM) {
                //实现循环播放的功能
                iv_mode.setImageResource(R.drawable.order);
                LOCATE_MODE = LOCATE_NEXT;
                setNextUrl(LOCATE_NEXT);
                return;
            }

        }
        if (id == R.id.iv_next) {

            setNextUrl(LOCATE_NEXT, new OnUrlSetListener() {
                @Override
                public void onUrlSet() {
                    Log.d(TAG, "onUrlSet: ");
                    Intent intent1 = new Intent(ACTION_NEXT);
                    sendBroadcast(intent1);
                }
            });


        }
        if (id == R.id.iv_last) {
            setLastUrl(new OnUrlSetListener() {
                @Override
                public void onUrlSet() {
                    Intent intent1 = new Intent(ACTION_LAST);
                    sendBroadcast(intent1);
                }
            });

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        mHelper = ListSqliteHelper.getInstance(this);
        mHelper.openWriteLink();
        mHelper.openReadLink();
        setNextUrl(LOCATE_NEXT);

    }

    @Override
    protected void onStop() {
        super.onStop();
        // unBind();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 外部类Activity生命周期结束时，同时清空消息队列 & 结束Handler生命周期,防止内存泄露
        handler.removeCallbacksAndMessages(null);
        unregisterReceiver(completionReceiver);
    }
}