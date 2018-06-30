package br.leandromartins.swapi.tests;

import br.leandromartins.swapi.config.TestConfig;
import br.leandromartins.swapi.model.World;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestConfig.class)
public class WorldEndpointsTests {

    @Autowired
    private MongoTemplate mongo;

    @Autowired
    private TestRestTemplate rest;

    @LocalServerPort
    private String port;

    @After
    public void dropCollection() {
        this.mongo.dropCollection(World.class);
    }

    @Test
    public void createsNewPlanet() throws URISyntaxException {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("name", "Yavin IV");
        map.add("climate", "temperate, tropical");
        map.add("terrain", "jungle, rainforests");
        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(new URI("http://localhost:" + port + "/world"))
                .body(map);
        ResponseEntity<Object> responseEntity = this.rest.exchange(requestEntity, Object.class);
        Assert.assertEquals(201, responseEntity.getStatusCodeValue());
        List<World> worlds = this.mongo.find(new Query(Criteria.where("name").is("Yavin IV")), World.class);
        Assert.assertEquals(1, worlds.size());
        Assert.assertEquals("Yavin IV", worlds.get(0).getName());
    }

    @Test
    public void listsPlanets() throws URISyntaxException {
        List<World> worlds = Arrays.asList(
                new World("Tatooine", "arid", "desert"),
                new World("Alderaan", "standard", "grasslands, mountains"),
                new World("Yavin IV", "jungle, rainforests", "jungle, rainforests")
        );
        this.mongo.insert(worlds, World.class);
        URI uri = new URI("http://localhost:" + port + "/world");
        ResponseEntity<World[]> responseEntity = this.rest.getForEntity(uri, World[].class);
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertEquals(3, responseEntity.getBody().length);
    }

    @Test
    public void findsPlanet() throws URISyntaxException {
        this.mongo.insert(new World("Tatooine", "arid", "desert"));
        List<World> worlds = this.mongo.findAll(World.class);
        Assert.assertEquals(1, worlds.size());
        String id = worlds.get(0).getId();
        URI uri = new URI("http://localhost:" + port + "/world/" + id);
        ResponseEntity<World> responseEntity = this.rest.getForEntity(uri, World.class);
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertEquals(id, responseEntity.getBody().getId());
    }

    @Test
    public void listPlanetsByName() throws URISyntaxException {
        List<World> worlds = Arrays.asList(
                new World("Tatooine", "arid", "desert"),
                new World("Alderaan", "standard", "grasslands, mountains"),
                new World("Yavin IV", "jungle, rainforests", "jungle, rainforests")
        );
        this.mongo.insert(worlds, World.class);
        URI uri = new URI("http://localhost:" + port + "/world?name=Alderaan");
        ResponseEntity<World[]> responseEntity = this.rest.getForEntity(uri, World[].class);
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertEquals(1, responseEntity.getBody().length);
        Assert.assertEquals("standard", responseEntity.getBody()[0].getClimate());
    }

    @Test
    public void removesPlanet() throws URISyntaxException {
        this.mongo.insert(new World("Tatooine", "arid", "desert"));
        List<World> worlds = this.mongo.findAll(World.class);
        Assert.assertEquals(1, worlds.size());
        this.rest.delete(new URI("http://localhost:" + port + "/world/" + worlds.get(0).getId()));
        List<World> worlds2 = this.mongo.findAll(World.class);
        Assert.assertEquals(0, worlds2.size());
    }

}
