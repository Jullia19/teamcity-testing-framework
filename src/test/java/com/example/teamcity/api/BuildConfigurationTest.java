package com.example.teamcity.api;

import com.example.teamcity.api.models.User;
import com.example.teamcity.api.spec.Specifications;
import org.testng.annotations.Test;
import org.apache.http.HttpStatus;
import io.restassured.RestAssured;

public class BuildConfigurationTest extends BaseApiTest{

    @Test
    public void buildConfigurationTest(){
        User user = User.builder()
                .username("admin")
                .password("admin")
                .build();

        String token = RestAssured
                .given()
                .spec(Specifications.getSpec().authSpec(user))
                .get("/app/rest/builds/id:4")
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().asString();
        System.out.println(token);
    }
}