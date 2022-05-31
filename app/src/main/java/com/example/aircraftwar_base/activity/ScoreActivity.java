package com.example.aircraftwar_base.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraftwar_base.R;
import com.example.aircraftwar_base.application.GameView;
import com.example.aircraftwar_base.Player.Player;
import com.example.aircraftwar_base.db.DBOpenHelper;


import java.util.LinkedList;
import java.util.List;

public class ScoreActivity extends AppCompatActivity {
    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private final int FP = ViewGroup.LayoutParams.WRAP_CONTENT;
    private static String mode;
    private static String this_user_name = null;

    //  players：所有游戏记录
    private List<Player> players = new LinkedList<Player>();
    private DBOpenHelper mDBOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        MainActivity.activityList.add(this);
        mDBOpenHelper = new DBOpenHelper(this);
        mDBOpenHelper.addRecord(this_user_name,
                Integer.valueOf(GameView.getScore()).toString(),
                mode);
    //  players：所有游戏记录
        players = mDBOpenHelper.getAllRecord();
        createTable();
    }


    public void remove(View v) {
        TableLayout tableLayout = (TableLayout) findViewById(R.id.id_tableLayout);
        //获取TableLayout中的行数
        int len = tableLayout.getChildCount();
        tableLayout.removeView(tableLayout.getChildAt(len));
    }

//  劝大哥排行榜换个方式实现。。你这个有点别扭啊。。你怎么做删除。。删除接口我给你了。DBOpenHelper的deleteRecord
    public void createTable() {
        TableLayout tableLayout = (TableLayout) findViewById(R.id.id_tableLayout);
        tableLayout.setStretchAllColumns(true);     //设置指定列号的列属性为Stretchable
        for(int i=0;i<players.size();++i){
            TableRow tableRow = new TableRow(ScoreActivity.this);
            tableRow.setBackgroundColor(Color.rgb(222, 220, 210));

            TextView tv = new TextView(ScoreActivity.this);
            tv.setText(Integer.valueOf(i+1).toString());
            tableRow.addView(tv);

            tv = new TextView(ScoreActivity.this);
            String name = players.get(i).getName();
            tv.setText(name);
            tableRow.addView(tv);

            tv = new TextView(ScoreActivity.this);
            String score = Integer.valueOf(players.get(i).getScore()).toString();
            tv.setText(score);
            tableRow.addView(tv);

            tv = new TextView(ScoreActivity.this);
            String date = players.get(i).getDate();
            tv.setText(date);
            tableRow.addView(tv);


            tableLayout.addView(tableRow,new ViewGroup.LayoutParams(FP,WC));
        }

    }
    public void exit()
    {
        for(Activity act:MainActivity.activityList)
        {
            act.finish();
        }
        System.exit(0);
    }
    public void exitGame(View v)
    {
        exit();
    }

    public static void setMode(String m){
        mode = m;
    }

    public static String getMode(){
        return mode;
    }

    public static String getThis_user_name() {
        return this_user_name;
    }

    public static void setThis_user_name(String this_user_name) {
        ScoreActivity.this_user_name = this_user_name;
    }
}