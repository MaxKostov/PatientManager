package patientmanager.data;

import org.springframework.data.jpa.repository.JpaRepository;
import patientmanager.objects.MedicalInfo;

public interface MedicalInfoRepo extends JpaRepository<MedicalInfo, Long> {
}
