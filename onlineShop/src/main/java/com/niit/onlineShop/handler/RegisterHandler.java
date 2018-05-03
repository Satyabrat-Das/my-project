package com.niit.onlineShop.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.niit.onlineShop.model.RegisterModel;
import com.niit.onlineShopBackend.dao.UserDAO;
import com.niit.onlineShopBackend.model.Address;
import com.niit.onlineShopBackend.model.Cart;
import com.niit.onlineShopBackend.model.User;


@Component
public class RegisterHandler {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	 private PasswordEncoder passwordEncoder;
	
	public RegisterModel init(){
		
		return new RegisterModel();
	}
	
	public void addUser(RegisterModel registerModel, User user){
		
	registerModel.setUser(user);	
	}
	
	public void addBilling(RegisterModel registerModel, Address billing){
		
		registerModel.setBilling(billing);	
		}
	

	
	public String saveAll(RegisterModel model){
		
		String transitionValue="success";
		
		//fetch the user
		User user=model.getUser();
		
		if(user.getRole().equals("USER")){
			Cart cart = new Cart();
			cart.setUser(user);
			user.setCart(cart);
		}
		
		
		 // encode the password
		  user.setPassword(passwordEncoder.encode(user.getPassword()));
		  
		  
		//save the user
		
		userDAO.add(user);
		
		//get address
		
		Address billing = model.getBilling();
		 billing.setUserId(user.getId());
		  billing.setBilling(true);  
		  userDAO.addAddress(billing);
		  return transitionValue ;
	}
	
}
