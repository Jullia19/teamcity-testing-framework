package com.example.teamcity.api;


import com.example.teamcity.api.models.*;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import com.example.teamcity.api.spec.Specifications;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import org.apache.http.HttpStatus;

import java.util.Arrays;

import static com.example.teamcity.api.enums.Endpoint.*;
import static com.example.teamcity.api.generators.TestDataGenerator.generate;


@Test(groups = {"Regression"})
public class BuildTypeTest extends BaseApiTest{

    @Test(description = "User should be able to create build type", groups = {"Positive", "CRUD"})
    public void userCreatesBuildTypeTest(){
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));
        userCheckRequests.<Project>getRequest(PROJECT).create(testData.getProject());
        userCheckRequests.getRequest(BUILD_TYPES).create(testData.getBuildType());
        var createdBuildType = userCheckRequests.<BuildType>getRequest(BUILD_TYPES).read(testData.getBuildType().getId());
        softy.assertEquals(testData.getBuildType().getName(), createdBuildType.getName(), "Build type name is not correct");
    }

    @Test(description = "User should not be able to create two build types with the same id", groups = {"Negative", "CRUD"})
    public void userCreatesTwoBuildTypesWithTheSameIdTest() {
        var buildTypeWithSameId = generate(Arrays.asList(testData.getProject()), BuildType.class, testData.getBuildType().getId());
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));
        userCheckRequests.<Project>getRequest(PROJECT).create(testData.getProject());
        userCheckRequests.getRequest(BUILD_TYPES).create(testData.getBuildType());
        new UncheckedBase(Specifications.authSpec(testData.getUser()), BUILD_TYPES)
                .create(buildTypeWithSameId)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("The build configuration / template ID \"%s\" is already used by another configuration or template".formatted(testData.getBuildType().getId())));
    }

    @Test(description = "Project admin should be able to create build type for their project", groups = {"Positive", "Roles"})
    public void projectAdminCreatesBuildTypeTest() {
        superUserCheckRequests.getRequest(PROJECT).create(testData.getProject());
        testData.getUser().setRoles(generate(Roles.class, "PROJECT_ADMIN", "p:" + testData.getProject().getId()));
        superUserCheckRequests.<User>getRequest(USERS).create(testData.getUser());
        var buildTypeCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));
        buildTypeCheckRequests.getRequest(BUILD_TYPES).create(testData.getBuildType());
        var createdBuildType = buildTypeCheckRequests.<BuildType>getRequest(BUILD_TYPES).read(testData.getBuildType().getId());
        softy.assertEquals(testData.getBuildType().getName(), createdBuildType.getName(), "Build type name is not correct");
    }


    @Test(description = "Project admin should not be able to create build type for not their project", groups = {"Negative", "Roles"})
    public void projectAdminCreatesBuildTypeForAnotherUserProjectTest() {

        superUserCheckRequests.getRequest(PROJECT).create(testData.getProject());
        testData.getUser().setRoles(generate(Roles.class, "PROJECT_ADMIN", "p:" + testData.getProject().getId()));
        superUserCheckRequests.<User>getRequest(USERS).create(testData.getUser());
        User user1 = testData.getUser();
        testData = generate();
        superUserCheckRequests.getRequest(PROJECT).create(testData.getProject());
        testData.getUser().setRoles(generate(Roles.class, "PROJECT_ADMIN", "p:" + testData.getProject().getId()));
        superUserCheckRequests.<User>getRequest(USERS).create(testData.getUser());
        new UncheckedBase(Specifications.authSpec(user1), BUILD_TYPES)
                .create(testData.getBuildType())
                .then().assertThat().statusCode(HttpStatus.SC_FORBIDDEN)
                .body(Matchers.containsString("You do not have enough permissions to edit project with id: %s".formatted(testData.getProject().getId())));
    }

    @Test(description = "User should be able to run build type", groups = {"Positive", "CRUD"})
    public void userRunBuildTypeTest(){
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));
        userCheckRequests.<Project>getRequest(PROJECT).create(testData.getProject());
       // Property prop = generate(Property.class, "script.content", "echo 'Hello World!'");
        //System.out.println("8888"+prop);
       // Steps steps = generate(Steps.class);
       // Step step = generate(Arrays.asList(Step.class));
       // testData.getBuildType().setSteps(steps);
        userCheckRequests.getRequest(BUILD_TYPES).create(testData.getBuildType());
    }
}