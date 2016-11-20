package ict.mgame.gamemove.model;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Pikachu {
	private Bitmap bitmap;	// the actual bitmap
	private int x;			// the X coordinate
	private int y;			// the Y coordinate
	public Rect frameRect,posRect;
	int currentFrame;
	float elapsedTime, timeStep;
	int i = 0;
	public int R;
	Random r=new Random();
	
	
	public Pikachu(Bitmap bitmap,int x,int y) {
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		timeStep = 1000/25;
		frameRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		posRect=new Rect(x*4,y*4,x*4+x,y*4+y);
	
	}
	
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap,frameRect,posRect,null);
	}
	
	
	public void Initialize(int r){
		this.R=r;
		if(R>=0&&R<=5){
			posRect= new Rect((R+1)*x,y,(R+1)*x+ x,y+ y);
		}else if(R>=6&&R<=11){
			posRect= new Rect(0,(R%6+2)*y,x,(R%6+2)*y+y);
		}else if(R>=12&&R<=17){
			posRect= new Rect(x*7,(R%6+2)*y,x*8,(R%6+2)*y+y);
		}else if(R>=18&&R<=23){
			posRect= new Rect((R%6+1)*x,y*8,(R%6+1)*x+x,y*9);
		}
	}
	
	public void update() {
		if(i==1){
			frameRect.offset(1000, 1000);
			i=0;
		}else{
			frameRect = new Rect(0,0, bitmap.getWidth(), bitmap.getHeight());
			i=1;
		}
	}
}
