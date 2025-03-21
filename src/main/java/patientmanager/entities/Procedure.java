package patientmanager.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "procedure")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Procedure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @ManyToMany(mappedBy = "procedures")
    private List<PatientStayPeriod> patientStayPeriods;
}
