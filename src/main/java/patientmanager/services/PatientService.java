package patientmanager.services;

import patientmanager.entities.Patient;
import patientmanager.entities.PatientStayPeriod;

import java.util.List;

public interface PatientService {
    public List<Patient> getAllPatients();

    public Patient getPatientById(long id);

    public Patient getPatientByPassportId(String passportId);

    public Patient savePatient(Patient patient);

    public Patient updatePatient(Patient patient);

    public Patient dischargePatient(String passportID);

    public Patient addStayPeriod(Patient patient, PatientStayPeriod patientStayPeriod);
}
