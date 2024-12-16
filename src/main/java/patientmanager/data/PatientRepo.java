package patientmanager.data;

import org.springframework.data.jpa.repository.JpaRepository;
import patientmanager.objects.Patient;

public interface PatientRepo extends JpaRepository<Patient, Long> {
    Patient findByPassportID(String passportID);
}
