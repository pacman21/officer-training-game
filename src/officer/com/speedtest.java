package officer.com;

import com.e3roid.E3Activity;
import com.e3roid.util.FPSListener;
import android.app.Activity;
import android.os.Bundle;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.microedition.khronos.opengles.GL10;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.e3roid.E3Activity;
import com.e3roid.E3Engine;
import com.e3roid.E3Scene;
import com.e3roid.drawable.Background;
import com.e3roid.drawable.Shape;
import com.e3roid.drawable.Sprite;
import com.e3roid.drawable.sprite.TextSprite;
import com.e3roid.drawable.texture.AssetTexture;
import com.e3roid.drawable.texture.Texture;
import com.e3roid.drawable.texture.TiledTexture;
import com.e3roid.physics.PhysicsShape;
import com.e3roid.physics.PhysicsWorld;
import com.e3roid.util.Debug;
import com.e3roid.util.FPSListener;
import com.e3roid.util.MathUtil;


public class speedtest  extends E3Activity {

	private final static int WIDTH  = 320;
	private final static int HEIGHT = 480;
	
	private Shape bar;
	private Texture robotTexture;
	private Sprite robot;
	private Sprite gunshot;
	private TextSprite label;
	private TextSprite label2;
	private Texture gunshotTexture;
	
	int time = 35;
	boolean timeon = false;
	
	@Override
	public E3Engine onLoadEngine() {
		E3Engine engine = new E3Engine(this, WIDTH, HEIGHT);
		engine.requestFullScreen();
		engine.requestLandscape();
		return engine;
	}

	@Override
	public E3Scene onLoadScene() {
		time = 35 - (userinfo.speedlevel * 3);
		userinfo.wonorlost = false;
		E3Scene scene = new E3Scene();
		scene.addEventListener(this);
		
		int centerX = (getWidth()  - robotTexture.getWidth())  / 2;
		int centerY = (getHeight() - robotTexture.getHeight()) / 2;
		bar = new Shape(getWidth() - 90, 0, 10, getHeight());
		//bar = new Shape(0, getHeight() - 50, getWidth(), 10);
		bar.setColor(1, 0, 0);
		scene.getTopLayer().add(bar);
		
		label.move(50, 30);
		label2.move(50, 55);
		scene.getTopLayer().add(label);
		scene.getTopLayer().add(label2);
		label.setTextSize(20);
		label2.setText("Time Left: " + time);
		label.setText("Touch the Car to Move It Across the Screen Before the Time Runs Out.");
		
		robot = new Sprite(robotTexture, 4, centerY) {
			@Override
			public void onRemove() {
				Debug.d("robot: onRemove()");
				super.onRemove();
			}
			@Override
			public void onDispose() {
				Debug.d("robot: onDispose()");
				super.onDispose();
			}
		};
		scene.getTopLayer().add(robot);
		
		Background background = new Background(
				new TiledTexture("back2.png", getWidth(), getHeight(), this)) {
			@Override
			public void onRemove() {
				Debug.d("background: onRemove()");
				super.onRemove();
			}
			@Override
			public void onDispose() {
				Debug.d("background: onDispose()");
				super.onDispose();
			}
			@Override
			protected void unload(GL10 gl) {
				Debug.d("background: unload()");
				super.unload(gl);
			}
			
		};
		scene.getTopLayer().setBackground(background);
		
		//Toast.makeText(this, "Touch screen to remove the sprite.", Toast.LENGTH_LONG).show();
		
		final Timer timer2 = new Timer();
		timer2.scheduleAtFixedRate(new TimerTask() {
			
			public void run() {
				if(timeon == true && time > 0)
				{
					time = time - 1;
					label2.setText("Time Left: " + time);
				}
			}

			}, 100, 1000);
		
		
		return scene;
	}

	@Override
	public void onLoadResources() {
		robotTexture = new AssetTexture("car.png", this);
		gunshotTexture = new AssetTexture("gunshot.png", this);
		label = new TextSprite("Hello World", 20, Color.WHITE, Color.TRANSPARENT, Typeface.DEFAULT_BOLD, this);
		label2 = new TextSprite("Time Left:", 20, Color.WHITE, Color.TRANSPARENT, Typeface.DEFAULT_BOLD, this);
	}

	@Override
	public void onUnloadResources() {
		robot = null;
		robotTexture = null;
	}

	@Override
	public boolean onSceneTouchEvent(final E3Scene scene, MotionEvent motionEvent) {
		if (robot != null && motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
			int x = getTouchEventX(scene, motionEvent);
			int y = getTouchEventY(scene, motionEvent);
			
			if(timeon == false)
				timeon = true;
			
			gunshot = new Sprite(gunshotTexture, x, y);
			scene.getTopLayer().add(gunshot);
			
			if(robot.containsX(x) && robot.containsY(y) )
				robot.move(robot.getRealX()+8, robot.getRealY());
		
			if(robot.collidesWith(bar) && time > 0)
			{
				timeon = false;
				time = 35;
				Toast.makeText(this, "Passed!!", Toast.LENGTH_SHORT).show();
				userinfo.wonorlost = true;
				userinfo.speedlevel = userinfo.speedlevel + 1;
				robot.move(5, robot.getRealY());
				Intent myIntent = new Intent(speedtest.this, wonorlostscreen.class);
				speedtest.this.startActivity(myIntent);
			}
			else if(time == 0)
			{
				timeon = false;
				Toast.makeText(this, "Failed!!", Toast.LENGTH_SHORT).show();
				userinfo.wonorlost = false;
				Intent myIntent = new Intent(speedtest.this, wonorlostscreen.class);
				speedtest.this.startActivity(myIntent);
			}
			
			// Sprite can't be used anymore after removal from layer.
			// If you just want to hide the sprite (that would be used later on),
			// use sprite's hide() or setVisible() method instead like below.
			// 
			// if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
			//	robot.setVisible(!robot.isVisible());
			//}
			//
			// The texture is also removed when Sprite is removed.
			// If you don't want the Texture removed, set Texture#setReusable(true).
		}
		return false;
	}
}
