package com.castis.pvs.entity;


import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "tbl_common_config", uniqueConstraints = { })
@NoArgsConstructor
public class CommonConfig implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private long id;
	private String parent_full_code; 
	private String full_code; 
	private String code; 
	private String name; 
	private String value_type; 
	private String string_value; 
	private String double_value; 
	private Long long_value; 
	private Integer int_value; 
	private Boolean boolean_value; 
	private Integer display_order; 

	
	private Date create_time;
	private Date update_time;

	

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idx", unique = true, nullable = false)
	public long getId() {
		return id;
	}


	

	@Column(name = "create_time", nullable = true,insertable = false,updatable = false)
	public Date getCreate_time() {
		return create_time;
	}


	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}


	@Column(name = "update_time", nullable = true,insertable = false,updatable = false)
	public Date getUpdate_time() {
		return update_time;
	}


	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}




	public String getParent_full_code() {
		return parent_full_code;
	}




	public void setParent_full_code(String parent_full_code) {
		this.parent_full_code = parent_full_code;
	}




	public String getCode() {
		return code;
	}




	public void setCode(String code) {
		this.code = code;
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public String getValue_type() {
		return value_type;
	}




	public void setValue_type(String value_type) {
		this.value_type = value_type;
	}




	public String getString_value() {
		return string_value;
	}




	public void setString_value(String string_value) {
		this.string_value = string_value;
	}




	public String getDouble_value() {
		return double_value;
	}




	public void setDouble_value(String double_value) {
		this.double_value = double_value;
	}




	public Long getLong_value() {
		return long_value;
	}




	public void setLong_value(Long long_value) {
		this.long_value = long_value;
	}




	public Integer getInt_value() {
		return int_value;
	}




	public void setInt_value(Integer int_value) {
		this.int_value = int_value;
	}




	public Boolean getBoolean_value() {
		return boolean_value;
	}




	public void setBoolean_value(Boolean boolean_value) {
		this.boolean_value = boolean_value;
	}




	public Integer getDisplay_order() {
		return display_order;
	}




	public void setDisplay_order(Integer display_order) {
		this.display_order = display_order;
	}




	public void setId(long id) {
		this.id = id;
	}




	public String getFull_code() {
		return full_code;
	}




	public void setFull_code(String full_code) {
		this.full_code = full_code;
	}

	
}
