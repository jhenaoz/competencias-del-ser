package co.com.psl.evaluacionser.service;

import co.com.psl.evaluacionser.domain.Aptitude;
import co.com.psl.evaluacionser.domain.Behavior;
import co.com.psl.evaluacionser.persistence.ElasticsearchAptitudeRepository;
import co.com.psl.evaluacionser.service.dto.AptitudeDto;
import co.com.psl.evaluacionser.service.dto.BehaviorDto;
import co.com.psl.evaluacionser.service.transformer.AptitudeTransformer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AptitudeServiceTest {

    @Mock
    private ElasticsearchAptitudeRepository mockAptitudeRepository;
    private AptitudeService aptitudeService;

    private BehaviorDto behaviorDto;

    @Before
    public void setup() {
        List<Behavior> behaviorList = new ArrayList<>();
        Behavior behavior = new Behavior(1, "acepta sugerencias", "accept suggestions");
        behaviorList.add(behavior);

        List<Aptitude> aptitudeList = new ArrayList<>();
        Aptitude aptitude = new Aptitude(1L, "Apertura", "Openness", behaviorList);
        aptitudeList.add(aptitude);

        when(mockAptitudeRepository.findAll()).thenReturn(aptitudeList);
        when(mockAptitudeRepository.findById("1")).thenReturn(aptitude);
        when(mockAptitudeRepository.findAllBehaviors("1")).thenReturn(behaviorList);
        when(mockAptitudeRepository.findBehaviorById("1", 1)).thenReturn(behavior);

        behaviorDto = new BehaviorDto(behavior.getEs(), behavior.getEn());
        when(mockAptitudeRepository.addBehavior(behaviorDto, "1")).thenReturn(behavior);
        when(mockAptitudeRepository.updateBehaviorById(eq("1"), any(Behavior.class))).thenReturn(behavior);
        when(mockAptitudeRepository.deleteBehavior("1", 1)).thenReturn(aptitude);

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
        assertEquals(1, behaviorReturned.getId());
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
        Behavior behaviorReturned = aptitudeService.findAptitudeBehaviorById("1", 1);

        assertEquals(1, behaviorReturned.getId());
        assertEquals("acepta sugerencias", behaviorReturned.getEs());
        assertEquals("accept suggestions", behaviorReturned.getEn());
    }

    @Test
    public void findBehaviorByIdWithBehaviorNullReturnsNull() {
        when(mockAptitudeRepository.findBehaviorById("-1", -1)).thenReturn(null);

        assertNull(aptitudeService.findAptitudeBehaviorById("-1", -1));
    }

    @Test
    public void createAptitudeBehaviorReturnBehaviorCreated() {
        Behavior behaviorCreated = aptitudeService.createAptitudeBehavior("1", behaviorDto);

        assertEquals(1, behaviorCreated.getId());
        assertEquals("acepta sugerencias", behaviorCreated.getEs());
        assertEquals("accept suggestions", behaviorCreated.getEn());
    }

    @Test
    public void createAptitudeBehaviorWithInvalidIdReturnNull() {
        when(mockAptitudeRepository.findById("-1")).thenReturn(null);

        assertNull(aptitudeService.createAptitudeBehavior("-1", behaviorDto));
    }

    @Test
    public void updateAptitudeBehaviorReturnBehaviorUpdated() {
        Behavior behaviorUpdated = aptitudeService.updateAptitudeBehavior("1", 1, behaviorDto);

        assertEquals(1, behaviorUpdated.getId());
        assertEquals("acepta sugerencias", behaviorUpdated.getEs());
        assertEquals("accept suggestions", behaviorUpdated.getEn());
    }

    @Test
    public void updateAptitudeBehaviorWithBadAptitudeIdReturnNull() {
        when(mockAptitudeRepository.findById("-1")).thenReturn(null);

        assertNull(aptitudeService.updateAptitudeBehavior("-1", 1, behaviorDto));
    }

    @Test
    public void updateAptitudeBehaviorWithBadBehaviorIdReturnNull() {
        when(mockAptitudeRepository.findBehaviorById("1", -1)).thenReturn(null);

        assertNull(aptitudeService.updateAptitudeBehavior("1", -1, behaviorDto));
    }

    @Test
    public void deleteAptitudeBehaviorReturnAptitudeUpdated() {
        Aptitude aptitudeReturned = aptitudeService.deleteAptitudeBehavior("1", 1);

        assertEquals(1L, (long) aptitudeReturned.getId());
        assertEquals("Apertura", aptitudeReturned.getEs());
        assertEquals("Openness", aptitudeReturned.getEn());
    }

    @Test
    public void deleteAptitudeBehaviorWithBadAptitudeIdReturnNull() {
        when(mockAptitudeRepository.findById("-1")).thenReturn(null);

        assertNull(aptitudeService.deleteAptitudeBehavior("-1", 1));
    }

    @Test
    public void deleteAptitudeBehaviorWithBadBehaviorIdReturnNull() {
        when(mockAptitudeRepository.findBehaviorById("1", -1)).thenReturn(null);

        assertNull(aptitudeService.deleteAptitudeBehavior("1", -1));
    }

}
