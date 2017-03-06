package co.com.psl.evaluacionser.controller;

import co.com.psl.evaluacionser.domain.Behavior;
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
        // Setup aptitude mock for getAptitude methods
        List<AptitudeDto> aptitudeList = new ArrayList<>();
        AptitudeDto aptitude = new AptitudeDto("1", "Apertura", "Openness");
        aptitudeList.add(aptitude);
        when(mockAptitudeService.findAllAptitudes()).thenReturn(aptitudeList);
        when(mockAptitudeService.findAptitudeById("1")).thenReturn(aptitude);

        // Setup aptitude mock for getBehavior methods
        List<Behavior> behaviorList = new ArrayList<>();
        Behavior behavior = new Behavior("1", "acepta sugerencias", "accept suggestions");
        behaviorList.add(behavior);
        when(mockAptitudeService.findAptitudeBehaviors("1")).thenReturn(behaviorList);
        when(mockAptitudeService.findAptitudeBehaviorById("1", "1")).thenReturn(behavior);

        aptitudeController = new AptitudeController(mockAptitudeService);
    }

    @Test
    public void getAptitudesReturnHttpStatusOK() {
        ResponseEntity<List<AptitudeDto>> responseEntity = aptitudeController.getAptitudes();

        HttpStatus responseStatus = responseEntity.getStatusCode();
        assertEquals("OK", responseStatus.name());
    }

    @Test
    public void getAptitudesShouldReturnAptitudeArraySize1() {
        ResponseEntity<List<AptitudeDto>> responseEntity = aptitudeController.getAptitudes();

        List<AptitudeDto> allAptitudes = responseEntity.getBody();
        assertEquals(1, allAptitudes.size());

        AptitudeDto aptitudeReturned = allAptitudes.get(0);
        assertEquals("1", aptitudeReturned.getId());
        assertEquals("Apertura", aptitudeReturned.getEs());
        assertEquals("Openness", aptitudeReturned.getEn());
    }

    @Test
    public void getAptitudesWithAptitudeListNullReturnsNotFound() {
        when(mockAptitudeService.findAllAptitudes()).thenReturn(null);

        ResponseEntity<List<AptitudeDto>> responseEntity = aptitudeController.getAptitudes();
        HttpStatus responseStatus = responseEntity.getStatusCode();

        assertEquals("NOT_FOUND", responseStatus.name());
    }

    @Test
    public void getAptitudeByIdReturnHttpStatusOk() {
        ResponseEntity<AptitudeDto> responseEntity = aptitudeController.getAptitudeById("1");

        HttpStatus responseStatus = responseEntity.getStatusCode();
        assertEquals("OK", responseStatus.name());
    }

    @Test
    public void getAptitudeByIdShouldReturnOpennessAptitudeDto() {
        ResponseEntity<AptitudeDto> responseEntity = aptitudeController.getAptitudeById("1");

        AptitudeDto aptitudeReturned = responseEntity.getBody();
        assertEquals("1", aptitudeReturned.getId());
        assertEquals("Apertura", aptitudeReturned.getEs());
        assertEquals("Openness", aptitudeReturned.getEn());
    }

    @Test
    public void getAptitudeByIdWithAptitudeNullReturnsNotFound() {
        when(mockAptitudeService.findAptitudeById("-1")).thenReturn(null);

        ResponseEntity<AptitudeDto> responseEntity = aptitudeController.getAptitudeById("-1");

        HttpStatus responseStatus = responseEntity.getStatusCode();
        assertEquals("NOT_FOUND", responseStatus.name());
    }

    @Test
    public void getBehaviorsReturnHttpStatusOK() {
        ResponseEntity<List<Behavior>> responseEntity = aptitudeController.getBehaviors("1");

        HttpStatus responseStatus = responseEntity.getStatusCode();
        assertEquals("OK", responseStatus.name());
    }

    @Test
    public void getBehaviorsShouldReturnBehaviorArraySize1() {
        ResponseEntity<List<Behavior>> responseEntity = aptitudeController.getBehaviors("1");

        List<Behavior> allBehaviors = responseEntity.getBody();
        assertEquals(1, allBehaviors.size());

        Behavior behaviorReturned = allBehaviors.get(0);
        assertEquals("1", behaviorReturned.getId());
        assertEquals("acepta sugerencias", behaviorReturned.getEs());
        assertEquals("accept suggestions", behaviorReturned.getEn());
    }

    @Test
    public void getBehaviorsWithInvalidAptitudeReturnsNotFound() {
        when(mockAptitudeService.findAptitudeBehaviors("-1")).thenReturn(null);

        ResponseEntity<List<Behavior>> responseEntity = aptitudeController.getBehaviors("-1");
        HttpStatus responseStatus = responseEntity.getStatusCode();

        assertEquals("NOT_FOUND", responseStatus.name());
    }

    @Test
    public void getbehaviorByIdReturnHttpStatusOk() {
        ResponseEntity<Behavior> responseEntity = aptitudeController.getBehaviorById("1", "1");

        HttpStatus responseStatus = responseEntity.getStatusCode();
        assertEquals("OK", responseStatus.name());
    }

    @Test
    public void getBehaviorByIdShouldReturnOpennessBehavior() {
        ResponseEntity<Behavior> responseEntity = aptitudeController.getBehaviorById("1", "1");

        Behavior behaviorReturned = responseEntity.getBody();
        assertEquals("1", behaviorReturned.getId());
        assertEquals("acepta sugerencias", behaviorReturned.getEs());
        assertEquals("accept suggestions", behaviorReturned.getEn());
    }

    @Test
    public void getBehaviorByIdWithInvalidAptitudeReturnsNotFound() {
        when(mockAptitudeService.findAptitudeBehaviorById("-1", "-1")).thenReturn(null);

        ResponseEntity<Behavior> responseEntity = aptitudeController.getBehaviorById("-1", "-1");

        HttpStatus responseStatus = responseEntity.getStatusCode();
        assertEquals("NOT_FOUND", responseStatus.name());
    }

}
