package com.vaadin.samples.about;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.Version;
import com.vaadin.samples.MainScreen;

@Route(value = "About", layout = MainScreen.class)
@RouteAlias(value = "", layout = MainScreen.class)
@PageTitle("About")
public class AboutView extends HorizontalLayout {

    public static final String VIEW_NAME = "About";

    public AboutView() {
        add(VaadinIcon.INFO_CIRCLE.create());
        add(new Span(" This application is using Vaadin "
                + Version.getFullVersion()));

        setSizeFull();
        setMargin(false);
        getStyle().set("justify-content", "center");
        setDefaultVerticalComponentAlignment(Alignment.CENTER);
    }
}
