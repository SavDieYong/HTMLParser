package ru.savdieyong.HtmlParser.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.savdieyong.HtmlParser.service.PageService;
import ru.savdieyong.HtmlParser.service.WordService;

@Controller
@RequestMapping("/words")
public class WordController {

    private final PageService pageService;
    private final WordService wordService;

    public WordController(PageService pageService, WordService wordService) {
        this.pageService = pageService;
        this.wordService = wordService;
    }

    @GetMapping("/{id}")
    public String getAllWordsAtPage(@PathVariable Long id, Model model) {
        model.addAttribute("page", pageService.findById(id));
        model.addAttribute("words", wordService.findByPageId(id));
        return "words/words";
    }
}
