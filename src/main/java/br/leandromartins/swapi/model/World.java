package br.leandromartins.swapi.model;

import org.springframework.data.annotation.Id;

public class World {

    @Id
    private String id;

    private String name;
    private String climate;
    private String terrain;
    private Integer apparitionsInFilms;

    public World() {
    }

    public World(String name, String climate, String terrain) {
        this.name = name;
        this.climate = climate;
        this.terrain = terrain;
    }


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClimate() {
        return climate;
    }

    public void setClimate(String climate) {
        this.climate = climate;
    }

    public String getTerrain() {
        return terrain;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    public Integer getApparitionsInFilms() {
        return apparitionsInFilms;
    }

    public void setApparitionsInFilms(Integer apparitionsInFilms) {
        this.apparitionsInFilms = apparitionsInFilms;
    }
}
