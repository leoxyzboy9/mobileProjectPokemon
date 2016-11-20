package ict.mgame.gamemove;
import android.widget.VideoView;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
public class About extends Activity{
	VideoView vv,videoStreaning;
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.about);
		vv = (VideoView)findViewById(R.id.video);
		vv.setVideoURI(
		Uri.parse("android.resource://" +
		getPackageName()+"/"+R.raw.video));
		vv.start();
		
		videoStreaning= (VideoView)findViewById(R.id.videoStreaning);
		videoStreaning.setVideoURI(
				Uri.parse("http://10.0.2.2:80/video.3gp"));
		videoStreaning.start();
				
    }


}
