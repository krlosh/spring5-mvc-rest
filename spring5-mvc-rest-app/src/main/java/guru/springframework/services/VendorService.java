package guru.springframework.services;

import guru.springframework.api.v1.model.VendorDTO;

import java.util.List;

public interface VendorService {
    List<VendorDTO> getAllVendors();

    VendorDTO getVendorById(Long vendorId);

    VendorDTO createNewVendor(VendorDTO newVendor);

    VendorDTO saveVendorByDto(Long id, VendorDTO vendor);

    VendorDTO patchVendor(long id, VendorDTO dto);

    void deleteVendorById(Long id);
}
