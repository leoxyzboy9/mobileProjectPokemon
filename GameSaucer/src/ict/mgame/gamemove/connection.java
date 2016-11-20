package ict.mgame.gamemove;

import ict.mgame.gamemove.R;

import java.io.*;
import java.net.Socket;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.*;

public class connection extends Activity implements OnClickListener{
	Button btnConnect;
	public EditText etIp,etName;
	
	public String ip,Name;
	int port =8888;
	private MultPlayer p;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.connection);
		etIp = (EditText)findViewById(R.id.etIp);
		etName= (EditText)findViewById(R.id.etName);
		btnConnect = (Button)findViewById(R.id.btnConnect);
		btnConnect.setOnClickListener(this);
		etIp.setText("10.0.2.2");
		etName.setText("player1");

	}
	public void onStart() {
		super.onStart();

	}
	


	
	
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(arg0.getId() == R.id.btnConnect) {
			ip=etIp.getText().toString();
			Name=etName.getText().toString();
			//connect(etIp.getText().toString(), port ,etName.getText().toString());
//			requestWindowFeature(Window.FEATURE_NO_TITLE);
//	        // making it full screen
//	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        // set our MultPlayer as the View
	        setContentView(p = new MultPlayer(this,this));

		}
		
	}
	


}
