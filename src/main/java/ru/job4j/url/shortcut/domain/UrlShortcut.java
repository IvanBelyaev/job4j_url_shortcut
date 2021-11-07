package ru.job4j.url.shortcut.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "url_shortcuts")
public class UrlShortcut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String url;

    private String code;

    private long total;

    public UrlShortcut(String url, String code) {
        this.url = url;
        this.code = code;
    }
}
