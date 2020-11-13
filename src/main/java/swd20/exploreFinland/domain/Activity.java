package swd20.exploreFinland.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Activity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long activityId;
	
	@Size(min=5, max=30)
	private String name;
	private String desc;
	private boolean isCompleted;
	
	@ManyToOne // activity many to one user
	@JsonManagedReference
	@JoinColumn(name = "id")
	private User user;

	@ManyToOne //activity many to one category
	@JsonManagedReference //prevents endless loop
	@JoinColumn(name = "categoryId")
	private Category category;
	
	@ManyToOne
	@JsonManagedReference //prevents endless loop
	@JoinColumn(name = "cityId")
	private City city;
	
	public Activity() {
		this.name = null;
		this.desc = null;
		this.isCompleted = false;
		this.user = null;
		this.category = null;
		this.city = null;
	}
	
	public Activity(String name, String desc, boolean isCompleted, User user, Category category, City city) {
		this.name = name;
		this.desc = desc;
		this.isCompleted = isCompleted;
		this.user = user;
		this.category = category;
		this.city = city;
	}
	
	public Long getActivityId() {
		return activityId;
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	public boolean getIsCompleted() {
		return isCompleted;
	}
	
	public User getUser() {
		return user;
	}

	public Category getCategory() {
		return category;
	}
	
	public City getCity() {
		return city;
	}
	
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setIsCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public void setCity(City city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "Activity [activityId=" + activityId + ", name=" + name + ", desc=" + desc + ", isCompleted="
				+ isCompleted + ", user=" + user + ", category=" + category + ", city=" + city + "]";
	}

}
