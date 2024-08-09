package com.educationCommittee.controllers;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.educationCommittee.entityes.User;
import com.educationCommittee.otpService.GenerateOTP;
import com.educationCommittee.otpService.S3Service;
import com.educationCommittee.repos.UserRepo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@RequestMapping("/user")
public class userController {
	
//	GenerateOTP diffrentValueforimageSaving = new GenerateOTP();

    @Autowired
    private UserRepo userRepo;

//    @Autowired
//    private S3Service s3Service;

    private GenerateOTP generateOTP = new GenerateOTP();

    @GetMapping("/dashboard")
    public String UserHome(Principal principal, Model model) {
        model.addAttribute("title", "User Home - Education committee");

        String username = principal.getName();
        User user = userRepo.getUserByUserName(username);
        model.addAttribute("info", user);

        if (user.getName() == null) {
            return "user/Dashboard";
        } else if (user.getPaymentStatus().equals("NOT DONE")) {
            return "user/Dashboard";
        } else {
            model.addAttribute("info", user);
            return "user/Userinformation";
        }
    }
    
	@PostMapping("/do_process")
	public String SaveData(@ModelAttribute("info") User info,
	                       @RequestParam("school_idimg") MultipartFile school_idimg,
	                       @RequestParam("marksheetimg") MultipartFile marksheetimg,
	                       Principal p,Model m) throws IOException {

		
		try {
			
			m.addAttribute("title","Data Save Process - Education committee");
					    
		    String username = p.getName();
		    User user = this.userRepo.getUserByUserName(username);

		  
		    user.setName(info.getName());
		    user.setAddress(info.getAddress());
		    user.setPhoneno(info.getPhoneno());
		    user.setParentNo(info.getParentNo());
		    user.setQualification(info.getQualification());
		    user.setGender(info.getGender());
		    user.setPaymentStatus("NOT DONE");

		    // Set the filenames with rendomValue
		    user.setIdPhoto(school_idimg.getBytes());
		    user.setMarksheetPhoto(marksheetimg.getBytes()); 

		    this.userRepo.save(user);
		    
		    m.addAttribute("name",info.getName());
			
		}catch (Exception e) {
			// TODO: handle exception
		}

	    return "user/pay";
	}
    
	
//	@PostMapping("/do_process")
//	public String SaveData(@ModelAttribute("info") User info,
//	                       @RequestParam("school_idimg") MultipartFile school_idimg,
//	                       @RequestParam("marksheetimg") MultipartFile marksheetimg,
//	                       Principal p,Model m) throws IOException {
//
//		
//		try {
//			
//			m.addAttribute("title","Data Save Process - Education committee");
//			
//		    String rendomValue = diffrentValueforimageSaving.getOTP();
//		    
//		    String username = p.getName();
//		    User user = this.userRepo.getUserByUserName(username);
//
//		    // Save images
//		    File savefile = new ClassPathResource("static/images").getFile();
//
//		    // Generate unique filenames
//		    String idFilename = rendomValue + "_" + school_idimg.getOriginalFilename();
//		    String marksheetFilename = rendomValue + "_" + marksheetimg.getOriginalFilename();
//
//		    Path idPath = Paths.get(savefile.getAbsolutePath() + File.separator + idFilename);
//		    Files.copy(school_idimg.getInputStream(), idPath, StandardCopyOption.REPLACE_EXISTING);
//
//		    Path marksheetPath = Paths.get(savefile.getAbsolutePath() + File.separator + marksheetFilename);
//		    Files.copy(marksheetimg.getInputStream(), marksheetPath, StandardCopyOption.REPLACE_EXISTING);
//
//		    user.setName(info.getName());
//		    user.setAddress(info.getAddress());
//		    user.setPhoneno(info.getPhoneno());
//		    user.setParentNo(info.getParentNo());
//		    user.setQualification(info.getQualification());
//		    user.setGender(info.getGender());
//		    user.setPaymentStatus("NOT DONE");
//
//		    // Set the filenames with rendomValue
//		    user.setIdPhoto(idFilename);
//		    user.setMarksheetPhoto(marksheetFilename); 
//
//		    this.userRepo.save(user);
//		    
//		    m.addAttribute("name",info.getName());
//			
//		}catch (Exception e) {
//			// TODO: handle exception
//		}
//
//	    return "user/pay";
//	}


//    @PostMapping("/do_process")
//    public String SaveData(@ModelAttribute("info") User info,
//                           @RequestParam("aadharimg") MultipartFile aadharimg,
//                           @RequestParam("marksheetimg") MultipartFile marksheetimg,
//                           Principal principal, Model model) throws IOException {
//
//        model.addAttribute("title", "Data Save Process - Education committee");
//
//        String randomValue = generateOTP.getOTP();
//
//        String username = principal.getName();
//        User user = userRepo.getUserByUserName(username);
//
//        // Save images to S3
//        String aadharFilename = username+randomValue + "_" + aadharimg.getOriginalFilename();
//        String marksheetFilename = username+randomValue + "_" + marksheetimg.getOriginalFilename();
//
//        String aadharUrl = s3Service.uploadFile(aadharimg);
//        String marksheetUrl = s3Service.uploadFile(marksheetimg);
//
//        user.setName(info.getName());
//        user.setAddress(info.getAddress());
//        user.setPhoneno(info.getPhoneno());
//        user.setParentNo(info.getParentNo());
//        user.setQualification(info.getQualification());
//        user.setGender(info.getGender());
//        user.setPaymentStatus("NOT DONE");
//
//        // Set the URLs returned from S3
//        user.setIdPhoto(aadharUrl);
//        user.setMarksheetPhoto(marksheetUrl);
//
//        userRepo.save(user);
//
//        model.addAttribute("name", info.getName());
//
//        return "user/pay";
//    }
}
