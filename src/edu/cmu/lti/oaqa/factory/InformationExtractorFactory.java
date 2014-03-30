package edu.cmu.lti.oaqa.factory;

import org.json.JSONException;
import org.json.JSONObject;

import edu.cmu.lti.oaqa.framework.IComponent;
import edu.cmu.lti.oaqa.framework.composite.BackOffInformationExtractor;
import edu.cmu.lti.oaqa.framework.composite.ComponentPipeline;
import edu.cmu.lti.oaqa.framework.decorator.MessageAndStopWatchDecorator;
import edu.cmu.lti.oaqa.framework.decorator.MessageDecorator;
import edu.cmu.lti.oaqa.stable_impl.ComboCandidateFilter;
import edu.cmu.lti.oaqa.stable_impl.EphyraInformationExtractorByAnswerType;
import edu.cmu.lti.oaqa.stable_impl.EphyraInformationExtractorByPattern;
import edu.cmu.lti.oaqa.toy_impl.HardCodedInformationExtractor;

public class InformationExtractorFactory extends AbstractComponentFactory {

  @Override
  public IComponent create(JSONObject config) throws JSONException {
    IComponent component = null;
    String id = config.getString("id");
    if ( id.equalsIgnoreCase("stable") ) {
      BackOffInformationExtractor extractors = new BackOffInformationExtractor(
              "Back-off Information Extractor",
              new MessageAndStopWatchDecorator( new EphyraInformationExtractorByAnswerType() ),
              new MessageAndStopWatchDecorator( new EphyraInformationExtractorByPattern() ) );
      ComponentPipeline pipeline = new ComponentPipeline(
              "Component and Filter Information Extractor",
              new MessageDecorator( extractors ), 
              new MessageAndStopWatchDecorator( new ComboCandidateFilter() ) );
      component = pipeline;
    } else if ( id.equalsIgnoreCase("hardcoded") ) {
      component = new HardCodedInformationExtractor();
    }
    if (component == null) LOGGER.error("Factory failed to create Information Extractor.");
    return new MessageDecorator(component);
  }

}
