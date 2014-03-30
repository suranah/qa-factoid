package edu.cmu.lti.oaqa.factory;

import org.json.JSONException;
import org.json.JSONObject;

import edu.cmu.lti.oaqa.framework.IComponent;
import edu.cmu.lti.oaqa.framework.decorator.MessageAndStopWatchDecorator;
import edu.cmu.lti.oaqa.stable_impl.EphyraRetrievalStrategist;
import edu.cmu.lti.oaqa.toy_impl.HardCodedRetrievalStrategist;

public class RetrievalStrategistFactory extends AbstractComponentFactory {

  @Override
  public IComponent create(JSONObject config) throws JSONException {
    IComponent component = null;
    String id = config.getString("id");
    if ( id.equalsIgnoreCase("stable") ) {
      boolean serverMode = config.getBoolean("isServer");
      String[] locations = convertJSONArray(config.getJSONArray("locations"));
      component = new EphyraRetrievalStrategist( serverMode, locations );
    } else if ( id.equalsIgnoreCase("hardcoded") ) {
      component = new HardCodedRetrievalStrategist();
    }
    if (component == null) LOGGER.error("Factory failed to create Retrieval Strategist.");
    return new MessageAndStopWatchDecorator( component );
  }

}
