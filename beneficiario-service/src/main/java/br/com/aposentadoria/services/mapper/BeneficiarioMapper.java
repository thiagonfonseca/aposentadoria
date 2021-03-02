package br.com.aposentadoria.services.mapper;

import br.com.aposentadoria.services.dto.BeneficiarioDTO;
import br.com.aposentadoria.services.entity.Beneficiario;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BeneficiarioMapper {

    BeneficiarioMapper INSTANCE = Mappers.getMapper(BeneficiarioMapper.class);

    Beneficiario toModel(BeneficiarioDTO beneficiarioDto);

    BeneficiarioDTO toDTO(Beneficiario beneficiario);

}
