package com.vaadin.samples;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.icon.VaadinIcons;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.samples.about.AboutView;
import com.vaadin.samples.crud.SampleCrudView;

@Route("")
@StyleSheet("/css/shared-styles.css")
@HtmlImport("frontend://com/vaadin/samples/MainScreenDesign.html")
@Tag("main-screen")
public class MainScreen extends PolymerTemplate<TemplateModel> implements RouterLayout {
    private Menu menu;

    @Id("layout")
    private VerticalLayout layout;

    public MainScreen() {

        layout.setSpacing(false);
        layout.setClassName("main-screen");

        menu = new Menu();
        menu.addView(SampleCrudView.class, SampleCrudView.VIEW_NAME,
                SampleCrudView.VIEW_NAME, VaadinIcons.EDIT.create());
        menu.addView(AboutView.class, AboutView.VIEW_NAME, AboutView.VIEW_NAME,
                VaadinIcons.INFO_CIRCLE.create());

        layout.add(menu);
        layout.setSizeFull();
    }
}
