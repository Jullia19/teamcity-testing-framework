package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;


public class CreateBuildTypePage extends CreateBasePage {

    private static final String PROJECT_SHOW_MODE = "createBuildTypeMenu";

    private SelenideElement buildTypeNameInput = $("#buildTypeName");
    private SelenideElement submitAnywayButton = $("#submitAnywayButton");
    private static SelenideElement errorMessage = $("#error_buildTypeName");


    public static CreateBuildTypePage open(String projectId) {
        return Selenide.open(CREATE_URL.formatted(projectId, PROJECT_SHOW_MODE), CreateBuildTypePage.class);
    }

    public CreateBuildTypePage createForm(String url) {
        baseCreateForm(url);
        return this;
    }

    public void setupBuildType(String buildTypeName) {
        buildTypeNameInput.val(buildTypeName);
        submitButton.click();
        //return page(ProjectPage.class);
    }

    public static String getErrorText() {
        errorMessage.shouldBe(Condition.visible, BASE_WAITING);
        return errorMessage.getText();
    }

    public void clickSubmitAnywayButton() {
        submitAnywayButton.click();
    }
}
