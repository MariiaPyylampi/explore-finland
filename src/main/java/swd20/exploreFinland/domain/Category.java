package swd20.exploreFinland.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long categoryId;
	
	@Size(min=3, max=30)
	private String name;
	
	@JsonBackReference
	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "category") // category onetomany activity
	private List<Activity> activities;
	
	@PreRemove
	private void preRemove() {
	   activities.forEach(activity -> activity.setCategory(null));
	}
	
	public Category() {
		this.name = null;
	}
	
	public Category(String name) {
		this.name = name;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public String getName() {
		return name;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	@Override
	public String toString() {
		return "Category [categoryId=" + categoryId + ", name=" + name + "]";
	}
	
}
