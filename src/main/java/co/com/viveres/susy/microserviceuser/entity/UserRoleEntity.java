package co.com.viveres.susy.microserviceuser.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "USERS_ROLES")
public class UserRoleEntity {

	@Id
	@SequenceGenerator(name = "users_roles_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_roles_id_seq")
	@Column(name = "ID")
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_USER")
	private UserEntity user;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_ROLE")
	private RoleEntity role;

}