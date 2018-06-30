package br.leandromartins.swapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.leandromartins.swapi.model.World;
import br.leandromartins.swapi.repository.WorldRepository;

@RestController
@RequestMapping("/planets")
public class WorldController {

    @Autowired
    private WorldRepository worldRepository;
    
    
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody World world) {
        worldRepository.insert(world);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping
    public List<World> read(@Param("name") String name) {
        if (name != null) {
            return worldRepository.findByName(name);
        }
        return worldRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<World> find(@PathVariable("id") String id) {
        return worldRepository.findById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        worldRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
