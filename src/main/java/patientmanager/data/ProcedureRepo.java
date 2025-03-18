package patientmanager.data;

import org.springframework.data.jpa.repository.JpaRepository;
import patientmanager.entities.Procedure;

public interface ProcedureRepo extends JpaRepository<Procedure, Long> {
    Procedure findByName(String name);
}
