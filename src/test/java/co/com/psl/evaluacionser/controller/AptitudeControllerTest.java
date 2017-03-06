package co.com.psl.evaluacionser.controller;

import co.com.psl.evaluacionser.service.AptitudeService;
import co.com.psl.evaluacionser.service.dto.AptitudeDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AptitudeControllerTest {

    @Mock
    private AptitudeService mockAptitudeService;
    private AptitudeController aptitudeController;

    @Before
    public void setup() {
        aptitudeController = new AptitudeController(mockAptitudeService);
    }

    @Test
    public void getAptitudesReturnHttpStatusOK() {
        when(mockAptitudeService.findAllAptitudes()).thenReturn(new ArrayList<AptitudeDto>());

        ResponseEntity<List<AptitudeDto>> responseEntity = aptitudeController.getAptitudes();

        HttpStatus responseStatus = responseEntity.getStatusCode();
        assertEquals("OK", responseStatus.name());
    }

    @Test
    public void getAptitudesShouldReturnAptitudeArraySize1() {
        List<AptitudeDto> aptitudeListExample = new ArrayList<>();
        AptitudeDto aptitudeExample = new AptitudeDto("2", "Comunicacion", "Comunication");
        aptitudeListExample.add(aptitudeExample);
        when(mockAptitudeService.findAllAptitudes()).thenReturn(aptitudeListExample);

        ResponseEntity<List<AptitudeDto>> responseEntity = aptitudeController.getAptitudes();

        List<AptitudeDto> allAptitudes = responseEntity.getBody();
        assertEquals(1, allAptitudes.size());

        AptitudeDto aptitudeReturned = allAptitudes.get(0);
        assertEquals("2", aptitudeReturned.getId());
        assertEquals("Comunicacion", aptitudeReturned.getEs());
        assertEquals("Comunication", aptitudeReturned.getEn());
    }

    @Test
    public void getAptitudesWithAptitudeListNullReturnsNotFound() {
        when(mockAptitudeService.findAllAptitudes()).thenReturn(null);

        ResponseEntity<List<AptitudeDto>> responseEntity = aptitudeController.getAptitudes();
        HttpStatus responseStatus = responseEntity.getStatusCode();

        assertEquals("NOT_FOUND", responseStatus.name());
    }

}
