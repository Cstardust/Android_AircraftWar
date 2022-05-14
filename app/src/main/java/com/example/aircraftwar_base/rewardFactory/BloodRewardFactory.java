package com.example.aircraftwar_base.rewardFactory;


import com.example.aircraftwar_base.aircraft.AbstractAircraft;
import com.example.aircraftwar_base.reward.BloodReward;

public class BloodRewardFactory extends BaseRewardFactory {
    @Override
    public BloodReward createReward(AbstractAircraft craft) {
        return new BloodReward(craft.getLocationX(),craft.getLocationY(),speedX,speedY);
    }
}
