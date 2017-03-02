package co.com.psl.evaluacionser.persistence;

import co.com.psl.evaluacionser.domain.Aptitude;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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

    @Test
    public void aptitudeWithId1993NotFound() {

        Aptitude aptitudeFoundById = elasticsearchAptitudeRepository.findById(String.valueOf(1993));

        assertNull(aptitudeFoundById);

    }

    @Test
    public void deleteAptitudeWithId1994isFalse() {
        boolean wasDeleted = true;
        wasDeleted = elasticsearchAptitudeRepository.deleteAptitudeById(String.valueOf(1994));
        assertEquals(false, wasDeleted);
    }

    @Test
    public void foundAllAptitudes() {
        Aptitude aptitude = new Aptitude();
        aptitude.setId(1993L);
        aptitude.setEn("ENaptitudeEN");
        aptitude.setEs("ESaptitudeES");

        Aptitude aptitude2 = new Aptitude();
        aptitude2.setId(2000L);
        aptitude2.setEn("ENEN");
        aptitude2.setEs("ESES");

        elasticsearchAptitudeRepository.save(aptitude);
        elasticsearchAptitudeRepository.save(aptitude2);
        List<Aptitude> aptitudeList = elasticsearchAptitudeRepository.findAll();

        boolean contains1 = false;
        for (Aptitude aptitudeInList : aptitudeList) {

            if (aptitudeInList.getId().equals(aptitude.getId())) contains1 = true;

        }

        boolean contains2 = false;
        for (Aptitude aptitudeInList : aptitudeList) {

            if (aptitudeInList.getId().equals(aptitude2.getId())) contains2 = true;

        }
        assertEquals(true, contains1);
        assertEquals(true,contains2);

        elasticsearchAptitudeRepository.deleteAptitudeById(String.valueOf(aptitude.getId()));
        elasticsearchAptitudeRepository.deleteAptitudeById(String.valueOf(aptitude2.getId()));
    }

}
