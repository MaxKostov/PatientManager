package patientmanager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "medicine")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Name is required")
    private String name;

    @Min(value = 0, message = "Quantity can't be less than 0")
    private int quantity;

    @NotNull(message = "Price is required")
    private double price;

    @ManyToMany(mappedBy = "medicines", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<PatientStayPeriod> patientStayPeriodSet;
}
