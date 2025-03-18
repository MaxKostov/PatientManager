package patientmanager.services;

import patientmanager.entities.PatientStayPeriod;

import java.util.List;

public interface PatientStayPeriodService {
    PatientStayPeriod addInitialDiagnosis(String initDiagnosis, String passportId);

    PatientStayPeriod addDischargeSummary(String dischargeSummary, String passportId);

    PatientStayPeriod showPatientStayPeriod(String passportId);

    List<PatientStayPeriod> showAllPeriods(String passportId);

    List<PatientStayPeriod> getFilteredPeriods(Integer month, Integer voucher, Integer year, Boolean active);
}
