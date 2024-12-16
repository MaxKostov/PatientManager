package patientmanager.objects;

import jakarta.persistence.*;
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

    public  PatientStayPeriod() {
        admissionDate = LocalDate.now();
    }
}
