package patientmanager.services.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import patientmanager.data.ProcedureRepo;
import patientmanager.entities.Procedure;
import patientmanager.services.ProcedureService;

import java.util.List;
import java.util.Optional;

@Service
public class ProcedureServiceImpl implements ProcedureService {
    private final ProcedureRepo procedureRepo;

    @Autowired
    public ProcedureServiceImpl(ProcedureRepo procedureRepo) {
        this.procedureRepo = procedureRepo;
    }


    @Override
    public Procedure createProcedure(Procedure procedure) {
        Procedure optionalProcedure = procedureRepo.findByName(procedure.getName());
        if (optionalProcedure != null) {
            return null;
        }
        return procedureRepo.save(procedure);
    }

    @Override
    @Transactional
    public Procedure updateProcedure(Procedure procedure) {
        if (procedure == null || procedure.getId() == null) {
            throw new IllegalArgumentException("Procedure and its ID must not be null.");
        }

        Optional<Procedure> existingProcedure = procedureRepo.findById(procedure.getId());
        if (existingProcedure.isEmpty()) {
            throw new EntityNotFoundException("Procedure with ID " + procedure.getId() + " not found.");
        }

        return procedureRepo.save(procedure);
    }

    @Override
    @Transactional
    public Procedure deleteProcedure(int id) {
        Optional<Procedure> procedure = procedureRepo.findById((long) id);
        if (procedure.isPresent()) {
            procedureRepo.delete(procedure.get());
            return procedure.get();
        } else {
            throw new IllegalArgumentException("Procedure with ID " + id + " not found.");
        }
    }

    @Override
    public Procedure getProcedureByName(String procedureName) {
        Procedure optionalProcedure = procedureRepo.findByName(procedureName);
        if (optionalProcedure == null) {
            throw new IllegalArgumentException("Procedure with name " + procedureName + " not found.");
        }
        return optionalProcedure;
    }

    @Override
    public List<Procedure> getAllProcedure() {
        return procedureRepo.findAll();
    }

    @Override
    public  Procedure getProcedureById(int id) {
        return procedureRepo.findById((long) id).orElse(null);
    }
}
