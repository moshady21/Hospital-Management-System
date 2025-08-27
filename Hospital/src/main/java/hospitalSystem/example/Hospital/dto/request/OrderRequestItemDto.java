package hospitalSystem.example.Hospital.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestItemDto {
    @NotNull(message = "Medicine ID is required")
    private Long medicineId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
}
