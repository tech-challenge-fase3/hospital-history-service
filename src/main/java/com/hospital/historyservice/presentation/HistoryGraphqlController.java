package com.hospital.historyservice.presentation;

import java.util.List;
import java.util.UUID;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.hospital.historyservice.infra.persistence.AppointmentHistoryRepository;

@Controller
public class HistoryGraphqlController {

    private final AppointmentHistoryRepository repository;

    public HistoryGraphqlController(AppointmentHistoryRepository repository) {
        this.repository = repository;
    }

    @QueryMapping
    public List<AppointmentHistoryGraphql> appointmentsByPatient(
            @Argument("patientId") UUID patientId) {
        return repository.findByPatientIdOrderByAppointmentDateDesc(patientId.toString())
                .stream().map(AppointmentHistoryGraphql::from).toList();
    }

    @QueryMapping
    public List<AppointmentHistoryGraphql> appointmentsByDoctor(
            @Argument("doctorId") UUID doctorId) {
        return repository.findByDoctorIdOrderByAppointmentDateDesc(doctorId.toString())
                .stream().map(AppointmentHistoryGraphql::from).toList();
    }

    @QueryMapping
    public List<AppointmentHistoryGraphql> patientHistory(
            @Argument("patientId") UUID patientId) {
        return repository.findByPatientIdOrderByAppointmentDateDesc(patientId.toString())
                .stream().map(AppointmentHistoryGraphql::from).toList();
    }
}
