package online.csdm.newsfeed.repository.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

import online.csdm.newsfeed.domain.Feed;
import online.csdm.newsfeed.repository.FeedRepository;

@Repository
public class FeedRepositoryJPAWrapper implements FeedRepository {

	@Autowired
	private FeedRepositoryJPA feedRepositoryJPA;

	@Override
	public List<Feed> findAll(int page, int size) {
		Sort sortByDate = Sort.by(Direction.DESC, "publicationDate");
		PageRequest pageRequest = PageRequest.of(page, size, sortByDate);
		return feedRepositoryJPA.findAll(pageRequest).getContent();
	}

	@Override
	public boolean existsByGuid(String guid) {
		return feedRepositoryJPA.existsByGuid(guid);
	}

	@Override
	public void saveAll(List<Feed> feeds) {
		feedRepositoryJPA.saveAll(feeds);
	}

}
