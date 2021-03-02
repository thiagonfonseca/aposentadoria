package br.com.aposentadoria.services.service;

import br.com.aposentadoria.services.dto.BeneficiarioDTO;
import br.com.aposentadoria.services.entity.Beneficiario;
import br.com.aposentadoria.services.exception.DataNotFoundException;
import br.com.aposentadoria.services.mapper.BeneficiarioMapper;
import br.com.aposentadoria.services.repository.BeneficiarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BeneficiarioService {

    private final BeneficiarioRepository repo;

    private final BeneficiarioMapper mapper = BeneficiarioMapper.INSTANCE;

    public String saveBeneficiario(BeneficiarioDTO beneficiarioDTO) {
        Beneficiario beneficiario = mapper.toModel(beneficiarioDTO);
        Beneficiario saved = repo.save(beneficiario);
        return "Beneficiário " + saved.getNome() + " cadastrado com sucesso!";
    }

    public String updateBeneficiario(Long id, BeneficiarioDTO beneficiarioDTO) throws DataNotFoundException {
        verifyId(id);

        Beneficiario beneficiario = mapper.toModel(beneficiarioDTO);

        Beneficiario updated = repo.save(beneficiario);
        return "Beneficiário " + updated.getNome() + " atualizado com sucesso!";
    }

    public List<BeneficiarioDTO> listAll() {
        List<Beneficiario> all = repo.findAll();
        return all.stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    public BeneficiarioDTO findById(Long id) throws DataNotFoundException {
        return mapper.toDTO(verifyId(id));
    }

    public BeneficiarioDTO findByCPF(String cpf) throws DataNotFoundException {
        Beneficiario beneficiario = repo.findByCpf(cpf).orElseThrow(() -> new DataNotFoundException(cpf));

        return mapper.toDTO(beneficiario);
    }

    public void delete(Long id) throws DataNotFoundException {
        verifyId(id);
        repo.deleteById(id);
    }

    private Beneficiario verifyId(Long id) throws DataNotFoundException {
        return repo.findById(id).orElseThrow(() -> new DataNotFoundException(id));
    }

}
