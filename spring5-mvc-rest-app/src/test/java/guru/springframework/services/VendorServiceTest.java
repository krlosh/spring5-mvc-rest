package guru.springframework.services;

import guru.springframework.api.v1.mapper.VendorMapper;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.VendorRepository;
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

public class VendorServiceTest {

    public static final String NAME = "Vendor 1";
    public static final long ID = 1L;
    @Mock
    private VendorRepository repository;

    private VendorService service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.service = new VendorServiceImpl(VendorMapper.INSTANCE, this.repository);
    }

    @Test
    public void testGetAllVendors() {
        //given
        List<Vendor> vendorList = Arrays.asList(new Vendor(), new Vendor());
        when(this.repository.findAll()).thenReturn(vendorList);

        //when
        List<VendorDTO> dtoList = this.service.getAllVendors();
        assertEquals(2, dtoList.size());

    }

    @Test
    public void testGetVendorById() {
        //given
        Vendor v1 = new Vendor();
        v1.setId(ID);
        v1.setName(NAME);
        when(this.repository.findById(any())).thenReturn(Optional.of(v1));

        //when:
        VendorDTO dto = this.service.getVendorById(ID);

        //then
        assertEquals(Long.valueOf(ID), dto.getId());
        assertEquals(NAME, dto.getName());
        assertEquals("/api/v1/vendors" + "/"+ID, dto.getVendorUrl());
    }

    @Test
    public void testNewCustomerCreation(){
        //given:
        VendorDTO v1 = new VendorDTO();
        v1.setName(NAME);

        Vendor savedVendor = new Vendor();
        savedVendor.setId(ID);
        savedVendor.setName(v1.getName());
        when(this.repository.save(any(Vendor.class))).thenReturn(savedVendor);

        //when:
        VendorDTO createdDto = this.service.createNewVendor(v1);

        //then:
        assertEquals(Long.valueOf(ID), createdDto.getId());
        assertEquals(NAME, createdDto.getName());
        assertEquals("/api/v1/vendors" + "/"+ID, createdDto.getVendorUrl());

    }
    
    @Test
    public void testUpdateVendor(){
        //given:
        VendorDTO v1 = new VendorDTO();
        v1.setName(NAME);

        Vendor savedVendor = new Vendor();
        savedVendor.setId(ID);
        savedVendor.setName(v1.getName());
        when(this.repository.save(any(Vendor.class))).thenReturn(savedVendor);

        //when:
        VendorDTO savedDto = this.service.saveVendorByDto(ID,v1);

        //then:
        assertEquals(Long.valueOf(ID), savedDto.getId());
        assertEquals(NAME, savedDto.getName());
        assertEquals("/api/v1/vendors" + "/"+ID, savedDto.getVendorUrl());
    }

    @Test
    public void testPatchVendorName() {
        //Given:
        VendorDTO dto = new VendorDTO();
        dto.setName("Patched name");
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(NAME);
        when(this.repository.findById(any())).thenReturn(Optional.of(vendor));
        when(this.repository.save(any(Vendor.class))).thenAnswer(i -> i.getArguments()[0]);

        //when:
        VendorDTO patchedDto = this.service.patchVendor(ID,dto);

        //then
        assertEquals(Long.valueOf(ID), patchedDto.getId());
        assertEquals("Patched name", patchedDto.getName() );
        assertEquals("/api/v1/vendors" + "/"+ID, patchedDto.getVendorUrl());
    }

    @Test
    public void testDeleteVendorById() {
        Long id = 1L;

        this.service.deleteVendorById(id);

        verify(this.repository, times(1)).deleteById(anyLong());
    }
}