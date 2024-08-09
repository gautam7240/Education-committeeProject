package com.educationCommittee.entityes;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String Username;
	private String number;
	
	@Column(unique = true)
	private String email;
	private String Password;
	private String Role;
	
	
	private String name;
	private String Address;
	private String Phoneno;
	private String parentNo;
	private String Qualification;
	private String Gender;
	
	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] idPhoto;
	
	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] MarksheetPhoto;
	
//	private String idPhoto;
//	private String MarksheetPhoto;
	
	private String paymentStatus;
	
	private String isDeleted;
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getRole() {
		return Role;
	}
	public void setRole(String role) {
		Role = role;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getPhoneno() {
		return Phoneno;
	}
	public void setPhoneno(String phoneno) {
		Phoneno = phoneno;
	}
	public String getParentNo() {
		return parentNo;
	}
	public void setParentNo(String parentNo) {
		this.parentNo = parentNo;
	}
	public String getQualification() {
		return Qualification;
	}
	public void setQualification(String qualification) {
		Qualification = qualification;
	}
	public String getGender() {
		return Gender;
	}
	public void setGender(String gender) {
		Gender = gender;
	}
	
	
	
//	public String getIdPhoto() {
//		return idPhoto;
//	}
//	public void setIdPhoto(String Idphoto) {
//		idPhoto = Idphoto;
//	}
//	public String getMarksheetPhoto() {
//		return MarksheetPhoto;
//	}
//	public void setMarksheetPhoto(String marksheetPhoto) {
//		MarksheetPhoto = marksheetPhoto;
//	}
	
	public byte[] getIdPhoto() {
		return idPhoto;
	}
	public void setIdPhoto(byte[] idPhoto) {
		this.idPhoto = idPhoto;
	}
	public byte[] getMarksheetPhoto() {
		return MarksheetPhoto;
	}
	public void setMarksheetPhoto(byte[] marksheetPhoto) {
		MarksheetPhoto = marksheetPhoto;
	}
	
	
	
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	
	


}
