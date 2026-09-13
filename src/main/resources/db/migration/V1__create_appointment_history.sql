CREATE TABLE appointment_history (
    appointment_id UUID PRIMARY KEY,
    patient_id VARCHAR(255) NOT NULL,
    doctor_id VARCHAR(255) NOT NULL,
    appointment_date TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL,
    notes TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_appointment_history_patient ON appointment_history(patient_id);
CREATE INDEX idx_appointment_history_doctor ON appointment_history(doctor_id);