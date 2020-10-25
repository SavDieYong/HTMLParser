package ru.savdieyong.HtmlParser.service;

import org.springframework.stereotype.Service;
import ru.savdieyong.HtmlParser.model.Page;
import ru.savdieyong.HtmlParser.repository.PageRepository;

import java.util.List;

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

    public Page findByAddress(String address) {
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
}
