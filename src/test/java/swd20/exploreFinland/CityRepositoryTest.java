package swd20.exploreFinland;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import swd20.exploreFinland.domain.City;
import swd20.exploreFinland.domain.CityRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CityRepositoryTest {
	
	@Autowired
	private CityRepository repository;
	
	@Test
	public void findByName() {
		List<City> cities = repository.findByName("Helsinki");
		assertThat(cities).hasSize(1);		
	}
	
	@Test
	public void createCity() {
		City city = new City("Porvoo");
		repository.save(city);
		assertThat(city.getCityId()).isNotNull();
	}
	
	@Test
	public void deleteCity() {
		List<City> cities = repository.findByName("Helsinki");
		repository.deleteById(cities.get(0).getCityId());
		List<City> end = repository.findByName("Helsinki");
		assertThat(end).hasSize(0);
	}

}

