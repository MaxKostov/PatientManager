package patientmanager.objects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<PatientStayPeriod> periodList = new LinkedList<>();

    public boolean addPeriod(PatientStayPeriod period) {
        if ((periodList.isEmpty() || periodList.getLast().getDischargeDate() != null)) {
            period.setPatient(this);
            periodList.add(period);
            return true;
        } else {
            System.out.println("This patient has not been discharged yet");
            return false;
        }
    }

    public PatientStayPeriod getLastPeriod() {
        return periodList.isEmpty() ? new PatientStayPeriod() : periodList.getLast();
    }

    @Override
    public String toString() {
        return "Patient [id=" + id + ", name=" + name + ", surname=" + surname +
                ", passportID=" + passportID + ", birthDate=" + birthDate + ", address=" + address + "]";
    }



    public Patient(){}
}
