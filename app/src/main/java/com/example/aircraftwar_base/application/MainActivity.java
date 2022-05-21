package com.example.aircraftwar_base.application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.aircraftwar_base.R;



public class MainActivity extends AppCompatActivity {
    public String TAG = "我恨安卓";
    private GameView mGameView;
    private static boolean isMusic;     //  游戏是否有音乐
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt = (Button) findViewById(R.id.btn);  //对Button 进行实例化

        Switch sw = (Switch)findViewById(R.id.switch1);
        //  添加监听
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        isMusic = true;
                    }else{
                        isMusic = false;
                    }
                  System.out.println("isMusic " + isMusic);
              }
            }
        );
    }



    public void showEasyGameView(View v)
    {
        getScreenHW();
        mGameView = new GameView(this);
        setContentView(mGameView);
    }


    public void showMediumGameView(View v)
    {
        getScreenHW();
        mGameView = new GameView(this);
        setContentView(mGameView);
    }

    public void showHardGameView(View v)
    {
        getScreenHW();
        mGameView = new GameView(this);
        setContentView(mGameView);
    }


    //  获取界面的长、宽
    public void getScreenHW()
    {
        //  定义DisplayMetric对象
        DisplayMetrics dm = new DisplayMetrics();
        //  取得窗口属性
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        //  窗口宽度
        GameView.screenWidth = dm.widthPixels;
        Log.i(TAG,"screenWidth : "+ GameView.screenWidth);

        //  窗口高度
        GameView.screenHeight = dm.heightPixels;
        Log.i(TAG,"screenHeight : "+GameView.screenHeight);
    }

    public boolean getIsMusic(){
        return isMusic;
    }
}