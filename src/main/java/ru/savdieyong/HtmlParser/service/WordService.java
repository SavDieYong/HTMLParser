package ru.savdieyong.HtmlParser.service;

import org.springframework.stereotype.Service;
import ru.savdieyong.HtmlParser.model.Word;
import ru.savdieyong.HtmlParser.repository.WordRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WordService {

    private final WordRepository wordRepository;

    public WordService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public Word findById(Long id) {
        return wordRepository.getOne(id);
    }

    public List<Word> findAll() {
        return wordRepository.findAll();
    }

    public void save(Word word) {
        wordRepository.save(word);
    }

    public void deleteById(Long id) {
        wordRepository.deleteById(id);
    }

    public List<Word> findByPageId(Long id) {
        return wordRepository.findAll()
                .stream()
                .filter(page -> page.getPage().getId().equals(id))
                .collect(Collectors.toList());
    }

}
