package patientmanager.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import patientmanager.data.PatientRepo;
import patientmanager.data.PatientStayPeriodRepo;
import patientmanager.entities.Patient;
import patientmanager.entities.PatientStayPeriod;
import patientmanager.services.PatientService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientServiceImpl implements PatientService {
    private final PatientRepo patientRepo;
    private final PatientStayPeriodRepo patientStayPeriodRepo;

    public List<Patient> getAllPatients() {
        return patientRepo.findAll();
    }

    public Patient getPatientById(long id) {
        Optional<Patient> patient = patientRepo.findById(id);
        if (patient.isPresent()) {
            return patient.get();
        }
        log.info("Patient with id: " + id + " not found");
        return null;
    }

    public Patient getPatientByPassportId(String passportId) {
        Optional<Patient> optionalPatient = Optional.ofNullable(patientRepo.findByPassportID(passportId));
        if (optionalPatient.isEmpty()) {
            log.info("Patient with id: " + passportId + " not found");
            return null;
        }
        return optionalPatient.get();
    }


    public Patient savePatient(Patient patient) {
        PatientStayPeriod patientStayPeriod = patient.getLastPeriod();

        Patient savedPatient = patientRepo.save(patient);
        patientStayPeriodRepo.save(patientStayPeriod);

        log.info("Patient saved: " + savedPatient);
        return savedPatient;
    }


    public Patient updatePatient(Patient patient) {
        Optional<Patient> optionalPatient = Optional.ofNullable(patientRepo.findByPassportID(patient.getPassportID()));
        if (optionalPatient.isEmpty()) {
            log.info("Patient with id: " + patient.getId() + " not found");
            return null;
        }

        Patient existingPatient = optionalPatient.get();
        existingPatient.setName(patient.getName());
        existingPatient.setSurname(patient.getSurname());
        existingPatient.setPassportID(patient.getPassportID());
        existingPatient.setAddress(patient.getAddress());
        existingPatient.setBirthDate(patient.getBirthDate());
        Patient updatedPatient = patientRepo.save(existingPatient);
        log.info("Patient updated: " + updatedPatient);
        return updatedPatient;
    }

    public Patient dischargePatient(String passportID) {
        Optional<Patient> optionalPatient = Optional.ofNullable(patientRepo.findByPassportID(passportID));
        if (optionalPatient.isEmpty()) {
            log.info("Patient with passport id: " + passportID + " not found");
            return null;
        }

        Patient existingPatient = optionalPatient.get();
        PatientStayPeriod lastPeriod = existingPatient.getLastPeriod();

        if (lastPeriod == null || lastPeriod.getDischargeDate() != null) {
            log.info("No active stay period found for patient with passport id: " + passportID);
            return null;
        }

        lastPeriod.setDischargeDate(LocalDate.now());
        patientStayPeriodRepo.save(lastPeriod);

        log.info("Patient discharged: " + passportID);
        return existingPatient;
    }

    public Patient addStayPeriod(Patient patient, PatientStayPeriod patientStayPeriod) {
        if (patient.addPeriod(patientStayPeriod)) {
            System.out.println(patientStayPeriodRepo.save(patientStayPeriod));
            return patient;
        } else {
            return null;
        }
    }

    public void deletePatient(long id) {
        patientRepo.deleteById(id);
    }
}
