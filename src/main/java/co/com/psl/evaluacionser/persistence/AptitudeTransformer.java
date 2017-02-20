package co.com.psl.evaluacionser.persistence;

import co.com.psl.evaluacionser.domain.Aptitude;
import co.com.psl.evaluacionser.domain.AptitudeDto;

public class AptitudeTransformer {

    public static AptitudeDto convertToDto(Aptitude aptitude) {
        return new AptitudeDto(aptitude.getId().toString(), aptitude.getEs(), aptitude.getEn());
    }

}
