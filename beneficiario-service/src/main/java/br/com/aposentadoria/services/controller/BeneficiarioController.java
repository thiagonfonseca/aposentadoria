package br.com.aposentadoria.services.controller;

import br.com.aposentadoria.services.dto.BeneficiarioDTO;
import br.com.aposentadoria.services.exception.DataNotFoundException;
import br.com.aposentadoria.services.service.BeneficiarioService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/beneficiarios")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BeneficiarioController implements BeneficiarioControllerDocs {

    private final BeneficiarioService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createBeneficiario(@RequestBody @Valid BeneficiarioDTO beneficiarioDTO) {
        return service.saveBeneficiario(beneficiarioDTO);
    }

    @PutMapping("/{id}")
    public String updateBeneficiario(@PathVariable Long id, @RequestBody @Valid BeneficiarioDTO beneficiarioDTO) throws DataNotFoundException {
        return service.updateBeneficiario(id, beneficiarioDTO);
    }

    @GetMapping
    public List<BeneficiarioDTO> listAll() {
        return service.listAll();
    }

    @GetMapping("/{id}")
    public BeneficiarioDTO findById(@PathVariable Long id) throws DataNotFoundException {
        return service.findById(id);
    }

    @GetMapping("/cpf/{cpf}")
    public BeneficiarioDTO findByCpf(@PathVariable String cpf) throws DataNotFoundException {
        return service.findByCPF(cpf);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) throws DataNotFoundException {
        service.delete(id);
    }
}
