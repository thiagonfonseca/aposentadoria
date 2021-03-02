# Serviço de Beneficiarios - Sistema de Caixa Eletrônico para retirada de benefício de aposentadoria

- Sistema desenvolvido em Spring Boot 2.3.8
- Documentaçao: http://localhost:9001/swagger-ui.html
- Mensageria: Apache Kafka


# Serviço de Aportes - Sistema de Caixa Eletrônico para retirada de benefício de aposentadoria

- Sistema desenvolvido em Spring Boot 2.3.8
- Mensageria: Apache Kafka

# Operações do sistema (Serviço de Aportes)

- Aporte financeiro em aposentadoria: http://localhost:9002/api/v1/caixa

- Busca de Aporte por id: http://localhost:9002/api/v1/caixa/{ID}

- Busca de Aporte por CPF do Beneficiário: http://localhost:9002/api/v1/caixa/cpf/{CPF}

- Calcula Aposentadoria por CPF do Beneficiário: http://localhost:9002/api/v1/caixa/calcula/{CPF}


# FrontEnd (Aposentadoria UI)

- Sistema desenvolvido em Angular 10
- Gerenciamento de Estados: Redux (ngrx)


# Dockerfile Kafka

- Arquivo se encontra em docker-kafka


# docker-compose PostgreSQL

- Arquivo se encontra em docker-postgres