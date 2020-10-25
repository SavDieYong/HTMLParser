package ru.savdieyong.HtmlParser.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import ru.savdieyong.HtmlParser.model.Page;
import ru.savdieyong.HtmlParser.model.Word;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ParserService {

    private final WordService wordService;

    private final static String PATH = "src/main/resources/pages/";
    private String fileName;

    public ParserService(WordService wordService){
        this.wordService = wordService;
    }

    public void parse(Page page) throws IOException {
        downloadPage(page);
        List<String> words = new ArrayList<>();
        fileName = getDomain(page);
        String line;
        BufferedReader bufferedReader = new BufferedReader(
                new FileReader(PATH + fileName + ".html"));
        while ((line = bufferedReader.readLine()) != null) {
            Document doc = Jsoup.parse(line);
            words = Arrays.asList(doc.text()
                    .toUpperCase()
                    .split("[^a-zA-Zа-яА-Я]+|[\\n\\t\\r\\s]"));
        }

        countDuplicate(page, words);
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


    private void downloadPage(Page page) throws IOException {
        File directory = new File(PATH);
        fileName = getDomain(page);
        if (!directory.exists()) {
            directory.mkdir();
        }

        URL url = new URL(page.getAddress());
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(url.openStream()));
        BufferedWriter bufferedWriter = new BufferedWriter(
                new FileWriter(PATH + fileName + ".html"));

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            bufferedWriter.write(line);
        }
    }

    private String getDomain(Page page) {
        return page.getAddress()
                .replace("https://", "");
    }
}
