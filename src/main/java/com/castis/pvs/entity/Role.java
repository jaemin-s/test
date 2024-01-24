package com.castis.pvs.entity;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Role generated by hbm2java
 */
@Entity
@Table(name = "role")
public class Role implements java.io.Serializable {

	private String id;
	private String description;
	private Set<UserRoleMap> userRoleMaps = new HashSet<UserRoleMap>(0);

	public Role() {
	}

	public Role(String id) {
		this.id = id;
	}

	public Role(String id, String description, Set<UserRoleMap> userRoleMaps) {
		this.id = id;
		this.description = description;
		this.userRoleMaps = userRoleMaps;
	}

	@Id

	@Column(name = "id", unique = true, nullable = false, length = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "description", length = 500)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
	public Set<UserRoleMap> getUserRoleMaps() {
		return this.userRoleMaps;
	}

	public void setUserRoleMaps(Set<UserRoleMap> userRoleMaps) {
		this.userRoleMaps = userRoleMaps;
	}

}
