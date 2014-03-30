package edu.cmu.lti.oaqa.factory;

import org.json.JSONException;
import org.json.JSONObject;

import edu.cmu.lti.oaqa.framework.IComponent;
import edu.cmu.lti.oaqa.framework.decorator.MessageAndStopWatchDecorator;
import edu.cmu.lti.oaqa.stable_impl.EphyraAnswerGenerator;
import edu.cmu.lti.oaqa.toy_impl.CombinedAnswerGenerator;
import edu.cmu.lti.oaqa.toy_impl.DeduplicationAnswerGenerator;
import edu.cmu.lti.oaqa.toy_impl.WebReinforcementAnswerGenerator;

public class AnswerGeneratorFactory extends AbstractComponentFactory {

  @Override
  public IComponent create(JSONObject config) throws JSONException {
    IComponent component = null;
    String id = config.getString("id");
    if ( id.equalsIgnoreCase("stable") ) {
      component = new EphyraAnswerGenerator();
    } else if ( id.equalsIgnoreCase("deduplication") ) {
      component = new DeduplicationAnswerGenerator();
    } else if ( id.equalsIgnoreCase("web")){
      component = new WebReinforcementAnswerGenerator();
    } else if ( id.equalsIgnoreCase("combined")){
        component = new CombinedAnswerGenerator();
      }
    if (component == null) LOGGER.error("Factory failed to create Answer Generator.");
    return new MessageAndStopWatchDecorator( component );
  }

}
