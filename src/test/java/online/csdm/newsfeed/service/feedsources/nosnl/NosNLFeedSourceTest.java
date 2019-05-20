package online.csdm.newsfeed.service.feedsources.nosnl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import online.csdm.newsfeed.domain.Feed;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NosNLFeedSourceTest {

	@Autowired
	private NosNLFeedSource nosNLFeedSource;
	
	@Test
	public void test_contextLoads_success() {
		assertNotNull(nosNLFeedSource);
	}
	
	@Test
	public void test_contextLoads() {
		List<Feed> feeds = nosNLFeedSource.getFeeds();
		assertNotNull(feeds);
		assertFalse(feeds.isEmpty());
		feeds.forEach(feed -> {
			assertFalse(StringUtils.isEmpty(feed.getTitle()));
			assertFalse(StringUtils.isEmpty(feed.getDescription()));
			assertFalse(StringUtils.isEmpty(feed.getGuid()));
		});
	}
	
}
