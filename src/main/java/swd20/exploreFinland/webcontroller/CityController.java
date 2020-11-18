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
	
	//uuden kaupungin lisäyslomake, vain sisäänkirjautuneelle
	@GetMapping("/addcity")
	@PreAuthorize("isAuthenticated()")
	public String addCity(Model model) {
		model.addAttribute("city", new City());
		return "addCity";
	}
	
	//uuden kaupungin tallennus, vain sisäänkirjautuneelle
	//epävalidilla syötteellä palauttaa takaisin lisäyslomakkeelle
	@PostMapping("/savecity")
	@PreAuthorize("isAuthenticated()")
	public String saveCity(@Valid City city, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "addCity";
		} else {
			cityRepository.save(city);
			return "redirect:useradventures";
		}
	}
	
	//kaupungin poisto, vain ADMINille
	@GetMapping("/deletecity/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String deleteCity(@PathVariable("id") Long id, Model model) {
		cityRepository.deleteById(id);
		return "redirect:../useradventures";
	}
	
	//kaupungin muokkauslomake, vain ADMINille
	@GetMapping("/editcity/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String editCity(@PathVariable("id") Long id, Model model) {
		model.addAttribute("city", cityRepository.findById(id));
		return "editCity";
	}
	
	//muokatun kaupungin tallennus, vain ADMINille
	//epävalidilla syötteellä palauttaa takaisin muokkauslomakkeelle
	@PostMapping("/saveeditcity")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String saveEditCity(@Valid City city, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("city", city);
			return "editCity";
		} else {
			cityRepository.save(city);
			return "redirect:useradventures";
		}
	}
	
	
}
