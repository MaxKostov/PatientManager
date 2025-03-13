package patientmanager.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import patientmanager.entities.PatientStayPeriod;
import patientmanager.entities.TravelVoucher;

import java.util.List;

public interface PatientStayPeriodRepo extends JpaRepository<PatientStayPeriod, Long> {
    @Query(value = "SELECT * FROM period p WHERE EXTRACT(MONTH FROM p.admission_date) = :month AND p.travel_voucher = :travelVoucher", nativeQuery = true)
    List<PatientStayPeriod> findByMonthAndTravelVoucher(@Param("month") int month, @Param("travelVoucher") TravelVoucher travelVoucher);

}
