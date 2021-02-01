package be.hvwebsites.restservice.controllers;

import be.hvwebsites.restservice.domain.Filiaal;
import be.hvwebsites.restservice.services.FiliaalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/filialen")
public class FiliaalController {
    private final FiliaalService service;

    public FiliaalController(FiliaalService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    Filiaal get(@PathVariable long id){
        return service.findById(id).get();
    }
}
