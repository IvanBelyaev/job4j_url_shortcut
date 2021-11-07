package ru.job4j.url.shortcut.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.url.shortcut.service.UrlShortcutService;
import ru.job4j.url.shortcut.dto.ConvertRequestDTO;
import ru.job4j.url.shortcut.dto.ConvertResponseDTO;
import ru.job4j.url.shortcut.dto.StatisticResponseDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.util.List;

@RestController
public class UrlShortcutController {
    private UrlShortcutService urlShortcutService;

    public UrlShortcutController(UrlShortcutService urlShortcutService) {
        this.urlShortcutService = urlShortcutService;
    }

    @PostMapping("/convert")
    public ResponseEntity<ConvertResponseDTO> convertUrl(
            @Valid @RequestBody ConvertRequestDTO convertRequestDTO) {
        return ResponseEntity.ok(urlShortcutService.convertUrl(convertRequestDTO));
    }

    @GetMapping("/redirect/{code}")
    public ResponseEntity<Void> redirectToUrl(
            @NotBlank(message = "code must not be empty") @PathVariable String code) {
        String url = urlShortcutService.redirectToUrl(code);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url)).build();
    }

    @GetMapping("/statistic")
    public ResponseEntity<List<StatisticResponseDTO>> getStatistic() {
        return ResponseEntity.ok(urlShortcutService.getStatistic());
    }
}
