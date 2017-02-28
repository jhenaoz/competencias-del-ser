package co.com.psl.evaluacionser.service.transformer;

import co.com.psl.evaluacionser.domain.*;
import co.com.psl.evaluacionser.persistence.AptitudeRepository;
import co.com.psl.evaluacionser.service.dto.AptitudeDto;
import co.com.psl.evaluacionser.service.dto.AptitudeSurveyDto;
import co.com.psl.evaluacionser.service.dto.BehaviorSurveyDto;
import co.com.psl.evaluacionser.service.dto.SurveyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is in charge of transform the json from the front end to the form
 * required in the db
 */

@Service
public class SurveyTransformer {

    @Autowired
    private AptitudeRepository aptitudeRepository;

    /**
     * This method calls the other methods required for the transformation
     *
     * @param surveyDto the Json of the Survey to save in the DB
     * @return Survey
     */
    public Survey transformer(SurveyDto surveyDto) {
        Survey survey = new Survey();
        survey.setEvaluator(surveyDto.getEvaluator());
        survey.setEvaluated(surveyDto.getEvaluated());
        survey.setRole(surveyDto.getRole());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        survey.setTimestamp(dateFormat.format(date));
        survey.setAptitudes(surveyDto.getAptitudes().stream().map(this::aptitudeSurveyTransformer)
                .collect(Collectors.toList()));

        return survey;
    }

    public AptitudeSurvey aptitudeSurveyTransformer(AptitudeSurveyDto aptitudeSurveyDto) {
        AptitudeSurvey aptitudeSurvey = new AptitudeSurvey();

        Aptitude aptitude = aptitudeRepository.findById(aptitudeSurveyDto.getAptitudeId());
        AptitudeDto aptitudeDto = AptitudeTransformer.convertToDto(aptitude);
        aptitudeSurvey.setAptitude(aptitudeDto);
        aptitudeSurvey.setObservation(aptitudeSurveyDto.getObservation());
        aptitudeSurvey.setBehaviors(
                this.behaviorsSurveyTransformer(aptitude.getBehaviors(), aptitudeSurveyDto.getBehaviors()));
        return aptitudeSurvey;
    }

    public List<BehaviorSurvey> behaviorsSurveyTransformer(List<Behavior> behaviors,
                                                           List<BehaviorSurveyDto> behaviorsSurveyDto) {
        List<BehaviorSurvey> behaviorsSurvey = new ArrayList<>();
        for (int i = 0; i < behaviorsSurveyDto.size(); i++) {
            BehaviorSurvey behaviorSurvey = new BehaviorSurvey();
            for (int j = 0; j < behaviors.size(); j++) {
                if (behaviors.get(j).getId().equals(behaviorsSurveyDto.get(i).getBehaviorId())) {
                    behaviorSurvey.setBehavior(behaviors.get(j));
                    behaviorSurvey.setScore(behaviorsSurveyDto.get(i).getScore());
                    behaviorsSurvey.add(behaviorSurvey);
                    break;
                }
            }
        }
        return behaviorsSurvey;
    }
}