package ru.savdieyong.HtmlParser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.savdieyong.HtmlParser.model.Word;

/**
 * @Author Savelyev A.D.
 */

public interface WordRepository extends JpaRepository<Word, Long> {

    Word findByBody(String body);
}
