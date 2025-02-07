package patientmanager.data;

import org.springframework.data.jpa.repository.JpaRepository;
import patientmanager.entities.MedicalInfo;

public interface MedicalInfoRepo extends JpaRepository<MedicalInfo, Long> {
}
