package com.vaadin.samples.about;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.vaadin.samples.AbstractViewTest;
import com.vaadin.samples.authentication.LoginFormElement;
import com.vaadin.samples.MainLayoutElement;

public class AboutViewIT extends AbstractViewTest {

    @Test
    public void openAboutView_showsFlowVersion() {
        // given authenticated as a regular user
        $(LoginFormElement.class).first().login("user", "user");

        // when selecting "About" from the sidebar menu
        final MainLayoutElement mainElem = $(MainLayoutElement.class).first();
        mainElem.clickMenuLink("About");

        // then the view contents a span with Flow version information
        final WebElement aboutSpan = mainElem.
                findElement(By.xpath("./vaadin-horizontal-layout/span"));

        Assert.assertTrue("Expected link to admin view",
                aboutSpan.getText().contains("Flow"));
    }
}
