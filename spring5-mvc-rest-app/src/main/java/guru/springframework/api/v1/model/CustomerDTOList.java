package guru.springframework.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class CustomerDTOList {
    private List<CustomerDTO> customers;
}
