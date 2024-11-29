package com.example.teamcity;

import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.models.TestData;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

import static com.example.teamcity.api.generators.TestDataGenerator.generate;

public class BaseTest {
    protected static final String REPO_URL = "https://github.com/AlexPshe/spring-core-for-qa";
    protected SoftAssert softy;
    protected CheckedRequests superUserCheckRequests = new CheckedRequests(Specifications.superUserAuth());
    protected TestData testData;

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {

        softy = new SoftAssert();
        testData = generate();

    }

    @AfterMethod(alwaysRun = true)
    public void afterTest() {

        softy.assertAll();
        TestDataStorage.getStorage().deleteCreatedEntities();

    }


}