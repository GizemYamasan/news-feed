package online.csdm.newsfeed.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.ValidationException;

import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import online.csdm.newsfeed.domain.Feed;
import online.csdm.newsfeed.repository.FeedRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FeedServiceTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Mock
	private FeedRepository feedRepository;

	@Mock
	private FeedSource feedSource;

	@InjectMocks
	private FeedService feedService = new FeedService();

	private Feed feed;
	private List<Feed> feeds;

	@Before
	public void setup() {
		feed = new Feed();
		feed.setTitle("demo title");
		feed.setDescription("demo desc");
		feed.setImageUrl("http://...");
		feed.setGuid("2y35732y75");
		feed.setPublicationDate(new Date());

		feeds = new ArrayList<>();
		feeds.add(feed);
	}

	@Test
	public void test_getFeeds_success() {
		when(feedRepository.findAll(1, 1)).thenReturn(feeds);
		List<Feed> returnedFeeds = feedService.getFeeds(1, 1);
		assertNotNull(returnedFeeds);
		assertTrue(returnedFeeds.size() == 1);
	}

	@Test
	public void test_getFeedsWithInvalidPage_fail() {
		thrown.expect(ValidationException.class);
		feedService.getFeeds(-1, 1);
	}
	
	@Test
	public void test_getFeedsWithInvalidLimit_fail() {
		thrown.expect(ValidationException.class);
		feedService.getFeeds(1, 10000000);
	}

	@Test
	public void test_contextLoads_success() {
		assertNotNull(feedSource);
		assertNotNull(feedService);
	}

}
