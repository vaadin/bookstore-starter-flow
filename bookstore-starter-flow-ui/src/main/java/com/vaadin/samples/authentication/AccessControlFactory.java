package com.vaadin.samples.authentication;

public class AccessControlFactory {
    private static final AccessControlFactory instance = new AccessControlFactory();
    private final AccessControl accessControl = new BasicAccessControl();

    private AccessControlFactory() {
    }

    public static AccessControlFactory getInstance() {
        return instance;
    }

    public AccessControl createAccessControl() {
        return accessControl;
    }
}
