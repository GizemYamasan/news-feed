package online.csdm.newsfeed.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import online.csdm.newsfeed.domain.Feed;

@Repository
public interface FeedRepositoryJPA extends PagingAndSortingRepository<Feed, Long> {

	boolean existsByGuid(String guid);

}