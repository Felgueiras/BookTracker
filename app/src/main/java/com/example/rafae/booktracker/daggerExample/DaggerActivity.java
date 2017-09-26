package com.example.rafae.booktracker.daggerExample;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.rafae.booktracker.R;
import com.example.rafae.booktracker.daggerExample.component.VehicleComponent;


public class DaggerActivity extends AppCompatActivity {

    Vehicle vehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main3);

        VehicleComponent vehicleComponent = DaggerApplication.getComponent();
        vehicle = vehicleComponent.provideVehicle();

        Toast.makeText(this, String.valueOf(this.vehicle.getSpeed()), Toast.LENGTH_SHORT).show();

        // Lambda expressions!
//        Button clickyThingy = findViewById(R.id.clickyThing);
//        clickyThingy.setOnClickListener(this::handleClicky);



//        clickyThingy.setOnClickListener(v ->
//                {
//                    Toast.makeText(this, "Button clicked", Toast.LENGTH_LONG).show();
//                }
//        );

        Context context = this.vehicle.getContext();
        Toast.makeText(context,"IT WORKS!",Toast.LENGTH_SHORT).show();

    }

    public void handleClicky(View v) {
        Toast.makeText(this, "Method references rock!", Toast.LENGTH_LONG).show();

    }

    void doSomething() {
        int x = 0;
        x++;
        Log.d("X", x + "");
    }
}
