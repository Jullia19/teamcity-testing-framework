package com.example.teamcity.api;

import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import com.example.teamcity.api.spec.Specifications;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Arrays;

import static com.example.teamcity.api.enums.Endpoint.*;
import static com.example.teamcity.api.generators.TestDataGenerator.generate;

@Test(groups = {"Regression"})
public class ProjectTest extends BaseApiTest{

    @Test(description = "User should be able to create a project", groups = {"Positive", "CRUD"})
    public void userCreatesProjectTest(){
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));
        userCheckRequests.<Project>getRequest(PROJECT).create(testData.getProject());
        var createdProject = userCheckRequests.<Project>getRequest(PROJECT).read(testData.getProject().getId());
        softy.assertEquals(testData.getProject().getName(), createdProject.getName(), "Project name is not correct");
    }

    @Test(description = "User not be able to create two projects with the same id", groups = {"Negative", "CRUD"})
    public void userCreatesTwoProjectWithTheSameIdTest(){
        var projectWithSameId = generate(Arrays.asList(testData.getProject()), Project.class, testData.getProject().getId());
        System.out.println("projectWithSameId =" + projectWithSameId);
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));
        userCheckRequests.<Project>getRequest(PROJECT).create(testData.getProject());
        new UncheckedBase(Specifications.authSpec(testData.getUser()), PROJECT)
                .create(projectWithSameId)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("Project ID \"%s\" is already used by another project".formatted(testData.getProject().getId())));

    }

}
