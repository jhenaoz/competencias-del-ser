package co.com.psl.evaluacionser.service.transformer;

import co.com.psl.evaluacionser.domain.Aptitude;
import co.com.psl.evaluacionser.service.dto.AptitudeDto;
import org.springframework.stereotype.Service;

@Service
public class AptitudeTransformer {

    public AptitudeDto convertToDto(Aptitude aptitude) {
        return new AptitudeDto(aptitude.getId().toString(), aptitude.getEs(), aptitude.getEn());
    }

}
