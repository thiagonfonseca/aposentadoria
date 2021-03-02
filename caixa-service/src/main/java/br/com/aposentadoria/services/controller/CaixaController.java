package br.com.aposentadoria.services.controller;

import br.com.aposentadoria.services.dto.CaixaDTO;
import br.com.aposentadoria.services.exception.DataNotFoundException;
import br.com.aposentadoria.services.service.CaixaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/caixa")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CaixaController implements CaixaControllerDocs {

    private final CaixaService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String saveAporte(@RequestBody @Valid CaixaDTO caixaDTO) {
        return service.saveAporte(caixaDTO);
    }

    @GetMapping("/{id}")
    public CaixaDTO findById(@PathVariable Long id) throws DataNotFoundException {
        return service.findById(id);
    }

    @GetMapping("/cpf/{cpf}")
    public List<CaixaDTO> findByCpf(@PathVariable String cpf) throws DataNotFoundException {
        return service.findByCPF(cpf);
    }

    @GetMapping("/calcula/{cpf}")
    public Double calculaAposentadoria(@PathVariable String cpf) throws DataNotFoundException {
        return service.calculaAposentadoria(cpf);
    }

}
