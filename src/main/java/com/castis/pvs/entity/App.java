package com.castis.pvs.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "app")
@Getter
@Setter
@NoArgsConstructor
public class App implements Serializable{


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	private String title;

	private String app_code;

	private boolean IS_MEMBER_APP;

	private String type;

	private Timestamp create_time;

	private Timestamp update_time;

}
