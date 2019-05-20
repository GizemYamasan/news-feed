package online.csdm.newsfeed.controller;

import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import online.csdm.newsfeed.domain.Feed;
import online.csdm.newsfeed.service.FeedService;

@RestController
public class FeedController {

	@Autowired
	FeedService feedService;

	@ApiOperation("list most recent feeds from different sources")
	@GetMapping(path = "/feeds", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Feed> getFeeds(
			@ApiParam("result page number")
			@RequestParam(name = "page", defaultValue = "1") int page,
			@ApiParam("number of feed items on per result page")
			@RequestParam(name = "size", defaultValue = "10") int size) {
		return feedService.getFeeds(page, size);
	}
	
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ErrorMessage> handleValidationException(ValidationException ex) {
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
		errorMessage.setMessage(ex.getMessage());
		return ResponseEntity.badRequest().body(errorMessage);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> handleException(Exception ex) {
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		errorMessage.setMessage("unexpected server error occured");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
	}
}
