package ru.savdieyong.HtmlParser.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.savdieyong.HtmlParser.model.Page;
import ru.savdieyong.HtmlParser.service.PageService;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/parser")
public class ParserController {

    private final PageService pageService;

    public ParserController(PageService pageService) {
        this.pageService = pageService;
    }

    @GetMapping("")
    public String createParseForm(Page page){
        return "parser/parse";
    }


    @PostMapping("")
    public String parse(@Valid Page page, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()){
            return "parser/parse";
        }

        if (pageService.findByAddress(page.getAddress()) == null){
            pageService.save(page);
            pageService.downloadPage(page);
            pageService.parse(page);
        }
        return String.format("redirect:pages/%d",  pageService.findByAddress(page.getAddress()).getId());
    }
}
