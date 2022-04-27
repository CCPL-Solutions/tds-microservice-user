package co.com.viveres.susy.microserviceuser.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UserDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	@NotBlank
	private String username;

	@NotBlank
	private String password;

	@NotNull
	private Boolean enabled;

	@NotBlank
	private String name;

	@NotBlank
	private String lastName;

	@NotBlank
	@Email
	private String email;
	
	private Integer attempts;

	@Valid
	private List<RoleDto> roles;

}