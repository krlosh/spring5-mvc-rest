package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerMapper mapper;
    private CustomerRepository repository;

    public CustomerServiceImpl(CustomerMapper mapper, CustomerRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return this.repository.findAll()
                    .stream()
                    .map(customer-> {
                        CustomerDTO dto = mapper.customerToCustomerDTO(customer);
                        dto.setCustomerUrl("/api/v1/customers/"+customer.getId());
                        return dto;
                        }).collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        Optional<Customer> customerOptional = this.repository.findById(id);
        if (!customerOptional.isPresent()) {
            throw new IllegalArgumentException("Customer "+id+"not found");
        }
        return mapper.customerToCustomerDTO(customerOptional.get());
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        Customer customerToSave = this.mapper.customerDTOToCustomer(customerDTO);
        CustomerDTO createdDto = saveAndReturn(customerToSave);
        return createdDto;
    }

    public CustomerDTO saveAndReturn(Customer customerToSave) {
        Customer savedCustomer = this.repository.save(customerToSave);
        CustomerDTO createdDto = this.mapper.customerToCustomerDTO(savedCustomer);
        createdDto.setCustomerUrl("/api/v1/customers/"+savedCustomer.getId());
        return createdDto;
    }

    @Override
    public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
        Customer customer = this.mapper.customerDTOToCustomer(customerDTO);
        customer.setId(id);
        return this.saveAndReturn(customer);
    }
}
