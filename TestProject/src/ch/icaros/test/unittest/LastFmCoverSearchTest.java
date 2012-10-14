package ch.icaros.test.unittest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.icaros.test.TTLastFmCoverSearch;

public class LastFmCoverSearchTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSearchCover() {
		TTLastFmCoverSearch coverSearch = new TTLastFmCoverSearch();
		
		coverSearch.searchCover();
	}

}
