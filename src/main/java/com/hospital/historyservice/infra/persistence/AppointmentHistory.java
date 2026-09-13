package com.hospital.historyservice.infra.persistence;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "appointment_history")
public class AppointmentHistory {

    @Id
    private UUID appointmentId;
    private String patientId;
    private String doctorId;
    private LocalDateTime appointmentDate;
    private String status;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    protected AppointmentHistory() {
    }

    public void update(
            String patientId,
            String doctorId,
            LocalDateTime appointmentDate,
            String status,
            String notes,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
        this.status = status;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public AppointmentHistory(UUID appointmentId, AppointmentEvent event) {
        this.appointmentId = appointmentId;
        update(event.patientId(), event.doctorId(), event.appointmentDate(), event.status(),
                event.notes(), event.createdAt(), event.updatedAt());
    }

    public UUID getAppointmentId() {
        return appointmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public String getStatus() {
        return status;
    }

    public String getNotes() {
        return notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
