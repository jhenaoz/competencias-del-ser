package co.com.psl.evaluacionser.controller;

import co.com.psl.evaluacionser.domain.Aptitude;
import co.com.psl.evaluacionser.domain.AptitudeDto;

public class AptitudeDtoTransformer {

	public static AptitudeDto convertToDto(Aptitude aptitude) {
		return new AptitudeDto(aptitude.getId().toString(), aptitude.getEs(), aptitude.getEn());
	}

}
