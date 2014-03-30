package edu.cmu.lti.oaqa.factory;

import org.json.JSONException;
import org.json.JSONObject;

import edu.cmu.lti.oaqa.framework.IComponent;
import edu.cmu.lti.oaqa.framework.decorator.MessageAndStopWatchDecorator;
import edu.cmu.lti.oaqa.stable_impl.EphyraAnswerTypeExtractor;
import edu.cmu.lti.oaqa.toy_impl.RamdomAnswerTypeExtractor;

public class AnswerTypeExtractorFactory extends AbstractComponentFactory {

  @Override
  public IComponent create(JSONObject config) throws JSONException {
    IComponent component = null;
    String id = config.getString("id");
    if (id.equalsIgnoreCase("stable")) {
      component = new EphyraAnswerTypeExtractor();
    } else if (id.equalsIgnoreCase("random")) {
      component = new RamdomAnswerTypeExtractor();
    }
    if (component == null) LOGGER.error("Factory failed to create Answer Type Extractor.");
    return new MessageAndStopWatchDecorator( component );
  }

}
