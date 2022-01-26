package com.empirica.tourismagency.repository;

import com.empirica.tourismagency.field.Tour;
import org.springframework.data.repository.CrudRepository;


import java.util.List;


public interface TourRepository extends CrudRepository<Tour, Long> {
    List<Tour> findByCategoryOrCategory2OrCategory3OrCategory4(String category, String category2, String category3, String category4);
    List<Tour> findByTitleContaining(String title);

}
