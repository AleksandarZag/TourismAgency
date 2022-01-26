package com.empirica.tourismagency.maintenance;


import com.empirica.tourismagency.field.Tour;

import java.util.List;

public interface TourMaintenance {

	Tour save(Tour tour);

	List<Tour> findAll();

	Tour findOne(Long id);

	void removeOne(Long id);

	List <Tour> findByCategoryOrCategory2OrCategory3OrCategory4(String category, String category2, String category3, String category4);
	List <Tour> blurrySearch(String title);

}
