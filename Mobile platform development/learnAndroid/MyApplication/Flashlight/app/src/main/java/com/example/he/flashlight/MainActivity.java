package com.example.he.flashlight;

import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    private ToggleButton button;
    private android.hardware.Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (ToggleButton) findViewById(R.id.tb);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    PackageManager manager = getPackageManager();
                    FeatureInfo info[] = manager.getSystemAvailableFeatures();
                    for (FeatureInfo i : info) {
                        if (PackageManager.FEATURE_CAMERA_FLASH.equals(i.name)) {
                            if (camera == null) {
                                camera = android.hardware.Camera.open();
                            }
                            android.hardware.Camera.Parameters parameters = camera.getParameters();
                            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                            camera.setParameters(parameters);
                            camera.startPreview();

                        }
                    }
                } else {
                    if (camera != null) {
                        camera.startPreview();
                        camera.release();
                        camera = null;
                    }


                }
            }
        });
    }
}
