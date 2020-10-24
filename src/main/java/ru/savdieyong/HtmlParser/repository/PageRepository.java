package ru.savdieyong.HtmlParser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.savdieyong.HtmlParser.model.Page;

public interface PageRepository extends JpaRepository<Page, Long> {

    Page findByAddress(String address);
}
