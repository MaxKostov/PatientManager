package patientmanager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "medicine", cascade = CascadeType.ALL)
    private List<Prescription> prescriptions = new ArrayList<>();

    public void decreaseStock(int amount) {
        if (this.quantity >= amount) {
            this.quantity -= amount;
        } else {
            throw new IllegalStateException("There are no enough stock to decrease quantity");
        }
    }
}
