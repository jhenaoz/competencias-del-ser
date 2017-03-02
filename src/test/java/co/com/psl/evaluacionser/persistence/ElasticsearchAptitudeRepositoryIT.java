package co.com.psl.evaluacionser.persistence;

import co.com.psl.evaluacionser.domain.Aptitude;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchAptitudeRepositoryIT {

    @Autowired
    private ElasticsearchAptitudeRepository elasticsearchAptitudeRepository;

    @Test
    public void aptitudeSaveDelete() {
        Aptitude aptitude = new Aptitude();
        aptitude.setId(1993L);
        aptitude.setEn("ENaptitudeEN");
        aptitude.setEs("ESaptitudeES");
        Aptitude aptitudeSaved = elasticsearchAptitudeRepository.save(aptitude);

        assertEquals(aptitude.getEs(), aptitudeSaved.getEs());
        assertEquals(aptitude.getEn(), aptitudeSaved.getEn());
        assertEquals(aptitude.getId(), aptitudeSaved.getId());

        Aptitude aptitudeFoundById = elasticsearchAptitudeRepository.findById(String.valueOf(aptitude.getId()));

        assertEquals(aptitude.getId(), aptitudeFoundById.getId());
        assertEquals(aptitude.getEn(), aptitudeFoundById.getEn());
        assertEquals(aptitude.getEs(), aptitudeFoundById.getEs());

        boolean wasDeleted;
        wasDeleted = elasticsearchAptitudeRepository.deleteAptitudeById(String.valueOf(aptitude.getId()));
        assertEquals(true, wasDeleted);
    }

}
