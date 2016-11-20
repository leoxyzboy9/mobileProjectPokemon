package ict.mgame.gamemove;

import ict.mgame.gamemove.MainThread;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class MultPlayerThread extends Thread {
	
	private static final String TAG = MainThread.class.getSimpleName();

	// Surface holder that can access the physical surface
	private SurfaceHolder surfaceHolder;
	// The actual view that handles inputs
	// and draws to the surface
	private MultPlayer gamePanel;

	// flag to hold game state 
	private boolean running;
	public void setRunning(boolean running) {
		this.running = running;
	}
	public MultPlayerThread(SurfaceHolder surfaceHolder, MultPlayer gamePanel) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
	}

	@Override
	public void run() {
		Canvas canvas;
		Log.d(TAG, "Starting game loop");
		gamePanel.init();
		while (running) {
			canvas = null;
			// try locking the canvas for exclusive pixel editing
			// in the surface
			
			try {
				canvas = this.surfaceHolder.lockCanvas();
				synchronized (surfaceHolder) {
					// update game state 
						this.gamePanel.update();
						// render state to the screen
						// draws the canvas on the panel
						this.gamePanel.render(canvas);	
				}
			} finally {
				// in case of an exception the surface is not left in 
				// an inconsistent state
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}	// end finally
		}
		lose();
	}
	public void lose(){
		synchronized (surfaceHolder) {
            //quit to mainmenu
			gamePanel.host.finish();
        }        
	}
}
