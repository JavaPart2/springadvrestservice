package be.hvwebsites.restservice.services;

import be.hvwebsites.restservice.domain.Filiaal;
import be.hvwebsites.restservice.exceptions.FiliaalNietGevondenException;
import be.hvwebsites.restservice.repositories.FiliaalRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DefaultFiliaalService implements FiliaalService{
    private final FiliaalRepository repository;

    public DefaultFiliaalService(FiliaalRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Filiaal> findById(long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Filiaal> findAll() {
        return repository.findAll();
    }

    @Override
    public void create(Filiaal filiaal) {
        repository.save(filiaal);
    }

    @Override
    public void update(Filiaal filiaal) {
        repository.save(filiaal);
    }

    @Override
    public void delete(long id) {
        try {
            repository.deleteById(id);
        }catch (EmptyResultDataAccessException ex){
            throw new FiliaalNietGevondenException();
        }
    }
}
