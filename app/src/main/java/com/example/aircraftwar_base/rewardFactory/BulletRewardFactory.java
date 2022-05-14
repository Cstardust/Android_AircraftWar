package com.example.aircraftwar_base.rewardFactory;


import com.example.aircraftwar_base.aircraft.AbstractAircraft;
import com.example.aircraftwar_base.reward.AbstractReward;
import com.example.aircraftwar_base.reward.BulletReward;

public class BulletRewardFactory extends BaseRewardFactory {
    @Override
    public AbstractReward createReward(AbstractAircraft craft) {
        return new BulletReward(craft.getLocationX(),craft.getLocationY(),speedX,speedY);
    }
}
