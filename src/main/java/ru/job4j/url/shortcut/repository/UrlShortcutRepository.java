package ru.job4j.url.shortcut.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.url.shortcut.domain.UrlShortcut;

import java.util.List;
import java.util.Optional;

public interface UrlShortcutRepository extends CrudRepository<UrlShortcut, Integer> {

    Optional<UrlShortcut> findByCode(String code);

    Optional<UrlShortcut> findByUrl(String url);

    List<UrlShortcut> findAllByUrlContains(String site);

    @Modifying
    @Query("update UrlShortcut u set u.total = u.total + 1 where u.id = :id")
    void incrementTotalById(@Param("id") int id);
}
