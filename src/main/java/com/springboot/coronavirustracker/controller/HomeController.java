package com.springboot.coronavirustracker.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.springboot.coronavirustracker.Services.CoronaVirusService;
import com.springboot.coronavirustracker.models.LocationStats;

@Controller
public class HomeController {
	@Autowired
	CoronaVirusService coronaVirusService;

	@GetMapping("/")
	public String home(Model model) throws IOException {
		List<LocationStats> locationStats = coronaVirusService.fetchVirusData();
		int totalReportedCases = locationStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
		int totalNewCases = locationStats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
		model.addAttribute("locationStats", locationStats);
		model.addAttribute("totalReportedCases", totalReportedCases);
		model.addAttribute("totalNewCases", totalNewCases);
		return "home";
	}
}
