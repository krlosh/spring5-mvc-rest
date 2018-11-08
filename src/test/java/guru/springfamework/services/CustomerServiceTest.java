package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {

    public static final long ID = 1L;
    public static final String FIRST_NAME = "John";
    public static final String LAST_NAME = "Foo";
    @Mock
    CustomerRepository repository;

    CustomerService service;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.service = new CustomerServiceImpl(CustomerMapper.INSTANCE, this.repository);

    }

    @Test
    public void getAllCustomers() {
        //given:
        List<Customer> customers = Arrays.asList(new Customer(), new Customer());
        when(this.repository.findAll()).thenReturn(customers);

        //when:
        assertEquals(2, this.service.getAllCustomers().size());

    }

    @Test
    public void getCustomerById() {
        //given
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);
        when(this.repository.findById(any())).thenReturn(Optional.of(customer));

        //when
        CustomerDTO dto = this.service.getCustomerById(ID);
        assertEquals(Long.valueOf(ID), dto.getId());
        assertEquals(FIRST_NAME, dto.getFirstName());
        assertEquals(LAST_NAME, dto.getLastName());
    }

    @Test
    public void testNewCustomerCreation() {
        //given
        CustomerDTO dto = new CustomerDTO();
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME);

        Customer savedCustomer = new Customer();
        savedCustomer.setId(ID);
        savedCustomer.setFirstName(dto.getFirstName());
        savedCustomer.setLastName(dto.getLastName());
        when(this.repository.save(any(Customer.class))).thenReturn(savedCustomer);

        //when
        CustomerDTO newDto = this.service.createNewCustomer(dto);

        //then
        assertEquals(Long.valueOf(ID), newDto.getId());
        assertEquals(FIRST_NAME,newDto.getFirstName());
        assertEquals(LAST_NAME,newDto.getLastName());
        assertEquals("/api/v1/customers/1",newDto.getCustomerUrl());
    }

    @Test
    public void testUpdateCustomer(){
        //given
        CustomerDTO dto = new CustomerDTO();
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME);

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstName(dto.getFirstName());
        savedCustomer.setLastName(dto.getLastName());
        savedCustomer.setId(ID);
        when(this.repository.save(any(Customer.class))).thenReturn(savedCustomer);

        //when
        CustomerDTO updatedDto = this.service.saveCustomerByDTO(ID, dto);

        //then
        assertEquals(dto.getFirstName(), updatedDto.getFirstName());
        assertEquals("/api/v1/customers/1", updatedDto.getCustomerUrl());
    }

    @Test
    public void deleteCustomerById() throws Exception {

        Long id = 1L;

        this.service.deleteCustomerById(id);

        verify(this.repository, times(1)).deleteById(anyLong());
    }

}