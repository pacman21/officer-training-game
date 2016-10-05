package officer.com;

import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.khronos.opengles.GL10;

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
import com.e3roid.util.Debug;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class accuracytest  extends E3Activity {

	private final static int WIDTH  = 320;
	private final static int HEIGHT = 480;
	
	private Shape bar;
	private TextSprite label;
	private TextSprite label2;
	private Texture target1texture;
	private Texture target2texture;
	private Texture target3texture;
	private Texture target4texture;
	private Texture target5texture;
	private Texture gunshotTexture;
	private Sprite target1;
	private Sprite target2;
	private Sprite target3;
	private Sprite target4;
	private Sprite target5;
	private Sprite gunshot;
	private Texture bullettexture;
	private Sprite bullet;

	int bullets = 5;
	int points = 0;
	boolean timeon = false;
	
	@Override
	public E3Engine onLoadEngine() {
		userinfo.wonorlost = false;
		E3Engine engine = new E3Engine(this, WIDTH, HEIGHT);
		engine.requestFullScreen();
		engine.requestLandscape();
		return engine;
	}

	@Override
	public E3Scene onLoadScene() {
		points = 0;
		bullets = 5;
		final E3Scene scene = new E3Scene();
		scene.addEventListener(this);
		
		//bar = new Shape(getWidth() - 90, 0, 10, getHeight());
		//bar = new Shape(0, getHeight() - 50, getWidth(), 10);
		//bar.setColor(1, 0, 0);
		//scene.getTopLayer().add(bar);
		
		int t1x = (getWidth()  - target1texture.getWidth())  / 2;
		int t1y = (getHeight() - target1texture.getHeight()) / 2;
		target1 = new Sprite(target1texture, t1x, t1y);
		int t2x = (getWidth()  - target2texture.getWidth())  / 2;
		int t2y = (getHeight() - target2texture.getHeight()) / 2;
		target2 = new Sprite(target2texture, t2x, t2y);
		int t3x = (getWidth()  - target3texture.getWidth())  / 2;
		int t3y = (getHeight() - target3texture.getHeight()) / 2;
		target3 = new Sprite(target3texture, t3x, t3y);
		int t4x = (getWidth()  - target4texture.getWidth())  / 2;
		int t4y = (getHeight() - target4texture.getHeight()) / 2;
		target4 = new Sprite(target4texture, t4x, t4y);
		int t5x = (getWidth()  - target5texture.getWidth())  / 2;
		int t5y = (getHeight() - target5texture.getHeight()) / 2;
		target5 = new Sprite(target5texture, t5x, t5y);
		bullet = new Sprite(bullettexture, 50,55);
		scene.getTopLayer().add(target1);
		scene.getTopLayer().add(target2);
		scene.getTopLayer().add(target3);
		scene.getTopLayer().add(target4);
		scene.getTopLayer().add(target5);

		label.move(50, 30);
		label2.move(50, 55);
		scene.getTopLayer().add(label);
		scene.getTopLayer().add(label2);
		label.setTextSize(20);
		label2.setText("Bullets Left: " + bullets + "    Points: " + points + "     ");
		label.setText("Shoot the Target to Gain Points. Get 210 Points to Advance!");
		
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
				if(target1.getRealX() < getWidth())
				{
					target1.move(target1.getRealX() + 10, target1.getRawY());
					target2.move(target2.getRealX() + 10, target2.getRawY());
					target3.move(target3.getRealX() + 10, target3.getRawY());
					target4.move(target4.getRealX() + 10, target4.getRawY());
					target5.move(target5.getRealX() + 10, target5.getRawY());
				}
				else
				{
					target1.move((getWidth() - target1.getWidth())/2 - 550, target1.getRawY());
					target2.move((getWidth() - target2.getWidth())/2 - 550, target2.getRawY());
					target3.move((getWidth() - target3.getWidth())/2 - 550, target3.getRawY());
					target4.move((getWidth() - target4.getWidth())/2 - 550, target4.getRawY());
					target5.move((getWidth() - target5.getWidth())/2 - 550, target5.getRawY());
				}
			}

			}, 100, (15 - (userinfo.accuracylevel * 2)));
		
		return scene;
	}

	@Override
	public void onLoadResources() {
		gunshotTexture = new AssetTexture("gunshot.png", this);
		target1texture = new AssetTexture("target1.png", this);
		target2texture = new AssetTexture("target2.png", this);
		target3texture = new AssetTexture("target3.png", this);
		target4texture = new AssetTexture("target4.png", this);
		target5texture = new AssetTexture("target5.png", this);
		bullettexture = new AssetTexture("bullet.png", this);
		label = new TextSprite("Hello World", 20, Color.WHITE, Color.TRANSPARENT, Typeface.DEFAULT_BOLD, this);
		label2 = new TextSprite("Time Left:", 20, Color.WHITE, Color.TRANSPARENT, Typeface.DEFAULT_BOLD, this);
	}

	@Override
	public boolean onSceneTouchEvent(final E3Scene scene, MotionEvent motionEvent) {
		
		if (bullets > 0 && motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
			int x = getTouchEventX(scene, motionEvent);
			int y = getTouchEventY(scene, motionEvent);
			
			
			bullets = bullets - 1;
			gunshot = new Sprite(gunshotTexture, x, y);
			scene.getTopLayer().add(gunshot);
			
			if(target5.containsX(x) && target5.containsY(y) )
				points = points + 50;
			else if(target4.containsX(x) && target4.containsY(y) )
				points = points + 40;
			else if(target3.containsX(x) && target3.containsY(y) )
				points = points + 30;
			else if(target2.containsX(x) && target2.containsY(y) )
				points = points + 20;
			else if(target1.containsX(x) && target1.containsY(y) )
				points = points + 10;
			
			label2.setText("Bullets Left: " + bullets + "    Points: " + points);
		}
		
		if(bullets <= 0 && points >= 210)
		{
			//bullets = 5;
			Toast.makeText(this, "Passed!!", Toast.LENGTH_SHORT).show();
			userinfo.wonorlost = true;
			userinfo.accuracylevel = userinfo.accuracylevel + 1;
			points = 0;
			bullets = 5;
			Intent myIntent = new Intent(accuracytest.this, wonorlostscreen.class);
			accuracytest.this.startActivity(myIntent);
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
		else if(bullets <= 0 && points < 210)
		{
			//bullets = 5;
			Toast.makeText(this, "Failed!!", Toast.LENGTH_SHORT).show();
			userinfo.wonorlost = false;
			Intent myIntent = new Intent(accuracytest.this, wonorlostscreen.class);
			accuracytest.this.startActivity(myIntent);
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
