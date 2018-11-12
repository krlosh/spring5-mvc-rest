package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    private VendorMapper mapper;
    private VendorRepository repository;

    public VendorServiceImpl(VendorMapper mapper, VendorRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        return this.repository.findAll()
                    .stream()
                    .map(mapper::vendorToVendorDTO)
                    .map(dto -> {
                        dto.setVendorUrl("/api/v1/vendors"+"/"+dto.getId());
                        return dto;
                    })
                    .collect(Collectors.toList());
    }

    @Override
    public VendorDTO getVendorById(Long vendorId) {
        return this.repository.findById(vendorId)
                .map(mapper::vendorToVendorDTO)
                .map(dto -> {
                    dto.setVendorUrl("/api/v1/vendors"+"/"+dto.getId());
                    return dto;
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO newVendor) {
        Vendor vendorToSave = this.mapper.vendorDtoToVendor(newVendor);
        VendorDTO dto = this.saveAndReturn(vendorToSave);
        return dto;
    }

    private VendorDTO saveAndReturn(Vendor vendor) {
        Vendor savedVendor = this.repository.save(vendor);
        VendorDTO dto = this.mapper.vendorToVendorDTO(savedVendor);
        dto.setVendorUrl("/api/v1/vendors" + "/" + savedVendor.getId());
        return dto;
    }

    @Override
    public VendorDTO saveVendorByDto(Long id,VendorDTO vendor) {
        Vendor vendorToSave = this.mapper.vendorDtoToVendor(vendor);
        vendorToSave.setId(id);
        return this.saveAndReturn(vendorToSave);
    }

    @Override
    public VendorDTO patchVendor(long id, VendorDTO dto) {
        return this.repository.findById(id)
                    .map(vendor ->{
                        if(dto.getName()!=null){
                            vendor.setName(dto.getName());
                        }
                        return this.saveAndReturn(vendor);
                    })
                    .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteVendorById(Long id) {
        this.repository.deleteById(id);
    }
}
