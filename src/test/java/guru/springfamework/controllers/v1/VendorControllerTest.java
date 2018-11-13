package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.RestResponseEntityExceptionHandler;
import guru.springfamework.services.ResourceNotFoundException;
import guru.springfamework.services.VendorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VendorControllerTest extends AbstractRestControllerTest {

    public static final long ID = 1L;
    public static final String VENDOR_NAME = "Vendor name";
    @Mock
    private VendorService service;

    @InjectMocks
    private VendorController controller;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.controller)
                        .setControllerAdvice(new RestResponseEntityExceptionHandler())
                        .build();
    }

    @Test
    public void testGetAllVendors() throws Exception {
        //given
        when(this.service.getAllVendors()).thenReturn(Arrays.asList(new VendorDTO(), new VendorDTO()));
        //when/then
        mockMvc.perform(get(VendorController.VENDORS_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }

    @Test
    public void testGetVendorById()throws Exception {
        //given:
         VendorDTO dto = new VendorDTO();
        dto.setId(ID);
        dto.setName(VENDOR_NAME);
        dto.setVendorUrl(VendorController.VENDORS_BASE_URL+"/"+dto.getId());
        when(this.service.getVendorById(anyLong())).thenReturn(dto);

        //when/then
        mockMvc.perform(get(VendorController.VENDORS_BASE_URL+"/"+ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo(VENDOR_NAME)))
                .andExpect(jsonPath("$.vendor_url",equalTo(VendorController.VENDORS_BASE_URL+"/"+ID)));
    }

    @Test
    public void testCreateVendor() throws Exception {
        //given
        VendorDTO dto = new VendorDTO();
        dto.setName(VENDOR_NAME);
        VendorDTO savedDto = new VendorDTO();
        savedDto.setId(ID);
        savedDto.setName(dto.getName());
        savedDto.setVendorUrl(VendorController.VENDORS_BASE_URL +"/"+savedDto.getId());
        when(this.service.createNewVendor(any(VendorDTO.class))).thenReturn(savedDto);

        //when/then
        mockMvc.perform(post(VendorController.VENDORS_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name",equalTo(VENDOR_NAME)))
                .andExpect(jsonPath("$.vendor_url",equalTo(VendorController.VENDORS_BASE_URL+"/"+ID)));
    }

    @Test
    public void testUpdateVendor()throws Exception {
        VendorDTO dto = new VendorDTO();
        dto.setId(ID);
        dto.setName(VENDOR_NAME);
        when(this.service.saveVendorByDto(anyLong(), any(VendorDTO.class))).thenAnswer(i -> i.getArguments()[1]);
        mockMvc.perform(put(VendorController.VENDORS_BASE_URL+"/"+ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo(VENDOR_NAME)));
    }

    @Test
    public void testPatchVendor() throws Exception {
        //given
        VendorDTO dto = new VendorDTO();
        dto.setId(ID);
        dto.setName("Updated");
        when(this.service.patchVendor(anyLong(),any(VendorDTO.class))).thenAnswer(i -> i.getArguments()[1]);
        //when/then
        mockMvc.perform(patch(VendorController.VENDORS_BASE_URL+"/"+ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo("Updated")));
    }

    @Test
    public void testDeleteVendorById() throws Exception {
        //whem/then
        mockMvc.perform(delete(VendorController.VENDORS_BASE_URL+"/"+ID)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
        verify(this.service, times(1)).deleteVendorById(anyLong());
    }

    @Test
    public void testNotFoundVendor() throws Exception {
        //given
        when(this.service.getVendorById(anyLong())).thenThrow(ResourceNotFoundException.class);

        //when/then
        mockMvc.perform(get(VendorController.VENDORS_BASE_URL+"/"+ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}