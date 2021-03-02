package br.com.aposentadoria.services.kafka.consumer;

import br.com.aposentadoria.services.dto.BeneficiarioDTO;
import br.com.aposentadoria.services.dto.CaixaDTO;
import br.com.aposentadoria.services.exception.DataNotFoundException;
import br.com.aposentadoria.services.service.BeneficiarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.CountDownLatch;

public class Receiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    private CountDownLatch latch = new CountDownLatch(1);

    @Autowired
    private BeneficiarioService service;

    @KafkaListener(topics = "${br.com.aposentadoria.services.kafka.producer.topic.aporteCreated}")
    public void receive(CaixaDTO payload) throws DataNotFoundException {
        BeneficiarioDTO beneficiario = service.findByCPF(payload.getCpf());
        beneficiario.setSaldo(beneficiario.getSaldo() + payload.getAporte());
        service.updateBeneficiario(beneficiario.getId(), beneficiario);

        latch.countDown();
    }

}
