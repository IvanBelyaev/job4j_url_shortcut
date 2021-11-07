package ru.job4j.url.shortcut.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RegistrationRequestDTO {

    @NotBlank(message = "site must not be empty")
    private String site;
}
