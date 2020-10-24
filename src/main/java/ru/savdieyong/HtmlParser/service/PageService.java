package ru.savdieyong.HtmlParser.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import ru.savdieyong.HtmlParser.model.Page;
import ru.savdieyong.HtmlParser.model.Word;
import ru.savdieyong.HtmlParser.repository.PageRepository;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PageService {


    private static final String FILE_PATH = "src/main/resources/pages/";

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

    public void parse(Page page) throws IOException {
        String fileName = getDomain(page);
        List<String> words = new ArrayList<>();

        String line;
        BufferedReader bufferedReader = new BufferedReader(
                new FileReader(FILE_PATH+fileName+".html"));
        while ((line=bufferedReader.readLine())!=null){
            Document doc = Jsoup.parse(line);
            words = Arrays.asList(doc.text()
                    .toUpperCase()
                    .split("[^a-zA-Zа-яА-Я]+|[\\n\\t\\r\\s]"));
        }

        countDuplicate(page, words);
    }

    public void downloadPage(Page page) throws IOException {
        String fileName = getDomain(page);

        File directory = new File(FILE_PATH);
        if (!directory.exists()){
            directory.mkdir();
        }

        URL url = new URL(page.getAddress());
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(url.openStream()));
        BufferedWriter bufferedWriter = new BufferedWriter(
                new FileWriter(FILE_PATH + fileName + ".html"));

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            bufferedWriter.write(line);
        }
    }

    private String getDomain(Page page) {
        return page.getAddress()
                    .replace("https://", "");
    }

    private void countDuplicate(Page page, List<String> words) {
        Map<String, Long> map = words.stream()
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            Word word = new Word();
            word.setBody(entry.getKey());
            word.setPage(page);
            word.setCount(entry.getValue());
            wordService.save(word);
        }

    }
}
