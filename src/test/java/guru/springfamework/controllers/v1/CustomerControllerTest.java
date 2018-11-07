package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest extends AbstractRestControllerTest{

    public static final long ID = 1L;
    public static final String FIRST_NAME = "John";
    public static final String LAST_NAME = "Foo";
    @Mock
    CustomerService service;

    @InjectMocks
    CustomerController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetAllCustomers() throws Exception {
        //given
        List<CustomerDTO> customerList = new ArrayList<>();
        CustomerDTO c1 = new CustomerDTO();
        c1.setId(ID);
        c1.setFirstName(FIRST_NAME);
        c1.setLastName(LAST_NAME);
        c1.setCustomerUrl("/api/v1/customers/"+ID);
        customerList.add(c1);
        CustomerDTO c2 = new CustomerDTO();
        c2.setId(2L);
        c2.setFirstName("First");
        c2.setLastName("Last");
        c1.setCustomerUrl("/api/v1/customers/2");
        customerList.add(c2);
        when(this.service.getAllCustomers()).thenReturn(customerList);
        //when

        mockMvc.perform(get("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    public void getCustomerById() throws Exception {
        //given
        CustomerDTO c1 = new CustomerDTO();
        c1.setId(ID);
        c1.setFirstName(FIRST_NAME);
        c1.setLastName(LAST_NAME);
        c1.setCustomerUrl("/api/v1/customers/"+ID);
        when(this.service.getCustomerById(any())).thenReturn(c1);

        mockMvc.perform(get("/api/v1/customers/"+ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME)));
    }

    @Test
    public void testCreateNewCustomer() throws Exception {
        //given
        CustomerDTO dto = new CustomerDTO();
        dto.setFirstName("Fred");
        dto.setLastName("Flinstones");

        CustomerDTO savedDto = new CustomerDTO();
        savedDto.setFirstName(dto.getFirstName());
        savedDto.setLastName(dto.getLastName());
        savedDto.setCustomerUrl("/api/v1/customers/1");
        when(this.service.createNewCustomer(any(CustomerDTO.class))).thenReturn(savedDto);

        //when/then
        mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName",equalTo("Fred")))
                .andExpect(jsonPath("$.lastName",equalTo("Flinstones")))
                .andExpect(jsonPath("$.customer_url",equalTo("/api/v1/customers/1")));

    }
}