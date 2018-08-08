package com.vaadin.samples;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.server.VaadinSession;

public class Menu extends FlexLayout {

    private Tabs tabs;
    private VerticalLayout menuPart;

    public Menu() {

        menuPart = new VerticalLayout();

        // header of the menu
        final HorizontalLayout top = new HorizontalLayout();
        top.setDefaultVerticalComponentAlignment(Alignment.START);

        Label title = new Label("My CRUD");
        title.setSizeUndefined();
        Image image = new Image("frontend/img/table-logo.png", "");
        top.add(image);
        top.add(title);
        menuPart.add(top);

        // button for toggling the visibility of the menu when on a small screen
        final Button showMenu = new Button("Menu",
                event -> tabs.setVisible(!tabs.isVisible()));

        showMenu.setIcon(new Icon(VaadinIcon.MENU));
        menuPart.add(showMenu);

        // container for the navigation buttons, which are added by addView()
        tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);

        menuPart.add(tabs);

        Div expander = new Div();
        expander.getStyle().set("flex", "100");
        menuPart.add(expander);

        // logout menu item
        Button logoutButton = new Button("Logout",
                VaadinIcon.SIGN_OUT.create());
        logoutButton.addClickListener(event -> {
            VaadinSession.getCurrent().getSession().invalidate();
            UI.getCurrent().getPage().reload();
        });

        logoutButton.getElement().getThemeList().add("tertiary-inline");
        menuPart.add(logoutButton);

        add(menuPart);
    }

    /**
     * Add a view to the navigation menu
     *
     * @param viewClass that has a {@code Route} annotation
     * @param caption   view caption in the menu
     * @param icon      view icon in the menu
     */
    public void addView(Class<? extends Component> viewClass, String caption,
                        Icon icon) {
        Tab tab = new Tab();
        Button button = new Button(caption, icon,
                event -> UI.getCurrent().navigate(viewClass));
        button.getElement().getThemeList().add("tertiary-inline");
        tab.add(button);

        tabs.add(tab);
    }
}
