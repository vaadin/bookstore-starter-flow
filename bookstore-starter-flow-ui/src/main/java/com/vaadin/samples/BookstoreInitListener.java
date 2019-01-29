package com.vaadin.samples;

import java.util.Collection;
import java.util.Collections;

import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.Router;
import com.vaadin.flow.server.RouteRegistry;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.samples.authentication.AccessControl;
import com.vaadin.samples.authentication.AccessControlFactory;
import com.vaadin.samples.authentication.LoginScreen;
import com.vaadin.samples.backend.DataService;

/**
 * This class is used to listen to BeforeEnter event of all UIs in order to
 * check whether a user is signed in or not before allowing entering any page.
 * It is registered in a file named
 * com.vaadin.flow.server.VaadinServiceInitListener in META-INF/services.
 */
public class BookstoreInitListener implements VaadinServiceInitListener {
    @Override
    public void serviceInit(ServiceInitEvent initEvent) {
        final AccessControl accessControl = AccessControlFactory.getInstance()
                .createAccessControl();


        // TODO: instead of redirect to login, add store view when it is
        //  available
        if (DataService.get().hasPublicStoreFront()) {
            Router router = initEvent.getSource().getRouter();
            RouteRegistry registry = router.getRegistry();
            registry.setRoute("",
                    StoreView.class, Collections.EMPTY_LIST);
        } else {
            initEvent.getSource().addUIInitListener(uiInitEvent -> {
                uiInitEvent.getUI().addBeforeEnterListener(enterEvent -> {
                    if (!accessControl.isUserSignedIn() && !LoginScreen.class
                            .equals(enterEvent.getNavigationTarget()))
                        enterEvent.rerouteTo(LoginScreen.class);
                });
            });
        }

    }
}
