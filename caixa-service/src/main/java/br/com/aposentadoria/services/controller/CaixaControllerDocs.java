package br.com.aposentadoria.services.controller;

import br.com.aposentadoria.services.dto.CaixaDTO;
import br.com.aposentadoria.services.exception.DataNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Api("Serviço de Caixa Eletrônico")
public interface CaixaControllerDocs {

    @ApiOperation(value = "Cadastro de Aporte")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Aporte realizado com sucesso")
    })
    String saveAporte(@RequestBody @Valid CaixaDTO caixaDTO);

    @ApiOperation(value = "Retorna um aporte financeiro por seu id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Aporte encontrado"),
            @ApiResponse(code = 404, message = "Não foi possível encontrar este aporte.")
    })
    CaixaDTO findById(@PathVariable Long id) throws DataNotFoundException;

    @ApiOperation(value = "Retorna uma lista de aportes por CPF")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de aportes por CPF"),
            @ApiResponse(code = 404, message = "Não foi possível encontrar este CPF.")
    })
    List<CaixaDTO> findByCpf(@PathVariable String cpf) throws DataNotFoundException;

    @ApiOperation(value = "Retorna o cálculo de aposentadoria do beneficiário")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cálculo realizado com sucesso"),
            @ApiResponse(code = 404, message = "Não foi possível encontrar este CPF.")
    })
    Double calculaAposentadoria(@PathVariable String cpf) throws DataNotFoundException;

}
