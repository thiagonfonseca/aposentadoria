# Serviço de Aportes - Sistema de Caixa Eletrônico para retirada de benefício de aposentadoria

- Sistema desenvolvido em Spring Boot 2.3.8
- Mensageria: Apache Kafka

# Operações do sistema

- Aporte financeiro em aposentadoria: http://localhost:9002/api/v1/caixa

- Busca de Aporte por id: http://localhost:9002/api/v1/caixa/{ID}

- Busca de Aporte por CPF do Beneficiário: http://localhost:9002/api/v1/caixa/cpf/{CPF}

- Calcula Aposentadoria por CPF do Beneficiário: http://localhost:9002/api/v1/caixa/calcula/{CPF}