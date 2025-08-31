package hospitalSystem.example.Hospital.repository;

import hospitalSystem.example.Hospital.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    @Query("SELECT m FROM Medicine m WHERE m.stockQuantity > 0")
    List<Medicine> findAllWithAvailableQuantity();

}
