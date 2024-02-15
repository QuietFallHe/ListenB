package com.quietfall.utils;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.quietfall.AddMusicListActivity;
import com.quietfall.entity.LinkInfo;
import com.quietfall.entity.ListInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtils {
    private static final String TAG = "qf";

    /**
     * 通过收藏夹id获取收藏夹名字，要求id为字符串
     *
     * @param id
     * @return
     */
    public static String getListNameById(String id) {
        String name[] = new String[1];
        String url = "https://api.bilibili.com/x/v3/fav/resource/list?media_id=" + id + "&pn=1&ps=20&keyword=&order=mtime&type=0&tid=0&platform=web";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            String json = response.body().string();
            Gson gson = new Gson();
            ListInfo listInfo = gson.fromJson(json, ListInfo.class);
            try {
                name[0] = listInfo.getData().getInfo().getTitle();
            } catch (Exception e) {
                Log.d(TAG, "HttpUtils的getListNameById出错");
                e.printStackTrace();
            }
            Log.d(TAG, "HttpUtils的getListNameById: " + name[0]);
            Log.d(TAG, "HttpUtils的getListNameById: " + json);

        } catch (IOException e) {
            Log.d(TAG, "HttpUtils的getListNameById出错");
            e.printStackTrace();
        }
       /*new Thread(){
           @Override
           public void run() {
               super.run();

               OkHttpClient okHttpClient = new OkHttpClient();
               Request request = new Request.Builder().url(url).build();
               Call call = okHttpClient.newCall(request);
               try {
                   Response response = call.execute();
                   String json = response.body().string();
                   Gson gson = new Gson();
                   ListInfo listInfo = gson.fromJson(json, ListInfo.class);
                   name[0] = listInfo.getData().getInfo().getTitle();
                   Log.d(TAG, "onResponse: "+name[0]);
                   Log.d(TAG, "onResponse: "+json);

               } catch (IOException e) {
                   throw new RuntimeException(e);
               }
               *//*call.enqueue(new Callback() {



                   @Override
                   public void onFailure(@NonNull Call call, @NonNull IOException e) {

                   }

                   @Override
                   public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (response.isSuccessful())
                        {
                            String json = response.body().string();
                            Gson gson = new Gson();
                            ListInfo listInfo = gson.fromJson(json, ListInfo.class);
                            name[0] = listInfo.getData().getInfo().getTitle();
                            Log.d(TAG, "onResponse: "+name[0]);
                            Log.d(TAG, "onResponse: "+json);

                        }
                   }
               });*//*
           }
       }.start();*/
        return name[0];
    }

    /**
     * 通过收藏夹id获取收藏夹视频数量
     * @param id
     * @return
     */
    public static Long getCountById(String id) {
        Long mediaCount = null;
        String url = "https://api.bilibili.com/x/v3/fav/resource/list?media_id=" + id + "&pn=1&ps=20&keyword=&order=mtime&type=0&tid=0&platform=web";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            String json = response.body().string();
            Gson gson = new Gson();
            ListInfo listInfo = gson.fromJson(json, ListInfo.class);
            Log.d(TAG, "com.quietfall.utils.HttpUtils.getCountById: " + listInfo.getData().getInfo().getMediaCount());
            mediaCount = listInfo.getData().getInfo().getMediaCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mediaCount;

    }

    /**
     * 通过收藏夹id<批量>获取用于插入数据库中的ContentValues
     *
     * @param id
     * @return
     */
    public static List<ContentValues> getColumnById(String id, Long count) throws IOException {
        List<ContentValues> list = new ArrayList<>();

        OkHttpClient okHttpClient = new OkHttpClient();
        Gson gson = new Gson();
        try {
            for (int pn = 1; pn <= count / 20 + 1; pn++) {
                String url = "https://api.bilibili.com/x/v3/fav/resource/list?media_id=" + id + "&pn=" + pn + "&ps=20&keyword=&order=mtime&type=0&tid=0&platform=web";
                Request request = new Request.Builder().url(url).build();
                Call call = okHttpClient.newCall(request);
                Response response = call.execute();
                String json = response.body().string();
                ListInfo listInfo = gson.fromJson(json, ListInfo.class);
                for (ListInfo.DataDTO.MediasDTO media : listInfo.getData().getMedias()) {
                    ContentValues values = new ContentValues();
                    String bvId = media.getBvid();
                    String title = media.getTitle();
                    String up_name = media.getUpper().getName();
                    String cover = media.getCover();
                    Long duration = media.getDuration();
                    Long cid = media.getUgc().getFirstCid();
                    values.put("title", title);
                    values.put("up_name", up_name);
                    values.put("cover", cover);
                    values.put("bv", bvId);
                    values.put("cid",cid);
                    values.put("duration",duration);
                    list.add(values);

                }
            }
        } catch (Exception e) {

        }

        /*call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful())
                {
                    String json = response.body().string();
                    Gson gson = new Gson();
                    ListInfo listInfo = gson.fromJson(json, ListInfo.class);
                    Log.d(TAG, "onResponse: "+listInfo.getData().getInfo().getTitle());
                    mediaCount[0] = listInfo.getData().getInfo().getMediaCount();
                }
            }
        });*/



           /* call.enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful())
                    {
                        String json = response.body().string();
                        Gson gson = new Gson();
                        ListInfo listInfo = gson.fromJson(json, ListInfo.class);
                        for (ListInfo.DataDTO.MediasDTO media : listInfo.getData().getMedias()) {
                            ContentValues values = new ContentValues();
                            String bvId = media.getBvId();
                            String url = "https://www.bilibili.com/video/"+bvId;
                            String title = media.getTitle();
                            String up_name = media.getUpper().getName();
                            String cover = media.getCover();
                            values.put("title",title);
                            values.put("up_name",up_name);
                            values.put("cover",cover);
                            values.put("url",url);
                            list.add(values);

                        }
                    }

                }
            });*/
        return list;
    }

    /**
     * 通过bv和cid获得原视频链接
     * @param bv
     * @param cid
     */
    public static String getLinkByBvAndCid(String bv,Long cid){
        String url = "https://api.bilibili.com/x/player/playurl?otype=json&fnver=0&fnval=2&player=1&qn=64&bvid="+bv+"&cid="+cid;
        OkHttpClient okHttpClient = new OkHttpClient();
        Gson gson = new Gson();
        Request request = new Request.Builder().url(url).build();
        String srcLink = null;
        try {
            Response response = okHttpClient.newCall(request).execute();
            String json = response.body().string();
            LinkInfo linkInfo = gson.fromJson(json, LinkInfo.class);
             srcLink = linkInfo.getData().getDurl().get(0).getUrl();
        } catch (IOException e) {
            Log.d(TAG, "HttpUtils的getLinkByBvAndCid:出错！！！");
            e.printStackTrace();
        }
        return srcLink;
    }

}

