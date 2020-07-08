package edu.cnm.deepdive.quotes.service;

import edu.cnm.deepdive.quotes.model.entity.Quote;
import edu.cnm.deepdive.quotes.model.entity.Source;
import edu.cnm.deepdive.quotes.model.entity.Tag;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository extends JpaRepository<Quote, Long> {

  Iterable<Quote> getAllByOrderByTextAsc();

//this methods will query the quotes by source.Return all quotes by the chosen source.
  Iterable<Quote> getAllBySourceOrderByTextAsc(Source source);

  Iterable<Quote> getAllByTagsContainingOrderByTextAsc(Tag tag);

  Iterable<Quote> getAllByTextContainingOrderByTextAsc(String filter);


}
