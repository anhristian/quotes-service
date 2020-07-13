package edu.cnm.deepdive.quotes.view;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.net.URI;
import java.util.Date;

@JsonPropertyOrder({"id", "created", "updated", "text", "href"})   //{}-multiple values
public interface FlatQuote {

  Long getId();      // Long shows that methods return Long.

  Date getCreated();

  Date getUpdated();

  String getText();

  URI getHref();

}
