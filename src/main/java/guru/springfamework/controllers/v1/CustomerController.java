package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerDTOList;
import guru.springfamework.services.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Api(description = "This is customer controller")
@Controller
@RequestMapping(CustomerController.CUSTOMERS_BASE_URL)
public class CustomerController {

    public static final String CUSTOMERS_BASE_URL = "/api/v1/customers";
    private CustomerService service;


    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @ApiOperation(value = "This will get a list of customers.", notes = "These are some notes about the API.")
    @GetMapping
    public ResponseEntity<CustomerDTOList> getAllCustomers(){
        return new ResponseEntity<>(new CustomerDTOList(this.service.getAllCustomers()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id){
        return new ResponseEntity<>(this.service.getCustomerById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createNewCustomer(@RequestBody CustomerDTO dto) {
        return  new ResponseEntity<>(this.service.createNewCustomer(dto),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO dto) {
        return new ResponseEntity<>(this.service.saveCustomerByDTO(id, dto), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomerDTO> patchCustomer(@PathVariable Long id, @RequestBody CustomerDTO dto) {
        return new ResponseEntity<>(this.service.patchCustomer(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id){
        this.service.deleteCustomerById(id);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}

