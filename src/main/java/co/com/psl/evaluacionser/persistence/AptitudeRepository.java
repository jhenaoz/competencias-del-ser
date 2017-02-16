package co.com.psl.evaluacionser.persistence;

import co.com.psl.evaluacionser.controller.AptitudeController;
import co.com.psl.evaluacionser.domain.Aptitude;
import co.com.psl.evaluacionser.domain.Behavior;
import co.com.psl.evaluacionser.domain.BehaviorDto;

import java.util.List;

public interface AptitudeRepository{
    Aptitude save(Aptitude aptitude);

    Aptitude deleteBehavior(String id, String behaviorId);

    Behavior findBehaviorById(String id, String behaviorId);

    Behavior addBehavior(BehaviorDto behaviorDto, String id);

    Aptitude findById(String id);

    List<Aptitude> findAll();
}
