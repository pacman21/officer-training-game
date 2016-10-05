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
import android.util.DisplayMetrics;
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


public class visualtest  extends E3Activity {

	private final static int WIDTH  = 320;
	private final static int HEIGHT = 480;
	
		private TextSprite label;
		private TextSprite label2;
		private Texture fieldTexture;
		private Sprite field;
		private Texture gunshotTexture;
		private Sprite gunshot;
		private Texture goodTexture;
		private Texture badTexture;
		private Sprite bad;
		private Sprite good;
		private TextSprite label3;
	
		int bullets = 12;
		int points = 0;
		boolean timeon = false;
		
		
		@Override
		public E3Engine onLoadEngine() {
			points = 0;
			bullets = 12;
			userinfo.wonorlost = false;
			E3Engine engine = new E3Engine(this, WIDTH, HEIGHT);
			engine.requestFullScreen();
			engine.requestLandscape();
			return engine;
		}

		@Override
		public E3Scene onLoadScene() {
			points = 0;
			bullets = 12;
			final E3Scene scene = new E3Scene();
			scene.addEventListener(this);
			
			//bar = new Shape(getWidth() - 90, 0, 10, getHeight());
			//bar = new Shape(0, getHeight() - 50, getWidth(), 10);
			//bar.setColor(1, 0, 0);
			//scene.getTopLayer().add(bar);
			//ul = upperleft of the field
			
			int t1x = (getWidth()  - fieldTexture.getWidth())  / 2;
			int t1y = (getHeight() - fieldTexture.getHeight()) / 2;
			field = new Sprite(fieldTexture, t1x, t1y);
			
			scene.getTopLayer().add(field);
			

			int ulx = (getWidth()  - fieldTexture.getWidth())  / 2;
			int uly = (getHeight() - fieldTexture.getHeight()) / 2;
			/*
			int umx = (getWidth()  - fieldTexture.getWidth())  / 2 + 100;
			int umy = (getHeight() - fieldTexture.getHeight()) / 2;
			int urx = (getWidth()  - fieldTexture.getWidth())  / 2 + 200;
			int ury = (getHeight() - fieldTexture.getHeight()) / 2;
			
			int mlx = (getWidth()  - fieldTexture.getWidth())  / 2;
			int mly = (getHeight() - fieldTexture.getHeight()) / 2 + 100;
			int mmx = (getWidth()  - fieldTexture.getWidth())  / 2 + 100;
			int mmy = (getHeight() - fieldTexture.getHeight()) / 2 + 100;
			int mrx = (getWidth()  - fieldTexture.getWidth())  / 2 + 200;
			int mry = (getHeight() - fieldTexture.getHeight()) / 2 + 100;
			
			int blx = (getWidth()  - fieldTexture.getWidth())  / 2;
			int bly = (getHeight() - fieldTexture.getHeight()) / 2 + 200;
			int bmx = (getWidth()  - fieldTexture.getWidth())  / 2 + 100;
			int bmy = (getHeight() - fieldTexture.getHeight()) / 2 + 200;
			int brx = (getWidth()  - fieldTexture.getWidth())  / 2 + 200;
			int bry = (getHeight() - fieldTexture.getHeight()) / 2 + 200;
			*/
			good = new Sprite(goodTexture, ulx, uly);
			
			bad = new Sprite(badTexture, ulx, uly);
			
			scene.getTopLayer().add(good);
			scene.getTopLayer().add(bad);
			
			good.move(1000, 1000);
			bad.move(1000, 1000);
			
			label.move(25, 30);
			label2.move(25, 55);
			label3.move(165, 55);
			scene.getTopLayer().add(label);
			scene.getTopLayer().add(label2);
			scene.getTopLayer().add(label3);
			label.setTextSize(20);
			label2.setText("Bullets Left: " + bullets);
			label.setText("Shoot the Green Target to Gain Points. X Target Loses Points. 200 Points to Advance!");
			label3.setText("Points: " + points + "   ");
			
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
					whereToMove();
				}
				}, 100, (700 - (userinfo.visuallevel * 50)));
			
			return scene;
		}

		@Override
		public void onLoadResources() {
			gunshotTexture = new AssetTexture("gunshot.png", this);
			fieldTexture = new AssetTexture("field.png", this);
			badTexture = new AssetTexture("badvis.png", this);
			goodTexture = new AssetTexture("goodvis.png", this);
			label = new TextSprite("Hello World", 20, Color.WHITE, Color.TRANSPARENT, Typeface.DEFAULT_BOLD, this);
			label2 = new TextSprite("Bullets Left:         Points:              ", 18, Color.WHITE, Color.TRANSPARENT, Typeface.DEFAULT_BOLD, this);
			label3 = new TextSprite("Points:              ", 20, Color.WHITE, Color.TRANSPARENT, Typeface.DEFAULT_BOLD, this);
		}

		@Override
		public boolean onSceneTouchEvent(final E3Scene scene, MotionEvent motionEvent) {
			
			if (bullets > 0 && motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
				int x = getTouchEventX(scene, motionEvent);
				int y = getTouchEventY(scene, motionEvent);
				userinfo.wonorlost = false;
				if(good.contains(x, y))
					points = points + 20;
				if(bad.contains(x, y))
					points = points - 20;
				
				bullets = bullets - 1;
				gunshot = new Sprite(gunshotTexture, x, y);
				scene.getTopLayer().add(gunshot);
				
				label2.setText("Bullets Left: " + bullets);
				label3.setText("Points: " + points);
			}
			
			if(bullets <= 0 && points >= 200)
			{
				//bullets = 5;
				Toast.makeText(this, "Passed!!", Toast.LENGTH_SHORT).show();
				userinfo.wonorlost = true;
				userinfo.visuallevel = userinfo.visuallevel + 1;
				points = 0;
				bullets = 5;
				Intent myIntent = new Intent(visualtest.this, wonorlostscreen.class);
				visualtest.this.startActivity(myIntent);
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
			else if(bullets <= 0 && points < 200)
			{
				//bullets = 5;
				Toast.makeText(this, "Failed!!", Toast.LENGTH_SHORT).show();
				userinfo.wonorlost = false;
				Intent myIntent = new Intent(visualtest.this, wonorlostscreen.class);
				visualtest.this.startActivity(myIntent);
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
		
		public void whereToMove()
		{
			
			int ulx = (getWidth()  - fieldTexture.getWidth())  / 2;
			int uly = (getHeight() - fieldTexture.getHeight()) / 2;
			int umx = (getWidth()  - fieldTexture.getWidth())  / 2 + 100;
			int umy = (getHeight() - fieldTexture.getHeight()) / 2;
			int urx = (getWidth()  - fieldTexture.getWidth())  / 2 + 200;
			int ury = (getHeight() - fieldTexture.getHeight()) / 2;
			
			int mlx = (getWidth()  - fieldTexture.getWidth())  / 2;
			int mly = (getHeight() - fieldTexture.getHeight()) / 2 + 100;
			int mmx = (getWidth()  - fieldTexture.getWidth())  / 2 + 100;
			int mmy = (getHeight() - fieldTexture.getHeight()) / 2 + 100;
			int mrx = (getWidth()  - fieldTexture.getWidth())  / 2 + 200;
			int mry = (getHeight() - fieldTexture.getHeight()) / 2 + 100;
			
			int blx = (getWidth()  - fieldTexture.getWidth())  / 2;
			int bly = (getHeight() - fieldTexture.getHeight()) / 2 + 200;
			int bmx = (getWidth()  - fieldTexture.getWidth())  / 2 + 100;
			int bmy = (getHeight() - fieldTexture.getHeight()) / 2 + 200;
			int brx = (getWidth()  - fieldTexture.getWidth())  / 2 + 200;
			int bry = (getHeight() - fieldTexture.getHeight()) / 2 + 200;
			
			Random rgen = new Random();
			Random rgen2 = new Random();
			
			int good1 = rgen.nextInt(8);
			
			if(good1 == 0)
				good.move(urx, ury);
			else if(good1 == 1)
				good.move(umx, umy);
			else if(good1 == 2)
				good.move(ulx, uly);
			else if(good1 == 3)
				good.move(mmx, mmy);
			else if(good1 == 4)
				good.move(mlx, mly);
			else if(good1 == 5)
				good.move(mrx, mry);
			else if(good1 == 6)
				good.move(bmx, bmy);
			else if(good1 == 7)
				good.move(blx, bly);
			else if(good1 == 8)
				good.move(brx, bry);
			
			int bad1 = 0;
			do
			{
				bad1 = rgen2.nextInt(8);
			}
			while(bad1 == good1);
			
			if(bad1 == 0)
				bad.move(urx, ury);
			else if(bad1 == 1)
				bad.move(umx, umy);
			else if(bad1 == 2)
				bad.move(ulx, uly);
			else if(bad1 == 3)
				bad.move(mmx, mmy);
			else if(bad1 == 4)
				bad.move(mlx, mly);
			else if(bad1 == 5)
				bad.move(mrx, mry);
			else if(bad1 == 6)
				bad.move(bmx, bmy);
			else if(bad1 == 7)
				bad.move(blx, bly);
			else if(bad1 == 8)
				bad.move(brx, bry);
			
		}
	}