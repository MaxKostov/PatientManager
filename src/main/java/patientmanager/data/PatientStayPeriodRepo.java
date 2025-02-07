package patientmanager.data;

import org.springframework.data.jpa.repository.JpaRepository;
import patientmanager.entities.PatientStayPeriod;

public interface PatientStayPeriodRepo extends JpaRepository<PatientStayPeriod, Long> {
}
