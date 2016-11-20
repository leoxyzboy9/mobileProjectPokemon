package ict.mgame.gamemove;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class MultiMainActivity extends Activity {
	private static final String TAG = MultiMainActivity.class.getSimpleName();
	private MultPlayer p;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // set our MultPlayer as the View
       // setContentView(p = new MultPlayer(this));
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
