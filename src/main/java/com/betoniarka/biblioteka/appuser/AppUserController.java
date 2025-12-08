package com.betoniarka.biblioteka.appuser;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "appusers")
public class AppUserController {

    private final AppUserService service;

    public AppUserController(AppUserService service) {
        this.service = service;
    }

    @GetMapping
    public List<AppUserDto> getAppUsers() {
        return service.getAll().stream().map(appUser ->
           new AppUserDto(
                   appUser.getAppUserId(),
                   appUser.getEmail(),
                   appUser.getFirstname(),
                   appUser.getLastname())
        ).toList();
    }

    @PostMapping
    public void createAppUser(@RequestBody AppUser appUser) {
        service.create(appUser);
    }

}
