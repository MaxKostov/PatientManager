package patientmanager.services;

import patientmanager.entities.Medicine;

import java.util.List;

public interface MedicineService {
    public Medicine createMedicine(String name, int quantity, double price);

    public Medicine updateMedicine(Medicine medicine);

    public List<Medicine> getAllMedicine();

    public String deleteMedicine(long id);

    public void updateQuantity(Long medicineId, int quantityChange);

    public String assignMedicineToPatientStayPeriod(Long medicineId, Long patientStayPeriodId, int quantity);

    public Medicine getMedicine(long id);
}
