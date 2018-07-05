package com.vaadin.samples.authentication;

public class AccessControlFactory {
    private static AccessControlFactory instance;

    private AccessControlFactory() {
    }

    public static AccessControlFactory getInstance() {
        if (instance == null)
            instance = new AccessControlFactory();

        return instance;
    }

    public AccessControl createAccessControl() {
        return new BasicAccessControl();
    }
}
