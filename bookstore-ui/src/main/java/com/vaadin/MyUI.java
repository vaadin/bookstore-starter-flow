package com.vaadin;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinServlet;
import com.vaadin.flow.server.VaadinServletConfiguration;
import com.vaadin.flow.theme.Theme;
import com.vaadin.samples.MainScreen;
import com.vaadin.samples.authentication.AccessControl;
import com.vaadin.samples.authentication.BasicAccessControl;
import com.vaadin.samples.authentication.LoginScreen;

import javax.servlet.annotation.WebServlet;

//TODO use VerticalLayout instead of UI in case of error
public class MyUI extends UI {

    private AccessControl accessControl = new BasicAccessControl();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        addBeforeEnterListener(event -> {
            if(!accessControl.isUserSignedIn() && !LoginScreen.class.equals(event.getNavigationTarget()))
                event.rerouteTo(LoginScreen.class);
        });

        //TODO Use VerticalLayout to make the page responsive in case
        //Responsive.makeResponsive(this);

        setLocale(vaadinRequest.getLocale());
        getPage().setTitle("My");
//        if (!accessControl.isUserSignedIn()) {
//            add(new LoginScreen(accessControl, (LoginScreen.LoginListener) () -> showMainView()));
//        } else {
//            showMainView();
//        }
    }

    protected void showMainView() {
        //addStyleName(ValoTheme.UI_WITH_MENU);
        add(new MainScreen());
    }

    public static MyUI get() {
        return (MyUI) UI.getCurrent();
    }

    public AccessControl getAccessControl() {
        return accessControl;
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
