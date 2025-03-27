package patientmanager.services;

import patientmanager.entities.PatientStayPeriod;
import patientmanager.entities.Procedure;

import java.util.List;

public interface ProcedureService {
    Procedure createProcedure(Procedure procedure);

    Procedure updateProcedure(Procedure procedure);

    Procedure deleteProcedure(Long id);

    Procedure getProcedureByName(String procedureName);

    List<Procedure> getAllProcedure();

    Procedure getProcedureById(Long id);

    String assignProcedureToStayPeriod(Long procedure_id, PatientStayPeriod patientStayPeriod);

    String removeProcedureFromStayPeriod(Long procedure_id, PatientStayPeriod patientStayPeriod);
}
