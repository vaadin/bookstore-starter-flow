package com.vaadin.samples.authentication;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * UI content when the user is not logged in yet.
 */
@Route("Login")
@PageTitle("Login")
public class LoginScreen extends HorizontalLayout {

    private TextField username;
    private PasswordField password;
    private Button login;
    private Button forgotPassword;
    private AccessControl accessControl;

    public LoginScreen() {
        this.accessControl = AccessControlFactory.getInstance().createAccessControl();
        buildUI();
        username.focus();
    }

    private void buildUI() {
        this.setSizeFull();
        this.getStyle().set("display", "flex");

        // login form, centered in the available part of the screen
        Component loginForm = buildLoginForm();

        // information text about logging in
        Component loginInformation = buildLoginInformation();

        add(loginInformation);
        add(loginForm);
    }

    private Component buildLoginForm() {
        FormLayout loginForm = new FormLayout();
        loginForm.setWidth("310px");

        loginForm.addFormItem(username = new TextField(), "Username");
        username.setWidth("15em");
        username.setPlaceholder("admin");
        loginForm.add(new Html("<br/>"));
        loginForm.addFormItem(password = new PasswordField(), "Password");
        password.setWidth("15em");

        HorizontalLayout buttons = new HorizontalLayout();
        loginForm.add(new Html("<br/>"));
        loginForm.add(buttons);

        buttons.add(login = new Button("Login"));

        login.addClickListener(event -> {
            login.setEnabled(false);
            try {
                login();
            } finally {
                login.setEnabled(true);
            }
        });

        login.getElement().getThemeList().add("success primary");

        buttons.add(forgotPassword = new Button("Forgot password?"));
        forgotPassword.getElement().getThemeList().add("tertiary");
        forgotPassword.addClickListener(event -> {
            showNotification(new Notification("Hint: Try anything"));
        });

        Div div = new Div(loginForm);
        div.setSizeFull();
        div.getStyle().set("display", "flex");
        div.getStyle().set("justify-content", "center");
        div.getStyle().set("align-items", "center");
        div.getStyle().set("flex", "1");

        return div;
    }

    private Component buildLoginInformation() {
        VerticalLayout loginInformation = new VerticalLayout();

        H1 loginInfoHeader = new H1("Login Information");
        Span loginInfoText = new Span(
                "Log in as \"admin\" to have full access. Log in with any other username to have read-only access. "
                        + "For all users, any password is fine");
        loginInformation.add(loginInfoHeader);
        loginInformation.add(loginInfoText);
        loginInformation.getStyle().set("min-width", "300px");
        loginInformation.getStyle().set("flex", "0");
        return loginInformation;
    }

    private void login() {
        if (accessControl.signIn(username.getValue(), password.getValue())) {
            UI.getCurrent().navigate("");
        } else {
            showNotification(new Notification("Login failed. " +
                    "Please check your username and password and try again."));
            username.focus();
        }
    }

    private void showNotification(Notification notification) {
        // keep the notification visible a little while after moving the
        // mouse, or until clicked
        notification.setDuration(2000);
        notification.open();
    }
}
