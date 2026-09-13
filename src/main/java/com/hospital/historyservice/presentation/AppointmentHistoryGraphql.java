package com.hospital.historyservice.presentation;

import java.util.UUID;

import com.hospital.historyservice.infra.persistence.AppointmentHistory;

public record AppointmentHistoryGraphql(
        UUID appointmentId,
        String patientId,
        String doctorId,
        String appointmentDate,
        String status,
        String notes,
        String createdAt,
        String updatedAt) {

    public static AppointmentHistoryGraphql from(AppointmentHistory history) {
        return new AppointmentHistoryGraphql(
                history.getAppointmentId(),
                history.getPatientId(),
                history.getDoctorId(),
                history.getAppointmentDate().toString(),
                history.getStatus(),
                history.getNotes(),
                history.getCreatedAt().toString(),
                history.getUpdatedAt().toString()
        );
    }
}
