package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class ProjectPage extends BasePage {
    private static final String PROJECT_URL = "/project/%s";
    private static final String BUILDTYPE_URL = "/buildConfiguration/%s";

    public SelenideElement title = $("span[class*='ProjectPageHeader']");
    public SelenideElement buildType = $("span[class*='MiddleEllipsis']");


    public static ProjectPage open(String projectId) {
        return Selenide.open(PROJECT_URL.formatted(projectId), ProjectPage.class);
    }

    public static ProjectPage openBuildType(String buildTypeId) {
        return Selenide.open(BUILDTYPE_URL.formatted(buildTypeId), ProjectPage.class);
    }

}