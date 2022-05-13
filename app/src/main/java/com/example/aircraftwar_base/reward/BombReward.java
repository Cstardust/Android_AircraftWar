package com.example.aircraftwar_base.reward;


import com.example.aircraftwar_base.aircraft.HeroAircraft;

import java.util.ArrayList;

/*
道具：炸药包
作用：打印
 */
public class BombReward extends AbstractReward {
    @Override
    //  炸弹没用到heroAircraft
    public void takeEffect(HeroAircraft heroAircraft) {

    }

    public BombReward(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

}
