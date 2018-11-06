package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.junit.Before;
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
        assertEquals(Long.valueOf(ID), dto.getId());
        assertEquals(FIRST_NAME, dto.getFirstName());
        assertEquals(LAST_NAME, dto.getLastName());
    }
}