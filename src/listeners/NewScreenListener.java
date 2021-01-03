
package listeners;

import java.util.EventListener;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;


public interface NewScreenListener extends EventHandler{

    
    public void changeScreen(Parent node);
    
    public void removetopscreen();
    
}
