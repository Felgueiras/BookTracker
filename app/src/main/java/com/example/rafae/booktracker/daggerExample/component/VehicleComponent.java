package com.example.rafae.booktracker.daggerExample.component;

import android.content.Context;


import com.example.rafae.booktracker.daggerExample.DaggerActivity;
import com.example.rafae.booktracker.daggerExample.Vehicle;
import com.example.rafae.booktracker.daggerExample.module.VehicleModule;
import com.example.rafae.booktracker.views.BooksListView;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by felgueiras on 22/09/2017.
 */

/**
 * The connection between the provider of dependencies, @Module, and the classes requesting them through @Inject is made using @Component, which is an interface:
 */
@Singleton
@Component(modules = {VehicleModule.class})
public interface VehicleComponent {

    void inject(BooksListView mainActivity2);

    /**
     * Within the interface, add methods for every object you need and it will automatically give you one with all its dependencies satisfied. In this case, I only need a Vehicle object, which is why there is only one method.
     *
     * @return
     */
    Vehicle provideVehicle();

//    Context provideContext();

}
