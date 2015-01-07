package com.example.app_1;



import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.Integer;
import java.net.URL;
import java.sql.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.DefaultClientConnection;
import org.apache.http.impl.conn.DefaultClientConnectionOperator;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.google.zxing.Result;

public class MainActivity extends Activity {
    public static Cookie cookie = null;


    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button loginView=(Button) this.findViewById(R.id.Login);
        Button cancleView=(Button) this.findViewById(R.id.Cancel);
        cancleView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText enIdEditText=(EditText) findViewById(R.id.EnterpriseIDText);
                EditText passwordText=(EditText) findViewById(R.id.EnterprisePasswordText);
                EditText userIdEditText=(EditText)findViewById(R.id.UserIDText);
                TextView logininfoEditText=(TextView)findViewById(R.id.loginInfo);
                enIdEditText.setText("");
                passwordText.setText("");
                userIdEditText.setText("");
                logininfoEditText.setText("");

            }
        });
		loginView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText enIdEditText=(EditText) findViewById(R.id.EnterpriseIDText);
				EditText passwordText=(EditText) findViewById(R.id.EnterprisePasswordText);
				EditText userIdEditText=(EditText)findViewById(R.id.UserIDText);
				TextView logininfoEditText=(TextView)findViewById(R.id.loginInfo);
				String enIdString=enIdEditText.getText().toString();
				String userIdString=userIdEditText.getText().toString();
				String passwordString=passwordText.getText().toString();
				if (enIdString.equals("")) {
					enIdString="0000";
					
					
				}
                EditText DomainUrlEdit= (EditText) findViewById(R.id.ServerIp);
                ((myapplication)getApplication()).DomainUrl =DomainUrlEdit.getText().toString();
                String DomainUrl=((myapplication)getApplication()).DomainUrl;
                if(DomainUrl.equals(""))
                {
                    DomainUrl="10.60.146.81:8123";

                }
				logininfoEditText.setText("正在验证请稍候".toString());
                String url="http://"+DomainUrl+"/Login/WebLoginCheck?";
                String urlparams="passwordString="+passwordString+"&userIdString="+userIdString+"&enIdString="+enIdString;
                url=url+urlparams;
                CheckUserThread checkUserThread=new CheckUserThread();
                checkUserThread.execute(url);

			}
	});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public class CheckUserThread extends AsyncTask<String, integer, String>
	{

       public  DefaultHttpClient httpClient=new DefaultHttpClient();
		@Override
		protected String doInBackground(String...params)
		{
			


			HttpGet resultGet=new HttpGet(params[0]);
			//resultGet.setParams(params[0]);
			
		  try {
			  
			  HttpResponse httpResponse=httpClient.execute(resultGet);
			  HttpEntity entity = httpResponse.getEntity();

		        if (entity != null) {
		        	String myjson=EntityUtils.toString(entity);
		        	return myjson;

		            // A Simple JSON Response Read
//		            InputStream instream = entity.getContent();
//		           
//		            BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
//		            String json = reader.readLine();
		        	//JSONObject myObject2 = new JSONObject(myjson);
		    
		            
		        }
			  
			  
			
		} catch (Exception e) {
			Log.w("checkuser","connect error");
			return null;
			// TODO: handle exception
		}
		return null;
			
			
			
		
		}
		
		
		@Override 
		protected void onPostExecute(String  result)
	
			
		{
			TextView logininfoEditText=(TextView)findViewById(R.id.loginInfo);
			try {
				JSONObject myObject2 = new JSONObject(result);
				
				if(myObject2.getString("checkresult").equals("1"))
				  {



                      List<Cookie> cookies = httpClient.getCookieStore().getCookies();
                      if (!cookies.isEmpty()) {
                          ((myapplication)getApplication()).cookies=cookies;
                      }
                      Intent i=new Intent(MainActivity.this,mywap.class);
                      i.putExtra("isScan", false);
                      startActivity(i);
                      Log.w("main", "1");
					  
					  
				  }
				  else if (myObject2.get("checkresult").toString().equals("0"))
				  
				  {

					  
					  logininfoEditText.setText(myObject2.getString("reason"));
				  }
				  
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
                logininfoEditText.setText("验证服务器异常");
			}
			
		}
		
		}
		
	

	
}
