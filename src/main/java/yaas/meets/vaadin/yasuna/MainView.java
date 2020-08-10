package yaas.meets.vaadin.yasuna;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

/**
 * The main view contains a button and a click listener.
 */


@Route("mainview")
public class MainView extends VerticalLayout {

    public MainView() {
    	Button button = new Button("Click me",
                event -> Notification.show("Clicked!"));

        Button button2 = new Button("Click me",
                event -> Notification.show("クリック２!"));
        Button arg[] = {button, button2};
        add(arg);
    }
}
