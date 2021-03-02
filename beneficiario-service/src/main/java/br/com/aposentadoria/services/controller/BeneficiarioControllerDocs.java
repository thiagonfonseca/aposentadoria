package br.com.aposentadoria.services.controller;

import br.com.aposentadoria.services.dto.BeneficiarioDTO;
import br.com.aposentadoria.services.exception.DataNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Api("Serviço de Registro de Beneficiários")
public interface BeneficiarioControllerDocs {

    @ApiOperation(value = "Cadastro de Beneficiário")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Cadastro de beneficiário realizado com sucesso")
    })
    String createBeneficiario(@RequestBody @Valid BeneficiarioDTO beneficiarioDTO);

    @ApiOperation(value = "Atualização de Beneficiário")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Beneficiário atualizado com sucesso"),
            @ApiResponse(code = 404, message = "Não foi possível encontrar este beneficiário.")
    })
    String updateBeneficiario(@PathVariable Long id, @RequestBody @Valid BeneficiarioDTO beneficiarioDTO) throws DataNotFoundException;

    @ApiOperation(value = "Retornando uma lista de todos os beneficiários do sistema")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de beneficiários registrados no sistema")
    })
    List<BeneficiarioDTO> listAll();

    @ApiOperation(value = "Retorna um beneficiário por seu id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Beneficiário encontrado"),
            @ApiResponse(code = 404, message = "Não foi possível encontrar este beneficiário.")
    })
    BeneficiarioDTO findById(@PathVariable Long id) throws DataNotFoundException;

    @ApiOperation(value = "Retorna um beneficiário por seu CPF")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Beneficiário encontrado"),
            @ApiResponse(code = 404, message = "Não foi possível encontrar este beneficiário.")
    })
    BeneficiarioDTO findByCpf(@PathVariable String cpf) throws DataNotFoundException;

    @ApiOperation(value = "Exclusão de beneficiário")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Beneficiário excluído com sucesso"),
            @ApiResponse(code = 404, message = "Não foi possível encontrar este beneficiário.")
    })
    void delete(@PathVariable Long id) throws DataNotFoundException;

}
