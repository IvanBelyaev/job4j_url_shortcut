package ru.job4j.url.shortcut.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ConvertRequestDTO {
    @NotBlank(message = "url must not be empty")
    private String url;
}
