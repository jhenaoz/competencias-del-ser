package co.com.psl.evaluacionser.service.transformer;

import co.com.psl.evaluacionser.domain.Aptitude;
import co.com.psl.evaluacionser.service.dto.AptitudeDto;
import org.springframework.stereotype.Service;

/**
 * Service class used to transform Aptitude objects to AptitudeDto's ones
 */
@Service
public class AptitudeTransformer {

    /**
     * This method is used to convert the aptitude received to an AptitudeDto, which does not have Behaviors.
     * @param aptitude Is the aptitude to be transformed, its "Id", "es", and "en" will be the data of the AptitudeDto
     * @return AptitudeDto
     */
    public AptitudeDto convertToDto(Aptitude aptitude) {
        return new AptitudeDto(aptitude.getId().toString(), aptitude.getEs(), aptitude.getEn());
    }

}
