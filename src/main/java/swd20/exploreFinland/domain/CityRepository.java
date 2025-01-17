package swd20.exploreFinland.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends CrudRepository<City, Long> {
	
	List<City> findByName(String name);

}
