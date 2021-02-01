package be.hvwebsites.restservice.controllers;

import be.hvwebsites.restservice.domain.Filiaal;
import be.hvwebsites.restservice.dto.FiliaalIdNaam;
import be.hvwebsites.restservice.exceptions.FiliaalNietGevondenException;
import be.hvwebsites.restservice.services.FiliaalService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/filialen")
@ExposesResourceFor(Filiaal.class)
public class FiliaalController {
    private final FiliaalService service;
    private final EntityLinks entityLinks;

    public FiliaalController(FiliaalService service, EntityLinks entityLinks) {
        this.service = service;
        this.entityLinks = entityLinks;
    }

    @GetMapping("{id}")
    EntityModel<Filiaal> get(@PathVariable long id){
        Optional<Filiaal> filiaal = service.findById(id);
        if (filiaal.isPresent()){
            var filiaal2 = filiaal.get();
            var model = new EntityModel<>(filiaal2);
            model.add(entityLinks.linkToItemResource(Filiaal.class, filiaal2.getId()));
            model.add(entityLinks.linkForItemResource(Filiaal.class, filiaal2.getId())
                    .slash("werknemers").withRel("werknemers"));
            return model;
        }
        throw new FiliaalNietGevondenException();
    }

    @ExceptionHandler({FiliaalNietGevondenException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void filiaalNietGevonden(){

    }

    @DeleteMapping("{id}")
    void delete(@PathVariable long id){
        service.delete(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HttpHeaders create(@RequestBody @Valid Filiaal filiaal){
        service.create(filiaal);
        var link = entityLinks.linkToItemResource(Filiaal.class, filiaal.getId());
        var headers = new HttpHeaders();
        headers.setLocation(link.toUri());
        return headers;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Map<String, String> verkeerdeData(MethodArgumentNotValidException ex){
        var fouten = new HashMap<String, String>();
        for (var error: ex.getBindingResult().getFieldErrors()){
            fouten.put(error.getField(), error.getDefaultMessage());
        }
        return fouten;
    }

    @PutMapping("{id}")
    void put(@RequestBody @Valid Filiaal filiaal){
        service.update(filiaal);
    }

    @GetMapping
    List<EntityModel<FiliaalIdNaam>> getAll(){
        var filialen = service.findAll();
        var idsEnNamen = new ArrayList<EntityModel<FiliaalIdNaam>>();

        for (var filiaal: filialen){
            var idEnNaam = new FiliaalIdNaam(filiaal);
            var model = new EntityModel<>(idEnNaam);
            model.add(entityLinks.linkToItemResource(Filiaal.class, filiaal.getId()));
            idsEnNamen.add(model);
        }
        return idsEnNamen;
    }
}
