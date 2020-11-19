package swd20.exploreFinland;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import swd20.exploreFinland.domain.Activity;
import swd20.exploreFinland.domain.ActivityRepository;
import swd20.exploreFinland.domain.Category;
import swd20.exploreFinland.domain.CategoryRepository;
import swd20.exploreFinland.domain.City;
import swd20.exploreFinland.domain.CityRepository;
import swd20.exploreFinland.domain.User;
import swd20.exploreFinland.domain.UserRepository;

@SpringBootApplication
public class ExploreFinlandApplication {
	
	private static final Logger log = LoggerFactory.getLogger(ExploreFinlandApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ExploreFinlandApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(
			ActivityRepository activityRepository, 
			CategoryRepository categoryRepository, 
			CityRepository cityRepository,
			UserRepository userRepository) {
		return (args) -> {
		
			log.info("test data for Users");
			User user1 = new User("maija", "$2a$10$iq4Ht73XYILwNdS65vVorOgTOntEQlIX5jJ6Vsv0rAl31boO1tNbK", "USER");
			User user2 = new User("mikko", "$2a$10$mBKkiBe2x5DxrrU87pf67.nJ.XISjxfEmAQFDCuICfHHSrtaP1e7m", "USER");
			User user3 = new User("admin", "$2a$10$8zORpL/mFVhvgK/nUflwv.26qTTbqr74GLTdelGFKR25Lw1Y8YZaK", "ADMIN");
			
			userRepository.save(user1);
			userRepository.save(user2);
			userRepository.save(user3);
			
			log.info("test data for Categories");
			Category category1 = new Category("Museot");
			Category category2 = new Category("Tapahtumat");
			Category category3 = new Category("Elämykset");
			Category category4 = new Category("Muu");
			
			categoryRepository.save(category1);
			categoryRepository.save(category2);
			categoryRepository.save(category3);
			categoryRepository.save(category4);
			
			log.info("test data for Cities");
			City city1 = new City("Tampere");
			City city2 = new City("Kaustinen");
			City city3 = new City("Helsinki");
			//City city4 = new City("Muu");
			
			cityRepository.save(city1);
			cityRepository.save(city2);
			cityRepository.save(city3);	
			//cityRepository.save(city4);	
			
			log.info("test data for Activities");
			Activity activity1 = new Activity("Vapriikki", "Museokeskus Vapriikki - vuosisadan museo", false, user1, category1, city1);
			Activity activity2 = new Activity("Kaustinen Folk Music Festival", "Kansanmusiikkitapahtuma heinäkuussa 2021", true, user1, category2, city2);
			Activity activity3 = new Activity("Sompasauna", "Yleinen puusauna Kalasatamassa, auki 24/7", false, user2, category3, city3);
			Activity activity4 = new Activity("Ateneum", "Ateneum: Suomen taiteen tarina -näyttely", false, user2, category1, city3);
			
			activityRepository.save(activity1);
			activityRepository.save(activity2);
			activityRepository.save(activity3);
			activityRepository.save(activity4);
			
			log.info("list all activities");
			for(Activity activity : activityRepository.findAll()) {
				log.info(activity.toString());
			}
			
			log.info("list all categories");
			for(Category category : categoryRepository.findAll()) {
				log.info(category.toString());
			}
		};
	}

}
