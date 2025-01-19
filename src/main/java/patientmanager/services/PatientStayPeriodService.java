package patientmanager.services;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import patientmanager.data.PatientRepo;
import patientmanager.data.PatientStayPeriodRepo;
import patientmanager.objects.MedicalInfo;
import patientmanager.objects.Patient;
import patientmanager.objects.PatientStayPeriod;

import java.util.List;

@Service
@Slf4j
public class PatientStayPeriodService {
    private static final Logger logger = LoggerFactory.getLogger(PatientStayPeriodService.class);
    private final PatientStayPeriodRepo patientStayPeriodRepo;
    private final PatientRepo patientRepo;

    @Autowired
    public PatientStayPeriodService(PatientStayPeriodRepo patientStayPeriodRepo, PatientRepo patientRepo) {
        this.patientStayPeriodRepo = patientStayPeriodRepo;
        this.patientRepo = patientRepo;
    }

    public PatientStayPeriod addInitialDiagnosis(String initDiagnosis, String passportId) {
        Patient patient = findPatientByPassportId(passportId);
        if (patient == null) {
            logger.warn("Cannot add initial diagnosis. Patient with passportId {} not found.", passportId);
            return null;
        }

        PatientStayPeriod period = getActivePatientStayPeriod(patient);
        if (period == null) {
            logger.warn("Cannot add initial diagnosis. Active stay period not found or patient already discharged.");
            return null;
        }

        MedicalInfo medicalInfo = period.getMedicalInfo();
        if (medicalInfo == null) {
            medicalInfo = new MedicalInfo();
            period.setMedicalInfo(medicalInfo);
        }

        medicalInfo.setInitialDiagnosis(initDiagnosis);
        patientStayPeriodRepo.save(period);
        return period;
    }

    public PatientStayPeriod addDischargeSummary(String dischargeSummary, String passportId) {
        Patient patient = findPatientByPassportId(passportId);
        if (patient == null) {
            logger.warn("Cannot add discharge summary. Patient with passportId {} not found.", passportId);
            return null;
        }

        PatientStayPeriod period = getActivePatientStayPeriod(patient);
        if (period == null) {
            logger.warn("Cannot add discharge summary. Active stay period not found or patient already discharged.");
            return null;
        }

        MedicalInfo medicalInfo = period.getMedicalInfo();
        if (medicalInfo == null) {
            logger.warn("Cannot add discharge summary. Medical information is not available for this stay period.");
            return null;
        }

        medicalInfo.setDischargeSummary(dischargeSummary);
        patientStayPeriodRepo.save(period);
        return period;
    }

    public PatientStayPeriod showPatientStayPeriod(String passportId) {
        Patient patient = findPatientByPassportId(passportId);
        if (patient == null) {
            logger.warn("Cannot display stay period. Patient with passportId {} not found.", passportId);
            return null;
        }
        return patient.getLastPeriod();
    }

    public List<PatientStayPeriod> showAllPeriods(String passportId) {
        Patient patient = findPatientByPassportId(passportId);
        if (patient == null) {
            logger.warn("Cannot display the list of periods. Patient with passportId {} not found.", passportId);
            return null;
        }
        return  patient.getPeriodList();
    }

    private Patient findPatientByPassportId(String passportId) {
        Patient patient = patientRepo.findByPassportID(passportId);
        if (patient == null) {
            logger.warn("Patient with passportId {} not found.", passportId);
        }
        return patient;
    }

    private PatientStayPeriod getActivePatientStayPeriod(Patient patient) {
        PatientStayPeriod period = patient.getLastPeriod();
        if (period == null || period.getDischargeDate() != null) {
            logger.warn("Active stay period not found or the patient has been discharged.");
            return null;
        }
        return period;
    }
}
