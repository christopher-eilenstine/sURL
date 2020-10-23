package com.ceilenstine.csci4830.controllers;

import com.ceilenstine.csci4830.models.LinkEntity;
import com.ceilenstine.csci4830.services.LinkService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@Controller
public class LinkController {

    private final LinkService linkService;

    @Autowired
    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:shorten";
    }

    @GetMapping("/shorten")
    public String get_shorten(Model model) {
        LinkEntity link = new LinkEntity();
        model.addAttribute("link", link);

        return "shorten";
    }

    @PostMapping("/shorten")
    public String post_shorten(@Valid @ModelAttribute("link") LinkEntity link, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "shorten";
        }

        String longUrl = link.getLongUrl();
        link.setShortUrl(linkTo(methodOn(LinkController.class).redirect(linkService.getShortUrl(longUrl)))
                .withRel("shortUrl").getHref());
        model.addAttribute("link", link);

        return "shorten_success";
    }

    @GetMapping(value = "/{shortUrl}")
    public ResponseEntity<Void> redirect(@PathVariable String shortUrl) {
            String longUrl = linkService.getLongUrl(shortUrl);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(longUrl))
                    .build();
    }
}
