package com.example.aircraftwar_base.rewardFactory;


import com.example.aircraftwar_base.aircraft.AbstractAircraft;
import com.example.aircraftwar_base.reward.AbstractReward;
import com.example.aircraftwar_base.reward.BombReward;

public class BombRewardFactory extends BaseRewardFactory {
    @Override
    public AbstractReward createReward(AbstractAircraft craft) {
        return new BombReward(craft.getLocationX(),craft.getLocationY(),speedX,speedY);
    }
}
