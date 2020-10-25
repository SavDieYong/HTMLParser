package ru.savdieyong.HtmlParser.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @Author Savelyev A.D.
 */

@Data
@Entity
@Table(name = "pages")
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "address", unique = true)
    @Pattern(regexp = "https://[a-zA-Zа-яА-Я]+.[a-zA-Zа-яА-Я]+.[a-zA-Zа-яА-Я]+[a-zA-Zа-яА-Я._/]+")
    private String address;
}
