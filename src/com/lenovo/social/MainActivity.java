package com.lenovo.social;

import com.lenovo.social.giveaway.GiveawayActivity;
import com.lenovo.social.quicknews.QuickNewsActivity;
import com.lenovo.social.socialevent.SocialEvent;
import com.lenovo.social.socialevent.SocialEventReaderActivity;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainActivity extends Activity {
        private ImageButton googleEventsButton;
        private ImageButton giveawaysButton;
        private ImageButton releasesButton;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                
                googleEventsButton = (ImageButton)findViewById(R.id.imageButtonSocialEvents);
                googleEventsButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent myIntent = new Intent(getApplicationContext(), SocialEventReaderActivity.class);
                        startActivity(myIntent);
					}
                	
                });
                giveawaysButton = (ImageButton)findViewById(R.id.imageButtonGiveaways);
                giveawaysButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent myIntent = new Intent(getApplicationContext(), GiveawayActivity.class);
                        startActivity(myIntent);
					}
                	
                });
                
                releasesButton = (ImageButton)findViewById(R.id.imageButtonReleases);
                releasesButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						//Intent myIntent = new Intent(getApplicationContext(), ReleaseActivity.class);
						Intent myIntent = new Intent(getApplicationContext(), QuickNewsActivity.class);
                        startActivity(myIntent);
					}
                	
                });
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
                // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.main, menu);
                return false;
        }
        
        @Override
        public boolean onOptionsItemSelected(MenuItem item){
                switch (item.getItemId()) {
                case R.id.action_settings:
                        startActivity(new Intent(this, SettingsActivity.class));
                        return true;
                case R.id.action_donate:
                        try {
                                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=SM6YYQ8K4FP3E"));
                                startActivity(myIntent);
                        } catch (ActivityNotFoundException e) {
                        }
                        return true;
                }
                return false;
        }
}