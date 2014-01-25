package com.lenovo.social;

import java.util.ArrayList;

import com.lenovo.social.EventsArrayAdapter.ViewHolder;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
        private ImageButton googleEventsButton;
        private ImageButton giveawaysButton;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                
                googleEventsButton = (ImageButton)findViewById(R.id.imageButton1);
                googleEventsButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent myIntent = new Intent(getApplicationContext(), FeedReaderActivity.class);
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