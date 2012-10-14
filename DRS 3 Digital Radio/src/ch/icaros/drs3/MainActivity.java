package ch.icaros.drs3;

import java.io.InputStream;
import java.net.URL;

import ch.icaros.drs3.storage.FileStorage;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity {

	public static final String TAG = "DRS 3 Player";
	private Handler handler;
	private Drawable image;
	private ImageButton imageButtonstartStopMusic;
	private ImageButton imageButtonsAddSongToList;
	private ImageButton imageButtonShowSongList;
	TextView textViewSongTitle;
	private boolean onAir = false;
	long starttime = 0;
	private String currentSong = "";
	private String currentLogo = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			setContentView(R.layout.activity_main);
			imageButtonstartStopMusic = (ImageButton) findViewById(R.id.imageButtonStartStop);
			imageButtonsAddSongToList = (ImageButton) findViewById(R.id.imageButtonAddToList);
			imageButtonShowSongList = (ImageButton) findViewById(R.id.imageButtonViewList);
			textViewSongTitle = (TextView) findViewById(R.id.textViewSong);

			imageButtonstartStopMusic
					.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {

							try {
								loadCurrentCover();

								if (onAir) {
									imageButtonstartStopMusic
											.setImageResource(R.drawable.stop);
									this.stopMusicService();
								} else {
									imageButtonstartStopMusic
											.setImageResource(R.drawable.start);
									this.startMusicService();
								}
								onAir = !onAir;

							} catch (Exception e) {
								Log.e(TAG, e.toString(), e);
							}

						}

						private void startMusicService() {
							startService(new Intent(
									"ch.icaros.drs3.Drs3OnAirService"));

						}

						private void stopMusicService() {
							stopService(new Intent(
									"ch.icaros.drs3.Drs3OnAirService"));

						}
					});

			imageButtonsAddSongToList
					.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {

							try {
								FileStorage fileStorage = new FileStorage();
								fileStorage.SaveToFile(currentSong + ";"
										+ currentLogo);
								Toast.makeText(getApplicationContext(),
										" Song added to list",
										Toast.LENGTH_LONG).show();
								String songs = fileStorage.ReadFile();
								Toast.makeText(getBaseContext(), songs,
										Toast.LENGTH_LONG).show();

							} catch (Exception e) {
								Log.e(TAG, e.toString(), e);
							}
						}
					});

			imageButtonShowSongList
					.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {

							try {
								Intent intent = new Intent(
										getApplicationContext(), SongList.class);
								startActivity(intent);

							} catch (Exception e) {
								Log.e(TAG, e.toString(), e);
							}
						}
					});

			handler = new Handler();
			this.loadCurrentCover();
			this.runTimedCoverLoader();
		} catch (Exception e) {
			Log.e(TAG, e.getLocalizedMessage(), e);
		}
	}

	private void runTimedCoverLoader() {
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				loadCurrentCover();
				handler.postDelayed(this, 30000);
			}
		};

		new Thread(runnable).start();

	}

	private void loadCurrentCover() {

		String streamUrl = "http://zlz-stream11.streamserver.ch/2/drs3/mp3_128";
		DownloadCoverPicture downloadCover = new DownloadCoverPicture();
		downloadCover.execute(new String[] { streamUrl });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	class DownloadCoverPicture extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			String response = "";
			for (String url : urls) {
				try {
					IcyStreamMeta meta = new IcyStreamMeta(new URL(url));
					String title = meta.getTitle();
					String artist = meta.getArtist();
					String streamTitle = meta.getStreamTitle();
					response += streamTitle;
					currentSong = streamTitle;
					Log.i(TAG, title + ": " + artist);

					LastFmCoverSearch search = new LastFmCoverSearch();
					String urlCoverPicture = search.searchCover(title, artist);
					currentLogo = urlCoverPicture;

					Object content = null;
					image = null;
					if(urlCoverPicture != null && urlCoverPicture != "")
					{
						URL urlCover = new URL(urlCoverPicture);
						content = urlCover.getContent();
						InputStream is = (InputStream) content;
						image = Drawable.createFromStream(is, "src");
					}

				} catch (Exception e) {
					Log.e(TAG, e.toString(), e);
				}
			}
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			try {
				// String urlCoverPicture =
				// result.substring(result.lastIndexOf(";")+1);

				TextView tw = (TextView) findViewById(R.id.textViewSong);
				tw.setText(result);

				ImageView imageView = (ImageView) findViewById(R.id.imageViewCover);

				if(image != null)
				{
					imageView.setImageDrawable(image);
				}
				else
				{
					imageView.setImageResource(R.drawable.drs3logo);
				}
				

			} catch (Exception e) {
				Log.e(TAG, e.toString(), e);
			}

		}
	}

}
