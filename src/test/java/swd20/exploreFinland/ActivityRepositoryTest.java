package swd20.exploreFinland;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import swd20.exploreFinland.domain.Activity;
import swd20.exploreFinland.domain.ActivityRepository;
import swd20.exploreFinland.domain.User;
import swd20.exploreFinland.domain.UserRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ActivityRepositoryTest {
	
	@Autowired
	private ActivityRepository repository;
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void findByNameShouldReturnActivity() {
		List<Activity> activities = repository.findByName("Ateneum");
		assertThat(activities).hasSize(1);
		assertThat(activities.get(0).getDesc()).isEqualTo("Ateneum: Suomen taiteen tarina -näyttely");
	}
	
	@Test
	public void findByIsCompletedShouldReturnActivity() {
		List<Activity> activities = repository.findByIsCompleted(true);
		assertThat(activities).hasSize(1);
		assertThat(activities.get(0).getName()).isEqualTo("Kaustinen Folk Music Festival");
	}
	
	@Test
	public void findByUserShouldReturnActivity() {
		User user = userRepository.findByUsername("maija"); 
		List<Activity> activities = repository.findByUser(user);
		assertThat(activities).hasSize(2);
		assertThat(activities.get(0).getName()).isEqualTo("Vapriikki");
	}
	
	@Test
	public void createNewActivity() {
		Activity activity = new Activity("Fiskarin Panimo", "Fiskarin Panimo & Noita Winery", false, null, null, null);
		repository.save(activity);
		assertThat(activity.getActivityId()).isNotNull();
	}
	
	@Test
	public void deleteActivity() {
		List<Activity> actitivities = repository.findByName("Ateneum");
		repository.deleteById(actitivities.get(0).getActivityId());
		List<Activity> end = repository.findByName("Ateneum");
		assertThat(end).hasSize(0);
	}

}
