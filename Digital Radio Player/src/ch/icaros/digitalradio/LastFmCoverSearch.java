package ch.icaros.digitalradio;

import java.util.Collection;

import de.umass.lastfm.Artist;
import de.umass.lastfm.Caller;
import de.umass.lastfm.ImageSize;
import de.umass.lastfm.Track;

public class LastFmCoverSearch {
	public String searchCover(String track, String artist) {
		String urlResult = null;
		String key = "49175f0086374abc087b4774bc075343";

		Caller.getInstance().setCache(null);

		int limit = 30;
		// track="Spirit Indestructible";
		// artist="Nelly Furtado";
		
		Collection<Track> chart1 = Track.search(artist, track, limit, key);

		if (chart1.isEmpty()) {
			Collection<Artist> artists = Artist.search(artist, key);
			for (Artist artist2 : artists) {
				urlResult = artist2.getImageURL(ImageSize.EXTRALARGE);
				if (urlResult != null) {
					break;
				}
			}
		} else {
			for (Track track2 : chart1) {
				urlResult = track2.getImageURL(ImageSize.EXTRALARGE);
				if (urlResult != null) {
					break;
				}
			}
		}

		if (urlResult == null) {
			urlResult = "http://www.drs3.ch/static/global/logo.gif";
		}

		return urlResult;

	}
}
