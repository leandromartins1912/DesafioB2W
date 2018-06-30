package br.leandromartins.swapi.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.leandromartins.swapi.model.World;

@Repository
public interface WorldRepository extends MongoRepository<World, String> {

    public List<World> findByName(String name);

}
