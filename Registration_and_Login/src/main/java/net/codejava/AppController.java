package net.codejava;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.codejava.cart.CartItem;
import net.codejava.menu.Food;
import net.codejava.menu.FoodRepository;
import net.codejava.role.Role;
import net.codejava.role.RoleRepository;
import net.codejava.user.User;
import net.codejava.user.UserRepository;
import net.codejava.userAddress.UserAddress;
import net.codejava.userAddress.UserAddressRepository;
import net.codejava.userInfo.UserInfo;
import net.codejava.userInfo.UserInfoRepository;

@Controller
public class AppController {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private UserInfoRepository UIrepo;
	
	@Autowired
	private RoleRepository Rrepo;
	
	@Autowired
	private UserAddressRepository UArepo;
	
	@Autowired
	private FoodRepository Frepo;
	
	@Autowired
	private CartItemService CIservice;
	
	public Boolean checkAccount(CustomeUserDetails loggedUser) {
		String name = null;
		try {
		name = loggedUser.getUsername();}
		catch(Exception e){}
		boolean check = true;
		if(name == null) {
			check = false;
		}
		return check;
	}
	
	
	@GetMapping("")
	public String viewHomePage(User user,Model model,@AuthenticationPrincipal CustomeUserDetails loggedUser) {
		Boolean check = checkAccount(loggedUser);
		model.addAttribute("check",check);
		return "home";
	}	
	
	@GetMapping("/register")
	public String viewSignUpForm(Model model) {
		model.addAttribute("user", new User());
		return "signUp";
	}
	
	@PostMapping("/process_register")
	public String processRegistration(User user, RedirectAttributes redirAttrs) throws Exception{
		String email = user.getEmail();
		Optional<User> emailEntry = repo.checkByEmail(email);
		if(emailEntry.isPresent()) {
			redirAttrs.addFlashAttribute("error","Username already exists!");
			return"redirect:/register";
		}
		else {
			BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();
			String encodedPassword = encoder.encode(user.getPassword());
			user.setPassword(encodedPassword);
			Role role = Rrepo.findByName("Customer");
			user.addRole(role);
			repo.save(user);
			redirAttrs.addFlashAttribute("success","Your account have been registered successfully!");
		return "redirect:/login";}
	}
	
	@GetMapping("/list_user")
	public String viewUsersList(Model model,@AuthenticationPrincipal CustomeUserDetails loggedUser) {
		String text = WebSecurityConfig.testResourceFile();
		model.addAttribute("text",text);
		List<User> listUsers = repo.findAll();
		model.addAttribute("listUsers",listUsers);
		Boolean check = checkAccount(loggedUser);
		model.addAttribute("check",check);
		
		return "users";
	}
	
	@GetMapping("/demo")
	public String demo(Model model,@AuthenticationPrincipal CustomeUserDetails loggedUser) {
		
		//Checking the file type(0,1)
		String text = WebSecurityConfig.testResourceFile();
		model.addAttribute("text",text);
		
		//Find all food information
		List<Food> listFoods = Frepo.findAll();
		model.addAttribute("listFoods",listFoods);
		
		//Check account and list quantity
		User user = repo.findByEmail(loggedUser.getUsername());
		List<CartItem> cartItems = CIservice.listCartItems(user);
		model.addAttribute("cartItems",cartItems);
		
		//list all type of items
		HashSet<String> listFoodType = new HashSet<>();
		for (Food i : listFoods) {
			listFoodType.add(i.getType());
		}
		
		System.out.println(listFoodType);
		
		
		//put type and items into dictionary
		Map<String,List<Food>> listFoodTypes = new HashMap<String,List<Food>>();
		for (String i : listFoodType) {
			//create new array list
			List<Food> values = new ArrayList<Food>();
			//based on type to put the food
			for(Food x:listFoods) {
				if(x.getType().equals(i)) {
					values.add(x);
				}
			}
			listFoodTypes.put(i, values);
		}
		
		System.out.println(listFoodTypes);
		model.addAttribute("listFoodTypes",listFoodTypes);
		Boolean check = checkAccount(loggedUser);
		model.addAttribute("check",check);
		return "demo";
	}
	
	@GetMapping("/addMenu")
	public String viewMenuAddedForm(Model model) {
		model.addAttribute("food", new Food());
		return "food_form";
	}
	
	@GetMapping("/file")
	public String viewFileContext(Model model) {
		String text = null;
		try {
			File resource = new ClassPathResource("static/Test.txt").getFile();
			text = new String(Files.readAllBytes(resource.toPath()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("text",text);
		return "text";
	}
	
	@GetMapping("/profile")
	public String viewProfileDetails(@AuthenticationPrincipal CustomeUserDetails loggedUser,Model model) {
		String email = loggedUser.getUsername();
		User user = repo.findByEmail(email);
		model.addAttribute("user", user);
		return "profile";
	}
	
	@GetMapping("/profile/edit")
	public String editProfileDetails(@AuthenticationPrincipal CustomeUserDetails loggedUser,Model model) {
		String email = loggedUser.getUsername();
		User user = repo.findByEmail(email);
		model.addAttribute("user", user);
		return "profileEdit";
	}
	
	@PostMapping("/profile/update")
	public String viewProfileForm(User user, RedirectAttributes redirectAttributes,@AuthenticationPrincipal CustomeUserDetails loggedUser) {
		
		loggedUser.setFirstName(user.getFirstName());
		loggedUser.setLastName(user.getLastName());
		
		
		BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		repo.save(user);
		
		
		redirectAttributes.addFlashAttribute("success","Your account details have been updated!");
		return "redirect:/profile";
	}
	
	@GetMapping("/profile/details")
	public String viewProfileForm(@AuthenticationPrincipal CustomeUserDetails loggedUser, Model model) {
		String email = loggedUser.getUsername();
		User user = repo.findByEmail(email);
		model.addAttribute("user", user);
		UserInfo userInfo = UIrepo.findByID(user.getId());
		if(userInfo==null) {
			model.addAttribute("userInfo", new UserInfo());
			model.addAttribute("userAddress", new UserAddress());
		}else {
			UserAddress userAddress = UArepo.findByID(userInfo.getID());
			model.addAttribute("userInfo", userInfo);
			model.addAttribute("userAddress", userAddress);}
		
		return "profileDetails";
	}
	
	@GetMapping("/profile/details/edit")
	public String viewEditProfileForm(@AuthenticationPrincipal CustomeUserDetails loggedUser, Model model) {
		String email = loggedUser.getUsername();
		User user = repo.findByEmail(email);
		model.addAttribute("user", user);
		UserInfo userInfo = UIrepo.findByID(user.getId());
		if(userInfo==null) {
			model.addAttribute("userInfo", new UserInfo());
			model.addAttribute("userAddress", new UserAddress());
		}else {
			UserAddress userAddress = UArepo.findByID(userInfo.getID());
			model.addAttribute("userInfo", userInfo);
			model.addAttribute("userAddress", userAddress);}
		return "profileForm";
	}
	
	@PostMapping("/profile/details/success")
	public String viewProfileDetails(UserInfo userInfo, RedirectAttributes redirectAttributes,@AuthenticationPrincipal CustomeUserDetails loggedUser) {
		UIrepo.save(userInfo);
		redirectAttributes.addFlashAttribute("success","Your profile details have been updated!");
		return "redirect:/profile/details";
	}
	
	@GetMapping("/set")
	public String viewSetNavbar() {
		return "profileNavbar";
	}
	
	@GetMapping("/users/edit/{id}")
	public String viewUser(@PathVariable("id") Long id, Model model) {
		User user = repo.findById(id).get();
		model.addAttribute("user",user);
		return "customerAccount";
	}
	
	@GetMapping("/users/edit/{id}/edit")
	public String editUser(@PathVariable("id") Long id, Model model) {
		User user = repo.findById(id).get();
		List<Role> listRoles = Rrepo.findAll();
		model.addAttribute("user",user);
		model.addAttribute("listRoles", listRoles);
		return "customerAccountEdit";
	}
	
	@PostMapping("/users/edit/{id}/update")
	public String updateUser(User user, RedirectAttributes redirectAttributes,@AuthenticationPrincipal CustomeUserDetails loggedUser) {
		BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		repo.save(user);
		
		
		redirectAttributes.addFlashAttribute("success","Your account details have been updated!");
		return "redirect:/users/edit/{id}";
	}
	
	@GetMapping("/users/edit/{id}/details")
	public String viewEditUserDetails(@PathVariable("id") Long id, Model model) {
		User user = repo.findById(id).get();
		model.addAttribute("user", user);
		UserInfo userInfo = UIrepo.findByID(user.getId());
		if(userInfo==null) {
			model.addAttribute("userInfo", new UserInfo());
		}else {
			model.addAttribute("userInfo", userInfo);}
		return "customerAccountDetails";
	}
	
	@GetMapping("/users/edit/{id}/details/form")
	public String editUserDetails(@PathVariable("id") Long id, Model model) {
		User user = repo.findById(id).get();
		model.addAttribute("user", user);
		UserInfo userInfo = UIrepo.findByID(user.getId());
		if(userInfo==null) {
			model.addAttribute("userInfo", new UserInfo());
		}else {
			model.addAttribute("userInfo", userInfo);}
		return "customerAccountForm";
	}
	
	@PostMapping("/users/edit/{id}/details/update")
	public String updateUserDetails(UserInfo userInfo, RedirectAttributes redirectAttributes,@AuthenticationPrincipal CustomeUserDetails loggedUser) {
		UIrepo.save(userInfo);
		redirectAttributes.addFlashAttribute("success","Your profile details have been updated!");
		return "redirect:/users/edit/{id}/details";
	}
	
	@GetMapping("/403")
	public String error403(RedirectAttributes redirAttrs,HttpServletRequest request) {
		redirAttrs.addFlashAttribute("error","Sorry, you don't have permission to access to the page!");
		return "redirect:"+request.getHeader("Referer");
	}
	
	@GetMapping("/cart")
	public String showShoppingCart(Model model,@AuthenticationPrincipal CustomeUserDetails loggedUser) {
		Boolean check = checkAccount(loggedUser);
		model.addAttribute("check",check);
		
		User user = repo.findByEmail(loggedUser.getUsername());
		List<CartItem> cartItems = CIservice.listCartItems(user);
		
		model.addAttribute("cartItems",cartItems);
		model.addAttribute("pageTitle","Shopping Cart");
		return "cartItem";
	}
}
