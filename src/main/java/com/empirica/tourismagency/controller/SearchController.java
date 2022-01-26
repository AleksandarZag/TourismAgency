package com.empirica.tourismagency.controller;

import com.empirica.tourismagency.field.Tour;
import com.empirica.tourismagency.field.User;
import com.empirica.tourismagency.maintenance.TourMaintenance;
import com.empirica.tourismagency.maintenance.UserMaintenance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class SearchController {
	@Autowired
	private UserMaintenance userMaintenance;

	@Autowired
	private TourMaintenance tourMaintenance;

	@RequestMapping("/searchByCategory")
	public String searchByCategory(
			@RequestParam("category") String category, @RequestParam("category2") String category2,
			@RequestParam("category3") String category3, @RequestParam("category4") String category4,
			Model model, Principal principal
			){
		if(principal!=null) {
			String username = principal.getName();
			User user = userMaintenance.findByUsername(username);
			model.addAttribute("user", user);
		}


		List<Tour> tourList = tourMaintenance.findByCategoryOrCategory2OrCategory3OrCategory4(category,category2,category3,category4);

		model.addAttribute("tourList", tourList);
		return "tourguide";
	}

	@RequestMapping("/searchTour")
	public String searchTour(
			@ModelAttribute("keyword") String keyword,
			Principal principal, Model model
			) {
		if(principal!=null) {
			String username = principal.getName();
			User user = userMaintenance.findByUsername(username);
			model.addAttribute("user", user);
		}

		List<Tour> tourList = tourMaintenance.blurrySearch(keyword);

		if (tourList.isEmpty()) {
			model.addAttribute("emptyList", true);
			return "tourguide";
		}

		model.addAttribute("tourList", tourList);

		return "tourguide";
	}
}
