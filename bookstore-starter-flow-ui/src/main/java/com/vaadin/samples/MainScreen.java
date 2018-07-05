package com.vaadin.samples;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.samples.about.AboutView;
import com.vaadin.samples.crud.SampleCrudView;

public class MainScreen extends HorizontalLayout implements RouterLayout {
    private Menu menu;

    public MainScreen() {

        this.setSpacing(false);
        setSizeFull();

        menu = new Menu();
        menu.addView(SampleCrudView.class, SampleCrudView.VIEW_NAME,
                SampleCrudView.VIEW_NAME, VaadinIcon.EDIT.create());
        menu.addView(AboutView.class, AboutView.VIEW_NAME, AboutView.VIEW_NAME,
                VaadinIcon.INFO_CIRCLE.create());

        this.add(menu);
    }
}
