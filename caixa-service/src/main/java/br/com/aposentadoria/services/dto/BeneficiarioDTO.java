package br.com.aposentadoria.services.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeneficiarioDTO {

    private Long id;

    @NotEmpty
    private String nome;

    @NotEmpty
    @CPF
    private String cpf;

    @NotEmpty
    private String email;

    @NotNull
    private Double saldo;

    @NotNull
    private Integer anos;

}
