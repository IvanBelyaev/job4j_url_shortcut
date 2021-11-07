package ru.job4j.url.shortcut.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String login;
    private String password;
}
