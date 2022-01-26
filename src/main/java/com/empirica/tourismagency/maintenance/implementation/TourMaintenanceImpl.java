package com.empirica.tourismagency.maintenance.implementation;

import java.util.ArrayList;
import java.util.List;

import com.empirica.tourismagency.field.Tour;
import com.empirica.tourismagency.repository.TourRepository;
import com.empirica.tourismagency.maintenance.TourMaintenance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TourMaintenanceImpl implements TourMaintenance {
	@Autowired
	private TourRepository tourRepository;

	public Tour save(Tour tour) {
		return tourRepository.save(tour);
	}
	public Tour findOne(Long id){
		return tourRepository.findById(id).orElse(null);
	}

	public void removeOne(Long id){
		tourRepository.deleteById(id);
	}
	
	public List<Tour> findAll() {

		List <Tour> tourList=(List<Tour>) tourRepository.findAll();
		List <Tour> activeTourList = new ArrayList<>();
		for(Tour tour : tourList) {
			if(tour.isActive()) {
				activeTourList.add(tour);
			}
		}
		return activeTourList;

	}
	

	public List <Tour> findByCategoryOrCategory2OrCategory3OrCategory4(String category, String category2, String category3, String category4) {
		{
			List <Tour> tourList = tourRepository.findByCategoryOrCategory2OrCategory3OrCategory4(category, category2, category3, category4);

			List <Tour> activeTourList = new ArrayList<>();

			for(Tour tour : tourList) {
				if(tour.isActive()) {
					activeTourList.add(tour);
				}
			}
			return activeTourList;
		}
	}

	public List <Tour> blurrySearch(String title){
		List <Tour> tourList = tourRepository.findByTitleContaining(title);
		List <Tour> activeTourList = new ArrayList<>();

		for(Tour tour : tourList) {
			if(tour.isActive()) {
				activeTourList.add(tour);
			}
		}
		return activeTourList;
	}
}
