package ch.icaros.drs3;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.support.v4.app.NavUtils;

public class SongList extends Activity {

	private ImageButton imageButtonMain;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        imageButtonMain = (ImageButton)findViewById(R.id.imageButtonMain);
        imageButtonMain.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				try {
					Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		            startActivity(intent);
					
				} catch (Exception e) {
					Log.e(MainActivity.TAG, e.toString(), e);
				}
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_song_list, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
