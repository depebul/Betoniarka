package com.betoniarka.biblioteka.appuser;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService {

    private final AppUserRepository repository;

    public AppUserService(AppUserRepository repository) {
        this.repository = repository;
    }

    public List<AppUser> getAll() {
        return repository.findAll();
    }

    public void create(AppUser appUser) {
        repository.save(appUser);
    }

}
