package swd20.exploreFinland.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ActivityRepository extends CrudRepository<Activity, Long> {
	
	List<Activity> findByName(String Name);
	
	List<Activity> findByIsCompleted(boolean isCompleted);
}
