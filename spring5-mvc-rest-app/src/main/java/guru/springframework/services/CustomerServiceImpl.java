package guru.springframework.services;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.controllers.v1.CustomerController;
import guru.springframework.domain.Customer;
import guru.springframework.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
                        dto.setCustomerUrl(getCustomerUrl(customer.getId()));
                        return dto;
                        }).collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {

        return this.repository.findById(id)
                            .map(mapper::customerToCustomerDTO)
                            .map(dto -> {
                                dto.setCustomerUrl(getCustomerUrl(id));
                                return dto;
                            }).orElseThrow(ResourceNotFoundException::new);
    }

    public String getCustomerUrl(Long id) {
        return CustomerController.CUSTOMERS_BASE_URL +"/" + id;
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
        createdDto.setCustomerUrl(getCustomerUrl(savedCustomer.getId()));
        return createdDto;
    }

    @Override
    public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
        Customer customer = this.mapper.customerDTOToCustomer(customerDTO);
        customer.setId(id);
        return this.saveAndReturn(customer);
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
       return this.repository.findById(id).map(customerToPatch -> {
            if(customerDTO.getFirstName()!=null){
                customerToPatch.setFirstName(customerDTO.getFirstName());
            }
            if(customerDTO.getLastName()!=null){
                customerToPatch.setLastName(customerDTO.getLastName());
            }
            CustomerDTO dto = this.mapper.customerToCustomerDTO(repository.save(customerToPatch));
            dto.setCustomerUrl(getCustomerUrl(id));
            return dto;
       }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteCustomerById(Long id) {
        this.repository.deleteById(id);
    }
}
