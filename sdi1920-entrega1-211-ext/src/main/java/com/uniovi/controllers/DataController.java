package com.uniovi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.services.InsertSampleData;

@Controller
public class DataController {

    @Autowired
    private InsertSampleData dataService;

    /**
     * Method to reset the database.
     * @return
     */
    @RequestMapping(value = "/database/clear", method = RequestMethod.GET)
    public String setDB() {
        dataService.clearData();
        dataService.insertData();
        return "home";
    }

}
