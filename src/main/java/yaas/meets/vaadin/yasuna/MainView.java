package yaas.meets.vaadin.yasuna;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.Scroller.ScrollDirection;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
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
                event -> openDialog());
        Button arg[] = {button, button2};
        add(arg);
    }

    private void openDialog() {
        HorizontalLayout dialogLayout = new HorizontalLayout();

        dialogLayout.setWidth("100%");
        dialogLayout.setMargin(false);
        dialogLayout.setPadding(false);
        FormLayout nameLayout = new FormLayout();
        nameLayout.setWidth("100%");
        nameLayout.setHeight("100%");

        TextField titleField = new TextField();
        titleField.setLabel("Title");
        titleField.setPlaceholder("Sir");
        TextField firstNameField = new TextField();
        firstNameField.setLabel("First name");
        firstNameField.setPlaceholder("John");
        TextField lastNameField = new TextField();
        lastNameField.setLabel("Last name");
        lastNameField.setPlaceholder("Doe");
        TextField titleField2 = new TextField();
        titleField2.setLabel("Title");
        titleField2.setPlaceholder("Sir");
        TextField firstNameField2 = new TextField();
        firstNameField2.setLabel("First name");
        firstNameField2.setPlaceholder("John");
        TextField lastNameField2 = new TextField();
        lastNameField2.setLabel("Last name");
        lastNameField2.setPlaceholder("Doe");
        TextField titleField3 = new TextField();
        titleField3.setLabel("Title");
        titleField3.setPlaceholder("Sir");
        TextField firstNameField3 = new TextField();
        firstNameField3.setLabel("First name");
        firstNameField3.setPlaceholder("John");
        TextField lastNameField3 = new TextField();
        lastNameField3.setLabel("Last name");
        lastNameField3.setPlaceholder("Doe");
        nameLayout.add(titleField, firstNameField, lastNameField, titleField2, firstNameField2, lastNameField2, titleField3, firstNameField3, lastNameField3);
        nameLayout.setResponsiveSteps(
                new ResponsiveStep("25em", 1),
                new ResponsiveStep("32em", 2),
                new ResponsiveStep("40em", 3));

        dialogLayout.add(nameLayout);


        Dialog dialog = new Dialog();
        dialog.add(titleField, firstNameField, lastNameField, titleField2, firstNameField2, lastNameField2, titleField3, firstNameField3, lastNameField3);
       dialog.getElement().getStyle().set("overflow-y", "scroll");
        dialog.setHeight("100vh");
        dialog.setWidth("90%");
        Scroller scroller = new Scroller(nameLayout);
        scroller.setScrollDirection(ScrollDirection.VERTICAL);

        dialog.open();

    }

}
