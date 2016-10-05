package officer.com;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Otraininggame extends Activity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        
        AdView adView = (AdView)findViewById(R.id.adView);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        AdRequest adRequest = new AdRequest();
        adView.loadAd(adRequest);
                
        if(userinfo.lives == 0)
        {
        	Toast.makeText(this, "YOU LOSE!!", Toast.LENGTH_SHORT).show();
        	userinfo.lives = 3;
        	userinfo.accuracylevel = 1;
        	userinfo.speedlevel = 1;
        	userinfo.timinglevel = 1;
        	userinfo.visuallevel = 1;
        }
        
        ImageButton speedtest = (ImageButton) findViewById(R.id.imageButton1);
        ImageButton accuracytest = (ImageButton) findViewById(R.id.imageButton2);
        ImageButton visualtest = (ImageButton) findViewById(R.id.imageButton3);
        ImageButton timingtest = (ImageButton) findViewById(R.id.imageButton4);
        TextView currlives = (TextView) findViewById(R.id.TextView01);
        TextView speedlevel = (TextView) findViewById(R.id.TextView02);
        TextView accuracylevel = (TextView) findViewById(R.id.TextView03);
        TextView timinglevel = (TextView) findViewById(R.id.TextView04);
        TextView visuallevel = (TextView) findViewById(R.id.TextView05);
        
        
        if(userinfo.wonorlost == false)
        {
        	userinfo.wonorlost = true;
        	userinfo.lives = userinfo.lives - 1;
        }
        
        speedlevel.setText("Level: " + userinfo.speedlevel);
        accuracylevel.setText("Level: " + userinfo.accuracylevel);
        timinglevel.setText("Level: " + userinfo.timinglevel);
        visuallevel.setText("Level: " + userinfo.visuallevel);
        
        currlives.setText("Lives: " + userinfo.lives);
        
        adView.loadAd(adRequest);
        
        speedtest.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(userinfo.speedlevel <= 6)
				{
					Intent myIntent = new Intent(Otraininggame.this, speedtest.class);
					Otraininggame.this.startActivity(myIntent);
				}
				else
				{
					Toast.makeText(Otraininggame.this, "You Beat This Portion!!", Toast.LENGTH_SHORT).show();
				}
			}});
        
        accuracytest.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(userinfo.accuracylevel <= 6)
				{
					Intent myIntent = new Intent(Otraininggame.this, accuracytest.class);
					Otraininggame.this.startActivity(myIntent);
				}
				else
				{
					Toast.makeText(Otraininggame.this, "You Beat This Portion!!", Toast.LENGTH_SHORT).show();
				}
			}});
        
        timingtest.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(userinfo.timinglevel <= 6)
				{
					Intent myIntent = new Intent(Otraininggame.this, timingtest.class);
					Otraininggame.this.startActivity(myIntent);
				}
				else
				{
					Toast.makeText(Otraininggame.this, "You Beat This Portion!!", Toast.LENGTH_SHORT).show();
				}
			}});
        
        visualtest.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(userinfo.visuallevel <= 6)
				{
					Intent myIntent = new Intent(Otraininggame.this, visualtest.class);
					Otraininggame.this.startActivity(myIntent);
				}
				else
				{
					Toast.makeText(Otraininggame.this, "You Beat This Portion!!", Toast.LENGTH_SHORT).show();
				}
			}});
      
	}
	
}
