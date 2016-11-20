package ict.mgame.gamemove;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
	
	private static final String TAG = MainActivity.class.getSimpleName();
	private MainGamePanel p;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // set our MainGamePanel as the View
        setContentView(p = new MainGamePanel(this));
        Log.d(TAG, "View added");
    }

	@Override
	protected void onDestroy() {
		Log.d(TAG, "Destroying...");
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		Log.d(TAG, "Stopping...");
		super.onStop();
	}
    protected void onPause(){
    	super.onPause();
    	super.onDestroy();
    	p.bgm.stop();
		p.dead.start();
		p.surfaceDestroyed(p.getHolder());
    }
    
}