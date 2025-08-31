package hospitalSystem.example.Hospital.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class MedicineRequestDto {
    @NotBlank(message = "Medicine name is required")
    private String name;

    @NotNull(message = "Medicine price is required")
    private double price;

    @NotNull(message = "Medicine stock quantity is required")
    private int stockQuantity;

    @NotBlank(message = "Medicine description is required")
    private String description;


    //private List<Long> medicineDetails;
}
