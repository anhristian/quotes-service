package edu.cnm.deepdive.quotes.controller;

import edu.cnm.deepdive.quotes.service.QuoteRepository;
import edu.cnm.deepdive.quotes.service.SourceRepository;
import edu.cnm.deepdive.quotes.service.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/quotes")
public class QuotePageController {

  private final QuoteRepository quoteRepository;
  private final SourceRepository sourceRepository;
  private final TagRepository tagRepository;

  @Autowired
  public QuotePageController(QuoteRepository quoteRepository,
      SourceRepository sourceRepository,
      TagRepository tagRepository) {
    this.quoteRepository = quoteRepository;
    this.sourceRepository = sourceRepository;
    this.tagRepository = tagRepository;
  }

  @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
  public String get(Model model) {
    model.addAttribute("quotes", quoteRepository.getAllByOrderByTextAsc());
    return "list";
  }

//  @GetMapping(value = "/{id:\\d+}", produces = MediaType.TEXT_HTML_VALUE)
//  public String get(@PathVariable long id, Model model) {
//    return quoteRepository.findById(id)
//        .orElseThrow(NoSuchElementException::new);
//    }

}
