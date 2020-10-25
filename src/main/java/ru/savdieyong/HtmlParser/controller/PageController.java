package ru.savdieyong.HtmlParser.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.savdieyong.HtmlParser.model.Page;
import ru.savdieyong.HtmlParser.service.PageService;

import java.util.List;

@Controller
@RequestMapping("/pages")
public class PageController {

    private final PageService pageService;

    public PageController(PageService pageService) {
        this.pageService = pageService;
    }

    @GetMapping("")
    //Get all pages and pass them to the view
    public String getAllPages(Model model) {
        List<Page> pages = pageService.findAll();
        model.addAttribute("pages", pages);
        return "pages/pages";
    }
}
