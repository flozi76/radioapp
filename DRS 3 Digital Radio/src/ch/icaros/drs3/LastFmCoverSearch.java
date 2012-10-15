package ch.icaros.drs3;

import java.util.Collection;

import android.util.Log;

import de.umass.lastfm.Artist;
import de.umass.lastfm.Caller;
import de.umass.lastfm.ImageSize;
import de.umass.lastfm.Track;

public class LastFmCoverSearch {
	public String searchCover(String track, String artist) {
		String urlResult = null;
		String key = "49175f0086374abc087b4774bc075343";

		try {
			Caller.getInstance().setCache(null);
			int limit = 30;

			Collection<Track> chart1 = Track.search(artist, track, limit, key);

			if (chart1 != null) {
				for (Track track2 : chart1) {
					urlResult = track2.getImageURL(ImageSize.EXTRALARGE);
					if (urlResult != null) {
						break;
					}
				}
			}

			if (urlResult == null && artist != null) {
				Collection<Artist> artists = Artist.search(artist, key);
				if (artist != null) {
					for (Artist artist2 : artists) {
						urlResult = artist2.getImageURL(ImageSize.EXTRALARGE);
						if (urlResult != null) {
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			Log.e(MainActivity.TAG, e.toString(), e);
		}

		if (urlResult == null || urlResult == "") {
//			urlResult = "http://upload.wikimedia.org/wikipedia/en/thumb/b/b5/DRS_3_logo.svg/602px-DRS_3_logo.svg.png";
			urlResult = null;
		}

		return urlResult;

	}
}
