package com.educationCommittee.controllers;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.educationCommittee.entityes.User;
import com.educationCommittee.otpService.S3Service;
import com.educationCommittee.repos.UserRepo;


@Controller
@RequestMapping("/admin")
public class AdminController {
	
	
	
	@Autowired
	UserRepo userRepo;
	
//	 @Autowired
//	 S3Service s3Service; // Add this line to inject S3Service

	
	@GetMapping("/Adminhome")
	public String AdminPage(Model m) {
		m.addAttribute("title","Admin Home - Education committee");
		
		List<User> allusers = this.userRepo.findAll();
		
		m.addAttribute("allusers", allusers);
			

		
		return "admin/AdminPage";
	}
	
	
	
	//show single user
	@GetMapping("/singleUser/{id}")
	public String single_user(@PathVariable("id") int id,Model m) {
		
		m.addAttribute("title","User Detail - Education committee");
		
		User user = this.userRepo.getUserByUserId(id);
		
		m.addAttribute("info",user);
		
		return "admin/Annausment";
	}	
	
	
	
//	//delete user
//	
		@GetMapping("/delete/{id}")
		public String DeleteUser(@PathVariable("id") int id,Model m) throws IOException {
			m.addAttribute("title","Delete User - Education committee");
			
			User user = this.userRepo.getUserByUserId(id);
			
			 if (user != null) {
				 user.setIsDeleted("Deleted_User");
		            this.userRepo.save(user);
			 }
	            // Delete user from database
				 
	            
			
//			this.userRepo.delete(user);
			
			
			//delete photo
//			
//			try {
//				// School Id photo 
//				File idPhoto = new ClassPathResource("static/images/" + user.getIdPhoto()).getFile();
//		        idPhoto.delete();
//		        
//		        //marksheert photo
//		        File marksheetphoto = new ClassPathResource("static/images/" + user.getMarksheetPhoto()).getFile();
//		        marksheetphoto.delete();
//			} catch (Exception e) {
//				// TODO: handle exception
//			}

			
			return "redirect:/admin/Adminhome";
		}	
		
		

//	
//	@GetMapping("/delete/{id}")
//    public String DeleteUser(@PathVariable("id") int id, Model m) {
//        m.addAttribute("title", "Delete User - Education committee");
//
//        User user = this.userRepo.getUserByUserId(id);
//        if (user != null) {
//            // Delete user from database
//            user.setIsDeleted("Deleted_User");
//            this.userRepo.save(user);
//            // Delete files from S3
////            String aadharPhotoKey = extractFileNameFromUrl(user.getIdPhoto());
////            String marksheetPhotoKey = extractFileNameFromUrl(user.getMarksheetPhoto());
//
////            s3Service.deleteFile(aadharPhotoKey);
////            s3Service.deleteFile(marksheetPhotoKey);
//        }
//
//        return "redirect:/admin/Adminhome";
//    }
    
//    private String extractFileNameFromUrl(String url) {
//        // Extract the file name from the URL
//        return url.substring(url.lastIndexOf('/') + 1);
//    }
		
		@GetMapping("/paymentStatus/{id}")
		public String getStatus(@PathVariable("id") int id) throws IOException {
			User user = this.userRepo.getUserByUserId(id);
			
//			String username = p.getName();		
//			User user = this.userRepo.getUserByUserName(username);
			user.setPaymentStatus("Done");
			this.userRepo.save(user);
			
			return "redirect:/admin/Adminhome";
			
		}
		
		//open idphoto image method
		
		 @GetMapping("/id/{id}")
		    public ResponseEntity<byte[]> getIdImage(@PathVariable int id) {
		        Optional<User> imageEntityOptional = userRepo.findById(id);
		        if (imageEntityOptional.isPresent()) {
		            byte[] image = imageEntityOptional.get().getIdPhoto();
		            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
		        } else {
		            return ResponseEntity.notFound().build();
		        }
		    }
		    
		  //open marksheet image method
			
			 @GetMapping("/marksheet/{id}")
			    public ResponseEntity<byte[]> getmarksheetImage(@PathVariable int id) {
			        Optional<User> imageEntityOptional = userRepo.findById(id);
			        if (imageEntityOptional.isPresent()) {
			            byte[] image = imageEntityOptional.get().getMarksheetPhoto();
			            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
			        } else {
			            return ResponseEntity.notFound().build();
			        }
			    }
	
}
