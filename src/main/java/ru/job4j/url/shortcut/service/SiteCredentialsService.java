package ru.job4j.url.shortcut.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.url.shortcut.repository.SiteCredentialsRepository;
import ru.job4j.url.shortcut.domain.SiteCredentials;
import ru.job4j.url.shortcut.dto.RegistrationRequestDTO;
import ru.job4j.url.shortcut.dto.RegistrationResponseDTO;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class SiteCredentialsService implements UserDetailsService {
    private SiteCredentialsRepository siteCredentialsRepository;

    public SiteCredentialsService(SiteCredentialsRepository siteCredentialsRepository) {
        this.siteCredentialsRepository = siteCredentialsRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        SiteCredentials siteCredentials = siteCredentialsRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException(login));
        return new User(
                siteCredentials.getLogin(),
                siteCredentials.getPassword(),
                List.of(siteCredentials)
        );
    }

    @Transactional
    public RegistrationResponseDTO registerSite(RegistrationRequestDTO registrationRequestDTO) {
        String site = registrationRequestDTO.getSite();
        AtomicBoolean registration = new AtomicBoolean(false);
        SiteCredentials siteCredentials = siteCredentialsRepository.findBySite(site)
                .orElseGet(() -> {
                    String login = RandomStringUtils.random(8, true, true);
                    String password = "{noop}" + RandomStringUtils.random(8, true, true);
                    registration.set(true);
                    return siteCredentialsRepository.save(new SiteCredentials(site, login, password));
                });
        return new RegistrationResponseDTO(
                registration.get(), siteCredentials.getLogin(), siteCredentials.getPassword().substring(6));
    }
}
