package online.csdm.newsfeed.service.feedsources.nosnl;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEnclosure;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import online.csdm.newsfeed.domain.Feed;
import online.csdm.newsfeed.service.FeedSource;

@Component
public class NosNLFeedSource implements FeedSource {

	private static final Logger LOGGER = LoggerFactory.getLogger(NosNLFeedSource.class);

	@Value("${feedsource.nosnl.url}")
	private String url;

	@Override
	public List<Feed> getFeeds() {
		List<Feed> feeds = new ArrayList<>();
		try {
			URL feedSource = new URL(url);
			SyndFeed syndFeed = new SyndFeedInput().build(new XmlReader(feedSource));
			List<SyndEntry> syndEntries = syndFeed.getEntries();
			for (SyndEntry syndEntry : syndEntries) {
				Feed feed = transformToFeed(syndEntry);
				feeds.add(feed);
			}
		} catch (Exception e) {
			LOGGER.error("getFeeds failed", e);
		}
		return feeds;
	}

	private Feed transformToFeed(SyndEntry syndEntry) {
		Feed feed = new Feed();
		feed.setTitle(syndEntry.getTitle());
		feed.setDescription(getDescription(syndEntry));
		feed.setPublicationDate(syndEntry.getPublishedDate());
		feed.setGuid(syndEntry.getUri());
		getImageUrl(syndEntry).ifPresent(feed::setImageUrl);
		return feed;
	}

	private String getDescription(SyndEntry syndEntry) {
		SyndContent description = syndEntry.getDescription();
		if (description != null && description.getValue() != null) {
			String content = description.getValue();
			if (content.length() > Feed.MAX_DESC_LENGTH) {
				return content.substring(0, Feed.MAX_DESC_LENGTH);
			}
			else {
				return content;
			}
		}
		return null;
	}

	// <enclosure type="image/jpeg"
	// url="https://nos.nl/data/image/2019/05/19/551271/1008x567.jpg" length="0" />
	private Optional<String> getImageUrl(SyndEntry syndEntry) {
		List<SyndEnclosure> enclosures = syndEntry.getEnclosures();
		for (SyndEnclosure syndEnclosure : enclosures) {
			if (syndEnclosure.getType().equalsIgnoreCase("image/jpeg")) {
				return Optional.ofNullable(syndEnclosure.getUrl());
			}
		}
		return Optional.empty();
	}
}
