# Hospital History Service

Microsservico de leitura do historico de agendamentos via GraphQL.

## Consultas

Endpoint: `POST http://localhost:8083/graphql`

Exemplo:

```graphql
query {
  patientHistory(patientId: "22cb3efc-a0ec-42cd-896b-e1060a25a94d") {
    appointmentId
    appointmentDate
    status
    notes
    updatedAt
  }
}
```

As consultas tambem disponiveis sao `appointmentsByPatient` e `appointmentsByDoctor`.

O servico consome os eventos de agendamento do RabbitMQ e mantem uma projecao propria para leitura. Ele nao armazena prontuario clinico, diagnosticos, prescricoes ou exames.

## Execucao

Dependencias locais: PostgreSQL, RabbitMQ e Consul.

```bash
./mvnw spring-boot:run
```

Por padrao, usa a porta `8083` e o banco `hospital_history_db`.