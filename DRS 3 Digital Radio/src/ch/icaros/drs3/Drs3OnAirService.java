package ch.icaros.drs3;

import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class Drs3OnAirService extends Service implements OnPreparedListener {

	private static final String TAG = "Drs3 On Air";
	MediaPlayer mediaPlayer;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		Log.d(TAG, "onCreate");
		try {
			String audioFile = "http://zlz-stream11.streamserver.ch/2/drs3/mp3_128";
			mediaPlayer = new MediaPlayer();	
			mediaPlayer.setDataSource(audioFile);
			mediaPlayer.prepareAsync();
			mediaPlayer.setOnPreparedListener(this);
			mediaPlayer.setLooping(false); // Set looping
		} catch (Exception e) {
			Log.e(TAG, e.toString(), e);
		}
	}

	@Override
	public void onDestroy() {
		Toast.makeText(this, TAG + " Stopped", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onDestroy");
		mediaPlayer.stop();
	}
	
	@Override
	public void onStart(Intent intent, int startid) {
		Toast.makeText(this, "Connecting " + TAG, Toast.LENGTH_LONG).show();
		Log.d(TAG, "onStart");
//		mediaPlayer.start();
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		try {
			Toast.makeText(this, TAG + " ON AIR!", Toast.LENGTH_LONG).show();
			mp.start();
		} catch (Exception e) {
			Log.e(TAG, e.toString(), e);
		}
		
	}

}
