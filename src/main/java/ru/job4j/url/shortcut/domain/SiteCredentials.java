package ru.job4j.url.shortcut.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "site_credentials")
public class SiteCredentials implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String site;

    private String login;

    private String password;

    public SiteCredentials(String site, String login, String password) {
        this.site = site;
        this.login = login;
        this.password = password;
    }

    @Override
    public String getAuthority() {
        return "USER";
    }
}
