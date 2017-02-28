package co.com.psl.evaluacionser.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import co.com.psl.evaluacionser.domain.Aptitude;
import co.com.psl.evaluacionser.domain.Behavior;
import co.com.psl.evaluacionser.persistence.AptitudeRepository;
import co.com.psl.evaluacionser.service.dto.AptitudeDto;
import co.com.psl.evaluacionser.service.dto.BehaviorDto;
import co.com.psl.evaluacionser.service.transformer.AptitudeTransformer;

@Service
public class AptitudeService {

    @Autowired
    private AptitudeRepository aptitudeRepository;

    @Autowired
    private AptitudeTransformer aptitudeTransformer;

    public List<AptitudeDto> findAllAptitudes() {
        List<Aptitude> aptitudes = aptitudeRepository.findAll();

        if (aptitudes == null)
            return null;

        List<AptitudeDto> aptitudesDto = aptitudes.stream().map(aptitudeTransformer::convertToDto)
                .collect(Collectors.toList());

        return aptitudesDto;
    }

    public AptitudeDto findAptitudeById(String id) {
        Aptitude aptitudeFound = aptitudeRepository.findById(id);

        if (aptitudeFound == null)
            return null;

        return aptitudeTransformer.convertToDto(aptitudeFound);
    }

    public List<Behavior> findAptitudeBehaviors(String aptitudeId) {
        List<Behavior> behaviorsFound = aptitudeRepository.findAllBehaviors(aptitudeId);

        if (behaviorsFound == null)
            return null;

        return behaviorsFound;
    }

    public Behavior findAptitudeBehaviorById(String id, String behaviorId) {
        Behavior behaviorFound = aptitudeRepository.findBehaviorById(id, behaviorId);

        if (behaviorFound == null)
            return null;

        return behaviorFound;
    }

    public Behavior createAptitudeBehavior(String id, BehaviorDto behaviorDto) {
        if (aptitudeRepository.findById(id) == null)
            return null;

        return aptitudeRepository.addBehavior(behaviorDto, id);
    }

    public Behavior updateAptitudeBehavior(String id, String behaviorId, BehaviorDto behaviorDto) {
        if (aptitudeRepository.findById(id) == null)
            return null;

        if (aptitudeRepository.findBehaviorById(id, behaviorId) == null)
            return null;

        Behavior behavior = new Behavior(behaviorId, behaviorDto.getEn(), behaviorDto.getEs());
        return aptitudeRepository.updateBehaviorById(id, behavior);
    }

    public Aptitude deleteAptitudeBehavior(String id, String behaviorId) {
        if (aptitudeRepository.findById(id) == null)
            return null;

        if (aptitudeRepository.findBehaviorById(id, behaviorId) == null)
            return null;

        return aptitudeRepository.deleteBehavior(id, behaviorId);
    }

}
