package com.mirea.zarin.mireaproject.practice5;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mirea.zarin.mireaproject.R;

public class Sensors extends Fragment implements SensorEventListener
{
    private SensorManager sensorManager;

    private TextView light;
    private TextView pressure;
    private TextView humidity;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        Sensor pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        sensorManager.registerListener(this, pressureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        Sensor humiditySensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        sensorManager.registerListener(this, humiditySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_sensors, container, false);
        this.light = (TextView)view.findViewById(R.id.light);
        this.pressure = (TextView)view.findViewById(R.id.pressure);
        this.humidity = (TextView)view.findViewById(R.id.humidity);
        return view;
    }
    @Override
    public void onPause()
    {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
    @Override
    public void onResume()
    {
        super.onResume();
    }
    private String getStringResource(int id)
    {
        return getResources().getString(id);
    }
    @SuppressLint("DefaultLocale")
    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        switch (sensorEvent.sensor.getType())
        {
            case Sensor.TYPE_LIGHT:
            float valueLight = sensorEvent.values[0];
            String textLight = getStringResource(R.string.lightSensor);
            this.light.setText(String.format("%s %f", textLight,valueLight));
            break;

            case Sensor.TYPE_PRESSURE:
            float valuePressure = sensorEvent.values[0];
            String textPressure = getStringResource(R.string.pressureSensor);
            this.pressure.setText(String.format("%s %f", textPressure,valuePressure));
            break;

            case Sensor.TYPE_RELATIVE_HUMIDITY:
            float valueHumidity = sensorEvent.values[0];
            String textHumidity = getStringResource(R.string.humiditySensor);
            this.humidity.setText(String.format("%s %f", textHumidity,valueHumidity));
            break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i)
    {

    }
}