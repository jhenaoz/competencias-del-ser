package co.com.psl.evaluacionser.service.transformer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.psl.evaluacionser.domain.Aptitude;
import co.com.psl.evaluacionser.service.dto.AptitudeDto;
import co.com.psl.evaluacionser.domain.AptitudeSurvey;
import co.com.psl.evaluacionser.service.dto.AptitudeSurveyDto;
import co.com.psl.evaluacionser.domain.Behavior;
import co.com.psl.evaluacionser.domain.BehaviorSurvey;
import co.com.psl.evaluacionser.service.dto.BehaviorSurveyDto;
import co.com.psl.evaluacionser.domain.Survey;
import co.com.psl.evaluacionser.service.dto.SurveyDto;
import co.com.psl.evaluacionser.persistence.AptitudeRepository;
import org.apache.log4j.Logger;

/**
 * This class is in charge of transform the json from the front end to the form
 * required in the db
 */

@Service
public class SurveyTransformer {

    @Autowired
    private AptitudeRepository aptitudeRepository;

    static Logger logger = Logger.getLogger(SurveyTransformer.class);

    /**
     * This method calls the other methods required for the transformation
     *
     * @param surveyDto
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
        try {
            Aptitude aptitude = aptitudeRepository.findById(aptitudeSurveyDto.getAptitudeId());
            AptitudeDto aptitudeDto = AptitudeTransformer.convertToDto(aptitude);
            aptitudeSurvey.setAptitude(aptitudeDto);
            aptitudeSurvey.setObservation(aptitudeSurveyDto.getObservation());
            aptitudeSurvey.setBehaviors(
                    this.behaviorsSurveyTransformer(aptitude.getBehaviors(), aptitudeSurveyDto.getBehaviors()));
        } catch (Exception e) {
            logger.error("The aptitude's id is not found in the data base. " + e);
        }
        return aptitudeSurvey;
    }

    public List<BehaviorSurvey> behaviorsSurveyTransformer(List<Behavior> behaviors,
                                                           List<BehaviorSurveyDto> behaviorsSurveyDto) {
        List<BehaviorSurvey> behaviorsSurvey = new ArrayList<>();
        for (BehaviorSurveyDto behaviorSurveyDto : behaviorsSurveyDto) {
            BehaviorSurvey behaviorSurvey = new BehaviorSurvey();
            for (Behavior behavior : behaviors) {
                if (behavior.getId().equals(behaviorSurveyDto.getBehaviorId())) {
                    behaviorSurvey.setBehavior(behavior);
                    behaviorSurvey.setScore(behaviorSurveyDto.getScore());
                    behaviorsSurvey.add(behaviorSurvey);
                    break;
                }
            }
        }
        return behaviorsSurvey;
    }
}