package edu.cmu.lti.oaqa.factory;

import org.json.JSONException;
import org.json.JSONObject;

import edu.cmu.lti.oaqa.framework.IComponent;
import edu.cmu.lti.oaqa.framework.decorator.MessageAndStopWatchDecorator;
import edu.cmu.lti.oaqa.stable_impl.EphyraKeytermExtractor;
import edu.cmu.lti.oaqa.toy_impl.SimplestKeytermExtractor;

public class KeytermExtractorFactory extends AbstractComponentFactory {

  @Override
  public IComponent create(JSONObject config) throws JSONException {
    IComponent component = null;
    String id = config.getString("id");
    if ( id.equalsIgnoreCase("stable") ) {
      component = new EphyraKeytermExtractor();
    } else if ( id.equalsIgnoreCase("simple") ) {
      component = new SimplestKeytermExtractor();
    }
    if (component == null) LOGGER.error("Factory failed to create Keyterm Extractor.");
    return new MessageAndStopWatchDecorator( component );
  }

}
