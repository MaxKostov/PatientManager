package patientmanager.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import patientmanager.data.MedicineRepo;
import patientmanager.data.PatientStayPeriodRepo;
import patientmanager.entities.Medicine;
import patientmanager.entities.PatientStayPeriod;
import patientmanager.entities.Prescription;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MedicineService {
    private static final Logger logger = LoggerFactory.getLogger(MedicineService.class);
    private final MedicineRepo medicineRepo;
    private final PatientStayPeriodRepo patientStayPeriodRepo;

    @Autowired
    public MedicineService(MedicineRepo medicineRepo, PatientStayPeriodRepo patientStayPeriodRepo) {
        this.medicineRepo = medicineRepo;
        this.patientStayPeriodRepo = patientStayPeriodRepo;
    }

    public Medicine createMedicine(Medicine medicine) {
        return medicineRepo.save(medicine);
    }

    public Medicine updateMedicine(Medicine medicine) {
        if (!medicineRepo.existsById(medicine.getId())) {
            throw new EntityNotFoundException("Medicine not found with id: " + medicine.getId());
        }
        return medicineRepo.save(medicine);
    }


    public Medicine getMedicine(long id) {
        return medicineRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Medicine not found with id: " + id));
    }


    public List<Medicine> getAllMedicine() {
        List<Medicine> medicines = medicineRepo.findAll();
        if (medicines.isEmpty()) {
            log.warn("No medicines found in the database");
        }
        return medicines;
    }


    @Transactional
    public String deleteMedicine(long id) {
        Medicine medicine = getMedicine(id);
        try {
            medicineRepo.delete(medicine);
            return "Medicine deleted successfully";
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete medicine with id " + id + " because it is linked to a patient.");
        }
    }


    @Transactional
    public void updateQuantity(Long medicineId, int quantityChange) {
        Medicine medicine = medicineRepo.findById(medicineId)
                .orElseThrow(() -> new EntityNotFoundException("Medicine not found with id: " + medicineId));

        int newQuantity = medicine.getQuantity() + quantityChange;
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Not enough medicine in stock to assign.");
        }

        medicine.setQuantity(newQuantity);
        medicineRepo.save(medicine);
    }


    @Transactional
    public String assignMedicineToPatientStayPeriod(Long medicineId, Long patientStayPeriodId, int quantity) {
        Medicine medicine = medicineRepo.findById(medicineId)
                .orElseThrow(() -> new EntityNotFoundException("Medicine not found with id: " + medicineId));

        PatientStayPeriod patientStayPeriod = patientStayPeriodRepo.findById(patientStayPeriodId)
                .orElseThrow(() -> new EntityNotFoundException("Patient Stay Period not found with id: " + patientStayPeriodId));

        Optional<Prescription> existingPrescription = patientStayPeriod.getPrescriptions().stream()
                .filter(m -> m.getMedicine().getId() == medicineId)
                .findFirst();
        if (existingPrescription.isPresent()) {
            Prescription patientMedicine = existingPrescription.get();
            medicine.decreaseStock(quantity);
            patientMedicine.setAssignedQuantity(patientMedicine.getAssignedQuantity() + quantity);
        } else {
            patientStayPeriod.prescribeMedicine(medicine, quantity);
        }

        patientStayPeriodRepo.save(patientStayPeriod);
        medicineRepo.save(medicine);


        return "Successfully assigned the medicine to patient stay period.";
    }


}
