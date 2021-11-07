package ru.job4j.url.shortcut.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.url.shortcut.domain.SiteCredentials;

import java.util.Optional;

public interface SiteCredentialsRepository extends CrudRepository<SiteCredentials, Integer> {
    Optional<SiteCredentials> findByLogin(String login);
    Optional<SiteCredentials> findBySite(String site);
}
