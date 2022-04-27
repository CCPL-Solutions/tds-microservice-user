package co.com.viveres.susy.microserviceuser.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "ROLES")
public class RoleEntity {

	@Id
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "NAME")
	private String name;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "role")
	private List<UserRoleEntity> listUserRole;

}