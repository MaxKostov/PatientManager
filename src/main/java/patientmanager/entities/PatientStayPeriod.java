package patientmanager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "medicine_patientStayPeriod", joinColumns = @JoinColumn(name = "patientStayPeriod_id"), inverseJoinColumns = @JoinColumn(name = "medicine_id"))
    @JsonManagedReference
    private Set<Medicine> medicines;

    public  PatientStayPeriod() {
        admissionDate = LocalDate.now();
    }

    public boolean addMedicine(Medicine medicine) {
        return medicines.add(medicine);
    }

    @Override
    public String toString() {
        return "PatientStayPeriod [id=" + id + ", admissionDate=" + admissionDate +
                ", dischargeDate=" + dischargeDate + ", travelVoucher=" + travelVoucher + "]";
    }

}
