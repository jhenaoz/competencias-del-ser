package co.com.psl.evaluacionser.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import co.com.psl.evaluacionser.domain.Behavior;
import co.com.psl.evaluacionser.domain.BehaviorSurvey;
import co.com.psl.evaluacionser.domain.BehaviorSurveyDto;
import co.com.psl.evaluacionser.domain.Survey;
import co.com.psl.evaluacionser.domain.SurveyDto;

public class SurveyTransformerTest {

    private SurveyTransformer surveyTransformer = new SurveyTransformer();
    
   @Test
   public void BehaviorsSurveyTransformerTest(){
       List<Behavior> behaviors = new ArrayList<>();
       behaviors.add(new Behavior("1", "Accepts review", "Acepta retroalimentacion"));
       behaviors.add(new Behavior("2", "Play in team", "Juega en equipo"));
       behaviors.add(new Behavior("3", "Good person", "Buena persona"));
       List<BehaviorSurveyDto> behaviorsSurveyDto = new ArrayList<>();
       behaviorsSurveyDto.add(new BehaviorSurveyDto("1", 5));
       behaviorsSurveyDto.add(new BehaviorSurveyDto("2", 4));
       behaviorsSurveyDto.add(new BehaviorSurveyDto("3", 1));
       List<BehaviorSurvey> behaviorSurvey = surveyTransformer.BehaviorsSurveyTransformer(behaviors, behaviorsSurveyDto);
       assertEquals("1", behaviorSurvey.get(0).getBehavior().getId());
   }
   
   @Test
   public void AptitudeSurveyTransformerTest(){
       List<Behavior> behaviors = new ArrayList<>();
       behaviors.add(new Behavior("1", "Accepts review", "Acepta retroalimentacion"));
       behaviors.add(new Behavior("2", "Play in team", "Juega en equipo"));
       behaviors.add(new Behavior("3", "Good person", "Buena persona"));
       List<BehaviorSurveyDto> behaviorsSurveyDto = new ArrayList<>();
       behaviorsSurveyDto.add(new BehaviorSurveyDto("1", 5));
       behaviorsSurveyDto.add(new BehaviorSurveyDto("2", 4));
       behaviorsSurveyDto.add(new BehaviorSurveyDto("3", 1));
       List<BehaviorSurvey> behaviorSurvey = surveyTransformer.BehaviorsSurveyTransformer(behaviors, behaviorsSurveyDto);
       assertEquals("1", behaviorSurvey.get(0).getBehavior().getId());
   }
   
   /* @Test
    public void AptitudeSurveyTransformer() {
        SurveyDto surveyDto = new SurveyDto("Juan Perez", "Juana Angela", "Team", null);
        Survey survey = new SurveyTransformer().Transformer(surveyDto);
        assertEquals("Juan Perez", survey.getEvaluator());
    }*/

}
