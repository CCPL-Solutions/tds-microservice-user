package co.com.viveres.susy.microserviceuser.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
public class RoleDto implements Serializable {

    private static final long serialVersionUID = 5930434021494746466L;
    @NotNull
    private Long id;

    @NotBlank
    private String name;
}
