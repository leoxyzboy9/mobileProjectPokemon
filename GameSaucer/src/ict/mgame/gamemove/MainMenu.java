package ict.mgame.gamemove;


import android.app.Activity;
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


public class MainMenu extends Activity implements OnClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.main);
    	// Set up click listeners for all the buttons
    	View newButton = this.findViewById(R.id.new_button);
    	newButton.setOnClickListener(this);
    	View multiButton = this.findViewById(R.id.multi_button);
    	multiButton.setOnClickListener(this);
    	View aboutButton = this.findViewById(R.id.about_button);
    	aboutButton.setOnClickListener(this);
    	View exitButton = this.findViewById(R.id.exit_button);
    	exitButton.setOnClickListener(this);
    }

    public void onClick(View v) {
		switch (v.getId()) {
			case R.id.about_button:
				about();
			break;
			case R.id.multi_button:
				startMultiPlayerGame();
			break;
			case R.id.new_button:
				startGame();
			break;

			case R.id.exit_button:
				finish();
			break;

			
		// More buttons go here (if any) ...
		}
	}
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//    	super.onCreateOptionsMenu(menu);
//    	MenuInflater inflater = getMenuInflater();
//    	inflater.inflate(R.menu.menu, menu);
//    	return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//    	switch (item.getItemId()) {
//    		case R.id.settings:
//    			startActivity(new Intent(this, Prefs.class));
//    			return true;
//    		// More items go here (if any) ...
//    	}
//    	return false;
//    }

    private void startMultiPlayerGame() {
    	Intent intent = new Intent(this, connection.class);
    	startActivity(intent);
    }
    private void startGame() {
    	Intent intent = new Intent(this, MainActivity.class);
    	startActivity(intent);
    }
    private void about(){
    	Intent intent = new Intent(this, About.class);
    	startActivity(intent);
    }

    
}
