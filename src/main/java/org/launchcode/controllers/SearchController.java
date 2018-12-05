package org.launchcode.controllers;

import org.launchcode.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    // Display Search Form
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String search(Model model) {
        model.addAttribute("columns", ListController.columnChoices);
        return "search";
    }

    // Display Search Results without Params
    @RequestMapping(value = "results", method = RequestMethod.POST)
    public String searchResults(Model model,
            @RequestParam String searchType, @RequestParam String searchTerm) {
        model.addAttribute("columns", ListController.columnChoices);
        return "search";
    }

    // Display Search Results with Params
    @RequestMapping(value = "results", method = RequestMethod.GET)
    public String displaySearchResults(Model model, @RequestParam String searchType, @RequestParam String searchTerm) {
        // Display Search Form options
        model.addAttribute("columns", ListController.columnChoices);

        ArrayList<HashMap<String, String>> jobs;

        if (searchType.equals("all")) {
            jobs = JobData.findByValue(searchTerm);
            model.addAttribute("resultsTitle", "Search: All | Search Term: " + searchTerm);
            model.addAttribute("numOfResults", Integer.toString(jobs.size()));
            model.addAttribute("jobs", jobs);
        } else {
            jobs = JobData.findByColumnAndValue(searchType, searchTerm);
            model.addAttribute("resultsTitle", "Search: " +
                    ListController.columnChoices.get(searchType) + " | Search Term: " + searchTerm);
            model.addAttribute("numOfResults", Integer.toString(jobs.size()));

            model.addAttribute("jobs", jobs);
        }

        return "search";
    }

}
