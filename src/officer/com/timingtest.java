package officer.com;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class timingtest extends Activity {
	private static long startTime = 0;
	private static long stopTime = 0;
	private static boolean running = false;
	boolean time1 = false;
	boolean time2 = false;
	boolean time3 = false;
	boolean time4 = false;
	int num1 = 0;
	int num2 = 0;
	int num3 = 0;
	int num4 = 0;
	int timeoffnum = 0;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timing);
        userinfo.wonorlost = false;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        final TextView timer1 = (TextView)findViewById(R.id.TextView01);
        final TextView timer2 = (TextView)findViewById(R.id.TextView02);
        final TextView timer3 = (TextView)findViewById(R.id.TextView03);
        final TextView timer4 = (TextView)findViewById(R.id.TextView04);
        final TextView timeoff = (TextView)findViewById(R.id.TextView06);
        
        ImageButton difuser1 = (ImageButton)findViewById(R.id.imageButton1);
        ImageButton difuser2 = (ImageButton)findViewById(R.id.imageButton2);
        ImageButton difuser3 = (ImageButton)findViewById(R.id.imageButton3);
        ImageButton difuser4 = (ImageButton)findViewById(R.id.imageButton4);
        int oneusenum = 900 - (userinfo.timinglevel * 100);
        timeoff.setText("Points Left: " + oneusenum);
        timer1.setText("Start");
        timer2.setText("Start");
        timer3.setText("Start");
        timer4.setText("Start");
        
        new CountDownTimer(2147483647, 10) {

			public void onTick(long millisUntilFinished) {
						
					if(time1 == true)
					{
						num1 = (int) getElapsedTime();
						timer1.setText(Integer.toString(num1));
					}
					if(time2 == true)
					{
						num2 = (int) getElapsedTime();
						timer2.setText(Integer.toString(num2));
					}
					if(time3 == true)
					{
						num3 = (int) getElapsedTime();
						timer3.setText(Integer.toString(num3));
					}
					if(time4 == true)
					{
						num4 = (int) getElapsedTime();
						timer4.setText(Integer.toString(num4));
					}
			}
			
            public void onFinish() {
                
            }
         }.start();
         
        
        difuser1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(timer1.getText().toString() == "Start")
				{
					start();
					time1 = true;
				}
				else
				{
					stop();
					time1 = false;
					timeoffnum = (900 - (userinfo.timinglevel * 100)) - Math.abs(num1-10000);
					timeoff.setText("Points Left: " + timeoffnum);
				}
				
			}});
        
        difuser2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(timer1.getText().toString() == "Start")
				{
					Toast.makeText(timingtest.this, "Headquarters Says To Disarm the Bombs In Order From Left to Right!", Toast.LENGTH_SHORT).show();
					return;
				}
				if(timer2.getText().toString() == "Start")
				{
					start();
					time2 = true;
				}
				else
				{
					stop();
					time2 = false;
					timeoffnum = (900 - (userinfo.timinglevel * 100)) - (Math.abs(num1-10000) + Math.abs(num2-10000));
					timeoff.setText("Points Left: " + timeoffnum);
				}
				
			}});
        
        difuser3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(timer2.getText().toString() == "Start")
				{
					Toast.makeText(timingtest.this, "Headquarters Says To Disarm the Bombs In Order From Left To Right!", Toast.LENGTH_SHORT).show();
					return;
				}
				if(timer3.getText().toString() == "Start")
				{
					start();
					time3 = true;
				}
				else
				{
					stop();
					time3 = false;
					timeoffnum = (900 - (userinfo.timinglevel * 100)) - (Math.abs(num1-10000) + Math.abs(num2-10000) + Math.abs(num3-10000));
					timeoff.setText("Points Left: " + timeoffnum);
				}
				
			}});
        
        difuser4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(timer3.getText().toString() == "Start")
				{
					Toast.makeText(timingtest.this, "Headquarters Says To Disarm the Bombs In Order From Left to Right!", Toast.LENGTH_SHORT).show();
					return;
				}
				if(timer4.getText().toString() == "Start")
				{
					start();
					time4 = true;
				}
				else
				{
					stop();
					time4 = false;
					timeoffnum = (900 - (userinfo.timinglevel * 100)) - (Math.abs(num1-10000) + Math.abs(num2-10000) + Math.abs(num3-10000) + Math.abs(num4-10000));
					timeoff.setText("Points Left: " + timeoffnum);
					if(Math.abs(num1-10000) + Math.abs(num2-10000) + Math.abs(num3-10000) + Math.abs(num4-10000) > (900 - (userinfo.timinglevel * 100)))
					{
						Toast.makeText(timingtest.this, "Failed!!", Toast.LENGTH_SHORT).show();
						userinfo.wonorlost = false;
						Intent myIntent = new Intent(timingtest.this, wonorlostscreen.class);
						timingtest.this.startActivity(myIntent);
					}
					else
					{
						Toast.makeText(timingtest.this, "Passed!!", Toast.LENGTH_SHORT).show();
						userinfo.wonorlost = true;
						userinfo.timinglevel += 1;
						Intent myIntent = new Intent(timingtest.this, wonorlostscreen.class);
						timingtest.this.startActivity(myIntent);
					}
						
				}
				
			}});
        
	}
	
	public static void start() {
        startTime = System.currentTimeMillis();
        running = true;
    }

    
    public void stop() {
        this.stopTime = System.currentTimeMillis();
        this.running = false;
    }

    
    //elaspsed time in milliseconds
    public long getElapsedTime() {
        long elapsed;
        if (running) {
             elapsed = (System.currentTimeMillis() - startTime);
        }
        else {
            elapsed = (stopTime - startTime);
        }
        return elapsed;
    }
    
    
    //elaspsed time in seconds
    public long getElapsedTimeSecs() {
        long elapsed;
        if (running) {
            elapsed = ((System.currentTimeMillis() - startTime) / 1000);
        }
        else {
            elapsed = ((stopTime - startTime) / 1000);
        }
        return elapsed;
    }
    
    public boolean isInteger( String input )
    {
    	Scanner scanner = new Scanner(input);
    	if (scanner.hasNextInt()) {
    	    return true;
    	}
    	else
    		return false;

    }

}