package com.example.rafae.booktracker.daggerExample;

import android.app.Application;
import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;

import com.example.rafae.booktracker.daggerExample.component.DaggerVehicleComponent;
import com.example.rafae.booktracker.daggerExample.component.VehicleComponent;
import com.example.rafae.booktracker.daggerExample.module.VehicleModule;
import com.example.rafae.booktracker.views.BooksListView;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by janisharali on 25/12/16.
 * <p>
 * This class gets DataManager through DI. The interesting part in this class is the ApplicationComponent.
 */

/**
 * This class gets DataManager through DI. The interesting part in this class is the ApplicationComponent.
 */

/**
 * -> ApplicationModule
 */
public class DaggerApplication extends Application {

    protected static VehicleComponent component;

    static LifecycleOwner lifeCycle;
    private static DaggerApplication context;

    public static DaggerApplication get(Context context) {
        return (DaggerApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * LeakCanary
         */
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // get application componentac
        component = DaggerVehicleComponent.builder()
                .vehicleModule(new VehicleModule(this, lifeCycle))
                .build();
        context = this;
    }

    public static void passLifeCycle(LifecycleOwner lifeCycle){
        DaggerApplication.lifeCycle = lifeCycle;

        component = DaggerVehicleComponent.builder()
                .vehicleModule(new VehicleModule(context, DaggerApplication.lifeCycle))
                .build();
    }





    public static VehicleComponent getComponent() {
        return component;
    }
}
