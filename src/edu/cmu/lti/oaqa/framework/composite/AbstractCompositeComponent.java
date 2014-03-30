package edu.cmu.lti.oaqa.framework.composite;

import java.util.ArrayList;
import java.util.List;

import edu.cmu.lti.oaqa.framework.IComponent;

public abstract class AbstractCompositeComponent implements IComponent {

  public AbstractCompositeComponent( IComponent ... components ) {
    for ( IComponent c : components ) {
      if (c!=null) add(c);
    }
  }
  
  protected List<IComponent> components = new ArrayList<IComponent>();

  @Override
  public void initialize() {
    for ( IComponent c : components ) {
      if (c==null) continue; 
      c.initialize();
    }
  }
  
  public void add( IComponent component ) {
    components.add( component );
  }
}
