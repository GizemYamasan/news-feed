package online.csdm.newsfeed.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import online.csdm.newsfeed.domain.Feed;
import online.csdm.newsfeed.repository.FeedRepository;

@Service
public class FeedService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FeedService.class);

	@Autowired
	private FeedRepository repository;

	@Autowired
	private FeedSource[] feedSources;

	public List<Feed> getFeeds(int page, int size) {
		if (size < 1 || size > 50) {
			throw new ValidationException("size must be between 1 and 50");
		}
		if (page < 0) {
			throw new ValidationException("page can not be negative");
		}
		return repository.findAll(page, size);
	}

	/**
	 * default: every minute
	 * */
	@Scheduled(cron = "${feedservice.fetch_and_store.cron:'0 * * ? * *'}")
	public void fetchAndStore() {
		LOGGER.info("fetch and store is started");
		for (FeedSource feedSource : feedSources) {
			List<Feed> feeds = feedSource.getFeeds()
					.stream()
					.filter(feed -> !repository.existsByGuid(feed.getGuid()))
					.collect(Collectors.toList());
			repository.saveAll(feeds);
		}
	}
}
