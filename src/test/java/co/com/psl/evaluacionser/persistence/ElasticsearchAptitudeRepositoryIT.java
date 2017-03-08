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

    /*
    this is an integration test, this access the DB saves an aptitude and deletes it
    for this purpose an aptitude was created and its values initialized and assigned
     */
    private Aptitude aptitude = new Aptitude();
    private Behavior localBehavior1 = new Behavior();
    private Behavior localBehavior2 = new Behavior();
    private Aptitude aptitudeReturnedFromSave = new Aptitude();
    private BehaviorDto behaviorDto = new BehaviorDto();
    private Behavior behaviorFromDto = new Behavior();
    private Aptitude aptitudeWithDeletedBehavior = new Aptitude();
    private Behavior behaviorToOverwrite = new Behavior();

    @Autowired
    ElasticsearchAptitudeRepository elasticsearchAptitudeRepository;

    @Before
    public void setUp() throws Exception {

        behaviorDto.setEn("english text for dto");
        behaviorDto.setEs("texto en ingles para el dto");


        localBehavior1.setId("1");
        localBehavior1.setEn("They accept suggestions regardless of who gives them");
        localBehavior1.setEs("Acepta sugerencias sin distincion de quien las de");

        localBehavior2.setId("2");
        localBehavior2.setEn("They recognize their mistakes");
        localBehavior2.setEs("Reconoce sus errores");

        behaviorToOverwrite.setId("2");
        behaviorToOverwrite.setEn("they dont recognize their mistakes");
        behaviorToOverwrite.setEs("no reconoce sus errores");

        List<Behavior> behaviors = new ArrayList<>();
        behaviors.add(localBehavior1);
        behaviors.add(localBehavior2);

        aptitude.setBehaviors(behaviors);
        aptitude.setEs("Apertura");
        aptitude.setEn("Openness");
        aptitude.setId(1993L);
        aptitudeReturnedFromSave = elasticsearchAptitudeRepository.save(aptitude);
        behaviorFromDto = elasticsearchAptitudeRepository.addBehavior(behaviorDto, String.valueOf(aptitude.getId()));
        aptitudeWithDeletedBehavior = elasticsearchAptitudeRepository.deleteBehavior(String.valueOf(this.aptitude.getId()), behaviorFromDto.getId());

    }

    @After
    public void tearDown() throws Exception {
        elasticsearchAptitudeRepository.deleteAptitudeById(String.valueOf(aptitude.getId()));
    }

    @Test
    public void anAptitudeShouldBeSaved() throws Exception {

        assertEquals(aptitude.getId(), aptitudeReturnedFromSave.getId());
        assertEquals(aptitude.getEn(), aptitudeReturnedFromSave.getEn());
        assertEquals(aptitude.getEs(), aptitudeReturnedFromSave.getEs());

    }

    @Test
    public void findAllAptitudes() throws Exception {
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
    public void findAnAptitudeById() throws Exception {

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

        Behavior behaviorById = elasticsearchAptitudeRepository.findBehaviorById(String.valueOf(aptitude.getId()), localBehavior1.getId());

        assertEquals(localBehavior1.getEs(), behaviorById.getEs());
        assertEquals(localBehavior1.getEn(), behaviorById.getEn());
        assertEquals(localBehavior1.getId(), behaviorById.getId());

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

        Behavior behavior = elasticsearchAptitudeRepository.updateBehaviorById(String.valueOf(aptitude.getId()), behaviorToOverwrite);
        assertEquals(behaviorToOverwrite.getId(),behavior.getId());
        assertEquals(behaviorToOverwrite.getEs(),behavior.getEs());
        assertEquals(behaviorToOverwrite.getEn(),behavior.getEn());

    }

    @Test
    public void deleteAptitudeById() throws Exception {

        boolean TheAptitudeWithId1993WasDeleted = elasticsearchAptitudeRepository.deleteAptitudeById("1993");
        assertTrue(TheAptitudeWithId1993WasDeleted);
    }

}