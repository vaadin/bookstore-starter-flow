package com.vaadin.samples.authentication;

public class AccessControlFactory {
    private static AccessControlFactory instance = new AccessControlFactory();
    private AccessControl accessControl = new BasicAccessControl();

    private AccessControlFactory() {
    }

    public static AccessControlFactory getInstance() {
        return instance;
    }

    public AccessControl createAccessControl() {
        return accessControl;
    }
}
