package com.empirica.tourismagency.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.empirica.tourismagency.field.Tour;
import com.empirica.tourismagency.maintenance.TourMaintenance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/tour")
public class TourController {

	@Autowired
	private TourMaintenance tourMaintenance;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addTour(Model model) {
		Tour tour = new Tour();
		model.addAttribute("tour", tour);
		return "addTour";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addTourPost(@ModelAttribute("tour") Tour tour, HttpServletRequest request) {
		tourMaintenance.save(tour);

		MultipartFile tourImage = tour.getTourImage();

		try {
			byte[] bytes = tourImage.getBytes();
			String name = tour.getId() + ".png";
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/static/image/Tour/" + name)));
			stream.write(bytes);
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}


		MultipartFile tourImage2 = tour.getTourImage2();

		try {
			byte[] bytes = tourImage2.getBytes();
			String name = tour.getId()*20 + ".png";
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/static/image/Tour/" + name)));
			stream.write(bytes);
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		MultipartFile tourImage3 = tour.getTourImage3();

		try {
			byte[] bytes = tourImage3.getBytes();
			String name = tour.getId()*30 + ".png";
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/static/image/Tour/" + name)));
			stream.write(bytes);
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		MultipartFile tourImage4 = tour.getTourImage4();

		try {
			byte[] bytes = tourImage4.getBytes();
			String name = tour.getId()*40 + ".png";
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/static/image/Tour/" + name)));
			stream.write(bytes);
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		MultipartFile tourImage5 = tour.getTourImage5();

		try {
			byte[] bytes = tourImage5.getBytes();
			String name = tour.getId()*50 + ".png";
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/static/image/Tour/" + name)));
			stream.write(bytes);
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:tourList";
	}

	@RequestMapping("/tourInfo")
	public String tourInfo(@RequestParam("id") Long id, Model model) {
		Tour tour = tourMaintenance.findOne(id);
		model.addAttribute("tour", tour);

		return "tourInfo";
	}

	@RequestMapping("/updateTour")
	public String updateTour(@RequestParam("id") Long id, Model model) {
		Tour tour = tourMaintenance.findOne(id);
		model.addAttribute("tour", tour);

		return "updateTour";
	}

	@RequestMapping(value="/updateTour", method=RequestMethod.POST)
	public String updateTourPost(@ModelAttribute("tour") Tour tour, HttpServletRequest request) {
		tourMaintenance.save(tour);

		MultipartFile tourImage = tour.getTourImage();

		if(!tourImage.isEmpty()) {
			try {
				byte[] bytes = tourImage.getBytes();
				String name = tour.getId() + ".png";

                Files.delete(Paths.get("src/main/resources/static/image/Tour/"+name));

                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(new File("src/main/resources/static/image/Tour/" + name)));
                stream.write(bytes);
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


		MultipartFile tourImage2 = tour.getTourImage2();

		if(!tourImage2.isEmpty()) {
			try {
				byte[] bytes = tourImage2.getBytes();
				String name = tour.getId()*20 + ".png";

				Files.delete(Paths.get("src/main/resources/static/image/Tour/"+name));

				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File("src/main/resources/static/image/Tour/" + name)));
				stream.write(bytes);
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		MultipartFile tourImage3 = tour.getTourImage3();

		if(!tourImage3.isEmpty()) {
			try {
				byte[] bytes = tourImage3.getBytes();
				String name = tour.getId()*30 + ".png";

				Files.delete(Paths.get("src/main/resources/static/image/Tour/"+name));

				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File("src/main/resources/static/image/Tour/" + name)));
				stream.write(bytes);
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		MultipartFile tourImage4 = tour.getTourImage4();

		if(!tourImage4.isEmpty()) {
			try {
				byte[] bytes = tourImage4.getBytes();
				String name = tour.getId()*40 + ".png";

				Files.delete(Paths.get("src/main/resources/static/image/Tour/"+name));

				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File("src/main/resources/static/image/Tour/" + name)));
				stream.write(bytes);
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		MultipartFile tourImage5 = tour.getTourImage5();

		if(!tourImage5.isEmpty()) {
			try {
				byte[] bytes = tourImage5.getBytes();
				String name = tour.getId()*50 + ".png";

				Files.delete(Paths.get("src/main/resources/static/image/Tour/"+name));

				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File("src/main/resources/static/image/Tour/" + name)));
				stream.write(bytes);
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return "redirect:/tour/tourInfo?id="+tour.getId();
	}

	@RequestMapping("/tourList")
	public String tourList(Model model) {
		List<Tour> tourList = tourMaintenance.findAll();
		model.addAttribute("tourList", tourList);
		return "tourList";
		
	}
	@RequestMapping(value="/remove", method=RequestMethod.POST)
	public String remove(
			@ModelAttribute("id") String id,
			Model model
	) {
		tourMaintenance.removeOne(Long.parseLong(id.substring(8)));
		List<Tour> tourList = tourMaintenance.findAll();
		model.addAttribute("tourList", tourList);

		return "redirect:/tour/tourList";
	}
}
