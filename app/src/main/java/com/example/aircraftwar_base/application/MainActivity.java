package com.example.aircraftwar_base.application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;

import com.example.aircraftwar_base.R;



public class MainActivity extends AppCompatActivity {
    public String TAG = "我恨安卓";
    private GameView mGameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  替换出游戏主界面
        getScreenHW();
        mGameView = new GameView(this);
        setContentView(mGameView);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            this.finish();
        }
        return true;
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
}