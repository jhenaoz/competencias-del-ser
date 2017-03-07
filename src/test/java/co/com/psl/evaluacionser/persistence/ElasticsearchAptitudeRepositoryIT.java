package co.com.psl.evaluacionser.persistence;

import co.com.psl.evaluacionser.domain.Aptitude;
import co.com.psl.evaluacionser.domain.Behavior;
import co.com.psl.evaluacionser.service.dto.BehaviorDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ElasticsearchAptitudeRepositoryIT {

    private Aptitude aptitude = new Aptitude();
    private Behavior behavior = new Behavior();
    private Behavior behavior1 = new Behavior();
    private Aptitude save = new Aptitude();
    private BehaviorDto behaviorDto = new BehaviorDto();
    private Behavior behaviorFromDto = new Behavior();
    private Aptitude aptitudeWithDeletedBehavior = new Aptitude();
    private Behavior behaviorO = new Behavior();

    @Autowired
    ElasticsearchAptitudeRepository elasticsearchAptitudeRepository;

    @Before
    public void setUp() throws Exception {

        behaviorDto.setEn("nuevo texto en ingles");
        behaviorDto.setEs("nuevo texto en español");


        behavior.setId("1");
        behavior.setEn("They accept suggestions regardless of who gives them");
        behavior.setEs("Acepta sugerencias sin distincion de quien las de");

        behavior1.setId("2");
        behavior1.setEn("They recognize their mistakes");
        behavior1.setEs("Reconoce sus errores");

        behaviorO.setId("2");
        behaviorO.setEn("Tor mistakes");
        behaviorO.setEs("Ros errores");

        List<Behavior> behaviors = new ArrayList<>();
        behaviors.add(behavior);
        behaviors.add(behavior1);

        aptitude.setBehaviors(behaviors);
        aptitude.setEs("Apertura");
        aptitude.setEn("Openness");
        aptitude.setId(1993L);
        save = elasticsearchAptitudeRepository.save(aptitude);
        behaviorFromDto = elasticsearchAptitudeRepository.addBehavior(behaviorDto, String.valueOf(aptitude.getId()));
        aptitudeWithDeletedBehavior = elasticsearchAptitudeRepository.deleteBehavior(String.valueOf(this.aptitude.getId()), behaviorFromDto.getId());

    }

    @After
    public void tearDown() throws Exception {
        elasticsearchAptitudeRepository.deleteAptitudeById(String.valueOf(aptitude.getId()));
    }

    @Test
    public void save() throws Exception {

        assertEquals(aptitude.getId(), save.getId());
        assertEquals(aptitude.getEn(), save.getEn());
        assertEquals(aptitude.getEs(), save.getEs());

    }

    @Test
    public void findAll() throws Exception {
        List<Aptitude> all = elasticsearchAptitudeRepository.findAll();

        boolean wasFound = false;
        for (Aptitude aptitudeInList : all) {
            if (aptitudeInList.getId().equals(aptitude.getId())) {
                wasFound = true;
                break;
            }

        }
        assertEquals(true, wasFound);
    }

    @Test
    public void findById() throws Exception {

        Aptitude byId = elasticsearchAptitudeRepository.findById(String.valueOf(aptitude.getId()));
        assertEquals(aptitude.getId(), byId.getId());
        assertEquals(aptitude.getEn(), byId.getEn());
        assertEquals(aptitude.getEs(), byId.getEs());

    }

    @Test
    public void findAllBehaviors() throws Exception {

        List<Behavior> allBehaviors = elasticsearchAptitudeRepository.findAllBehaviors(String.valueOf(aptitude.getId()));
        assertEquals(aptitude.getBehaviors().get(0).getEn(), allBehaviors.get(0).getEn());
        assertEquals(aptitude.getBehaviors().get(1).getEn(), allBehaviors.get(1).getEn());

    }

    @Test
    public void findBehaviorById() throws Exception {

        Behavior behaviorById = elasticsearchAptitudeRepository.findBehaviorById(String.valueOf(aptitude.getId()), behavior.getId());

        assertEquals(behavior.getEs(), behaviorById.getEs());
        assertEquals(behavior.getEn(), behaviorById.getEn());
        assertEquals(behavior.getId(), behaviorById.getId());

    }

    @Test
    public void addBehavior() throws Exception {

        assertEquals(behaviorDto.getEn(), behaviorFromDto.getEn());
        assertEquals(behaviorDto.getEs(), behaviorFromDto.getEs());

    }

    @Test
    public void deleteBehavior() throws Exception {

        assertEquals(aptitude.getEn(),aptitudeWithDeletedBehavior.getEn());
        assertEquals(aptitude.getEs(),aptitudeWithDeletedBehavior.getEs());
        assertEquals(aptitude.getId(),aptitudeWithDeletedBehavior.getId());
        assertEquals(aptitude.getBehaviors().size(),aptitudeWithDeletedBehavior.getBehaviors().size());

    }

    @Test
    public void updateBehaviorById() throws Exception {

        Behavior behavior = elasticsearchAptitudeRepository.updateBehaviorById(String.valueOf(aptitude.getId()), behaviorO);
        assertEquals(behaviorO.getId(),behavior.getId());
        assertEquals(behaviorO.getEs(),behavior.getEs());
        assertEquals(behaviorO.getEn(),behavior.getEn());

    }

    @Test
    public void deleteAptitudeById() throws Exception {

        boolean b = elasticsearchAptitudeRepository.deleteAptitudeById("1993");
        assertTrue(b);
    }

}