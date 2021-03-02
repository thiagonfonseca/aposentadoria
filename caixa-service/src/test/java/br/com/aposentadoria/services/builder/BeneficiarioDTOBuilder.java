package br.com.aposentadoria.services.builder;

import br.com.aposentadoria.services.dto.BeneficiarioDTO;
import lombok.Builder;

@Builder
public class BeneficiarioDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String nome = "João";

    @Builder.Default
    private String cpf = "75797619040";

    @Builder.Default
    private String email = "joao@joao.com";

    @Builder.Default
    private Double saldo = 200000.0;

    @Builder.Default
    private Integer anos = 15;

    public BeneficiarioDTO toBeneficiarioDTO() {
        return new BeneficiarioDTO(id, nome, cpf, email, saldo, anos);
    }

}
