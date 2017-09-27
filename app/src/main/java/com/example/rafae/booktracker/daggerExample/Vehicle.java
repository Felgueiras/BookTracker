package com.example.rafae.booktracker.daggerExample;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;

import javax.inject.Inject;

/**
 * Created by felgueiras on 22/09/2017.
 */

public class Vehicle {

    private final LifecycleOwner lifecycle;
    private Context context;
    private Motor motor;

    /**
     * Fetch dependencies specified in parameters.
     *  @param motor
     * @param mActivity
     * @param lifeCycle
     */
    @Inject
    public Vehicle(Motor motor, Context mActivity, LifecycleOwner lifeCycle) {
        this.motor = motor;
        this.context = mActivity;
        this.lifecycle = lifeCycle;
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

    public LifecycleOwner getLifecycle() {
        return lifecycle;
    }
}