package ru.job4j.url.shortcut.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.url.shortcut.service.SiteCredentialsService;
import ru.job4j.url.shortcut.dto.RegistrationRequestDTO;
import ru.job4j.url.shortcut.dto.RegistrationResponseDTO;

import javax.validation.Valid;

@RestController
public class RegistrationController {
    private SiteCredentialsService siteCredentialsService;

    public RegistrationController(SiteCredentialsService siteCredentialsService) {
        this.siteCredentialsService = siteCredentialsService;
    }

    @PostMapping("/registration")
    ResponseEntity<RegistrationResponseDTO> registration(
            @Valid @RequestBody RegistrationRequestDTO registrationRequestDTO) {
        RegistrationResponseDTO responseDTO = siteCredentialsService.registerSite(registrationRequestDTO);
        return new ResponseEntity<>(
                responseDTO,
                responseDTO.isRegistration() ? HttpStatus.OK : HttpStatus.BAD_REQUEST
        );
    }
}
