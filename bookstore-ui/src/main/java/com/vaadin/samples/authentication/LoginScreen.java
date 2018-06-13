package com.vaadin.samples.authentication;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.samples.MainScreen;
import com.vaadin.ui.themes.ValoTheme;

import java.io.Serializable;

/**
 * UI content when the user is not logged in yet.
 */
@Route("Login")
@StyleSheet("/css/shared-styles.css")
public class LoginScreen extends HorizontalLayout {

    private TextField username;
    private PasswordField password;
    private Button login;
    private Button forgotPassword;
    private LoginListener loginListener;
    private AccessControl accessControl;

    public LoginScreen() {
        this(new BasicAccessControl(), () -> {
            UI.getCurrent().navigate(MainScreen.class);
        });
    }

    public LoginScreen(AccessControl accessControl, LoginListener loginListener) {
        this.loginListener = loginListener;
        this.accessControl = accessControl;
        buildUI();
        username.focus();
    }

    private void buildUI() {
        addClassName("login-screen");

        // login form, centered in the available part of the screen
        Component loginForm = buildLoginForm();

        // layout to center login form when there is sufficient screen space
        // - see the theme for how this is made responsive for various screen
        // sizes
        VerticalLayout centeringLayout = new VerticalLayout();
        centeringLayout.setMargin(false);
        centeringLayout.setSpacing(false);
        centeringLayout.setClassName("centering-layout");
        centeringLayout.add(loginForm);
        centeringLayout.setHorizontalComponentAlignment(Alignment.CENTER, loginForm);

        // information text about logging in
        Component loginInformation = buildLoginInformation();

        add(centeringLayout);
        add(loginInformation);
    }

    private Component buildLoginForm() {
        FormLayout loginForm = new FormLayout();

        loginForm.addClassName("login-form");
        loginForm.setSizeUndefined();

        //TODO maybe needs a change in css
        //loginForm.setMargin(false);

        loginForm.add(username = new TextField("Username", "admin"));
        username.setWidth("15em");
        loginForm.add(password = new PasswordField("Password"));
        password.setWidth("15em");

        //TODO Tooltips
        //password.setDescription("Write anything");

        VerticalLayout buttons = new VerticalLayout();
        buttons.setClassName("buttons");
        loginForm.add(buttons);

        buttons.add(login = new Button("Login"));

        //TODO setDisableOnClick???
        //login.setDisableOnClick(true);

        login.addClickListener(event -> {
            login.setEnabled(false);
            try {
                login();
            } finally {
                login.setEnabled(true);
            }
        });

        //TODO
        //login.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        login.addClassName(ValoTheme.BUTTON_FRIENDLY);

        buttons.add(forgotPassword = new Button("Forgot password?"));
        forgotPassword.setClassName("v-button-danger");
        forgotPassword.addClickListener(event -> {
            showNotification(new Notification("Hint: Try anything"));
        });
        forgotPassword.addClassName(ValoTheme.BUTTON_LINK);
        return loginForm;
    }

    private Component buildLoginInformation() {
        HorizontalLayout loginInformation = new HorizontalLayout();
        loginInformation.setClassName("login-information");

        Label loginInfoHeader = new Label("Login Information");
        loginInfoHeader.setClassName("");
        Span loginInfoText = new Span(
                "Log in as \"admin\" to have full access. Log in with any other username to have read-only access. "
                        + "For all users, any password is fine");
        loginInfoText.setSizeFull();
        loginInformation.add(loginInfoHeader, loginInfoText);
        return loginInformation;
    }

    private void login() {
        if (accessControl.signIn(username.getValue(), password.getValue())) {
            loginListener.loginSuccessful();
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

    public interface LoginListener extends Serializable {
        void loginSuccessful();
    }
}
