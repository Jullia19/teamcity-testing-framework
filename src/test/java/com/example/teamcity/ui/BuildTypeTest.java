package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import com.example.teamcity.ui.pages.admin.CreateBuildTypePage;
import com.example.teamcity.ui.pages.admin.ProjectPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.example.teamcity.api.enums.Endpoint.PROJECT;

@Test(groups = {"Regression"})
public class BuildTypeTest extends BaseUiTest {

    @Test(description = "User should be able to create build type", groups = {"Positive"})
    public void userCreatesBuildType() {
        // подготовка окружения
        loginAs(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));
        userCheckRequests.<Project>getRequest(PROJECT).create(testData.getProject());
        // взаимодействие с UI
        CreateBuildTypePage.open(testData.getProject().getId()).createForm(REPO_URL).setupBuildType(testData.getBuildType().getName());
        // проверка состояния UI
        //var createdBuildType = superUserCheckRequests.<BuildType>getRequest(Endpoint.BUILD_TYPES).read("name:" + testData.getBuildType().getName());
        //softy.assertNotNull(createdBuildType);
        // (корректность считывания данных и отображение данных на UI)
        ProjectPage.open(testData.getProject().getId())
                .buildType.shouldHave(Condition.exactText(testData.getBuildType().getName()));
    }

    @Test(description = "User should not be able to create build type with the same name", groups = {"Negative"})
    public void userCreatesBuildTypeWithTheSameName() {
        CreateBuildTypePage buildTypePage = new CreateBuildTypePage();
        loginAs(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));
        userCheckRequests.<Project>getRequest(PROJECT).create(testData.getProject());
        buildTypePage.open(testData.getProject().getId()).createForm(REPO_URL).setupBuildType(testData.getBuildType().getName());
        var createdBuildType = superUserCheckRequests.<BuildType>getRequest(Endpoint.BUILD_TYPES).read("name:" + testData.getBuildType().getName());
        softy.assertNotNull(createdBuildType);
        buildTypePage.open(testData.getProject().getId()).createForm(REPO_URL).setupBuildType(testData.getBuildType().getName());
        buildTypePage.clickSubmitAnywayButton();
        Assert.assertEquals(buildTypePage.getErrorText(),"Build configuration with name \""+ testData.getBuildType().getName() +"\" already exists in project: \"" + testData.getProject().getName() + "\"");
    }

}
