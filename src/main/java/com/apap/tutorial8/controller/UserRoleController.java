package com.apap.tutorial8.controller;

import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.service.UserRoleService;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserRoleController {
	@Autowired
	private UserRoleService userService;
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	private String addUserSubmit(@ModelAttribute UserRoleModel user, Model model) {
		userService.addUser(user);
		return "home";
	}
	
	@RequestMapping("/updatePassword")
	public String updatePassword() {
		return "update-password";
	}
	
	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	private String updatePasswordSubmit(Principal principal, String passwordLama, String passwordBaru, String passwordBaruKonfirmasi, Model model) {
		UserRoleModel user = userService.findByUsername(principal.getName());
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		if(passwordEncoder.matches(passwordLama, user.getPassword())) {
			if(passwordBaru.equals(passwordBaruKonfirmasi)) {
				user.setPassword(passwordBaru);
				userService.addUser(user);
				model.addAttribute("message", "Password Berhasil Diubah!");
			} else {
				model.addAttribute("message", "Password Baru Tidak Sama Dengan Konfirmasi Password Baru");
			}
		} else {
			model.addAttribute("message", "Password Lama Tidak Sesuai!");
		}
        return "update-password";
	}
}
