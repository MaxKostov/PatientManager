package patientmanager.services;

import patientmanager.entities.Patient;
import patientmanager.entities.PatientStayPeriod;
import patientmanager.entities.TravelVoucher;

import java.util.List;

public interface PatientService {
    List<Patient> getAllPatients();

    Patient getPatientById(long id);

    Patient getPatientByPassportId(String passportId);

    Patient savePatient(Patient patient);

    Patient updatePatient(Patient patient);

    Patient dischargePatient(String passportID);

    Patient addStayPeriod(Patient patient, PatientStayPeriod patientStayPeriod);
}
