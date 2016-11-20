package ict.mgame.gamemove.model;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

public class Person {
	private Bitmap bitmap;	// the actual bitmap
	public int x;			// the X coordinate
	public int y;			// the Y coordinate
	public Rect frameRect,posRect;
	int currentFrame;
	float elapsedTime, timeStep;
	public int life,score;
	
	public Person(Bitmap bitmap,int x,int y) {
		score=0;
		life=5;
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		timeStep = 1000/25;
		frameRect = new Rect(0, 0, bitmap.getWidth() / 4, bitmap.getHeight()/ 4);
		posRect=new Rect(x*4,y*4,x*4+x,y*4+y);
	
	}
	
	
	

	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap,frameRect,posRect,null);
	}

	/**
	 * Method which updates the droid's internal state every tick
	 */
	public void update() {
		
	}
	public void setUp(){
		frameRect = new Rect(0, bitmap.getHeight()/ 4*3, bitmap.getWidth() / 4, bitmap.getHeight());
	}
	public void setDown(){
		frameRect = new Rect(0, 0, bitmap.getWidth() / 4, bitmap.getHeight()/ 4);
	}
	public void setLeft(){
		frameRect = new Rect(0, bitmap.getHeight()/ 4, bitmap.getWidth() / 4, bitmap.getHeight()/ 4*2);
	}
	public void setRight(){
		frameRect = new Rect(0, bitmap.getHeight()/ 4*2, bitmap.getWidth() / 4, bitmap.getHeight()/ 4*3);
	}






}
