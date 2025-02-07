package patientmanager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "patientStayPeriod", cascade = CascadeType.ALL)
    private List<Prescription> prescriptions = new ArrayList<>();

    public void prescribeMedicine(Medicine medicine, int quantity) {
        if (medicine.getQuantity() < quantity) {
            throw new IllegalStateException("There are less than quantity");
        }

        Prescription prescription = new Prescription();
        prescription.setMedicine(medicine);
        prescription.setPatientStayPeriod(this);
        prescription.setAssignedQuantity(quantity);

        prescriptions.add(prescription);
        medicine.decreaseStock(quantity);
    }


    public  PatientStayPeriod() {
        admissionDate = LocalDate.now();
    }

    @Override
    public String toString() {
        return "PatientStayPeriod [id=" + id + ", admissionDate=" + admissionDate +
                ", dischargeDate=" + dischargeDate + ", travelVoucher=" + travelVoucher + "]";
    }

}
