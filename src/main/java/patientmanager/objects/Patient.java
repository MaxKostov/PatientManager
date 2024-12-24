package patientmanager.objects;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@Data
@Entity
@Table(name = "PATIENT")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Surname is required")
    private String surname;

    @NotBlank(message = "Passport ID is required")
    private String passportID;

    @Past(message = "Birthdate must be in the past")
    private LocalDate birthDate;

    @NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "Travel voucher is required")
    private  TravelVoucher travelVoucher;

    @OneToMany(mappedBy = "patient")
    private List<PatientStayPeriod> periodList = new LinkedList<>();

    public void addPeriod(PatientStayPeriod period) {
        if (!periodList.isEmpty() && periodList.get(periodList.size() - 1).getDischargeDate() == null) {
            periodList.add(period);
        } else {
            System.out.println("This patient has not been discharged yet");
        }
    }

    public PatientStayPeriod getLastPeriod() {
        return periodList.getLast();
    }

    public Patient(){}
}
