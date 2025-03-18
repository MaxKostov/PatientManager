package patientmanager.services;

import patientmanager.entities.Procedure;

import java.util.List;

public interface ProcedureService {
    Procedure createProcedure(Procedure procedure);

    Procedure updateProcedure(Procedure procedure);

    Procedure deleteProcedure(int id);

    Procedure getProcedureByName(String procedureName);

    List<Procedure> getAllProcedure();

    Procedure getProcedureById(int id);
}
