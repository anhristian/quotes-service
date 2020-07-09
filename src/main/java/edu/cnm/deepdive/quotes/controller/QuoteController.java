package edu.cnm.deepdive.quotes.controller;

import edu.cnm.deepdive.quotes.model.entity.Quote;
import edu.cnm.deepdive.quotes.model.entity.Tag;
import edu.cnm.deepdive.quotes.service.QuoteRepository;
import edu.cnm.deepdive.quotes.service.SourceRepository;
import edu.cnm.deepdive.quotes.service.TagRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quotes")
@ExposesResourceFor(Quote.class) //change to each class respectively the Quote.class
public class QuoteController {

  private final QuoteRepository quoteRepository;
  private final SourceRepository sourceRepository;
  private final TagRepository tagRepository;

  @Autowired
  public QuoteController(QuoteRepository quoteRepository,
      SourceRepository sourceRepository,
      TagRepository tagRepository) {
    this.quoteRepository = quoteRepository;
    this.sourceRepository = sourceRepository;
    this.tagRepository = tagRepository;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Quote> get() {
    return quoteRepository.getAllByOrderByTextAsc();
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Quote> post(@RequestBody Quote quote) {
    //if you got a source object include in json that has an id let me look on the source.
    // refers to an id that already exist.
    if (quote.getSource() != null && quote.getSource().getId() != null) {
      quote.setSource(
          sourceRepository.findById(
              quote.getSource().getId())
          //this is a method reference..still lambda.
          .orElseThrow(NoSuchElementException::new));
    }
    List<Tag> resolvedTags = quote.getTags().stream()
        //take every elements and tag it
        .map((tag) ->
            (tag.getId() == null) ?
                tag : tagRepository.findById(tag.getId()).orElseThrow(NoSuchElementException::new))
        .collect(Collectors.toList());
    quote.getTags().clear();
    quote.getTags().addAll(resolvedTags);
    quoteRepository.save(quote);
    return ResponseEntity.created(quote.getHref()).body(quote);  //give the value of location ..Post should have it
  }

  @GetMapping(value = "/{id:\\d+}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Quote get(@PathVariable long id) {
    return quoteRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }
    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE) // a single quote or multiple with the word
  public Iterable<Quote> search(@RequestParam(name = "q", required = true) String filter){
    return quoteRepository.getAllByTextContainingOrderByTextAsc(filter);
    }
/*  @PutMapping(value = "/{id:\\d+}/text",
      produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
  public String putText(@PathVariable long id, @RequestBody String text)*/ //just example
}
