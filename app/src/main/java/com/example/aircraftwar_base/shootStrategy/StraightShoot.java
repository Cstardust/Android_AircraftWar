package com.example.aircraftwar_base.shootStrategy;


import static java.lang.Math.max;

import android.util.Log;

import com.example.aircraftwar_base.aircraft.AbstractAircraft;
import com.example.aircraftwar_base.aircraft.EliteEnemy;
import com.example.aircraftwar_base.aircraft.HeroAircraft;
import com.example.aircraftwar_base.bullet.BaseBullet;
import com.example.aircraftwar_base.bullet.EnemyBullet;
import com.example.aircraftwar_base.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * @author dell
 * 直射
 */
public class StraightShoot implements ShootStrategy{
    private int power = 0;
    private int direction = 0;
    private int x = 0;
    private int y = 0;
    private int speedX = 0;
    private int speedY = 0;
    private int shootNum = 0;

    @Override
    public List<BaseBullet> doShoot(AbstractAircraft air) {
        power = air.getPower();
        direction = air.getDirection();
        x = air.getLocationX();
        y = air.getLocationY() + air.getImage().getHeight()*direction;
        speedX = air.getSpeedX();
        speedY = air.getSpeedY() + direction*5;
        shootNum = max(shootNum,air.getShootNum());

        List<BaseBullet> res = new LinkedList<>();
        for(int i=0; i<shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            BaseBullet abstractBullet = null;
            if(air instanceof HeroAircraft)
            {
                abstractBullet = new HeroBullet(x + (i*2 - shootNum + 1)*10, y, speedX, speedY, power);
            }
            else if(air instanceof EliteEnemy)
            {
                abstractBullet = new EnemyBullet(x + (i*2 - shootNum + 1)*10, y, speedX, speedY, power);
            }
            else
            {
                Log.d("StraightShoot","sth else happened");
            }
            res.add(abstractBullet);
        }
        return res;
    }
}

