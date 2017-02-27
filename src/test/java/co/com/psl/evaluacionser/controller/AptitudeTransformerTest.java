package co.com.psl.evaluacionser.controller;

import co.com.psl.evaluacionser.domain.Aptitude;
import co.com.psl.evaluacionser.service.dto.AptitudeDto;
import co.com.psl.evaluacionser.domain.Behavior;
import co.com.psl.evaluacionser.service.transformer.AptitudeTransformer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AptitudeTransformerTest {

    @Test
    public void aptitudeTransformedToDto() {
        Behavior behavior1 = new Behavior("1", "Reconoce sus errores", "Recognize his mistakes");
        Behavior behavior2 = new Behavior("2", "Solicita sugerencias", "Asks for suggestions");
        List<Behavior> behaviors = new ArrayList<Behavior>();
        behaviors.add(behavior1);
        behaviors.add(behavior2);
        Aptitude aptitude = new Aptitude(1L, "Apertura", "Openness", behaviors);

        AptitudeDto aptitudeDto = AptitudeTransformer.convertToDto(aptitude);

        assertEquals(aptitude.getId().toString(), aptitudeDto.getId());
        assertEquals(aptitude.getEs(), aptitudeDto.getEs());
        assertEquals(aptitude.getEn(), aptitudeDto.getEn());
    }

}
