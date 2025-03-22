package com.kodnest.projecttunehub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * NavController class that handles navigation-related requests.
 */
@Controller
public class NavController {

	/**
	 * Handles the GET request for the login page.
	 *
	 * @return The name of the login view.
	 */
	@GetMapping(path = "/login")
	public String loginUser() {
		return "Login";
	}

	@GetMapping(path = "/home")
	public String indexPage() {
		return "index";
	}

	/**
	 * Handles the GET request for the registration page.
	 *
	 * @return The name of the registration view.
	 */
	@GetMapping(path = "/registration")
	public String registerUser() {
		return "Registration";
	}

	@GetMapping(path = "/newSong")
	public String newSong() {
		return "NewSong";
	}

}