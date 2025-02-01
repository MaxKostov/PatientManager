package patientmanager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@Table(name = "MED_INFO")
@AllArgsConstructor
public class MedicalInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String dischargeSummary;
    private String initialDiagnosis;

    @OneToOne(mappedBy = "medicalInfo")
    @JsonIgnore
    private PatientStayPeriod patientStayPeriod;

    public MedicalInfo(){}
}
