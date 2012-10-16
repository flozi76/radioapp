package ch.icaros.drs3;

import java.util.LinkedList;

import ch.icaros.drs3.storage.FileStorage;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.support.v4.app.NavUtils;

public class SongList extends Activity {

	private ImageButton imageButtonMain;
	private ImageButton imageButtonDeleteFile;
	private ImageButton imageButtonSendfile;
	private ListView listViewSongList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);
        
        listViewSongList = (ListView)findViewById(R.id.listViewSongs);
        
        
        
        imageButtonSendfile = (ImageButton)findViewById(R.id.imageButtonSendItems);
        imageButtonSendfile.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				try {
					Intent intent = new Intent(Intent.ACTION_SEND);
					intent.setType("*.txt"); 
					intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(FileStorage.getPath()));  
					startActivity(Intent.createChooser(intent, "title"));
				} catch (Exception e) {
					Log.e(MainActivity.TAG, e.toString(), e);
				}
			}
		});
        
        
        imageButtonDeleteFile = (ImageButton)findViewById(R.id.imageButtonDeleteFile);
        imageButtonDeleteFile.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				try {
					(new FileStorage()).DeleteFile();
					populateList();
				} catch (Exception e) {
					Log.e(MainActivity.TAG, e.toString(), e);
				}
			}
		});
        
        imageButtonMain = (ImageButton)findViewById(R.id.imageButtonMain);
        imageButtonMain.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				try {
					finish();
					
				} catch (Exception e) {
					Log.e(MainActivity.TAG, e.toString(), e);
				}
			}
		});
        
        this.populateList();
    }

    private void populateList() {
    	
    	LinkedList<String> songs = (new FileStorage()).ReadFileLines();
    	 
    	// ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.mylist, android.R.id.text1, songs);
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.mylist, songs);

    	// Assign adapter to ListView
    	listViewSongList.setAdapter(adapter); 
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
