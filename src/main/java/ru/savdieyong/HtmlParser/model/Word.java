package ru.savdieyong.HtmlParser.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "words")
@NoArgsConstructor
@AllArgsConstructor
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Page page;

    @NonNull
    private String body;

    @Column(name = "word_count")
    private Long count;

}
