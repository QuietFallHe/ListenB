package com.quietfall.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.quietfall.entity.Music;
import com.quietfall.entity.MusicList;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理歌单列表的
 */
public class ListSqliteHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "main.db";
    //存放歌单数据的表名
    private static final String TABLE_LIST_NAME = "list";
    //存放音乐数据的表名
    private static final Integer DB_VERSION = 1;
    private static ListSqliteHelper mHelper = null;
    private SQLiteDatabase mWDB = null;
    private SQLiteDatabase mRDB = null;


    private ListSqliteHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static ListSqliteHelper getInstance(Context context) {
        if (mHelper == null) {
            mHelper = new ListSqliteHelper(context);
        }
        return mHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_LIST_NAME + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " fid VARCHAR NOT NULL," +
                " name VARCHAR NOT NULL);";
        db.execSQL(sql);

    }
    public SQLiteDatabase openReadLink()
    {
        if (mRDB == null || !mRDB.isOpen())
        {
            mRDB = mHelper.getReadableDatabase();
        }
        return mRDB;
    }
    public SQLiteDatabase openWriteLink()
    {
        if (mWDB == null || !mWDB.isOpen())
        {
            mWDB = mHelper.getWritableDatabase();
        }
        return mWDB;
    }
    public void closeLink()
    {
        if (mRDB != null && mRDB.isOpen())
        {
            mRDB.close();
            mRDB = null;
        }
        if (mWDB != null && mWDB.isOpen())
        {
            mWDB.close();
            mWDB = null;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    /**
     * ===============================================================================================================
     * 歌单操作
     */

    /**
     * 列表中插入歌单
     * @param list
     * @return
     */
    public long insert(MusicList list)
    {
        ContentValues values = new ContentValues();
        values.put("name",list.name);
        values.put("fid",list.fid);
       return mWDB.insert(TABLE_LIST_NAME,null,values);
    }

    /**
     * 查询所有歌单
     * @return
     */
    public List<MusicList>  queryAll()
    {
        List<MusicList> musicListList = new ArrayList<>();
        Cursor cursor = mRDB.query(TABLE_LIST_NAME,null,null,null,null,null,null);
        while (cursor.moveToNext())
        {
            MusicList musicList = new MusicList();
            musicList.id = cursor.getInt(0);
            musicList.name = cursor.getString(2);
            musicList.fid = cursor.getString(1);
            musicListList.add(musicList);
        }
        return musicListList;
    }
    /**
     * 创建每个歌单对应的音乐表
     * @param id
     * @return
     */
    public String createMusicListTable(String id)
    {
        String tableName = "music_list_"+id;
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " title VARCHAR NOT NULL," +
                " up_name VARCHAR NOT NULL," +
                " bv VARCHAR NOT NULL," +
                " cid UNSIGNED BIG INT NOT NULL," +
                " duration UNSIGNED BIG INT NOT NULL," +
                " cover VARCHAR NOT NULL);";
        mWDB.execSQL(sql);

        return tableName;
    }

    /**
     * 通过id寻找歌单信息
     * 这里的id是Sqlite中的_id
     * @return
     */
    public MusicList queryListById(Integer iid)
    {
        MusicList musicList = null;
        String id = String.valueOf(iid);
        Cursor cursor = mRDB.query(TABLE_LIST_NAME, null, "_id=?", new String[]{id}, null, null, null);
        if (cursor.moveToNext())
        {
            musicList = new MusicList();
            musicList.id= cursor.getInt(0);
            musicList.fid = cursor.getString(1);
            musicList.name = cursor.getString(2);
        }
        return musicList;
    }
    /**
     * ===============================================================================================================
     *
     */
    /**
     * ===============================================================================================================
     * 歌单歌曲操作
     */

    /**
     * 批量插入数据
     * @param valuesList
     * @return
     */
    public long insertSomeMusic(List<ContentValues> valuesList,String id)
    {
        String tableName = "music_list_"+id;
        long count = 0L;
        for (ContentValues values : valuesList) {
            if (mWDB.insert(tableName,null,values) != -1)
            {
                ++count;
            }
        }
        Log.d("qf", "insertSomeMusic: "+count);
        if (count < valuesList.size())
        {
            return -1;
        }
        return count;

    }

    /**
     * 查询歌单中所有音乐信息
     * @param id
     * @return
     */
    public List<Music> musicQueryAll(String id)
    {
        List<Music> list = new ArrayList<>();
        String tableName = "music_list_"+id;
        Cursor cursor = mRDB.query(tableName, null, null, null, null, null, null, null);
        while (cursor.moveToNext())
        {
            Music music = new Music();
            music.id= cursor.getInt(0);
            music.title = cursor.getString(1);
            music.upName = cursor.getString(2);
            music.bv = cursor.getString(3);
            music.cid = cursor.getLong(4);
            music.duration = cursor.getLong(5);
            music.cover = cursor.getString(6);
            list.add(music);
        }
        return list;

    }

    /**
     * 根据id查询音乐信息
     * @param id
     * @return
     */
    public Music musicQueryById(String fid,Integer id)
    {
        String tableName = "music_list_"+fid;
        String sid = String.valueOf(id);
        Music music = new Music();
        Cursor cursor = mRDB.query(tableName, null, "_id=?", new String[]{sid}, null, null, null, null);
        if (cursor.moveToNext())
        {
            music.id= cursor.getInt(0);
            music.title = cursor.getString(1);
            music.upName = cursor.getString(2);
            music.bv = cursor.getString(3);
            music.cid = cursor.getLong(4);
            music.duration = cursor.getLong(5);
            music.cover = cursor.getString(6);
        }
        return music;

    }

}
