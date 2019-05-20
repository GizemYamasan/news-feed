package online.csdm.newsfeed.service;

import java.util.List;

import online.csdm.newsfeed.domain.Feed;

public interface FeedSource {
	
	public List<Feed> getFeeds();
	
}
