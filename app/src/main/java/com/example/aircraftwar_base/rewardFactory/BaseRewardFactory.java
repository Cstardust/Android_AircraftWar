package com.example.aircraftwar_base.rewardFactory;


import com.example.aircraftwar_base.aircraft.AbstractAircraft;
import com.example.aircraftwar_base.reward.AbstractReward;

public abstract class BaseRewardFactory{
    protected int speedX = 0;
    protected int speedY = 10;
    public abstract AbstractReward createReward(AbstractAircraft craft);

}
