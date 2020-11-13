package swd20.exploreFinland.webcontroller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import swd20.exploreFinland.domain.City;
import swd20.exploreFinland.domain.CityRepository;

@Controller
public class CityController {
	
	@Autowired
	private CityRepository cityRepository;
	
	@GetMapping("/addcity")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String addCity(Model model) {
		model.addAttribute("city", new City());
		return "addCity";
	}
	
	//RESTful
	@GetMapping("/cities")
	public @ResponseBody List<City> citiesRest() {
		return (List<City>) cityRepository.findAll();
	}
		
	//RESTful
	@GetMapping("/cities/{id}")
	public @ResponseBody Optional<City> findCityRest(@PathVariable("id") Long id) {
		return cityRepository.findById(id);
	}
	
	@PostMapping("/savecity")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String saveCity(@Valid City city, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "addCity";
		} else {
			cityRepository.save(city);
			return "redirect:useradventures";
		}
	}
	
	@GetMapping("/deletecity/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String deleteCity(@PathVariable("id") Long id, Model model) {
		cityRepository.deleteById(id);
		return "redirect:../useradventures";
	}
	
	
}
