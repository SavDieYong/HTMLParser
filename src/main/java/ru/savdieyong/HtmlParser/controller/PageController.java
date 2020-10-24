package ru.savdieyong.HtmlParser.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.savdieyong.HtmlParser.model.Page;
import ru.savdieyong.HtmlParser.service.PageService;
import ru.savdieyong.HtmlParser.service.WordService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
public class PageController {

    private final PageService pageService;
    private final WordService wordService;

    public PageController(PageService pageService, WordService wordService) {
        this.pageService = pageService;
        this.wordService = wordService;
    }

    @GetMapping("/pages")
    //Get all pages and pass them to the view
    public String findAll(Model model){
        List<Page> pages = pageService.findAll();
        model.addAttribute("pages", pages);
        return "pages";
    }

    @GetMapping("/parse")
    public String createParseForm(Page page){
        return "parse";
    }

    @PostMapping("/parse")
    public String parse(@Valid Page page, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()){
            return "parse";
        }

        if (pageService.findByAddress(page.getAddress()) == null){
            pageService.save(page);
        } else {
            return String.format("redirect:/%d",  pageService.findByAddress(page.getAddress()).getId());
        }

        pageService.parse(page);
        return "page";
    }

    @GetMapping("/{id}")
    public String page(@PathVariable Long id, Model model){
        Page page = pageService.findById(id);
        model.addAttribute("page", pageService.findById(id));
        model.addAttribute("words", wordService.findByPageId(id));
        return "page";
    }
}
