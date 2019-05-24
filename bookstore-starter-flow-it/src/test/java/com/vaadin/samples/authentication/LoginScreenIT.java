package com.vaadin.samples.authentication;

import org.junit.Assert;
import org.junit.Test;

import com.vaadin.samples.AbstractViewTest;
import com.vaadin.samples.MainLayoutElement;

public class LoginScreenIT extends AbstractViewTest {

    @Test
    public void loginAsAdmin_hasAdminViewLink() {
        // when authenticating as admin
        $(LoginFormElement.class).first().login("admin", "admin");

        // then there is a link to admin's view
        Assert.assertTrue("Expected link to admin view",
                $(MainLayoutElement.class).first().hasMenuLink("admin"));
    }

    @Test
    public void loginAsUser_noAdminViewLink() {
        // when authenticating as a regular user
        $(LoginFormElement.class).first().login("user", "user");

        // then there is no link to admin's view
        Assert.assertFalse("Expected no link to admin view",
                $(MainLayoutElement.class).first().hasMenuLink("admin"));
    }
}