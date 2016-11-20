package ict.mgame.gamemove;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import ict.mgame.gamemove.*;
import ict.mgame.gamemove.model.*;
import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MultPlayer extends SurfaceView implements
SurfaceHolder.Callback  {
	Socket socket = null;
	DataOutputStream writer = null;
	DataInputStream reader = null;
	ReceivingThread rt;
	String msg = "";
	
	Activity host;
	public MediaPlayer angry,bgm,dead;
	MultPlayerThread thread;
	Person person;
	Ball ball[];
	Pikachu pikachu[];
	int round;
	static int score = 0;
	int NoOfBall=4;
	Paint tPaint = null;
	boolean gameStart ;
	connection con;
	float x1 = 0;
	float x2 = 0;
	float y1 = 0;
	float y2 = 0;
	int currentBall;
	boolean collision;
	String name;
	public MultPlayer(Context context,connection con) {
		super(context);
		// TODO Auto-generated constructor stub
		getHolder().addCallback(this);
		ball=new Ball[4];
		pikachu = new Pikachu[4];
		host =(Activity) context;
		thread = new MultPlayerThread(getHolder(), this);
		setFocusable(true);
		this.con= con;
		name= con.Name;
		collision=false;
		angry = MediaPlayer.create(context, R.raw.angry);
		bgm = MediaPlayer.create(context, R.raw.bgm);
		dead = MediaPlayer.create(context, R.raw.dead);
		bgm.setLooping(true);
		bgm.start();

	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// at this point the surface is created and
		// we can safely start the game loop
		connect(con.ip, 8888 ,con.Name);
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		try { if(writer!=null) writer.close(); } catch (IOException e) {}
		try { if(reader!=null) reader.close(); } catch (IOException e) {}
		try { if(socket!=null) socket.close(); } catch (IOException e) {}
		bgm.stop();
		thread.setRunning(false);
	}
	public void init(){
		for(int i=0;i<NoOfBall;i++){
			ball[i] = new Ball(BitmapFactory.decodeResource(getResources(), R.drawable.ball),getWidth()/8,getHeight()/10);
			pikachu[i] = new Pikachu(BitmapFactory.decodeResource(getResources(), R.drawable.pika),getWidth()/8,getHeight()/10);
		}
		currentBall=0;
		gameStart = false;
		person= new Person(BitmapFactory.decodeResource(getResources(), R.drawable.person), getWidth()/8, getHeight()/10);
	}
	public void update() {
		
		if(msg.toString().equals("GameStart")){
			gameStart=true;
		}
		if(!collision){
			if(gameStart==true){
				if(name.equals("player1")){
					String [] msgRe = msg.split(" ");
					if(msgRe[0].equals("ballPos")){
						angry.start();
						ball[currentBall%4].Initialize(Integer.parseInt(msgRe[1]));
						currentBall++;
						
						score=Integer.parseInt(msgRe[2]);
						if(score>20&&score<39){
							ball[currentBall%4].speed=4;
						}else if(score>40){
							ball[currentBall%4].speed=5;
						}
						msg="";
					}
					try{
						for (int i=0;i<4;i++)
				        {
				            if (ball[i].posRect.intersects(person.posRect.left,person.posRect.top,person.posRect.right,person.posRect.bottom)) 
				            {
				            	collision=true;
				            	dead.start();
								send("collision");
				            }
				        }
	
					}catch(NullPointerException e){}
				}else{
					String [] msgRe = msg.split(" ");
					if(msgRe[0].equals("UpPOS")){
						person.posRect.left=(int)(Double.parseDouble(msgRe[1])*getWidth());
						person.posRect.top=(int)(Double.parseDouble(msgRe[2])*getHeight());
						person.posRect.right=(int)(Double.parseDouble(msgRe[3])*getWidth());
						person.posRect.bottom=(int)(Double.parseDouble(msgRe[4])*getHeight());
						person.setUp();
					}else if (msgRe[0].equals("DownPOS")){
						person.posRect.left=(int)(Double.parseDouble(msgRe[1])*getWidth());
						person.posRect.top=(int)(Double.parseDouble(msgRe[2])*getHeight());
						person.posRect.right=(int)(Double.parseDouble(msgRe[3])*getWidth());
						person.posRect.bottom=(int)(Double.parseDouble(msgRe[4])*getHeight());
						person.setDown();
					}else if (msgRe[0].equals("LeftPOS")){
						person.posRect.left=(int)(Double.parseDouble(msgRe[1])*getWidth());
						person.posRect.top=(int)(Double.parseDouble(msgRe[2])*getHeight());
						person.posRect.right=(int)(Double.parseDouble(msgRe[3])*getWidth());
						person.posRect.bottom=(int)(Double.parseDouble(msgRe[4])*getHeight());
						person.setLeft();
					}else if (msgRe[0].equals("RightPOS")){
						person.posRect.left=(int)(Double.parseDouble(msgRe[1])*getWidth());
						person.posRect.top=(int)(Double.parseDouble(msgRe[2])*getHeight());
						person.posRect.right=(int)(Double.parseDouble(msgRe[3])*getWidth());
						person.posRect.bottom=(int)(Double.parseDouble(msgRe[4])*getHeight());
						person.setRight();
					}else if(msgRe[0].equals("collision")){
						dead.start();
						collision=true;
					}
					msg="";
						
	
				}
				for(int i=0;i<NoOfBall;i++){
					try {
						ball[i].update();
					}catch(NullPointerException e){}
				}
			}

			
		}
	}
	public void render(Canvas canvas) {
		canvas.drawColor(Color.LTGRAY);
		tPaint=new Paint();
		tPaint.setTextSize(20);
		tPaint.setTextAlign(Align.CENTER);
		FontMetrics fontMetrics = tPaint.getFontMetrics(); 
		float fontWidth = fontMetrics.ascent - fontMetrics.top; 
		if(gameStart==true){
			if(!collision){
				canvas.drawText("Score = "+score, getWidth()*2/8, getWidth()/10, tPaint);
				person.draw(canvas);
				
				for(int i=0;i<NoOfBall;i++){
					try {
						ball[i].draw(canvas);
					}catch(NullPointerException e){}
					
				}
				for (int i = 0; i < 7; i++) {
					canvas.drawLine(getWidth()/8, (i+2)*getHeight()/10, getWidth()*7/8, (i+2)*getHeight()/10, tPaint);
					canvas.drawLine((i+1)*getWidth()/8, getHeight()*2/10, (i+1)*getWidth()/8, getHeight()*8/10, tPaint);
				}
			}else{
				canvas.drawText("End!!! The Score is "+score, getWidth()/2-fontWidth, getHeight()/2, tPaint);
			}

		}else{
			canvas.drawText("Waiting other player.....", getWidth()/2-fontWidth, getHeight()/2, tPaint);
		}
	}


	private class ReceivingThread extends Thread {
		public void run() {
			while(true) {
				try {
					msg = reader.readUTF();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}
	public void send(String message) {
		try {
			writer.writeUTF(message);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void connect(String ip, int port, String name) {

		try {
			socket = new Socket(ip, port);
			writer = new DataOutputStream(socket.getOutputStream());
			reader = new DataInputStream(socket.getInputStream());
			writer.writeUTF(name);
			rt = new ReceivingThread();
			rt.start();
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	public boolean onTouchEvent(MotionEvent event) {
		if(!collision){
			if(name.equals("player1")){
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
							send("UpPOS "+ (double)person.posRect.left/getWidth() +" "+(double)person.posRect.top/getHeight()+" "+(double)person.posRect.right/getWidth()+" "+(double)person.posRect.bottom/getHeight());
						}
					} else if(y2 - y1 > 30) {
						if((person.posRect.bottom+getHeight()/10)<=getHeight()*8/10){
							person.posRect.top+=getHeight()/10;
							person.posRect.bottom+=getHeight()/10;
							person.setDown();
							send("DownPOS "+ (double)person.posRect.left/getWidth() +" "+(double)person.posRect.top/getHeight()+" "+(double)person.posRect.right/getWidth()+" "+(double)person.posRect.bottom/getHeight());
						}
						//Toast.makeText(MainActivity.this, "DOWN", Toast.LENGTH_SHORT).show();
					} else if(x1 - x2 > 30) {
						if((person.posRect.left-getWidth()/8)>=getWidth()/8){
							person.posRect.left-=getWidth()/8;
							person.posRect.right-=getWidth()/8;
							person.setLeft();
							send("LeftPOS "+ (double)person.posRect.left/getWidth() +" "+(double)person.posRect.top/getHeight()+" "+(double)person.posRect.right/getWidth()+" "+(double)person.posRect.bottom/getHeight());					}
						//Toast.makeText(MainActivity.this, "LEFT", Toast.LENGTH_SHORT).show();
					} else if(x2 - x1 > 30) {
						if((person.posRect.left+getWidth()/8)<=getWidth()*6/8){
							person.posRect.left+=getWidth()/8;
							person.posRect.right+=getWidth()/8;
							person.setRight();
							send("RightPOS "+ (double)person.posRect.left/getWidth() +" "+(double)person.posRect.top/getHeight()+" "+(double)person.posRect.right/getWidth()+" "+(double)person.posRect.bottom/getHeight());
						}
						//Toast.makeText(MainActivity.this, "RIGHT", Toast.LENGTH_SHORT).show();
					}
				}
			}else{
				if(event.getAction() == MotionEvent.ACTION_DOWN) {
					if(event.getX()>0&&event.getX()<getWidth()/8){//left
						for(int i =0 ;i<6;i++){
							if(event.getY()>getHeight()*(i+2)/10&&event.getY()<getHeight()*(i+3)/10){
								score++;
								ball[currentBall%4].Initialize(i+6);
								if(score>20&&score<39){
									ball[currentBall%4].speed=4;
								}else if(score>40){
									ball[currentBall%4].speed=5;
								}
								send("ballPos "+ball[currentBall%4].R+" "+score);
								currentBall++;
								angry.start();
							}
						}
					}else if(event.getX()>getWidth()*7/8&&event.getX()<getWidth()){//right
						for(int i =0 ;i<6;i++){
							if(event.getY()>getHeight()*(i+2)/10&&event.getY()<getHeight()*(i+3)/10){
								score++;
								ball[currentBall%4].Initialize(i+12);
								if(score>20&&score<39){
									ball[currentBall%4].speed=4;
								}else if(score>40){
									ball[currentBall%4].speed=5;
								}
								send("ballPos "+ball[currentBall%4].R+" "+score);
								currentBall++;
								angry.start();
							}
						}
					}
					else if(event.getY()>getHeight()/10&&event.getY()<getHeight()*2/10){//up
						for(int i =0 ;i<6;i++){
							if(event.getX()>getWidth()*(i+1)/8&&event.getX()<getWidth()*(i+2)/8){
								score++;
								
								ball[currentBall%4].Initialize(i);
								if(score>20&&score<39){
									ball[currentBall%4].speed=4;
								}else if(score>40){
									ball[currentBall%4].speed=5;
								}
								send("ballPos "+ball[currentBall%4].R+" "+score);
								currentBall++;
								angry.start();
							}
						}
					}else if(event.getY()>getHeight()*8/10&&event.getY()<getHeight()*9/10){//down
						for(int i =0 ;i<6;i++){
							if(event.getX()>getWidth()*(i+1)/8&&event.getX()<getWidth()*(i+2)/8){
								score++;
								ball[currentBall%4].Initialize(i+18);
								if(score>20&&score<39){
									ball[currentBall%4].speed=4;
								}else if(score>40){
									ball[currentBall%4].speed=5;
								}
								send("ballPos "+ball[currentBall%4].R+" "+score);
								currentBall++;
								angry.start();
							}
						}
					}
				}
			}
		}
		return true;
	}
}
