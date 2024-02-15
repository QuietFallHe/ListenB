package com.quietfall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.quietfall.database.ListSqliteHelper;
import com.quietfall.entity.Music;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MusicListActivity extends AppCompatActivity {

    private ListView lv_music_list;
    private ListSqliteHelper mHelper;
    //接受intent传来的收藏夹id
    private String mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);
        TextView title_title = findViewById(R.id.title_title);

        lv_music_list = findViewById(R.id.lv_music_list);
        Intent intent = getIntent();
        mId = intent.getStringExtra("fid");
        title_title.setText("歌曲列表--"+intent.getStringExtra("name"));

    }

    private void initList() {
        List<Music> musicList = mHelper.musicQueryAll(mId);
        if (musicList.size() == 0)
        {
            lv_music_list.setVisibility(View.GONE);
        }
        else {
            lv_music_list.setVisibility(View.VISIBLE);
        }
        List<Map<String,Object>> dataList = new ArrayList<>();
        for (int i = 0; i < musicList.size(); i++) {
            Map<String,Object> map = new HashMap<>();
            map.put("desc",musicList.get(i).title);
            map.put("up_name",musicList.get(i).upName);
            dataList.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),dataList,R.layout.music_list_item,new String[]{"desc","up_name"},new int[]{R.id.tv_desc,R.id.tv_up_name});
        lv_music_list.setAdapter(adapter);
        lv_music_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MusicListActivity.this, MusicPlayActivity.class);
                int id = i + 1;
                Music music = mHelper.musicQueryById(mId,id);
                intent.putExtra("fid",mId);
                intent.putExtra("id",music.id);
                intent.putExtra("title",music.title);
                intent.putExtra("up_name",music.upName);
                intent.putExtra("cover",music.cover);
                intent.putExtra("duration",music.duration);
                intent.putExtra("bv",music.bv);
                intent.putExtra("cid",music.cid);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mHelper = ListSqliteHelper.getInstance(this);
        mHelper.openReadLink();
        mHelper.openWriteLink();
        initList();
    }


    public void back(View view) {
        onBackPressed();
    }


}