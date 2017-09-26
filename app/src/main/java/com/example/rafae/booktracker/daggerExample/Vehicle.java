package com.example.rafae.booktracker.daggerExample;

import android.app.Activity;
import android.content.Context;

import javax.inject.Inject;

/**
 * Created by felgueiras on 22/09/2017.
 */

public class Vehicle {

    private Context context;
    private Motor motor;

    /**
     * Fetch dependencies specified in parameters.
     *
     * @param motor
     * @param mActivity
     */
    @Inject
    public Vehicle(Motor motor, Context mActivity) {
        this.motor = motor;
        this.context = mActivity;
    }

    public void increaseSpeed(int value) {
        motor.accelerate(value);
    }

    public void stop() {
        motor.brake();
    }

    public int getSpeed() {
        return motor.getRpm();
    }

    public Context getContext() {
        return context;
    }

    public Motor getMotor() {
        return motor;
    }
}