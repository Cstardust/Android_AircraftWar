package com.example.aircraftwar_base.reward;


import com.example.aircraftwar_base.aircraft.AbstractAircraft;
import com.example.aircraftwar_base.aircraft.HeroAircraft;
import com.example.aircraftwar_base.bullet.BaseBullet;

import java.util.ArrayList;
import java.util.List;

/*
道具：炸药包
 */
public class BombReward extends AbstractReward {

    public void takeEffect(List<AbstractAircraft> enemys, List<BaseBullet> bullets) {
        for(AbstractAircraft enemy : enemys)
        {
            enemy.vanish();
        }
        for(BaseBullet bullet : bullets)
        {
            bullet.vanish();
        }
    }

    public BombReward(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

}

