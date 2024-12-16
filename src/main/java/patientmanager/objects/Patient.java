package patientmanager.objects;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String name;
    private String surname;
    private String passportID;
    private LocalDate birthDate;
    private String address;

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
