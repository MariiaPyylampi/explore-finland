package swd20.exploreFinland;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import swd20.exploreFinland.webcontroller.ActivityController;
import swd20.exploreFinland.webcontroller.CategoryController;
import swd20.exploreFinland.webcontroller.CityController;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ExploreFinlandApplicationTests {

	@Autowired
	private ActivityController activityController;
	
	@Test
	void contextLoads() throws Exception {
		assertThat(activityController).isNotNull();
	}
	
	@Autowired
	private CategoryController categoryController;
	
	@Test
	void contextLoadsCategory() throws Exception {
		assertThat(categoryController).isNotNull();
	}
	
	@Autowired
	private CityController cityController;
	
	@Test
	void contextLoadsCity() throws Exception {
		assertThat(cityController).isNotNull();
	}

}
