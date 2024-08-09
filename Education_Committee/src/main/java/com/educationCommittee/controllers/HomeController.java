package com.educationCommittee.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.educationCommittee.entityes.User;
import com.educationCommittee.otpService.GenerateOTP;
import com.educationCommittee.otpService.SendEmail;
import com.educationCommittee.repos.UserRepo;

@Controller
public class HomeController {
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private UserRepo userRepo; 
	
	private String Otp;
	
	private String Process;
	GenerateOTP generateOTP = new GenerateOTP();
	SendEmail sendEmail = new SendEmail();

	// home page
	@GetMapping("/")
	public String Home(Model m) {
		Otp=null;
		m.addAttribute("title","Home - Education committee");
		return "home";
	}

	// login page
	@GetMapping("/login")
	public String Login(Model m) {
		Otp=null;
		m.addAttribute("title","Login - Education committee");
		return "LoginPage";
	}

	// new registration page
	@GetMapping("/registration")
	public String Registration(Model m) {
		m.addAttribute("title","Registration - Education committee");
		m.addAttribute("user", new User());
		Process=null;
		return "Registration";
	}

//	contact page
	@GetMapping("/contact")
	public String Contact(Model m) {
		m.addAttribute("title","Contact us - Education committee");
		return "Contact";
	}

	// help page
	@GetMapping("/help")
	public String Help(Model m) {
		Otp=null;
		m.addAttribute("title","Help - Education committee");
		return "help";
	}

	// terms and conditions
	@GetMapping("/terms&condition")
	public String Terms(Model m) {
		Otp=null;
		m.addAttribute("title","Terms&Condition - Education committee");
		return "termsCondition";
	}

//	// send OTP method and fill OTP page
//	@PostMapping("/getotp")
//	public String GetOTP(@ModelAttribute("user") User user, Model m) {
//		m.addAttribute("title","Fill OTP - Education committee");
//		m.addAttribute("msg","OTP sent successfully on your email");
//		if(Otp==null) {
//			
//			Otp = generateOTP.getOTP();
//			
//			System.out.println(Otp);
//		try {
//			String msg = "your registration OTP is "+Otp;
//			sendEmail.SendMail(msg,"Registration OTP",user.getEmail());
//			
//
//			m.addAttribute("user", user);
//			
//			return "getOTP";
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			
//			return "redirect:/registration";
//		}
//			
//		}else {
//			return "getOTP";
//		}
//			
//		
//	}
//
	
	
	
	// send OTP method and fill OTP page
		@PostMapping("/getotp")
		public String GetOTP(@ModelAttribute("user") User user, Model m) {
			

			if(userRepo.findByEmail(user.getEmail())==null) {

				m.addAttribute("title","Fill OTP - Education committee");
				m.addAttribute("msg","OTP sent successfully on your email");
				if(Otp==null) {
					
					Otp = generateOTP.getOTP();
					
					System.out.println(Otp);
				try {
					String msg = "Welcome to education committee An OTP has been sent to your registered MAIL ID\r\n"
							+ "Please enter the OTP below to proceed with action or transaction your OTP is "+ Otp+"\r\n"
							+ "Note: OTP expires in [ 5 minutes]. Ensure to use it before it expires.\r\n"
							+ "Your security is important to us. Please do not share this OTP with anyone.\r\n"
							+ "If you haven’t received the OTP within a few minutes, please click on 'Resend OTP' or check your mobile network connectivity.\r\n"
							+ "\r\n"
							+ "For assistance, contact our support team at \r\n"
							+ " By Mailing address ;       educommitteestudy@gmail.com\r\n"
							+ "By Visiting site           ;        WWW.educommitte.in\r\n";
					
//					String msg = "your registration OTP is "+Otp;
					sendEmail.SendMail(msg,"Registration OTP",user.getEmail());
					

					m.addAttribute("user", user);
					
					return "getOTP";

				} catch (Exception e) {
					e.printStackTrace();
					
					return "redirect:/registration";
				}
					
				}else {
					return "getOTP";
				}
				
				
				
			}else {
				System.out.println("exist");
				m.addAttribute("error","this email address is already exist please use a diffrent email");
				return "Registration";
				
			}
			
		}


	
	
	
	// process form
	@PostMapping("/do_register")
	public String Process_Form(@ModelAttribute("user") User user,@RequestParam("otp") String otp,Model m) {
		
		m.addAttribute("title","Registration Process - Education committee");
		if(Process==null) {
			
			Process="done";
			
			
			try {
				
				if (otp.equals(Otp)) {
					Otp = null;

//					String messageemail = "you done your registration with your email " + user.getEmail()
//							+ " end your password is " + user.getPassword();
					String messageemail = "Welcome! Dear Student Your Registration with EDUCATION COMMITTEE Organized by ANAMAT CHARITABLE TRUST WADNER WARDHA is Complete, your registration password is "+user.getPassword()
							+ "/tWe are delighted to have you join us!\r\n"
							+ "                   This email confirms that your registration details have been successfully received and processed. You are now eligible for our community.\r\n"
							+ "HELP;         Any query  ;Wisit..   educommitte.in\r\n"
							+ "                          By Mailing address ;       educommitteestudy@gmail.com\r\n"
							+ "                                                   We are excited to have you on board and look forward to serving you. If you have any questions or concerns, please do not hesitate to reach out to us.\r\n"
							+ "Best regards,\r\n"
							+ "                                       ‘EDUCATION COMMITTEE’\r\n";
					
					sendEmail.SendMail(messageemail,"Registration Done",user.getEmail());
					


					user.setPassword(passwordEncoder.encode(user.getPassword()));
					
					//here add all data in database.
					user.setRole("USER");
					userRepo.save(user);
					Process=null;
					return "redirect:/login";
					
				} else {
					m.addAttribute("user", user);
					m.addAttribute("msg","wrong OTP");
					return "getOTP";
				}
				
				
				
				
			} catch (Exception e) {
				e.printStackTrace();
				m.addAttribute("user",user);
				Otp=null;
				Process=null;
				m.addAttribute("error","this email address is already exist please use a diffrent email");
				return "Registration";
			}

			
		}else {
			
			return "redirect:/login";
			
		}
		
	}


}
