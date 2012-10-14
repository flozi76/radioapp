package ch.icaros.digitalradio;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.MediaController;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import android.media.MediaPlayer.OnPreparedListener;
import android.view.MotionEvent;
import android.widget.MediaController;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Drs3RadioFragment extends Fragment implements
		 OnTouchListener, OnPreparedListener {

	public Drs3RadioFragment() {
	}

	private static final String TAG = "AudioPlayer";
	public static final String AUDIO_FILE_NAME = "audioFileName";

	private MediaPlayer mediaPlayer;
	private String audioFile;
	private ImageButton imageButtonstartStopMusic;
	private Drawable image;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		try {
			audioFile = "http://zlz-stream11.streamserver.ch/2/drs3/mp3_128";
			this.refreshTilte();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, e.getLocalizedMessage(), e);
		}

	}
	
	@Override
	public void onStart() {
		super.onStart();
		try {
			this.startMediaPlayer();
		} catch (Exception e) {
			Log.e(TAG, e.toString(), e);
		}
	};

	@Override
	public void onStop() {
		super.onStop();
		mediaPlayer.stop();
		mediaPlayer.release();
		mediaPlayer = null;
	}
	
	private void refreshTilte()
	{
			String streamUrl = "http://zlz-stream11.streamserver.ch/2/drs3/mp3_128";
			DownloadWebPageTask backgroundTask = new DownloadWebPageTask();
			backgroundTask.execute(new String[] {streamUrl});
	}

	public boolean onTouch(View v, MotionEvent event) {
		try {
			this.refreshTilte();
		} catch (Exception e) {
			Log.e(TAG, e.toString(), e);
		}
		return false;
	}
	
	private void startMediaPlayer() {
		try {
			this.refreshTilte();
			mediaPlayer = new MediaPlayer();	
			mediaPlayer.setDataSource(audioFile);
			mediaPlayer.prepareAsync();
			mediaPlayer.setOnPreparedListener(this);
			imageButtonstartStopMusic.setEnabled(false);
		} catch (Exception e) {
			Log.e(TAG, e.toString(), e);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.drs3_fragment, container, false);
		view.setOnTouchListener(this);
		
		imageButtonstartStopMusic = (ImageButton)view.findViewById(R.id.imageButtonStartMusic1);
		imageButtonstartStopMusic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
                try {
                	
					if(mediaPlayer != null && mediaPlayer.isPlaying())
					{
						mediaPlayer.stop();
						mediaPlayer.release();
						mediaPlayer = null;
						return;
					}
				} catch (Exception e) {
					Log.e(TAG, e.toString(), e);
				}
                
                startMediaPlayer();
            }
        });
		
		return view;
	}
	
	private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			String response = "";
			for (String url : urls) {
				try {
					IcyStreamMeta meta = new IcyStreamMeta(new URL(url));
					String title = meta.getTitle();
					String artist = meta.getArtist();
					String streamTitle = meta.getStreamTitle();
					response+=streamTitle;
					
					LastFmCoverSearch search = new LastFmCoverSearch();
					String urlCoverPicture = search.searchCover(title, artist);
//					response+=";"+urlCoverPicture;
					
					Object content = null;
				      URL urlCover = new URL(urlCoverPicture);
				      content = urlCover.getContent();
				    InputStream is = (InputStream)content;
				    image = Drawable.createFromStream(is, "src");
					
					
				} catch (Exception e) {
					Log.e(TAG, e.toString(), e);
				}
			}
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			try {
//				String urlCoverPicture = result.substring(result.lastIndexOf(";")+1);
				
				TextView tw =  (TextView) getActivity().findViewById(R.id.textViewSongtitle1);
				tw.setText(result);
							    
			    ImageView imageView = (ImageView)getActivity().findViewById(R.id.imageAlbum);
			    
			    imageView.setImageDrawable(image);
				
			} catch (Exception e) {
				Log.e(TAG, e.toString(), e);
			}

		}
	}

	public void onPrepared(MediaPlayer mp) {
		try {
			imageButtonstartStopMusic.setEnabled(true);
			mp.start();
		} catch (Exception e) {
			Log.e(TAG, e.toString(), e);
		}
	}
}
