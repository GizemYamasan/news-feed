package online.csdm.newsfeed.repository;

import java.util.List;

import online.csdm.newsfeed.domain.Feed;


public interface FeedRepository {

	List<Feed> findAll(int page, int size);

	boolean existsByGuid(String guid);

	void saveAll(List<Feed> feeds);

}
