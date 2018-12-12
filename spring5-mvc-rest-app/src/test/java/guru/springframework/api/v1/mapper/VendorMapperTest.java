package guru.springframework.api.v1.mapper;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.domain.Vendor;
import org.junit.Test;

import static org.junit.Assert.*;

public class VendorMapperTest {

    public static final long ID = 1L;
    public static final String VENDOR_NAME = "Vendor name";
    private VendorMapper mapper = VendorMapper.INSTANCE;

    @Test
    public void testVendorToVendorDTO() {
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(VENDOR_NAME);
        VendorDTO dto = mapper.vendorToVendorDTO(vendor);
        assertEquals(Long.valueOf(ID), dto.getId());
        assertEquals(VENDOR_NAME, dto.getName());
    }

    @Test
    public void testVendorDtoToVendor() {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setId(ID);
        vendorDTO.setName(VENDOR_NAME);
        Vendor vendor = mapper.vendorDtoToVendor(vendorDTO);
        assertEquals(Long.valueOf(ID), vendor.getId());
        assertEquals(VENDOR_NAME, vendor.getName());
    }
}