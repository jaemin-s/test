package com.castis.pvs.member.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tbl_address_ref")
@Getter
@Setter
@NoArgsConstructor
public class AddressRef implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	private int city_id;
	private String city_name;
	private int dist_id;
	private String dist_name;
	private int dong_id;
	private String dong_name;

}
