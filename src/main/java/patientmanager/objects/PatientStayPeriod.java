package patientmanager.objects;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "PERIOD")
public class PatientStayPeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    private LocalDate admissionDate;

    private LocalDate dischargeDate;

    @NotNull(message = "Travel voucher is required")
    private  TravelVoucher travelVoucher;

    public  PatientStayPeriod() {
        admissionDate = LocalDate.now();
    }

    @Override
    public String toString() {
        return "PatientStayPeriod [id=" + id + ", admissionDate=" + admissionDate +
                ", dischargeDate=" + dischargeDate + ", travelVoucher=" + travelVoucher + "]";
    }

}
