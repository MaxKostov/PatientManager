package patientmanager.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import patientmanager.entities.Medicine;

public interface MedicineRepo extends JpaRepository<Medicine, Long> {
    @Modifying
    @Query("UPDATE Medicine m SET m.quantity = m.quantity + :quantityChange WHERE m.id = :medicineId AND (m.quantity + :quantityChange) >= 0")
    int updateQuantity(@Param("medicineId") Long medicineId, @Param("quantityChange") int quantityChange);

}
