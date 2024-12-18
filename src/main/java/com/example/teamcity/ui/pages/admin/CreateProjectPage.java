package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;


public class CreateProjectPage extends CreateBasePage {
    private static final String PROJECT_SHOW_MODE = "createProjectMenu";

    private SelenideElement projectNameInput = $("#projectName");
    private static SelenideElement errorMessage = $("#error_projectName");

    public static CreateProjectPage open(String projectId) {
        Configuration.timeout = 10000;
        return Selenide.open(CREATE_URL.formatted(projectId, PROJECT_SHOW_MODE), CreateProjectPage.class);
    }

    public CreateProjectPage createForm(String url) {
        Configuration.timeout = 10000;
        baseCreateForm(url);
        return this;
    }

    public void setupProject(String projectName, String buildTypeName) {
        projectNameInput.val(projectName);
        buildTypeNameInput.val(buildTypeName);
        submitButton.click();
        //return page(ProjectPage.class);
    }

    public static String getErrorText() {
        return errorMessage.getText();
    }
}