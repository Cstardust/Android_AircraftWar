package com.example.aircraftwar_base.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.aircraftwar_base.R;
import com.example.aircraftwar_base.application.GameView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class inputActivity extends AppCompatActivity {

    public  static  TextView textView;
    public  static TextView editText;

    public static final SimpleDateFormat sml = new SimpleDateFormat("MM-dd kk:mm");
    private String stringDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inputname);
        MainActivity.activityList.add(this);
        stringDate = sml.format(new Date());
        textView = (TextView) findViewById(R.id.textView1);
        editText = (TextView) findViewById(R.id.editView1);
        textView.setText("您的分数是:"+ GameView.score+"请输入您的姓名:");
    }


    public void showGrade(View v)
    {
        Intent intent = new Intent(inputActivity.this, scoreActivity.class);
        startActivity(intent);
    }
}