package guru.springframework.api.v1.mapper;

import guru.springframework.domain.Customer;
import guru.springframework.model.CustomerDTO;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerMapperTest {

    public static final long ID = 1L;
    public static final String FIRST_NAME = "John";
    public static final String LAST_NAME = "Foo";
    private CustomerMapper mapper = CustomerMapper.INSTANCE;

    @Test
    public void customerToCustomerDTO() {
        //given
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);

        //when
        CustomerDTO dto = mapper.customerToCustomerDTO(customer);

        //then
        assertEquals(FIRST_NAME, dto.getFirstName());
        assertEquals(LAST_NAME, dto.getLastName());
    }

    @Test
    public void customerDTOToCustomer() {
        //given
        CustomerDTO dto = new CustomerDTO();
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME);

        Customer customer = mapper.customerDTOToCustomer(dto);

        //then
        assertEquals(FIRST_NAME, customer.getFirstName());
        assertEquals(LAST_NAME, customer.getLastName());
    }
}