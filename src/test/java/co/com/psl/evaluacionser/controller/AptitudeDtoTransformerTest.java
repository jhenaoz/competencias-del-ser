package co.com.psl.evaluacionser.controller;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Test;

import co.com.psl.evaluacionser.domain.Aptitude;
import co.com.psl.evaluacionser.domain.AptitudeDto;
import co.com.psl.evaluacionser.domain.Behavior;

public class AptitudeDtoTransformerTest {

	@Test
	public void aptitudeTransformedToDto() {
		Behavior behavior1 = new Behavior(1L, "Reconoce sus errores", "Recognize his mistakes");
		Behavior behavior2 = new Behavior(2L, "Solicita sugerencias", "Asks for suggestions");
		List<Behavior> behaviors = new ArrayList<Behavior>();
		behaviors.add(behavior1);
		behaviors.add(behavior2);
		Aptitude aptitude = new Aptitude(1L, "Apertura", "Openness", behaviors);

		AptitudeDto aptitudeDto = AptitudeDtoTransformer.convertToDto(aptitude);

		assertEquals(aptitude.getId().toString(), aptitudeDto.getId());
		assertEquals(aptitude.getEs(), aptitudeDto.getEs());
		assertEquals(aptitude.getEn(), aptitudeDto.getEn());
	}

}
