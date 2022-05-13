package com.example.aircraftwar_base.aircraft;

import com.example.aircraftwar_base.application.GameView;
import com.example.aircraftwar_base.bullet.BaseBullet;
import com.example.aircraftwar_base.shootStrategy.MyContext;
import com.example.aircraftwar_base.shootStrategy.ShootStrategy;
import com.example.aircraftwar_base.shootStrategy.StraightShoot;

import java.util.List;

public class EliteEnemy extends AbstractAircraft{
    //  默认直射方式
    private MyContext c = new MyContext(new StraightShoot());

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp,int power,int direction,int val,int shootNum) {
        super(locationX, locationY, speedX, speedY, hp,power,direction,val,shootNum);
    }

    public void setStrategy(ShootStrategy s)
    {
        c.setStrategy(s);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= GameView.screenHeight) {
            vanish();
        }
    }
    @Override
    public List<BaseBullet> shoot() {
        if(!isValid) {
            return null;
        }
        return c.executeStrategy(this);
    }

}
