package com.quietfall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.quietfall.database.ListSqliteHelper;
import com.quietfall.entity.MusicList;
import com.quietfall.fragment.AboutFragment;
import com.quietfall.fragment.HomeFragment;
import com.quietfall.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView lv_list;
    private ListSqliteHelper mHelper;
    private FragmentTransaction ft;
    private FragmentManager fm;
    private boolean isHome;
    private boolean isAbout;
    private ImageView iv_home;
    private ImageView iv_about;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*findViewById(R.id.iv_add)
                .setOnClickListener(this);*/
        iv_home = findViewById(R.id.iv_home);
        iv_about = findViewById(R.id.iv_about);



        iv_about.setOnClickListener(this);
        iv_home.setOnClickListener(this);
        isHome = true;
        isAbout = false;

    }

    /*private void initList() {
        List<MusicList> musicListList = mHelper.queryAll();
        if (musicListList.size() == 0) {
            lv_list.setVisibility(View.GONE);
        } else {
            lv_list.setVisibility(View.VISIBLE);
        }
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < musicListList.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("desc", musicListList.get(i).name);
            list.add(map);
        }

        SimpleAdapter listAdapter = new SimpleAdapter(this, list, R.layout.list_item, new String[]{"desc"}, new int[]{R.id.tv_desc});
        lv_list.setAdapter(listAdapter);
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //i从0开始
                int id = i + 1;
                MusicList musicList = mHelper.queryListById(id);
                ToastUtil.show(getApplicationContext(),musicList.name);
                Intent intent = new Intent(MainActivity.this,MusicListActivity.class);
                intent.putExtra("fid",musicList.fid);
                intent.putExtra("name",musicList.name);
                startActivity(intent);
            }
        });
    }*/

    @Override
    protected void onStart() {
        super.onStart();
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.content, new HomeFragment());
        ft.commit();
        mHelper = ListSqliteHelper.getInstance(this);
        mHelper.openReadLink();
        mHelper.openWriteLink();
        /*initList();*/

    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //  mHelper.closeLink();
    }

    @Override
    public void onClick(View view) {
        /*Intent intent = new Intent(this, AddMusicListActivity.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        EditText et_uid = new EditText(this);
        builder.setTitle("请输入收藏夹主人的Uid");
        builder.setView(et_uid);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String uid = et_uid.getText().toString();
                if (uid == null || uid.isEmpty())
                {
                    return;
                }
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("取消",null);
        builder.show();*/
        int id = view.getId();
        if (id == R.id.iv_home) {
            if (isAbout) {
                isAbout = false;
                isHome = true;
                iv_home.setImageResource(R.drawable.home2);
                iv_about.setImageResource(R.drawable.about1);
                ft = fm.beginTransaction();
                ft.remove(fm.findFragmentById(R.id.content));
                ft.replace(R.id.content, new HomeFragment());
                ft.commit();


            }
        }
        if (id == R.id.iv_about)
        {
            if (isHome)
            {
                isHome = false;
                isAbout = true;
                iv_about.setImageResource(R.drawable.about2);
                iv_home.setImageResource(R.drawable.home1);
                ft = fm.beginTransaction();
                ft.remove(fm.findFragmentById(R.id.content));
                ft.replace(R.id.content, new AboutFragment());
                ft.commit();
            }
        }

    }
}