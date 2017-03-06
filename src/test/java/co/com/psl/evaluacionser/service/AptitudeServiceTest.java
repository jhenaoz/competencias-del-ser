package co.com.psl.evaluacionser.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import co.com.psl.evaluacionser.domain.Aptitude;
import co.com.psl.evaluacionser.domain.Behavior;
import co.com.psl.evaluacionser.persistence.ElasticsearchAptitudeRepository;
import co.com.psl.evaluacionser.service.dto.AptitudeDto;
import co.com.psl.evaluacionser.service.transformer.AptitudeTransformer;

@RunWith(MockitoJUnitRunner.class)
public class AptitudeServiceTest {

    @Mock
    private ElasticsearchAptitudeRepository mockAptitudeRepository;
    private AptitudeService aptitudeService;

    @Before
    public void setup() {
        List<Behavior> behaviorList = new ArrayList<>();
        Behavior behavior = new Behavior("1", "acepta sugerencias", "accept suggestions");
        behaviorList.add(behavior);

        List<Aptitude> aptitudeList = new ArrayList<>();
        Aptitude aptitude = new Aptitude(1L, "Apertura", "Openness", behaviorList);
        aptitudeList.add(aptitude);

        when(mockAptitudeRepository.findAll()).thenReturn(aptitudeList);
        when(mockAptitudeRepository.findById("1")).thenReturn(aptitude);
        when(mockAptitudeRepository.findAllBehaviors("1")).thenReturn(behaviorList);
        when(mockAptitudeRepository.findBehaviorById("1", "1")).thenReturn(behavior);

        aptitudeService = new AptitudeService(mockAptitudeRepository, new AptitudeTransformer());
    }

    @Test
    public void findAllAptitudesShouldReturnArraySize1() {
        List<AptitudeDto> allAptitudes = aptitudeService.findAllAptitudes();

        assertEquals(1, allAptitudes.size());

        AptitudeDto aptitudeReturned = allAptitudes.get(0);
        assertEquals("1", aptitudeReturned.getId());
        assertEquals("Apertura", aptitudeReturned.getEs());
        assertEquals("Openness", aptitudeReturned.getEn());
    }

    @Test
    public void findAllAptitudesWithNullArrayReturnsNull() {
        when(mockAptitudeRepository.findAll()).thenReturn(null);

        assertNull(aptitudeService.findAllAptitudes());
    }

    @Test
    public void findAptitudeByIdReturnsOpennessAptitudeDto() {
        AptitudeDto aptitudeReturned = aptitudeService.findAptitudeById("1");

        assertEquals("1", aptitudeReturned.getId());
        assertEquals("Apertura", aptitudeReturned.getEs());
        assertEquals("Openness", aptitudeReturned.getEn());
    }

    @Test
    public void findAptitudeByIdWithNullAptitudeReturnsNull() {
        when(mockAptitudeRepository.findById("-1")).thenReturn(null);

        assertNull(aptitudeService.findAptitudeById("-1"));
    }

    @Test
    public void findAptitudeBehaviorsShouldReturnArraySize1() {
        List<Behavior> allBehaviors = aptitudeService.findAptitudeBehaviors("1");
        assertEquals(1, allBehaviors.size());

        Behavior behaviorReturned = allBehaviors.get(0);
        assertEquals("1", behaviorReturned.getId());
        assertEquals("acepta sugerencias", behaviorReturned.getEs());
        assertEquals("accept suggestions", behaviorReturned.getEn());
    }

    @Test
    public void findBehaviorsWithNullArrayReturnsNull() {
        when(mockAptitudeRepository.findAllBehaviors("-1")).thenReturn(null);

        assertNull(aptitudeService.findAptitudeBehaviors("-1"));
    }

    @Test
    public void findAptitudeBehaviorByIdReturnsOpennessBehavior() {
        Behavior behaviorReturned = aptitudeService.findAptitudeBehaviorById("1", "1");

        assertEquals("1", behaviorReturned.getId());
        assertEquals("acepta sugerencias", behaviorReturned.getEs());
        assertEquals("accept suggestions", behaviorReturned.getEn());
    }

    @Test
    public void findBehaviorByIdWithBehaviorNullReturnsNull() {
        when(mockAptitudeRepository.findBehaviorById("-1", "-1")).thenReturn(null);

        assertNull(aptitudeService.findAptitudeBehaviorById("-1", "-1"));
    }

}
