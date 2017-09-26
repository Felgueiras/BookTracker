package com.example.rafae.booktracker.daggerExample.module;

import android.app.Activity;
import android.app.Application;
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

    private Context mActivity;

    public VehicleModule(Context activity) {
        mActivity = activity;
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
        return new Vehicle(new Motor(), mActivity);
    }
}