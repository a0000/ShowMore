package com.a0000.showmore;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends ActionBarActivity {

    private Random rand = new Random();

    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.rv);
    }

    private void initData() {
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        //设置分隔线
        recyclerView.addItemDecoration(new RecyclerViewDivider(this));
        recyclerView.setVisibility(View.VISIBLE);

        mAdapter = new MyAdapter();
        recyclerView.setAdapter(mAdapter);
        List<Description> datas = mAdapter.getDatas();
        // 随机生成30以内个条目
        for (int i=0; i<5+rand.nextInt(30); i++) {
            Description desc = new Description();
            List<String> descs = new ArrayList<>();
            // 每个条目随机生成8个以内描述
            for (int j=0; j<rand.nextInt(8); j++) {
                // 每个描述随机70以内个字符
                descs.add(generateStr(rand.nextInt(50)));
            }
            if (descs.size()>0) {
                desc.setDescList(descs);
            }
            datas.add(desc);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String generateStr(int count) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0; i<20+count; i++) {
            char base;
            if (rand.nextBoolean()) {
                base = 'a';
            } else {
                base = 'A';
            }
            stringBuilder.append((char)(base+rand.nextInt(26)));
        }
        return stringBuilder.toString();
    }
}
