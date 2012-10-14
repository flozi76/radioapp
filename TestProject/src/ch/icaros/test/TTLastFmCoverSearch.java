package ch.icaros.test;

import java.io.Console;
import java.io.InputStream;
import java.util.Collection;

import de.umass.lastfm.Album;
import de.umass.lastfm.Artist;
import de.umass.lastfm.Caller;
import de.umass.lastfm.Chart;
import de.umass.lastfm.ImageSize;
import de.umass.lastfm.Track;
import de.umass.lastfm.User;
import de.umass.lastfm.cache.Cache;

public class TTLastFmCoverSearch {

	public void searchCover()
	{
		 try {
			String keyT = "b25b959554ed76058ac220b7b2e0a026"; //this is the key used in the Last.fm API examples
			 String key = "49175f0086374abc087b4774bc075343";
			    String user = "JRoar";
//		    Chart<Artist> chart = User.getWeeklyArtistChart(user, 10, key);
			    
			    Caller.getInstance().setCache(null);
			    int limit=10;
//				String track="Spirit Indestructible";
//				String artist="Nelly Furtado";
				String track="on the floor";
				String artist="jennifer lopez";
				Collection<Track> chart1 = Track.search(artist, track, limit, key);
				String url = "";
				if(chart1.isEmpty())
				{
					Collection<Artist> artists = Artist.search(artist, key);
					if(!artists.isEmpty())
					{
						url = artists.iterator().next().getImageURL(ImageSize.EXTRALARGE);	
					}

				}
				else
				{
				for (Track track2 : chart1) {
					url = track2.getImageURL(ImageSize.EXTRALARGE);
					if(url != null)
					{
						break;
					}
				}
				}
				
				Album album = Album.getInfo("", "4d7cf4d6-9661-4179-8503-8b58539e14fb", key);
				
				url = album.getImageURL(ImageSize.EXTRALARGE);
				
				System.out.println(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
}

class MyCache extends Cache
{

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean contains(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isExpired(String arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public InputStream load(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void store(String arg0, InputStream arg1, long arg2) {
		// TODO Auto-generated method stub
		
	}}
