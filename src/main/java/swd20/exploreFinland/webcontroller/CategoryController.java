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

import swd20.exploreFinland.domain.Category;
import swd20.exploreFinland.domain.CategoryRepository;

@Controller
public class CategoryController {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	//RESTful
	@GetMapping("categories")
	public @ResponseBody List<Category> categoriesRest() {
		return (List<Category>) categoryRepository.findAll();
	}
	
	//RESTful
	@GetMapping("categories/{id}")
	public @ResponseBody Optional<Category> findCategoryRest(@PathVariable("id") Long id) {
		return categoryRepository.findById(id);
	}
	
	@GetMapping("/addcategory")
	public String addCategory(Model model) {
		model.addAttribute("category", new Category());
		return "addCategory";
	}
	
	@PostMapping("/savecategory")
	public String saveCategory(@Valid Category category, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "addCategory";
		} else {
			categoryRepository.save(category);
			return "redirect:useradventures";
		}
	}
	
	@GetMapping("/deletecategory/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String deleteCategory(@PathVariable("id") Long id, Model model) {
		categoryRepository.deleteById(id);
		return "redirect:../useradventures";
	}
	
	@GetMapping("/editcategory/{id}")
	public String editCategory(@PathVariable("id") Long id, Model model) {
		model.addAttribute("category", categoryRepository.findById(id));
		return "editCategory";
	}
	
	@PostMapping("/saveeditcategory")
	public String saveeditCategory(@Valid Category category, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("category", category);
			return "editCategory";
		} else {
			categoryRepository.save(category);
			return "redirect:useradventures";
		}
	}

}
