package edu.cmu.lti.oaqa.framework.decorator;

import edu.cmu.lti.oaqa.framework.IComponent;

/**
 * Decorator that adds message showing and time-measuring capability to a component
 * @author Hideki Shima
 *
 */
public class MessageAndStopWatchDecorator extends MessageDecorator {

  public MessageAndStopWatchDecorator(IComponent component) {
    super(new StopWatchDecorator(component));
  }

}
