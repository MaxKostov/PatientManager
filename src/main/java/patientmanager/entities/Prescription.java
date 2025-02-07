package patientmanager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    @JsonIgnore
    private Medicine medicine;

    @ManyToOne
    @JoinColumn(name = "stay_period_id")
    @JsonIgnore
    private PatientStayPeriod patientStayPeriod;

    private int assignedQuantity;;
}
