package com.app_1.zxing.simple.demo;

import com.example.app_1.R;


import android.R.bool;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.example.app_1.mywap;


import org.json.JSONObject;

public class Scan_success extends Activity {
	private int sancInfoint = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		
		final boolean iscorrect=false;
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scan_success);
		Intent intent=getIntent();
		String scanInfo=intent.getExtras().getString("scanInfo");
		TextView scanIDView=(TextView) findViewById(R.id.scan_id);
		TextView scanCompanyView=(TextView) findViewById(R.id.scan_company);
		TextView scanCompanyTextView=(TextView) findViewById(R.id.scan_company_text);
		//TextView scanStatusView=(TextView) findViewById(R.id.scan_status);
		TextView scanStatusTextView=(TextView) findViewById(R.id.scan_status_text);
		
		
		try {
			
			if (("").equals(scanInfo))
			{
			 sancInfoint=Integer.parseInt(scanInfo);
		
			//Long sancInfoint=Long.parseLong(scanInfo);
			}
             JSONObject myJsonresult = new JSONObject(scanInfo);
            //scanCompanyTextView.setText(myJsonresult.getString("company"));
            final String num=myJsonresult.getString("ID");
            final String category=myJsonresult.getString("TYPE");
            scanIDView.setText(num);
            //scanCompanyTextView.setText("ID所述类型:");
            scanCompanyView.setText(category);
if(category.equals("Order")||category.equals("Distribution")) {

    //String enIdString=String.valueOf(scanInfo);

    scanIDView.setText(num);
    Button buttontempButton = (Button) findViewById(R.id.scanok_button);
    buttontempButton.setText("点击查看详细信息");
    buttontempButton.setOnClickListener(new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            Intent intenttemp = new Intent(Scan_success.this, mywap.class);
            //String num=myJsonresult.getString("id");
            int tempnum=Integer.parseInt(num);
            intenttemp.putExtra("num", tempnum);
            intenttemp.putExtra("category", category);
            intenttemp.putExtra("isScan", true);
           // intenttemp.putExtra("isScanString","yes");
            startActivity(intenttemp);
        }
    });
}
			
			else
{

    scanIDView.setText(scanInfo);
    Button buttontempButton=(Button) findViewById(R.id.scanok_button);
    buttontempButton.setText("无效的二维码");
    buttontempButton.setOnClickListener(new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            finish();
        }
    });

}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
			//String enIdString=String.valueOf(scanInfo);
			
			scanIDView.setText(scanInfo);
			Button buttontempButton=(Button) findViewById(R.id.scanok_button);
			buttontempButton.setText("无效的二维码");
				buttontempButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					finish();
				}
			});
			
			
		}
		
		
	}

}
