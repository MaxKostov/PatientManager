package patientmanager.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference
    @JsonIgnore
    private Patient patient;

    private LocalDate admissionDate;

    private LocalDate dischargeDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "med_info_id", referencedColumnName = "id")
    private MedicalInfo medicalInfo;

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
