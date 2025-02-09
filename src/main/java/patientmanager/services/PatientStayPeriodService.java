package patientmanager.services;

import patientmanager.entities.PatientStayPeriod;

import java.util.List;

public interface PatientStayPeriodService {
    public PatientStayPeriod addInitialDiagnosis(String initDiagnosis, String passportId);

    public PatientStayPeriod addDischargeSummary(String dischargeSummary, String passportId);

    public PatientStayPeriod showPatientStayPeriod(String passportId);

    public List<PatientStayPeriod> showAllPeriods(String passportId);

}
