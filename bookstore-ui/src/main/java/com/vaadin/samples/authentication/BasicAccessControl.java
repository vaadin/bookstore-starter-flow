package com.vaadin.samples.authentication;

import javax.servlet.http.HttpSession;

/**
 * Default mock implementation of {@link AccessControl}. This implementation
 * accepts any string as a password, and considers the user "admin" as the only
 * administrator.
 */
public class BasicAccessControl implements AccessControl {
    private HttpSession httpSession;

    public BasicAccessControl() {
    }

    public BasicAccessControl(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    @Override
    public boolean signIn(String username, String password) {
        if (username == null || username.isEmpty())
            return false;

        CurrentUser.set(username);
        return true;
    }

    @Override
    public boolean isUserSignedIn() {
        String username;

        if (httpSession != null)
            username = CurrentUser.get(httpSession);
        else
            username = CurrentUser.get();

        return !username.isEmpty();
    }

    @Override
    public boolean isUserInRole(String role) {
        if ("admin".equals(role)) {
            // Only the "admin" user is in the "admin" role
            return getPrincipalName().equals("admin");
        }

        // All users are in all non-admin roles
        return true;
    }

    @Override
    public String getPrincipalName() {
        return CurrentUser.get();
    }

}
