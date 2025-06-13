package edu.pnu.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Goods {
	@Id
	private String fullcode;
	private String imgname;
	private String main;
	private String mid;
	private String detail;
	private String gender;
	private String color;
	private String print;
	
	@Column(name = "product_name")
	private String productName;
	
	private int price;
	private String maincode;
	private String midcode;
	private String detailcode;
	private String gendercode;
	private String colorcode;
	private String categorycode;
	private String uniquecode;
	private int viewcount;
	

}
