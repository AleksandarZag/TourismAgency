package com.empirica.tourismagency.controller;

import com.empirica.tourismagency.maintenance.TourMaintenance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@Controller
public class ResourceController {

    @Autowired
    private TourMaintenance tourMaintenance;

    @RequestMapping(value="/tour/removeList", method = RequestMethod.POST)
    public String removeList(
            @RequestBody ArrayList<String> tourIdList, Model model
    ){
        for(String id: tourIdList) {
            String tourId = id.substring(8);
            tourMaintenance.removeOne(Long.parseLong(tourId));
        }
        return "uspješno obrisano";
    }
}
