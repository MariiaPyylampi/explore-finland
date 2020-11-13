package swd20.exploreFinland;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import swd20.exploreFinland.domain.Category;
import swd20.exploreFinland.domain.CategoryRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CategoryRepositoryTest {
	
	@Autowired
	private CategoryRepository repository;
	
	@Test
	public void findByName() {
		List<Category> categories = repository.findByName("Museot");
		assertThat(categories).hasSize(1);		
	}
	
	@Test
	public void createCategory() {
		Category category = new Category("Keikat");
		repository.save(category);
		assertThat(category.getCategoryId()).isNotNull();
	}
	
	@Test
	public void deleteCategory() {
		List<Category> categories = repository.findByName("Museot");
		repository.deleteById(categories.get(0).getCategoryId());
		List<Category> end = repository.findByName("Museot");
		assertThat(end).hasSize(0);
	}

}
