package com.example.he.compasstest;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
  private SensorManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager= (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor magneticSensor=manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);//地磁传感器
        Sensor accelerometerSensor=manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);//加速度传感器

        manager.registerListener(listener,magneticSensor,SensorManager.SENSOR_DELAY_GAME);
        manager.registerListener(listener,accelerometerSensor,SensorManager.SENSOR_DELAY_GAME);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (manager!=null){
             manager.unregisterListener(listener);
        }
    }

    private SensorEventListener listener=new SensorEventListener() {
        float[] magnetValues=new float[3];
        float[] accelerValues=new float[3];
        @Override
        public void onSensorChanged(SensorEvent event) {
            //判断传感器的类型
            if (event.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD){
                 magnetValues=event.values.clone();//赋值的时候调用clone()

            }else if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
                accelerValues=event.values.clone();
            }

            float R[]=new  float[9];
            float values[]=new float[3];
            SensorManager.getRotationMatrix(R,null,accelerValues,magnetValues);
            SensorManager.getOrientation(R, values);

            Log.i("MainActivity","Value is "+values[0]);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}
