package listeners;

import org.w3c.dom.events.MouseEvent;

public interface AttemptedQuizClick extends MouseEvent{
    void itemClicked(Integer nof, Integer noa);
}
