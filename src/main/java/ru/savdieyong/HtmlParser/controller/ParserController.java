package ru.savdieyong.HtmlParser.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.savdieyong.HtmlParser.model.Page;
import ru.savdieyong.HtmlParser.service.PageService;
import ru.savdieyong.HtmlParser.service.ParserService;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/parser")
public class ParserController {

    private final PageService pageService;
    private final ParserService parserService;

    public ParserController(PageService pageService, ParserService parserService) {
        this.pageService = pageService;
        this.parserService = parserService;
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
            parserService.parse(page);
        }
        return String.format("redirect:/words/%d",
                pageService.findByAddress(page.getAddress()).getId());
    }
}
