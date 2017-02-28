package co.com.psl.evaluacionser.service.transformer;

import co.com.psl.evaluacionser.domain.Aptitude;
import co.com.psl.evaluacionser.service.dto.AptitudeDto;

public class AptitudeTransformer {

    private AptitudeTransformer() throws IllegalAccessException {
        throw new IllegalAccessException("Utility class, not meant to be instantiated");
    }

    public static AptitudeDto convertToDto(Aptitude aptitude) {
        return new AptitudeDto(aptitude.getId().toString(), aptitude.getEs(), aptitude.getEn());
    }

}
