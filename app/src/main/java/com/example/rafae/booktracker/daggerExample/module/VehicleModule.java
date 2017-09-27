package com.example.rafae.booktracker.daggerExample.module;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;


import com.example.rafae.booktracker.daggerExample.Motor;
import com.example.rafae.booktracker.daggerExample.Vehicle;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by felgueiras on 22/09/2017.
 */

/**
 * Provides the objects which Vehicle class needs.
 */
@Module
public class VehicleModule {

    private final LifecycleOwner lifeCycle;
    private Context applicationContext;

    public VehicleModule(Context activity, LifecycleOwner lifeCycle) {
        applicationContext = activity;
        this.lifeCycle = lifeCycle;
    }

    /**
     * Independent model.
     *
     * @return
     */
    @Provides
    @Singleton
    Motor provideMotor() {
        return new Motor();
    }



    /**
     * Indicate the dependency.
     *
     * @return
     */
    @Provides
    @Singleton
    Vehicle provideVehicle() {
        return new Vehicle(new Motor(), applicationContext, lifeCycle);
    }
}