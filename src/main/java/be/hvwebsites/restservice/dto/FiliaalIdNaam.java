package be.hvwebsites.restservice.dto;

import be.hvwebsites.restservice.domain.Filiaal;

public class FiliaalIdNaam {
    private final long id;
    private final String naam;

    public FiliaalIdNaam(Filiaal filiaal) {
        this.id = filiaal.getId();
        this.naam = filiaal.getNaam();
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }
}
