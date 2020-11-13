package swd20.exploreFinland.webcontroller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/*")
	public String getAdventures1(Model model) {
		model.addAttribute("activities", activityRepository.findAll());
		return "adventures";
	}
		
	@GetMapping("/adventures")
	public String getAdventures(Model model) {
		model.addAttribute("activities", activityRepository.findAll());
		return "adventures";
	}
	
	@GetMapping("/useradventures")
	public String getUserAdventures(Model model, Principal principal) {
		String username = principal.getName();
		User user = userRepository.findByUsername(username);
		model.addAttribute("activities", activityRepository.findByUser(user));
		model.addAttribute("cities", cityRepository.findAll());
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("users", userRepository.findAll());
		return "useradventures";
	}
	
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
	
	@GetMapping("/addactivity")
	public String getActivityForm(Model model) {
		model.addAttribute("activity", new Activity());
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("cities", cityRepository.findAll());
		return "addActivity";
	}
		
	@PostMapping("/saveactivity")
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
	
	@GetMapping("/delete/{id}")
	public String deleteActivity(@PathVariable("id") Long id, Model model) {
		activityRepository.deleteById(id);
		return "redirect:../useradventures";
	}
	
	@GetMapping("/edit/{id}")
	public String editActivity(@PathVariable("id") Long id, Model model) {
		model.addAttribute("activity", activityRepository.findById(id));
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("cities", cityRepository.findAll());
		return "editActivity";
	}
	
	@GetMapping("/editcomplete/{id}")
	public String completeActivity(@PathVariable("id") Long id) {
		Activity activity = activityRepository.findById(id).orElse(null);
		activity.setIsCompleted(!activity.getIsCompleted());
		activityRepository.save(activity);
		return "redirect:../useradventures";
	}
	
	
}
