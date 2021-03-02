package br.com.aposentadoria.services.service;

import br.com.aposentadoria.services.dto.BeneficiarioDTO;
import br.com.aposentadoria.services.dto.CaixaDTO;
import br.com.aposentadoria.services.entity.Caixa;
import br.com.aposentadoria.services.exception.DataNotFoundException;
import br.com.aposentadoria.services.kafka.producer.Sender;
import br.com.aposentadoria.services.mapper.CaixaMapper;
import br.com.aposentadoria.services.repository.CaixaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CaixaService {

    @Value("${br.com.aposentadoria.services.kafka.producer.topic.aporteCreated}")
    private String APORTE_CREATED_TOPIC;

    @Autowired
    private CaixaRepository repo;

    private final CaixaMapper mapper = CaixaMapper.INSTANCE;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Sender sender;

    public String saveAporte(CaixaDTO caixaDTO) {
        Caixa caixa = mapper.toModel(caixaDTO);
        Caixa saved = repo.save(caixa);
        sender.send(APORTE_CREATED_TOPIC, caixaDTO);
        return "Aporte para " + saved.getCpf() + " cadastrado com sucesso!";
    }

    public CaixaDTO findById(Long id) throws DataNotFoundException {
        return mapper.toDTO(verifyId(id));
    }

    public List<CaixaDTO> findByCPF(String cpf) throws DataNotFoundException {
        List<Caixa> aportes = repo.findByCpf(cpf).orElseThrow(() -> new DataNotFoundException(cpf));

        return aportes.stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    public Double calculaAposentadoria(String cpf) throws DataNotFoundException {
        BeneficiarioDTO beneficiario = restTemplate.getForObject("http://localhost:9001/api/v1/beneficiarios/cpf/" + cpf, BeneficiarioDTO.class);
        if (beneficiario != null) {
            return beneficiario.getSaldo() / (beneficiario.getAnos() * 12);

        } else {
            throw new DataNotFoundException(cpf);
        }
    }

    private Caixa verifyId(Long id) throws DataNotFoundException {
        return repo.findById(id).orElseThrow(() -> new DataNotFoundException(id));
    }

}
