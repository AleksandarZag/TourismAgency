package com.empirica.tourismagency.controller;

import com.empirica.tourismagency.field.*;
import com.empirica.tourismagency.field.security.PasswordResetToken;
import com.empirica.tourismagency.field.security.Role;
import com.empirica.tourismagency.field.security.UserRole;
import com.empirica.tourismagency.maintenance.*;
import com.empirica.tourismagency.maintenance.implementation.UserSecurityMaintenance;
import com.empirica.tourismagency.applicability.MailConstructor;
import com.empirica.tourismagency.applicability.SecurityUtility;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.*;


@Controller
public class HomeController {

	@Autowired
	private JavaMailSender mailSender;


	@Autowired
	private MailConstructor mailConstructor;

	@Autowired
	private UserMaintenance userMaintenance;

	@Autowired
	private UserSecurityMaintenance userSecurityMaintenance;

	@Autowired
	private TourMaintenance tourMaintenance;

	@Autowired
	private UserPaymentMaintenance userPaymentMaintenance;

	@Autowired
	private ReservationItemMaintenance reservationItemMaintenance;

	@Autowired
	private OrderMaintenance orderMaintenance;



	@RequestMapping("/")

	public String index() {
		return "index";
	}

	@RequestMapping("/home")

	public String home() {
		return "home";
	}

	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("classActiveLogin", true);
		return "myAccount";
	}
	@RequestMapping("/admin")
	public String admin() {
		return "admin";
	}


	@RequestMapping("/hours")
	public String hours() {
		return "hours";
	}
	@RequestMapping("/faq")
	public String faq() {
		return "faq";
	}

	@RequestMapping("/tourguide")
	public String tourguide(Model model, Principal principal) {
		if(principal != null) {
			String username = principal.getName();
			User user = userMaintenance.findByUsername(username);
			model.addAttribute("user", user);
		}

		List<Tour> tourList = tourMaintenance.findAll();
		model.addAttribute("tourList", tourList);
		model.addAttribute("activeAll",true);

		return "tourguide";
	}
	@RequestMapping("/tourDetail")

	public String tourDetail(
			@PathParam("id") Long id, Model model, Principal principal
			) {
		if(principal != null) {
			String username = principal.getName();
			User user = userMaintenance.findByUsername(username);
			model.addAttribute("user", user);
		}

		Tour tour = tourMaintenance.findOne(id);

		model.addAttribute("tour", tour);

		List<Integer> qtyList = Arrays.asList(1,2,3,4,5,6,7,8,9,10);

		model.addAttribute("qtyList", qtyList);
		model.addAttribute("qty", 1);

		return "tourDetail";
	}
	@RequestMapping("/forgetPassword")

	public String forgetPassword(
            HttpServletRequest request,
            @ModelAttribute("email") String email,
	        Model model) {

		model.addAttribute("classActiveForgetPassword", true);
		User user = userMaintenance.findByEmail(email);

        if(user == null) {
            model.addAttribute("emailNotExist", true);
            return "myAccount";
        }
        String password = SecurityUtility.randomPassword();
        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
        user.setPassword(encryptedPassword);


        userMaintenance.save(user);
        String token = UUID.randomUUID().toString();
        userMaintenance.createPasswordResetTokenForUser(user, token);

        String appUrl = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();

        SimpleMailMessage newEmail = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user, password);
        mailSender.send(newEmail);

        model.addAttribute("forgetPasswordEmailSent", "true");
		return "myAccount";
	}
	@RequestMapping("/myProfile")
	public String myProfile(Model model, Principal principal){
		User user = userMaintenance.findByUsername(principal.getName());
		model.addAttribute("user", user);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
	    model.addAttribute("orderList", user.getOrderList());

		model.addAttribute("listOfCreditCards", true);
		return "myProfile";
	}

	@RequestMapping("/listOfCreditCards")
	public String listOfCreditCards(
			Model model, Principal principal, HttpServletRequest request
	) {
		User user = userMaintenance.findByUsername(principal.getName());
		model.addAttribute("user", user);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("orderList", user.getOrderList());
		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("classActiveBilling", true);

		return "myProfile";
	}

	@RequestMapping("/addNewCreditCard")
	public String addNewCreditCard(
			Model model, Principal principal
	) {
		User user = userMaintenance.findByUsername(principal.getName());
		model.addAttribute("user", user);
		model.addAttribute("addNewCreditCard", true);
		model.addAttribute("classActiveBilling", true);

		UserBilling userBilling = new UserBilling();
		UserPayment userPayment = new UserPayment();

		model.addAttribute("userBilling", userBilling);
		model.addAttribute("userPayment", userPayment);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("orderList", user.getOrderList());

		return "myProfile";
	}

	@RequestMapping(value="/addNewCreditCard", method=RequestMethod.POST)
	public String addNewCreditCard(

			@ModelAttribute("userPayment") UserPayment userPayment,
			@ModelAttribute("userBilling") UserBilling userBilling,
			Principal principal, Model model
	){
		User user = userMaintenance.findByUsername(principal.getName());
		userMaintenance.updateUserBilling(userBilling, userPayment, user);


		model.addAttribute("user", user);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("orderList", user.getOrderList());
		model.addAttribute("classActiveBilling", true);



		return "myProfile";

	}

	@RequestMapping("/updateCreditCard")
	public String updateCreditCard(
			@ModelAttribute("id") Long creditCardId, Principal principal, Model model
	) {
		User user = userMaintenance.findByUsername(principal.getName());
		UserPayment userPayment = userPaymentMaintenance.findById(creditCardId);
		if(user.getId() != userPayment.getUser().getId()){
			return "badRequestPage";
		} else {
			model.addAttribute("user", user);
			UserBilling userBilling = userPayment.getUserBilling();

			model.addAttribute("userPayment", userPayment);
			model.addAttribute("userBilling", userBilling);

			model.addAttribute("addNewCreditCard", true);
			model.addAttribute("classActiveBilling", true);

			model.addAttribute("userPaymentList", user.getUserPaymentList());
			model.addAttribute("orderList", user.getOrderList());

			return "myProfile";

		}
	}

	@RequestMapping(value="/setDefaultPayment", method=RequestMethod.POST)
	public String setDefaultPayment(
			@ModelAttribute("defaultUserPaymentId") Long defaultPaymentId, Principal principal, Model model
	) {
		User user = userMaintenance.findByUsername(principal.getName());
		userMaintenance.setUserDefaultPayment(defaultPaymentId, user);

		model.addAttribute("user", user);
		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("classActiveBilling", true);

		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("orderList", user.getOrderList());


		return "myProfile";
	}


	@RequestMapping("/removeCreditCard")
	public String removeCreditCard (
			@ModelAttribute("id") Long creditCardId, Principal principal, Model model
	){
		User user = userMaintenance.findByUsername(principal.getName());
		UserPayment userPayment = userPaymentMaintenance.findById(creditCardId);
		if(user.getId() != userPayment.getUser().getId()){
			return "badRequestPage";
		} else {
			model.addAttribute("user", user);
			userPaymentMaintenance.removeById(creditCardId);


			model.addAttribute("listOfCreditCards", true);
			model.addAttribute("classActiveBilling", true);
			model.addAttribute("userPaymentList", user.getUserPaymentList());
			model.addAttribute("orderList", user.getOrderList());

			return "myProfile";

		}
	}
	@RequestMapping(value="/newUser", method = RequestMethod.POST)

	public String newUserPost(
			HttpServletRequest request,
			@ModelAttribute("email") String userEmail,
			@ModelAttribute("username") String username,
			Model model
	)throws Exception{
		model.addAttribute("classActiveNewAccount", true);
		model.addAttribute("email", userEmail);
		model.addAttribute("username", username);

		if(userMaintenance.findByUsername(username) !=null) {
			model.addAttribute("usernameExists", true);
			return "myAccount";
		}
		if(userMaintenance.findByEmail(userEmail) !=null) {
			model.addAttribute("emailExists", true);
			return "myAccount";
		}
		User user = new User();
		user.setUsername(username);
		user.setEmail(userEmail);
		String password = SecurityUtility.randomPassword();
		String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
		user.setPassword(encryptedPassword);

		Role role = new Role();
		role.setRoleId(1);
		role.setName("ROLE_USER");
		Set<UserRole> userRoles = new HashSet<>();
		userRoles.add(new UserRole(user, role));
		userMaintenance.createUser(user, userRoles);
		String token = UUID.randomUUID().toString();
		userMaintenance.createPasswordResetTokenForUser(user, token);

        String appUrl = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();

		SimpleMailMessage email = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user, password);
		mailSender.send(email);

		model.addAttribute("emailSent", "true");
		model.addAttribute("orderList", user.getOrderList());

		return "myAccount";
	}


	@RequestMapping("/newUser")

	public String newUser(Locale locale,
            @RequestParam("token") String token,
	        Model model) {
        PasswordResetToken passToken = userMaintenance.getPasswordResetToken(token);
        if(passToken ==null) {
            String message="Invalid Token.";
            model.addAttribute("message", message);
            return "redirect:/badRequest";
        }
        User user = passToken.getUser();
        String username = user.getUsername();

        UserDetails userDetails = userSecurityMaintenance.loadUserByUsername(username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
                userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        model.addAttribute("user", user);

		model.addAttribute("classActiveEdit", true);

		return "myProfile";
	}

	@RequestMapping(value="/updateUserInfo", method=RequestMethod.POST)
	public String updateUserInfo(
			@ModelAttribute("user") User user,
			@ModelAttribute("newPassword") String newPassword,
			Model model
	) throws Exception {
		User currentUser = userMaintenance.findById(user.getId());

		if(currentUser == null) {
			throw new Exception("User not found");
		}
		/* check email already exists*/
		if(userMaintenance.findByEmail(user.getEmail()) !=null) {
			if(userMaintenance.findByEmail(user.getEmail()).getId() != currentUser.getId()){
				model.addAttribute("emailExists", true);
				return "myProfile";
			}
		}
		/* check username already exists*/
		if(userMaintenance.findByUsername(user.getUsername()) !=null) {
			if(userMaintenance.findByUsername(user.getUsername()).getId() != currentUser.getId()){
				model.addAttribute("usernameExists", true);
				return "myProfile";
			}
		}

		/*update Password*/
		if(newPassword != null && !newPassword.isEmpty() && !newPassword.equals("")){
			BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
			String dbPassword = currentUser.getPassword();
			if(passwordEncoder.matches(user.getPassword(), dbPassword)){
				currentUser.setPassword(passwordEncoder.encode(newPassword));
			} else{
				model.addAttribute("incorectPassword", true);

				return "myProfile";
			}
		}
		currentUser.setFirstName(user.getFirstName());
		currentUser.setLastName(user.getLastName());
		currentUser.setUsername(user.getUsername());
		currentUser.setEmail(user.getEmail());

		userMaintenance.save(currentUser);

		model.addAttribute("updateSuccess", true);
		model.addAttribute("user", currentUser);
		model.addAttribute("classActiveEdit", true);

		model.addAttribute("listOfShippingAddresses", true);
		model.addAttribute("listOfCreditCards", true);


		UserDetails userDetails = userSecurityMaintenance.loadUserByUsername(currentUser.getUsername());
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
				userDetails.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authentication);

		model.addAttribute("orderList", user.getOrderList());

		return "myProfile";
	}

	@RequestMapping("/orderDetail")
	public String orderDetail(
		@RequestParam("id") Long orderId,
				Principal principal, Model model
	){
		User user = userMaintenance.findByUsername(principal.getName());
		Order order = orderMaintenance.findOne(orderId);

		if(order.getUser().getId() != user.getId()){
			return "badRequestPage";
		} else{

			List <ReservationItem> reservationItemList = reservationItemMaintenance.findByOrder(order);

			model.addAttribute("reservationItemList", reservationItemList);
			model.addAttribute("user", user);
			model.addAttribute("order", order);
			model.addAttribute("userPaymentList", user.getUserPaymentList());
			model.addAttribute("orderList",user.getOrderList());
			model.addAttribute("classActiveOrders", true);
			model.addAttribute("listOfCreditCards", true);
			model.addAttribute("displayOrderDetail", true);

			return "myProfile";
		}
	}

}
