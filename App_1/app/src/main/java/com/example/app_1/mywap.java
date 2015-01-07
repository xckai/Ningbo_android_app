package com.example.app_1;

import android.R.bool;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.style.SuperscriptSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import android.view.View;

import android.view.View.OnTouchListener;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.cookie.Cookie;

import java.net.URL;
import java.util.List;

@SuppressLint("SetJavaScriptEnabled")
public  class mywap extends Activity {

    public WebView webview;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mywaplayout);
        webview = (WebView) findViewById(R.id.mywaplayoutview);
        webview.getSettings().setJavaScriptEnabled(true);
        Set_ButtonFunction();

       String myurl="http://10.60.146.81:8123/";

        String DomainUrl=((myapplication)getApplication()).DomainUrl;
        //String myurldomain=DomainUrl;
        if(!DomainUrl.equals(""))
        {
            myurl="http://"+DomainUrl;

        }

       Intent intent=this.getIntent();
       // String isScanString=intent.getExtras().getString("isScanString");

       boolean isScan=intent.getExtras().getBoolean("isScan",false);

       if(isScan) {

           try {
               String  category=intent.getExtras().getString("category");
               int num = intent.getExtras().getInt("num",0);
               if (num!=0) {
                   if (category.equals("Distribution")) {
                       myurl = myurl + "#/CPCAPPMobile/Transportation/TransportDetails?distributionID="+num+"&category=0" ;
                   } else if (category.equals("Order")) {

                       myurl = myurl + "#/CPCAPPMobile/OrderDemo/OrderDetails?orderId=" + num+"&category=0";

                   }

               }

           } catch (Exception e) {
              Log.w("mywap","获取二维码扫描出现异常");


           }
       }


        List<Cookie> cookies= ((myapplication) getApplication()).cookies;    //这里的cookie就是上面保存的cookie
       // CookieSyncManager.createInstance(this);
        CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        if (!cookies.isEmpty()) {
            cookieManager.removeSessionCookie();
            for(int i=0;i<cookies.size();++i)
            {
                String cookieString = cookies.get(i).getName() + "=" + cookies.get(i).getValue() + ";      domain=" + cookies.get(i).getDomain()+"; path="+cookies.get(i).getPath()+"; expiry="+cookies.get(i).getExpiryDate();
                String myurldomain=cookies.get(i).getDomain();
                cookieManager.setCookie(myurldomain, cookieString);


            }
            cookieSyncManager.getInstance().sync();



            //加载需要显示的网页


            //设置Web视图


            webview.loadUrl(myurl);
            webview.setWebViewClient(new WebViewClient ());

        }
        else {


            webview.loadUrl(myurl);
            Log.w("mywap", "4");
            //
            webview.setWebViewClient(new WebViewClient());
           // Set_ButtonFunction();
        }
    } 
     
    @Override
    //设置回退 
    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法 
    public boolean onKeyDown(int keyCode, KeyEvent event) { 
    	System.out.print(webview.canGoBack());
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) { 
            webview.goBack(); //goBack()表示返回WebView的上一页面 
            Log.w("mywap", "fanhui");
            return true; 
        } 
        return super.onKeyDown(keyCode, event);
    } 
     
    //Web视图 
//    private class HelloWebViewClient extends WebViewClient {
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//
//            view.loadUrl(url);
//            return true;
//        }
//    }
    public boolean MyWebViewGoBack()
    {
    	 if ( webview.canGoBack()) { 
	            webview.goBack(); //goBack()表示返回WebView的上一页面 
	        	Log.w("mywap", "goback");
	            return true; 
	        } 
		 else{   super.onBackPressed();
			Log.w("mywap", "onback");
		 return true;
		 }
    	
    }
    private void Set_ButtonFunction () {
		TextView orderlistButton=(TextView)this.findViewById(R.id.menubutton);
		orderlistButton.setOnTouchListener( new MyButtonClick(this));
		TextView MytranslationButton=(TextView)this.findViewById(R.id.homebutton);
		MytranslationButton.setOnTouchListener(new MyButtonClick(this));
		TextView EnterpriseInfoButton=(TextView)this.findViewById(R.id.backbutton);
		EnterpriseInfoButton.setOnTouchListener( new MyButtonClick(this));
		
    }
    
    
    
}
    
    
    
    
    
    
 class MyButtonClick implements OnTouchListener
    {
    	private static final KeyEvent KeyEvent = null;
		private mywap activity;
		
    	//private String classnameString;
    	public  MyButtonClick (mywap act) {
    		this.activity=act;
    		
    		
    	
    	}
    	@Override
    	 public boolean onTouch(View v, MotionEvent event)  {
    	
    		Intent intent=new Intent();
    		
    		switch (v.getId()) {
    		case R.id.menubutton:
    			intent.setClassName(activity, com.app_1.zxing.simple.demo.CaptureActivity.class.getName());
    			activity.startActivity(intent);
    	       return true;
    			
    		case R.id.homebutton:
    			 Intent startMain = new Intent(Intent.ACTION_MAIN);  
    		        startMain.addCategory(Intent.CATEGORY_HOME);  
    		        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
    		        activity.startActivity(startMain); 
    			return true;
    		case R.id.backbutton:
    			activity.MyWebViewGoBack();
    		
    		 	//activity.onKeyDown( android.view.KeyEvent.KEYCODE_BACK,  null);
    			
    		
    		default:
    		
    			
    			break;
    		}
			return false;
    	
    		
    			
    			
    		
    	
    		
    	}
	
    	
    }
	
	
	
