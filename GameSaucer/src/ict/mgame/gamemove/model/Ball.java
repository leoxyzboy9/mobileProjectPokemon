package ict.mgame.gamemove.model;

import java.util.Random;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Ball {
	private Bitmap bitmap;	// the actual bitmap
	public Rect frameRect,posRect;
	public int R,w,h;
	public int i=0;
	public int speed=3;
	public boolean finish;
	public boolean isvisible = true;
	public Ball(Bitmap bitmap,int w,int h) {
		this.w=w;
		this.h=h;
		this.bitmap = bitmap;	
		frameRect = new Rect(0,0, bitmap.getWidth() / 12, bitmap.getHeight()/ 4);
		
		
	}
	public void Initialize(int r){
		finish=false;
		R=r;
		if(R>=0&&R<=5){//up
			posRect= new Rect((R+1)*w,h,(R+1)*w+ w,h+ h);
		}else if(R>=6&&R<=11){//left
			posRect= new Rect(0,(R%6+2)*h,w,(R%6+2)*h+h);
		}else if(R>=12&&R<=17){//right
			posRect= new Rect(w*7,(R%6+2)*h,w*8,(R%6+2)*h+h);
		}else if(R>=18&&R<=23){//bottom
			posRect= new Rect((R%6+1)*w,h*8,(R%6+1)*w+w,h*9);
		}
	}
	
	

	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap,frameRect,posRect,null);
		
		
	}
	public void setvisible(boolean visible){
		isvisible = visible;
		if(!visible)
			frameRect.offset(1000, 1000);
		else
			frameRect.offset(-1000, -1000);
	}

	/**
	 * Method which updates the droid's internal state every tick
	 */
	public void update() {
		if(i!=12){
			frameRect.offset(bitmap.getWidth() / 12, 0);
		}else{
			frameRect = new Rect(0,0, bitmap.getWidth() / 12, bitmap.getHeight()/ 4);
			i=0;
		}
		i++;
		if(R>=0&&R<=5){
			posRect.top+=speed;
			posRect.bottom+=speed;
			if(posRect.top>=h*10)
				finish=true;
		}else if(R>=6&&R<=11){
			posRect.left+=speed;
			posRect.right+=speed;
			if(posRect.left>=w*8)
				finish=true;
		}else if(R>=12&&R<=17){
			posRect.left-=speed;
			posRect.right-=speed;
			if(posRect.right<=0)
				finish=true;
		}else if(R>=18&&R<=23){
			posRect.top-=speed;
			posRect.bottom-=speed;
			if(posRect.bottom<=0)
				finish=true;
		}

	}

}
