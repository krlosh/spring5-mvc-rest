package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorDTOsList;
import guru.springfamework.services.VendorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
@Api(description = "This is my vendor API")
@RestController
@RequestMapping(VendorController.VENDORS_BASE_URL)
public class VendorController {

    public static final String VENDORS_BASE_URL = "/api/v1/vendors";

    private VendorService service;

    public VendorController(VendorService service) {
        this.service = service;
    }

    @ApiOperation(value = "Get list of vendors")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VendorDTOsList getAllVendors(){
        return new VendorDTOsList(this.service.getAllVendors());
    }

    @ApiOperation(value = "Get vendor from id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO getVendorById(@PathVariable Long id) {
        return this.service.getVendorById(id);
    }

    @ApiOperation(value = "Modify an existing vendor")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO updateVendor(@PathVariable Long id, @RequestBody VendorDTO dto){
        return this.service.saveVendorByDto(id, dto);
    }

    @ApiOperation(value = "Create new vendor")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createNewVendor(@RequestBody VendorDTO dto){
        return this.service.createNewVendor(dto);
    }

    @ApiOperation(value = "Delete a vendor")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendorById(@PathVariable Long id) {
        this.service.deleteVendorById(id);
    }

    @ApiOperation(value = "Update a vendor property")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO patchVendor(@PathVariable Long id, @RequestBody VendorDTO dto){
        return this.service.patchVendor(id, dto);
    }
}
