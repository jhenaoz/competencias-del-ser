package co.com.psl.evaluacionser.controller;

import co.com.psl.evaluacionser.domain.Aptitude;
import co.com.psl.evaluacionser.domain.AptitudeDto;
import co.com.psl.evaluacionser.domain.Behavior;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AptitudeDtoTransformerTest {

    @Test
    public void aptitudeTransformedToDto() {
        Behavior behaviors1 = new Behavior("1", "Reconoce sus errores", "Recognize his mistakes");
        Behavior behaviors2 = new Behavior("2", "Solicita sugerencias", "Asks for suggestions");
        List<Behavior> behaviorss = new ArrayList<Behavior>();
        behaviorss.add(behaviors1);
        behaviorss.add(behaviors2);
        Aptitude aptitude = new Aptitude(1L, "Apertura", "Openness", behaviorss);

        AptitudeDto aptitudeDto = AptitudeDtoTransformer.convertToDto(aptitude);

        assertEquals(aptitude.getId().toString(), aptitudeDto.getId());
        assertEquals(aptitude.getEs(), aptitudeDto.getEs());
        assertEquals(aptitude.getEn(), aptitudeDto.getEn());
    }

}
