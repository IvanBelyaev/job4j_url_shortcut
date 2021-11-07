package ru.job4j.url.shortcut.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatisticResponseDTO {
    private String url;
    private long total;
}
