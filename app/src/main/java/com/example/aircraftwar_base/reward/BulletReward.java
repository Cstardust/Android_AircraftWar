package com.example.aircraftwar_base.reward;

import com.example.aircraftwar_base.aircraft.HeroAircraft;

/*
道具：弹药包
作用：打印
 */
public class BulletReward extends AbstractReward {
    private volatile static int validTime = 0;              //  累计时间清0
    private static Thread t = null;
    @Override
    public void takeEffect(HeroAircraft heroAircraft) {

    }

    public BulletReward(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

}
