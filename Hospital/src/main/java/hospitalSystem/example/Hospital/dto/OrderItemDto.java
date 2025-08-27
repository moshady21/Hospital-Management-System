package hospitalSystem.example.Hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class OrderItemDto {
    private Long medicineId;
    private String medicineName;
    private int quantity;
    private double price;
}


