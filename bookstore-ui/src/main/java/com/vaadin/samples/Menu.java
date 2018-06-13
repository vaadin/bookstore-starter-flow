package com.vaadin.samples;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcons;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Router;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.server.Page;
import com.vaadin.ui.themes.ValoTheme;

public class Menu extends HorizontalLayout {

    private static final String VALO_MENUITEMS = "valo-menuitems";
    private static final String VALO_MENU_TOGGLE = "valo-menu-toggle";
    private static final String VALO_MENU_VISIBLE = "valo-menu-visible";
//    private Map<String, RouterLink> viewButtons = new HashMap<>();

    private Tabs tabs;
    private VerticalLayout menuPart;

    public Menu() {

        //TODO
        //setPrimaryStyleName(ValoTheme.MENU_ROOT);

        menuPart = new VerticalLayout();
        menuPart.addClassName(ValoTheme.MENU_PART);

        // header of the menu
        final HorizontalLayout top = new HorizontalLayout();
        top.setDefaultVerticalComponentAlignment(Alignment.START);

        top.addClassName(ValoTheme.MENU_TITLE);

        Label title = new Label("My CRUD");
        title.addClassName(ValoTheme.LABEL_H3);
        title.setSizeUndefined();
        Image image = new Image("frontend/img/table-logo.png", "");
        image.setClassName("logo");
        top.add(image);
        top.add(title);
        menuPart.add(top);

        // logout menu item
        Button logoutButton = new Button("Logout", VaadinIcons.SIGN_OUT.create());
        logoutButton.addClickListener(event -> {
            VaadinSession.getCurrent().getSession().invalidate();

            UI.getCurrent().navigate("/");
        });

        logoutButton.addClassName("user-menu");
        menuPart.add(logoutButton);

        // button for toggling the visibility of the menu when on a small screen
        final Button showMenu = new Button("Menu", event -> {
            if (menuPart.getClassName().contains(VALO_MENU_VISIBLE)) {
                menuPart.removeClassName(VALO_MENU_VISIBLE);
            } else {
                menuPart.addClassName(VALO_MENU_VISIBLE);
            }
        });

        showMenu.addClassName(ValoTheme.BUTTON_PRIMARY);
        showMenu.addClassName(ValoTheme.BUTTON_SMALL);
        showMenu.addClassName(VALO_MENU_TOGGLE);
        showMenu.setIcon(new Icon(VaadinIcons.MENU));
        menuPart.add(showMenu);

        // container for the navigation buttons, which are added by addView()
        tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);

        //TODO
        //tabs.setPrimaryStyleName(VALO_MENUITEMS);

        menuPart.add(tabs);

        add(menuPart);
    }

    /**
     * Register a pre-created view instance in the navigation menu and in the
     * {@link Router}.
     *
     * @param viewClass view instance to register
     * @param name      view name
     * @param caption   view caption in the menu
     * @param icon      view icon in the menu
     */
    public void addView(Class<? extends Component> viewClass, final String name, String caption,
                        Icon icon) {
        createViewButton(viewClass, name, caption, icon);
    }

    private void createViewButton(final Class<? extends Component> viewClass, final String name, String caption,
                                  Icon icon) {
        Tab tab = new Tab();
        tab.setClassName(ValoTheme.MENU_ITEM);
        Button button = new Button(caption, icon, event -> UI.getCurrent().navigate(name));
        tab.add(button);

        tabs.add(tab);
//        viewButtons.put(name, tab);
    }

//    /**
//     * Highlights a view navigation button as the currently active view in the
//     * menu. This method does not perform the actual navigation.
//     *
//     * @param viewName the name of the view to show as active
//     */
//    public void setActiveView(String viewName) {
//        for (RouterLink routerLink : viewButtons.values()) {
//            routerLink.removeClassName("selected");
//        }
//        RouterLink selected = viewButtons.get(viewName);
//        if (selected != null) {
//            selected.addClassName("selected");
//        }
//        menuPart.removeClassName(VALO_MENU_VISIBLE);
//    }
}
