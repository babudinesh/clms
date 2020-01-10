package com.Ntranga.core.CLMS.entities;
// Generated Mar 8, 2017 3:14:10 PM by Hibernate Tools 5.2.0.Beta1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * UserLocationMapping generated by hbm2java
 */
@Entity
@Table(name = "user_location_mapping")
public class UserLocationMapping implements java.io.Serializable {

	private Integer userLocationMappingId;
	private Users users;
	private String type;
	private int typeId;

	public UserLocationMapping() {
	}

	public UserLocationMapping(Users users, String type, int typeId) {
		this.users = users;
		this.type = type;
		this.typeId = typeId;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "user_location_mapping_id", unique = true, nullable = false)
	public Integer getUserLocationMappingId() {
		return this.userLocationMappingId;
	}

	public void setUserLocationMappingId(Integer userLocationMappingId) {
		this.userLocationMappingId = userLocationMappingId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	@Column(name = "type", nullable = false, length = 100)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "type_id", nullable = false)
	public int getTypeId() {
		return this.typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

}
