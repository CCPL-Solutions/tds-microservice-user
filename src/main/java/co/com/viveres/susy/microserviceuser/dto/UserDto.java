package co.com.viveres.susy.microserviceuser.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
public class UserDto implements Serializable {

    private static final long serialVersionUID = -6198752891517868133L;

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