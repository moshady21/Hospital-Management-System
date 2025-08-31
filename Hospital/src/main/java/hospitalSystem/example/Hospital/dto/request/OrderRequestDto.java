package hospitalSystem.example.Hospital.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class OrderRequestDto {

    @NotNull(message = "Items are required")
    private List<OrderRequestItemDto> items;
}
