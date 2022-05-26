package com.example.aircraftwar_base.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraftwar_base.R;
import com.example.aircraftwar_base.application.GameView;
import com.example.aircraftwar_base.rank.ScoreDaoIm;

import java.text.SimpleDateFormat;
import java.util.Date;

public class scoreActivity extends AppCompatActivity {
    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private final int FP = ViewGroup.LayoutParams.WRAP_CONTENT;
    public static final SimpleDateFormat sml = new SimpleDateFormat("MM-dd kk:mm");
    public ScoreDaoIm scoreDaoIm;
    public String stringDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        MainActivity.activityList.add(this);
        if (MainActivity.choose == 1)
            scoreDaoIm = new ScoreDaoIm("easy.txt");
        if (MainActivity.choose == 2)
            scoreDaoIm = new ScoreDaoIm("common.txt");
        if (MainActivity.choose == 3)
            scoreDaoIm = new ScoreDaoIm("hard.txt");
        stringDate = sml.format(new Date());
        scoreDaoIm.addRecord(GameView.score, inputActivity.editText.getText().toString(), stringDate);

        createTable();
    }


    public void remove(View v) {
        TableLayout tableLayout = (TableLayout) findViewById(R.id.id_tableLayout);
        //获取TableLayout中的行数
        int len = tableLayout.getChildCount();
        tableLayout.removeView(tableLayout.getChildAt(len));
    }


    public void createTable() {

        TableLayout tableLayout = (TableLayout) findViewById(R.id.id_tableLayout);
        tableLayout.setStretchAllColumns(true);     //设置指定列号的列属性为Stretchable

        for (int row = 0; row < scoreDaoIm.dataToStrArr().length; row++) {     //产生的行数
            TableRow tableRow = new TableRow(scoreActivity.this);
            tableRow.setBackgroundColor(Color.rgb(222, 220, 210));      //设置表格背景
            for (int col = 0; col < 4; col++) {     //产生的列数
                TextView tv = new TextView(scoreActivity.this);
                tv.setText(scoreDaoIm.dataToStrArr()[row][col]);
                tableRow.addView(tv);
            }
            tableLayout.addView(tableRow, new LinearLayout.LayoutParams(FP, WC));

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


}