package ru.job4j.url.shortcut.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.url.shortcut.domain.SiteCredentials;
import ru.job4j.url.shortcut.domain.UrlShortcut;
import ru.job4j.url.shortcut.dto.ConvertRequestDTO;
import ru.job4j.url.shortcut.dto.ConvertResponseDTO;
import ru.job4j.url.shortcut.dto.StatisticResponseDTO;
import ru.job4j.url.shortcut.repository.SiteCredentialsRepository;
import ru.job4j.url.shortcut.repository.UrlShortcutRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UrlShortcutService {
    private UrlShortcutRepository urlShortcutRepository;
    private SiteCredentialsRepository siteCredentialsRepository;

    public UrlShortcutService(
            UrlShortcutRepository urlShortcutRepository,
            SiteCredentialsRepository siteCredentialsRepository) {
        this.urlShortcutRepository = urlShortcutRepository;
        this.siteCredentialsRepository = siteCredentialsRepository;
    }

    @Transactional
    public ConvertResponseDTO convertUrl(ConvertRequestDTO convertRequestDTO) {
        String url = convertRequestDTO.getUrl();
        checkUrl(url);
        UrlShortcut urlShortcut = urlShortcutRepository.findByUrl(url)
                .orElseGet(() -> {
                    String code = RandomStringUtils.random(8, true, true);
                    return urlShortcutRepository.save(new UrlShortcut(url, code));
                });
        return new ConvertResponseDTO(urlShortcut.getCode());
    }

    @Transactional
    public String redirectToUrl(String code) {
        UrlShortcut urlShortcut = urlShortcutRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("url with this code doesn't exist"));
        checkUrl(urlShortcut.getUrl());
        urlShortcutRepository.incrementTotalById(urlShortcut.getId());
        return urlShortcut.getUrl();
    }

    @Transactional
    public List<StatisticResponseDTO> getStatistic() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        SiteCredentials siteCredentials = siteCredentialsRepository.findByLogin(login).get();
        String site = siteCredentials.getSite();
        return urlShortcutRepository.findAllByUrlContains(site).stream()
                .map(urlShortcut -> (new StatisticResponseDTO(urlShortcut.getUrl(), urlShortcut.getTotal())))
                .collect(Collectors.toList());
    }

    private void checkUrl(String url) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        SiteCredentials siteCredentials = siteCredentialsRepository.findByLogin(login).get();
        String site = siteCredentials.getSite();
        boolean isUrlValid = url.contains(site);
        if (!isUrlValid) {
            throw new IllegalArgumentException("the url must belong to the site " + site);
        }
    }
}
