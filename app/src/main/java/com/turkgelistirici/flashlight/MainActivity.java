package com.turkgelistirici.flashlight;


import android.Manifest;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private CameraManager cameraManager;
    private ToggleButton toggleButton;
    private Camera camera;
    Camera.Parameters parameters;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toggleButton=(ToggleButton) findViewById(R.id.toggleButton);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            if(!Settings.System.canWrite(this))
            {
                requestPermissions(new String []{Manifest.permission.CAMERA},2909);
            }
        }


        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!toggleButton.isChecked())
                {
                    FlashON();
                }else
                {
                    FlashOFF();
                }

            }
        });




    }




    public void FlashON()
    {

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {


            try {
                cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                String cameraId = cameraManager.getCameraIdList()[0];
                cameraManager.setTorchMode(cameraId, true);

            } catch (CameraAccessException e) {
                e.printStackTrace();
            }

        } else
        {
            camera=Camera.open();
            parameters=camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(parameters);
            camera.startPreview();


        }




    }

    public void FlashOFF()
    {

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            try {
                cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                String camid = cameraManager.getCameraIdList()[0];
                cameraManager.setTorchMode(camid, false);

            } catch (CameraAccessException e) {
                e.printStackTrace();
            }

        }else
        {
            parameters=camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(parameters);
            camera.stopPreview();


        }



    }






}
