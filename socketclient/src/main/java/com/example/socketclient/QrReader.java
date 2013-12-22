package com.example.socketclient;

/**
 * Created by franqy on 23.11.13.
 */


import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Button;

import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;

import android.widget.TextView;
import android.graphics.ImageFormat;

/* Import ZBar Class files */
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;
import net.sourceforge.zbar.Config;

public class QrReader {


    private  static Camera mCamera;
    private static  CameraPreview mPreview;
    private  Handler autoFocusHandler;

   // TextView scanText;
    Button scanButton;

     ImageScanner scanner;

    private boolean barcodeScanned = false;
    private boolean previewing = true;

    Context kontext;
    Activity activity;

    static {
        System.loadLibrary("iconv");
    }



    public QrReader(Context kontext, Activity activity) {
       this.kontext=kontext;
        this.activity=activity;




        autoFocusHandler = new Handler();
       // mCamera = getCameraInstance();

        /* Instance barcode scanner */
        scanner = new ImageScanner();
        scanner.setConfig(0, Config.X_DENSITY, 3);
        scanner.setConfig(0, Config.Y_DENSITY, 3);



        //scanText = (TextView)findViewById(R.id.scanText);

        //scanButton = (Button)findViewById(R.id.ScanButton);

     /*   scanButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (barcodeScanned) {
                    barcodeScanned = false;
                   // scanText.setText("Scanning...");
                    mCamera.setPreviewCallback(previewCb);
                    mCamera.startPreview();
                    previewing = true;
                    mCamera.autoFocus(autoFocusCB);
                }
            }
        });*/
    }



    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e){
        }
        return c;
    }


    public void getIn()
    {

        try {
            if(mCamera==null){

                //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                autoFocusHandler = new Handler();
                mCamera = getCameraInstance();


                scanner = new ImageScanner();
                scanner.setConfig(0, Config.X_DENSITY, 3);
                scanner.setConfig(0, Config.Y_DENSITY, 3);

                mPreview = new CameraPreview(this.activity, mCamera, previewCb, autoFocusCB);
                FrameLayout preview = (FrameLayout) menu.nameView.findViewById(R.id.cameraPreview);
                preview.addView(mPreview);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block

        }
    }
    public void ad()
    {
        mCamera = getCameraInstance();

        mPreview = new CameraPreview(this.kontext, mCamera, previewCb, autoFocusCB);
    }

    public void releaseCamera() {
        if (mCamera != null) {
            mPreview.setVisibility(View.INVISIBLE);
            previewing = false;
            mCamera.setPreviewCallback(null);
            mPreview.getHolder().removeCallback(mPreview);
            mCamera.release();
            mCamera = null;
            mPreview= null;
        }
        FrameLayout preview = (FrameLayout) menu.nameView.findViewById(R.id.cameraPreview);

        preview.removeView(mPreview);

    }



    private Runnable doAutoFocus = new Runnable() {
        public void run() {
            if (previewing)
                mCamera.autoFocus(autoFocusCB);
        }
    };

    PreviewCallback previewCb = new PreviewCallback() {
        public void onPreviewFrame(byte[] data, Camera camera) {
            Camera.Parameters parameters = camera.getParameters();
            Size size = parameters.getPreviewSize();

            Image barcode = new Image(size.width, size.height, "Y800");
            barcode.setData(data);

            int result = scanner.scanImage(barcode);

            if (result != 0) {
                String[] ip = new String[5];
                previewing = false;
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();

                SymbolSet syms = scanner.getResults();
                for (Symbol sym : syms) {

                     ip = sym.getData().split("\\/");

                   // scanText.setText("barcode result " + sym.getData());
                    barcodeScanned = true;
                }

                if(ip.length>3)
                {
                pilot.file.write(ip[2]+":"+ip[3]);
               // MainActivity.men.closeMENUall();
               // MainActivity.men.close();
                MainActivity.blad.setText(Build.MANUFACTURER+" "+Build.MODEL);

                }
                else
                {
                    MainActivity.men.closeMENUall();
                    MainActivity.men.close();
                    MainActivity.blad.setText("zły kod");

                }

            }
        }
    };

    // Mimic continuous auto-focusing
    AutoFocusCallback autoFocusCB = new AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            autoFocusHandler.postDelayed(doAutoFocus, 1000);
        }
    };
}
