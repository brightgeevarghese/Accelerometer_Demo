package com.example.bright.accdemo2;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Sensor sensor;
    SensorManager sensorManager;
    long lastUpdateTime;
    long currentTime;
    float lastX, lastY, lastZ;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        textView = (TextView)(findViewById(R.id.textId));
        final Random random = new Random();
        sensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                currentTime = System.currentTimeMillis();
                long diff = currentTime - lastUpdateTime;
                lastUpdateTime = currentTime;
                float speed = (x+y+z - lastX - lastY - lastZ)/diff*1000;
                if (speed > 500){
                    textView.setText(random.nextInt(5)+"");
                }
                lastX = x;
                lastY = y;
                lastZ = z;
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                switch (accuracy) {
                    case 0:
                        System.out.println("Unreliable");
                        break;
                    case 1:
                        System.out.println("Low Accuracy");
                        break;
                    case 2:
                        System.out.println("Medium Accuracy");
                        break;
                    case 3:
                        System.out.println("High Accuracy");
                        break;
                }
            }
        }, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
}
