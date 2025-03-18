package patientmanager.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import patientmanager.entities.PatientStayPeriod;

import java.util.List;

public interface PatientStayPeriodRepo extends JpaRepository<PatientStayPeriod, Long> {
    @Query(value = """
    SELECT * FROM period p 
    WHERE (COALESCE(:month, 0) = 0 OR EXTRACT(MONTH FROM p.admission_date) = CAST(:month AS INTEGER))
      AND (:travelVoucher IS NULL OR p.travel_voucher = :travelVoucher)
      AND (COALESCE(:year, 0) = 0 OR EXTRACT(YEAR FROM p.admission_date) = CAST(:year AS INTEGER))
      AND (:active IS NULL OR :active = false OR p.discharge_date IS NULL)
    """, nativeQuery = true)
    List<PatientStayPeriod> findByFilters(@Param("month") Integer month,
                                          @Param("travelVoucher") Integer travelVoucher,
                                          @Param("year") Integer year,
                                          @Param("active") Boolean active);
}
