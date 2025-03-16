package patientmanager.services;

import patientmanager.entities.Medicine;

import java.util.List;

 public interface MedicineService {
     Medicine createMedicine(String name, int quantity, double price);

     Medicine updateMedicine(Medicine medicine);

     List<Medicine> getAllMedicine();

     String deleteMedicine(long id);

     void updateQuantity(Long medicineId, int quantityChange);

     String assignMedicineToPatientStayPeriod(Long medicineId, Long patientStayPeriodId, int quantity);

     Medicine getMedicine(long id);
}
