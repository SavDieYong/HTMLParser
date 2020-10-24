package ru.savdieyong.HtmlParser.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import ru.savdieyong.HtmlParser.model.Page;
import ru.savdieyong.HtmlParser.model.Word;
import ru.savdieyong.HtmlParser.repository.PageRepository;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PageService {


    private final PageRepository pageRepository;
    private final WordService wordService;

    public PageService(PageRepository pageRepository, WordService wordService) {
        this.pageRepository = pageRepository;
        this.wordService = wordService;
    }

    public Page findById(Long id) {
        return pageRepository.getOne(id);
    }

    public Page findByAddress(String address){
        return pageRepository.findAll()
                .stream()
                .filter(page -> page.getAddress().equals(address))
                .findAny()
                .orElse(null);
    }

    public List<Page> findAll() {
        return pageRepository.findAll();
    }

    public void save(Page page) {
        pageRepository.save(page);
    }

    public void deleteById(Long id) {
        pageRepository.deleteById(id);
    }

    public void parse(Page page) throws IOException {
        Document doc = Jsoup.connect(page.getAddress())
                .userAgent("Chrome/4.0.249.0 Safari/532.5")
                .referrer("http://www.google.com")
                .get();
        List<String> words = Arrays.asList(doc.text().toUpperCase()
                .split("[^a-zA-Zа-яА-Я]+|[\\n\\t\\r\\s]"));
        countDuplicate(page, words);
    }

    private void countDuplicate(Page page, List<String> words) {
        Map<String, Long> map = words.stream()
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
        for (Map.Entry<String, Long> entry : map.entrySet()){
            Word word = new Word();
            word.setBody(entry.getKey());
            word.setPage(page);
            word.setCount(entry.getValue());
            wordService.save(word);
        }

    }


}
