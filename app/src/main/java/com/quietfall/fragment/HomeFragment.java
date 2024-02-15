package com.quietfall.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.quietfall.AddMusicListActivity;
import com.quietfall.MainActivity;
import com.quietfall.MusicListActivity;
import com.quietfall.R;
import com.quietfall.database.ListSqliteHelper;
import com.quietfall.entity.MusicList;
import com.quietfall.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private ListSqliteHelper mHelper;

    private ListView lv_list;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,null);
        lv_list = view.findViewById(R.id.lv_list);
        Button btn_add = view.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);
        initList();

        return view;
    }
    private void initList() {
        mHelper = ListSqliteHelper.getInstance(getContext());
        mHelper.openReadLink();
        mHelper.openWriteLink();
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

        SimpleAdapter listAdapter = new SimpleAdapter(getContext(), list, R.layout.list_item, new String[]{"desc"}, new int[]{R.id.tv_desc});
        lv_list.setAdapter(listAdapter);
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //i从0开始
                int id = i + 1;
                MusicList musicList = mHelper.queryListById(id);
                ToastUtil.show(getContext(),musicList.name);
                Intent intent = new Intent(getContext(), MusicListActivity.class);
                intent.putExtra("fid",musicList.fid);
                intent.putExtra("name",musicList.name);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getContext(), AddMusicListActivity.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        EditText et_uid = new EditText(getContext());
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
        builder.show();

    }
}