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
    this is an integration test, this access the DB saves an localAptitude and deletes it
    for this purpose an localAptitude was created and its values initialized and assigned
     */
    private Aptitude localAptitude = new Aptitude();
    private Behavior localBehavior1 = new Behavior();
    private Behavior localBehavior2 = new Behavior();
    private Aptitude aptitudeReturnedFromSave = new Aptitude();
    private BehaviorDto behaviorDto = new BehaviorDto();
    private Behavior behaviorFromDto = new Behavior();
    private Aptitude aptitudeWithDeletedBehavior = new Aptitude();
    private Behavior behaviorToOverwrite = new Behavior();

    @Autowired
    private ElasticsearchAptitudeRepository elasticsearchAptitudeRepository;

    /**
     * This method is the one that leaves the DB ready for testing, to do so i create (locally, that's why the name)
     * and save an aptitude with its respective behaviors
     * @throws Exception as this is a method that accesses the DB it can throw an exception
     */
    @Before
    public void setUp() throws Exception {

        behaviorDto.setEn("english text for dto");
        behaviorDto.setEs("texto en ingles para el dto");

        localBehavior1.setId(1);
        localBehavior1.setEn("They accept suggestions regardless of who gives them");
        localBehavior1.setEs("Acepta sugerencias sin distincion de quien las de");

        localBehavior2.setId(2);
        localBehavior2.setEn("They recognize their mistakes");
        localBehavior2.setEs("Reconoce sus errores");

        behaviorToOverwrite.setId(2);
        behaviorToOverwrite.setEn("they dont recognize their mistakes");
        behaviorToOverwrite.setEs("no reconoce sus errores");

        List<Behavior> behaviors = new ArrayList<>();
        behaviors.add(localBehavior1);
        behaviors.add(localBehavior2);

        localAptitude.setBehaviors(behaviors);
        localAptitude.setEs("Apertura");
        localAptitude.setEn("Openness");
        localAptitude.setId(1993L);
        aptitudeReturnedFromSave =
                elasticsearchAptitudeRepository.save(localAptitude);
        behaviorFromDto =
                elasticsearchAptitudeRepository.addBehavior(behaviorDto, String.valueOf(localAptitude.getId()));
        aptitudeWithDeletedBehavior =
                elasticsearchAptitudeRepository.deleteBehavior(String.valueOf(this.localAptitude.getId()),
                                                                              behaviorFromDto.getId());

    }

    @After
    public void tearDown() throws Exception {
        elasticsearchAptitudeRepository.deleteAptitudeById(String.valueOf(localAptitude.getId()));
    }

    @Test
    public void anAptitudeShouldBeSaved() throws Exception {

        assertEquals(localAptitude.getId(), aptitudeReturnedFromSave.getId());
        assertEquals(localAptitude.getEn(), aptitudeReturnedFromSave.getEn());
        assertEquals(localAptitude.getEs(), aptitudeReturnedFromSave.getEs());

    }

    @Test
    public void findAllAptitudes() throws Exception {
        List<Aptitude> all = elasticsearchAptitudeRepository.findAll();

        boolean wasFound = false;
        for (Aptitude aptitudeInList : all) {
            if (aptitudeInList.getId().equals(localAptitude.getId())) {
                wasFound = true;
                break;
            }

        }
        assertEquals(true, wasFound);
    }

    @Test
    public void findAnAptitudeById() throws Exception {

        Aptitude byId = elasticsearchAptitudeRepository.findById(String.valueOf(localAptitude.getId()));
        assertEquals(localAptitude.getId(), byId.getId());
        assertEquals(localAptitude.getEn(), byId.getEn());
        assertEquals(localAptitude.getEs(), byId.getEs());

    }

    @Test
    public void findAllBehaviors() throws Exception {

        List<Behavior> allBehaviors =
                elasticsearchAptitudeRepository.findAllBehaviors(String.valueOf(localAptitude.getId()));
        assertEquals(localAptitude.getBehaviors().get(0).getEn(), allBehaviors.get(0).getEn());
        assertEquals(localAptitude.getBehaviors().get(1).getEn(), allBehaviors.get(1).getEn());

    }

    @Test
    public void findBehaviorById() throws Exception {

        Behavior behaviorById =
                elasticsearchAptitudeRepository.findBehaviorById(String.valueOf(localAptitude.getId()),
                                                                                localBehavior1.getId());

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

        assertEquals(localAptitude.getEn(), aptitudeWithDeletedBehavior.getEn());
        assertEquals(localAptitude.getEs(), aptitudeWithDeletedBehavior.getEs());
        assertEquals(localAptitude.getId(), aptitudeWithDeletedBehavior.getId());
        assertEquals(localAptitude.getBehaviors().size(), aptitudeWithDeletedBehavior.getBehaviors().size());

    }

    @Test
    public void updateBehaviorById() throws Exception {

        Behavior behavior =
                elasticsearchAptitudeRepository.updateBehaviorById(String.valueOf(localAptitude.getId()),
                                                                                  behaviorToOverwrite);
        assertEquals(behaviorToOverwrite.getId(), behavior.getId());
        assertEquals(behaviorToOverwrite.getEs(), behavior.getEs());
        assertEquals(behaviorToOverwrite.getEn(), behavior.getEn());

    }

    @Test
    public void deleteAptitudeById() throws Exception {

        boolean aptitudeWasDeleted = elasticsearchAptitudeRepository.deleteAptitudeById("1993");
        assertTrue(aptitudeWasDeleted);
    }

}
