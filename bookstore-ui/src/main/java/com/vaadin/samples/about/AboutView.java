package com.vaadin.samples.about;


import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.icon.VaadinIcons;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.Version;
import com.vaadin.samples.MainScreen;

@Route(value = "About", layout = MainScreen.class)
@StyleSheet("/css/shared-styles.css")
public class AboutView extends VerticalLayout {

    public static final String VIEW_NAME = "About";

    public AboutView() {
        HorizontalLayout aboutContent = new HorizontalLayout();
        aboutContent.setClassName("about-content");
        aboutContent.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        aboutContent.setSizeFull();
        aboutContent.setMargin(false);

        // you can add Vaadin components in predefined slots in the custom
        // layout
        aboutContent.add(VaadinIcons.INFO_CIRCLE.create());
        aboutContent.add(
                new Text(" This application is using Vaadin "
                        + Version.getFullVersion()));

        setSizeFull();
        setMargin(false);
        setClassName("about-view");
        add(aboutContent);
        this.setHorizontalComponentAlignment(Alignment.CENTER, aboutContent);
    }
}