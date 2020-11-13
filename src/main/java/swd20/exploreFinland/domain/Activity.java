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
	private Long id;
	
	@Size(min=5, max=30)
	private String name;
	private String desc;
	private boolean isCompleted;

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
		this.category = null;
		this.city = null;
	}
	
	public Activity(String name, String desc, boolean isCompleted, Category category, City city) {
		this.name = name;
		this.desc = desc;
		this.isCompleted = isCompleted;
		this.category = category;
		this.city = city;
	}

	public Long getId() {
		return id;
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

	public Category getCategory() {
		return category;
	}
	
	public City getCity() {
		return city;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public void setCity(City city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "Activity [id=" + id + ", name=" + name + ", desc=" + desc + ", isCompleted=" + isCompleted
				+ ", category=" + category + ", city=" + city + "]";
	}

}
