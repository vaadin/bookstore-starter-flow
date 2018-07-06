package com.vaadin.samples.about;


import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.Version;
import com.vaadin.samples.MainScreen;

@Route(value = "About", layout = MainScreen.class)
@PageTitle("About")
public class AboutView extends HorizontalLayout {

    public static final String VIEW_NAME = "About";

    public AboutView() {
        setDefaultVerticalComponentAlignment(Alignment.CENTER);
        setSizeFull();
        setMargin(false);
        getStyle().set("justify-content", "center");

        add(VaadinIcon.INFO_CIRCLE.create());
        add(new Span(" This application is using Vaadin "
                + Version.getFullVersion()));

    }
}
