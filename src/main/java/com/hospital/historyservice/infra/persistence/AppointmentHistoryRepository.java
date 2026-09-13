package com.hospital.historyservice.infra.persistence;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentHistoryRepository extends JpaRepository<AppointmentHistory, UUID> {

    List<AppointmentHistory> findByPatientIdOrderByAppointmentDateDesc(String patientId);

    List<AppointmentHistory> findByDoctorIdOrderByAppointmentDateDesc(String doctorId);
}
