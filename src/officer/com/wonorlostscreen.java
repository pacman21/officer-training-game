package officer.com;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class wonorlostscreen extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wonlost);
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        Button tomenu = (Button)findViewById(R.id.button1);
        ImageView image = (ImageView)findViewById(R.id.imageView1);
        
        if(userinfo.wonorlost == false)
        {
        	image.setBackgroundResource(R.drawable.failed);
        }
        else if(userinfo.wonorlost = true)
        {
        	image.setBackgroundResource(R.drawable.succeed);
        }
        
        tomenu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
					
					if(userinfo.wonorlost == false)
			        {
			        	userinfo.lives = userinfo.lives - 1;
			        }
					
					userinfo.wonorlost = true;
					Intent myIntent = new Intent(wonorlostscreen.this, Otraininggame.class);
					wonorlostscreen.this.startActivity(myIntent);
			}});
        
	}
}
