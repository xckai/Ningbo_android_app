package com.app_1.zxing.simple.demo;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;


import com.example.app_1.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;

import com.google.zxing.client.android.camera.CameraManager;


import android.app.Activity;
import android.app.AlertDialog;

import android.content.Intent;
import android.content.pm.ActivityInfo;

import android.graphics.Bitmap;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import android.widget.TextView;


public class CaptureActivity extends Activity implements SurfaceHolder.Callback {
 private static final String TAG = "CaptureActivity";
	private CameraManager cameraManager;
	private CaptureActivityHandler handler;
	private Result savedResultToShow;
	private ViewfinderView viewfinderView;
	private TextView statusView;
	private View resultView;

	private boolean hasSurface;
	
	// private IntentSource source;
	
	// private ScanFromWebPageManager scanFromWebPageManager;
	private Collection<BarcodeFormat> decodeFormats;
	private Map<DecodeHintType, ?> decodeHints;
	private String characterSet;

	private InactivityTimer inactivityTimer;

	// private BeepManager beepManager;
	// private AmbientLightManager ambientLightManager;
	ViewfinderView getViewfinderView() {
		
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	CameraManager getCameraManager() {
		return cameraManager;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 Window window = getWindow();
		    window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.capturelayout);
		hasSurface = false;
		
		// 初始化 CameraManager
		
		
		
		
		// inactivityTimer = new InactivityTimer(this);
	}

	@Override
	protected void onStart() {
		
		//必须在这里对相机进行初始化！不然进入其他界面或者关屏幕的时候后台相机依旧运行
		if (cameraManager !=null)
		{
			//cameraManager.stopPreview();
			//inactivityTimer.onPause();
			if(cameraManager.isOpen())
			{
				
			cameraManager.closeDriver();
			}
			hasSurface = false;
           cameraManager=null;
			
		}

            cameraManager = new CameraManager(getApplication());


	    viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
	    viewfinderView.setCameraManager(cameraManager);
	   
	    
	   
	    //statusView = (TextView) findViewById(R.id.status_view);

	    handler = null;
	 //   lastResult = null;
	   // setRequestedOrientation(getCurrentOrientation());
	   //resetStatusView();
	    //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
	     SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
	     SurfaceHolder surfaceHolder = surfaceView.getHolder();
         surfaceHolder.addCallback((Callback) this);
//	    if (hasSurface) {
//	      // The activity was paused but not stopped, so the surface still exists. Therefore
//	      // surfaceCreated() won't be called, so init the camera here.
//	      initCamera(surfaceHolder);
//	    } else {
//	      // Install the callback and wait for surfaceCreated() to init the camera.
//	      surfaceHolder.addCallback((Callback) this);
//	    }
	   
	  //  inactivityTimer.onResume();
	    
	    super.onStart();
		
	}
	private int getCurrentOrientation() {
	    int rotation = getWindowManager().getDefaultDisplay().getRotation();
	    switch (rotation) {
	      case Surface.ROTATION_0:
	      case Surface.ROTATION_90:
	        return ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
	      default:
	        return ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
	    }
	  }
	private void resetStatusView() {
	    resultView.setVisibility(View.GONE);
	   // statusView.setText(R.string.msg_default_status);
	    statusView.setVisibility(View.VISIBLE);
	    viewfinderView.setVisibility(View.VISIBLE);
	    //lastResult = null;
	  }
	/**
	   * A valid barcode has been found, so give an indication of success and show the results.
	   *
	   * @param rawResult The contents of the barcode.
	   * @param scaleFactor amount by which thumbnail was scaled
	   * @param barcode A greyscale bitmap of the camera data which was decoded.
	   */
//	  public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
//	    inactivityTimer.onActivity();
//	    //lastResult = rawResult;
//	    viewfinderView.drawResultBitmap(barcode);
//	    TextView 	    txtResult=(TextView) findViewById(R.id.txtResult);
//	    txtResult.setText(rawResult.getBarcodeFormat().toString() +":"
//	    + rawResult.getText());
//	   
//			Intent intent=new Intent(CaptureActivity.this,Scan_success.class);
//			intent.putExtra("scanInfo",  rawResult.getText());
//			startActivity(intent);
////				
//				
//			
//	   
//	   
//	  }
	//处理结果主要入口！！！
	  public void handleDecode(Result rawResult) {
		   // inactivityTimer.onPause();

		    
		  //  TextView 	    txtResult=(TextView) findViewById(R.id.txtResult);
//		    txtResult.setText(":"
//		    + rawResult.getText());
//		    //System.out.println(rawResult.getText());
		    Intent intent=new Intent(CaptureActivity.this,Scan_success.class);
			intent.putExtra("scanInfo",  rawResult.getText());
			startActivity(intent);
		   
		  }
	private void initCamera(SurfaceHolder surfaceHolder) {
	    if (surfaceHolder == null) {
	      throw new IllegalStateException("No SurfaceHolder provided");
	    }
	    if (cameraManager.isOpen()) {
	    	 
	      Log.w(TAG, "initCamera() while already open -- late SurfaceView callback?");
	      return;
	    }
	    try {
            if(!cameraManager.isOpen())


            { cameraManager.openDriver(surfaceHolder);}
	      // Creating the handler starts the preview, which can also throw a RuntimeException.
	      if (handler == null) {
	    	  
	        handler = new CaptureActivityHandler(this, decodeFormats, decodeHints, characterSet, cameraManager);
	      
	        }
	      decodeOrStoreSavedBitmap(null, null);
    } 
	      catch (IOException ioe) {
	      Log.w(TAG, ioe);
	     displayFrameworkBugMessageAndExit();
	    } 
	    catch (RuntimeException e) {
	      // Barcode Scanner has seen crashes in the wild of this variety:
	      // java.?lang.?RuntimeException: Fail to connect to camera service
	    	
	      Log.w(TAG, "Unexpected error initializing camera", e);
	     // displayFrameworkBugMessageAndExit();
	    }
	  }
	public void drawViewfinder() {
		
	    viewfinderView.drawViewfinder();
	   
	  }

	private void decodeOrStoreSavedBitmap(Bitmap bitmap, Result result) {
	    // Bitmap isn't used yet -- will be used soon
	    if (handler == null) {
	      savedResultToShow = result;
	    } else {
	      if (result != null) {
	        savedResultToShow = result;
	      }
	      if (savedResultToShow != null) {
	        Message message = Message.obtain(handler, R.id.decode_succeeded, savedResultToShow);
	        handler.sendMessage(message);
	      }
	      savedResultToShow = null;
	    }
	  } 
	private void displayFrameworkBugMessageAndExit() {
		    AlertDialog.Builder builder = new AlertDialog.Builder(this);
		    builder.setTitle(getString(R.string.app_name));
		    builder.setMessage("意外错误，请重启");
		    builder.setPositiveButton("确定", new FinishListener(this));
		    builder.setOnCancelListener(new FinishListener(this));
		    builder.show();
		  }


	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;
		//inactivityTimer.onPause();
		cameraManager.stopPreview();
        if(cameraManager.isOpen())
        {

            cameraManager.closeDriver();
        }
        cameraManager=null;
		//this.unregisterReceiver(receiver);



	}
	@Override  
	
	protected void onDestroy() { 

		//inactivityTimer.onPause();

	      super.onDestroy();  
	}
//	@Override
//	protected void onStop()
//	{
//		cameraManager.stopPreview();
//		//inactivityTimer.onPause();
//		if(cameraManager.isOpen())
//		{
//			
//		cameraManager.closeDriver();
//		}
//		hasSurface = false;
//		super.onStop();
//		
//	}
	@Override
	protected void onStop()
	{


		Log.w("camare","stople ");
		super.onStop();

	}

}
