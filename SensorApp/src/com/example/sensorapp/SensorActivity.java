package com.example.sensorapp;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class SensorActivity extends Activity implements SensorEventListener{
	
	  private SensorManager sensorManager;
	  private Camera camera;
	  static int stepsCount;
	  TextView view;	  
	  Parameters p;
	  private long lastUpdate;
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	        WindowManager.LayoutParams.FLAG_FULLSCREEN);

	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_sensor);
	    view = (TextView)findViewById(R.id.textView);
	    
	    camera = Camera.open();
		p = camera.getParameters();
		
	    sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

	}
	
	@Override
	protected void onStop() {
		super.onStop();
 
		if (camera != null) {
			camera.release();
		}
	}
	
	@Override
	  public void onSensorChanged(SensorEvent event) {
	    if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
	      getAccelerometer(event);
	    }

	  }
	
	private void getAccelerometer(SensorEvent event) {
		
		float[] values = event.values;
	    // Movement
	    float x = values[0];
	    float y = values[1];
	    float z = values[2];

	    float accelationSquareRoot = (x * x + y * y + z * z)
	        / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
	    long actualTime = System.currentTimeMillis();
	    if (accelationSquareRoot >= 2) //
	    {
	      
	      ++stepsCount;
			view.setText("Steps taken: "+stepsCount);
			if(stepsCount%5==0){
				if(stepsCount%10==0){
					p.setFlashMode(Parameters.FLASH_MODE_TORCH);
					camera.setParameters(p);
					camera.startPreview();
				}else{
					p.setFlashMode(Parameters.FLASH_MODE_OFF);
					camera.setParameters(p);
					camera.stopPreview();
				}
			}
	    }
	    
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sensor, menu);
		return true;
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	  protected void onResume() {
	    super.onResume();
	    // register this class as a listener for the orientation and
	    // accelerometer sensors
	    sensorManager.registerListener(this,
	        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
	        SensorManager.SENSOR_DELAY_NORMAL);
	  }

	  @Override
	  protected void onPause() {
	    // unregister listener
	    super.onPause();
	    sensorManager.unregisterListener(this);
	  }


}
