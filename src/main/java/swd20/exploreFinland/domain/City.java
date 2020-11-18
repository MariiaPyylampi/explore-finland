package swd20.exploreFinland.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.hibernate.action.internal.OrphanRemovalAction;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class City {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long cityId;
	
	@Size(min=5, max=30)
	private String name;
	
	@JsonBackReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "city")
	private List<Activity> activities;

	public City() {
		this.name = null;
	}
	
	public City(String name) {
		this.name = name;
	}

	public Long getCityId() {
		return cityId;
	}

	public String getName() {
		return name;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	@Override
	public String toString() {
		return "City [cityId=" + cityId + ", name=" + name + "]";
	}
}
