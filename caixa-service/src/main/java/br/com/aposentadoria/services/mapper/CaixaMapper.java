package br.com.aposentadoria.services.mapper;


import br.com.aposentadoria.services.dto.CaixaDTO;
import br.com.aposentadoria.services.entity.Caixa;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CaixaMapper {

    CaixaMapper INSTANCE = Mappers.getMapper(CaixaMapper.class);

    Caixa toModel(CaixaDTO caixaDto);

    CaixaDTO toDTO(Caixa caixa);

}
