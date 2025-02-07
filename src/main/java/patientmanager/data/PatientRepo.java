package patientmanager.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import patientmanager.entities.Patient;

public interface PatientRepo extends JpaRepository<Patient, Long> {
    @Query("SELECT p FROM Patient p LEFT JOIN FETCH p.periodList WHERE p.passportID = :passportId")
    Patient findByPassportID(@Param("passportId") String passportID);
}
