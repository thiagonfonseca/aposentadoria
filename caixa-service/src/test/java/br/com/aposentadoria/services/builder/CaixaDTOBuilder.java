package br.com.aposentadoria.services.builder;

import br.com.aposentadoria.services.dto.CaixaDTO;
import lombok.Builder;

@Builder
public class CaixaDTOBuilder {

    @Builder.Default
    private String cpf = "75797619040";

    @Builder.Default
    private Double aporte = 5000.0;

    public CaixaDTO toCaixaDTO() {
        return new CaixaDTO(cpf, aporte);
    }

}
