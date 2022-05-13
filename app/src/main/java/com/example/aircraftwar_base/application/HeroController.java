package com.example.aircraftwar_base.application;

import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.MotionEventCompat;

import com.example.aircraftwar_base.aircraft.HeroAircraft;

/**
 * 英雄机控制类
 * 监听鼠标，控制英雄机的移动
 *
 * @author hitsz
 */
public class HeroController{
    private GameView mView;
    private HeroAircraft heroAircraft;
    public HeroController(GameView v, HeroAircraft heroAircraft)
    {
        this.mView = v;
        this.heroAircraft = heroAircraft;
        setGestureListener();   //  监听鼠标位置，并实时改变英雄机位置
    }
    /**
     * 实时监听鼠标位置
     * @author
     */
    private void setGestureListener() {
        mView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
//                        if ( x<0 || x> GameView.screenWidth || y<0 || y>GameView.screenWidth){
//                          // 防止超出边界 加了不如不加。总教练可以帮忙改改
//                            break;
//                        }
                        heroAircraft.setLocation(event.getX(),event.getY()+heroAircraft.getImage().getHeight()/2);
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    case MotionEvent.ACTION_HOVER_MOVE:
                        break;
                }
                return true;
            }
        });
    }

}