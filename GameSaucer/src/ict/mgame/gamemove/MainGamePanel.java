/**
 * 
 */
package ict.mgame.gamemove;

import java.util.Random;

import ict.mgame.gamemove.model.Ball;
import ict.mgame.gamemove.model.Person;
import ict.mgame.gamemove.model.Pikachu;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * This is the main surface that handles the ontouch events and draws
 * the image to the screen.
 */
public class MainGamePanel extends SurfaceView implements
		SurfaceHolder.Callback {

	private static final String TAG = MainGamePanel.class.getSimpleName();
	public MediaPlayer angry,bgm,dead;
	private MainThread thread;
	private Ball ball[];
	private Person person;
	private Pikachu pikachu[];
	boolean roundStart, roundIn;
	static int score = 0;
	int round;
	int NoOfBall=3;
	String str = "Score = " + score;
	Paint tPaint = null;
	Random r = new Random();
	float x1 = 0;
	float x2 = 0;
	float y1 = 0;
	float y2 = 0;
	int flashcount = 0;
	int afterflash = 1;
	int therandomvalue;
	Activity host;
	public MainGamePanel(Context context) {
		super(context);
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);
		ball=new Ball[10];
		pikachu = new Pikachu[10];
		// create saucer and load bitmap
		angry = MediaPlayer.create(context, R.raw.angry);
		bgm = MediaPlayer.create(context, R.raw.bgm);
		dead = MediaPlayer.create(context, R.raw.dead);
		bgm.setLooping(true);
		bgm.setVolume((float)1, (float)1);
		angry.setVolume((float)0.3, (float)0.3);
		dead.setVolume((float)1,(float) 1);
		bgm.start();
		roundStart=true;
		roundIn=false;
		thread = new MainThread(getHolder(), this);
		host =(Activity) context;
		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}

	public void init(){

		for(int i=0;i<10;i++){
			ball[i] = new Ball(BitmapFactory.decodeResource(getResources(), R.drawable.ball),getWidth()/8,getHeight()/10);
			pikachu[i] = new Pikachu(BitmapFactory.decodeResource(getResources(), R.drawable.pika),getWidth()/8,getHeight()/10);
		}
		person= new Person(BitmapFactory.decodeResource(getResources(), R.drawable.person), getWidth()/8, getHeight()/10);
		// create the game loop thread
		
	}
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}


	public void surfaceCreated(SurfaceHolder holder) {
		// at this point the surface is created and
		// we can safely start the game loop
		thread.setRunning(true);
		thread.start();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "Surface is being destroyed");
		// tell the thread to shut down and wait for it to finish
		// this is a clean shutdown
//		boolean retry = true;
		thread.setRunning(false);
//    	Intent intent = new Intent(host, GameOver.class);
//    	host.startActivity(intent);
//		while (retry) {
//			try {
//				thread.setRunning(false);
//				thread.join();
//				retry = false;
//			} catch (InterruptedException e) {
//				// try again shutting down the thread
//			}
//		}
		Log.d(TAG, "Thread was shut down cleanly");
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			
			x1 = event.getX();
			y1 = event.getY();
		}
		if(event.getAction() == MotionEvent.ACTION_UP) {
			
			x2 = event.getX();
			y2 = event.getY();
			if(y1 - y2 > 30) {
				//Toast.makeText(MainActivity.this, "UP", Toast.LENGTH_SHORT).show();
				if((person.posRect.top-getHeight()/10)+5>=getHeight()*2/10){
					person.posRect.top-=getHeight()/10;
					person.posRect.bottom-=getHeight()/10;
					person.setUp();
				}
			} else if(y2 - y1 > 30) {
				if((person.posRect.bottom+getHeight()/10)<=getHeight()*8/10){
					person.posRect.top+=getHeight()/10;
					person.posRect.bottom+=getHeight()/10;
					person.setDown();
				}
				//Toast.makeText(MainActivity.this, "DOWN", Toast.LENGTH_SHORT).show();
			} else if(x1 - x2 > 30) {
				if((person.posRect.left-getWidth()/8)>=getWidth()/8){
					person.posRect.left-=getWidth()/8;
					person.posRect.right-=getWidth()/8;
					person.setLeft();
				}
				//Toast.makeText(MainActivity.this, "LEFT", Toast.LENGTH_SHORT).show();
			} else if(x2 - x1 > 30) {
				if((person.posRect.left+getWidth()/8)<=getWidth()*6/8){
					person.posRect.left+=getWidth()/8;
					person.posRect.right+=getWidth()/8;
					person.setRight();
				}
				//Toast.makeText(MainActivity.this, "RIGHT", Toast.LENGTH_SHORT).show();
			}
		}
		return true;
	}

	public void render(Canvas canvas) {
		canvas.drawColor(Color.LTGRAY);
		for(int i=0;i<NoOfBall;i++){
			ball[i].draw(canvas);
			pikachu[i].draw(canvas);
		}
		person.draw(canvas);
		tPaint=new Paint();
		canvas.drawText("Score = "+score, 10, 10, tPaint);
		canvas.drawText("Round = "+round, 100, 10, tPaint);
		for (int i = 0; i < 7; i++) {
			canvas.drawLine(getWidth()/8, (i+2)*getHeight()/10, getWidth()*7/8, (i+2)*getHeight()/10, tPaint);
			canvas.drawLine((i+1)*getWidth()/8, getHeight()*2/10, (i+1)*getWidth()/8, getHeight()*8/10, tPaint);
		}


	}

	/**
	 * This is the game update method. It iterates through all the objects
	 * and calls their update method if they have one or calls specific
	 * engine's update method.
	 */
	public void update() {
		flashcount++;
		person.update();
		if(score>=10&&score<=29){
			NoOfBall=4;
			for(int i=0;i<NoOfBall;i++){
				ball[i].speed=4;
			}
		}else if(score>=30&&score<=49){
			NoOfBall=5;
			for(int i=0;i<NoOfBall;i++){
				ball[i].speed=5;
			}
		}else if(score>=30&&score<=49){
			NoOfBall=6;
			for(int i=0;i<NoOfBall;i++){
				ball[i].speed=6;
			}
		}else if(score>=50&&score<=69){
			NoOfBall=7;
			for(int i=0;i<NoOfBall;i++){
				ball[i].speed=7;
			}
		}else if(score>=70&&score<=89){
			NoOfBall=8;
			for(int i=0;i<NoOfBall;i++){
				ball[i].speed=8;
			}
		}else if(score>=90){
			NoOfBall=9;
			for(int i=0;i<NoOfBall;i++){
				ball[i].speed=9;
			}
		}
		int count=0;
		
		if(roundStart==true){
			angry.start();
			round++;
			for(int i=0;i<NoOfBall;i++){
				therandomvalue = r.nextInt(24);
				ball[i].Initialize(therandomvalue);
				if(ball[i].isvisible==true)
					ball[i].setvisible(false);
				pikachu[i].Initialize(therandomvalue);
				
			}

			
			roundIn=true;
			roundStart=false;
		}
		if(flashcount%5==0){
			if(afterflash%11!=0){
				afterflash++;
				for(int i=0;i<NoOfBall;i++){
					pikachu[i].update();
				}
			}
		}
		if(afterflash%11==0){
			if(roundIn==true){
				for(int i=0;i<NoOfBall;i++){
					if(ball[i].isvisible==false)
						ball[i].setvisible(true);
					ball[i].update();
					
					if(ball[i].finish)
						count++;
					if(count==NoOfBall){
						score+=count;
						roundIn=false;
						roundStart=true;
						afterflash = 1;
					}
				}
			}
			
		}
		
		if(CheckCollision ()){
			bgm.stop();
			dead.start();
			surfaceDestroyed(getHolder());
		}
		
	}
	public boolean CheckCollision () {

        for (int i=0;i<NoOfBall;i++)
        {
            if (ball[i].posRect.intersects(person.posRect.left,person.posRect.top,person.posRect.right,person.posRect.bottom)) 
            {
                return true;
            }
        }
        return false;
    }
    
}
