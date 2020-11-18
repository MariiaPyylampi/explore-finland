package swd20.exploreFinland.webcontroller;

import java.security.Principal;
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

import swd20.exploreFinland.domain.Activity;
import swd20.exploreFinland.domain.ActivityRepository;
import swd20.exploreFinland.domain.CategoryRepository;
import swd20.exploreFinland.domain.CityRepository;
import swd20.exploreFinland.domain.User;
import swd20.exploreFinland.domain.UserRepository;

@Controller
public class ActivityController {
	
	@Autowired
	private ActivityRepository activityRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private UserRepository userRepository;
	
	//RESTful to get all activities
	@GetMapping("/activities")
	public @ResponseBody List<Activity> activitiesRest() {
		return (List<Activity>) activityRepository.findAll();
	}
		
	//RESTful to get activity by id
	@GetMapping("/activities/{id}")
	public @ResponseBody Optional<Activity> findActivityRest(@PathVariable("id") Long id) {
		return activityRepository.findById(id);
	}
		
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	//näkymä kirjautumattomalle käyttäjälle
	@GetMapping("/")
	public String getAdventures1(Model model) {
		model.addAttribute("activities", activityRepository.findAll());
		return "adventures";
	}
		
	//näkymä kirjautumattomalle käyttäjälle
	@GetMapping("/adventures")
	public String getAdventures(Model model) {
		model.addAttribute("activities", activityRepository.findAll());
		return "adventures";
	}
	
	//käyttäjäkohtainen näkymä, eri näkymä USERille ja ADMINille
	@GetMapping("/useradventures")
	public String getUserAdventures(Model model, Principal principal) {
		String username = principal.getName();
		User user = userRepository.findByUsername(username);
		if (user.getRole() == "ADMIN") {
			model.addAttribute("activities", activityRepository.findAll());
		} else {
			model.addAttribute("activities", activityRepository.findByUser(user));
		}
		model.addAttribute("cities", cityRepository.findAll());
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("users", userRepository.findAll());
		return "useradventures";
	}
	
	//aktiviteetin lisäyslomake vain USERille
	@GetMapping("/addactivity")
	@PreAuthorize("hasAuthority('USER')")
	public String getActivityForm(Model model) {
		model.addAttribute("activity", new Activity());
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("cities", cityRepository.findAll());
		return "addActivity";
	}
		
	//uuden aktiviteetin tallennus vain USERille
	//epävalidilla syötteellä palauttaa takaisin aktiviteetin lisäyslomakkeelle
	@PostMapping("/savenewactivity")
	@PreAuthorize("hasAuthority('USER')")
	public String saveActivity(@Valid Activity activity, BindingResult bindingResult, Model model, Principal principal) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("categories", categoryRepository.findAll());
			model.addAttribute("cities", cityRepository.findAll());
			return "addActivity";
		} else {
			String username = principal.getName();
			User user = userRepository.findByUsername(username);
			activity.setUser(user);
			activityRepository.save(activity);
			return "redirect:useradventures";
		}
	}
	
	//aktiviteetin poisto vain USERille
	@GetMapping("/delete/{id}")
	@PreAuthorize("hasAuthority('USER')")
	public String deleteActivity(@PathVariable("id") Long id, Model model) {
		activityRepository.deleteById(id);
		return "redirect:../useradventures";
	}
	
	//aktiviteetin editointilomake vain USERille
	@GetMapping("/edit/{id}")
	@PreAuthorize("hasAuthority('USER')")
	public String editActivity(@PathVariable("id") Long id, Model model) {
		model.addAttribute("activity", activityRepository.findById(id));
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("cities", cityRepository.findAll());
		return "editActivity";
	}
	
	//muokatun aktiviteetin tallennus vain USERille
	//epävalidilla syötteellä palauttaa takaisin aktiviteetin muokkauslomakkeelle
	@PostMapping("/saveeditactivity")
	@PreAuthorize("hasAuthority('USER')")
	public String saveEditedActivity(@Valid Activity activity, BindingResult bindingResult, Model model, Principal principal) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("activity", activity);
			model.addAttribute("categories", categoryRepository.findAll());
			model.addAttribute("cities", cityRepository.findAll());
			return "editActivity";
		} else {
			String username = principal.getName();
			User user = userRepository.findByUsername(username);
			activity.setUser(user);
			activityRepository.save(activity);
			return "redirect:useradventures";
		}
	}
	
	//aktiviteetin suoritusmerkinnän muokkaus vain USERille
	@GetMapping("/editcomplete/{id}")
	@PreAuthorize("hasAuthority('USER')")
	public String completeActivity(@PathVariable("id") Long id) {
		Activity activity = activityRepository.findById(id).orElse(null);
		activity.setIsCompleted(!activity.getIsCompleted());
		activityRepository.save(activity);
		return "redirect:../useradventures";
	}
	
	
}
