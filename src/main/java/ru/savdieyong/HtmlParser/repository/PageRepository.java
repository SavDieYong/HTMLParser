package ru.savdieyong.HtmlParser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.savdieyong.HtmlParser.model.Page;

/**
 * @Author Savelyev A.D.
 */

public interface PageRepository extends JpaRepository<Page, Long> {

    Page findByAddress(String address);
}
