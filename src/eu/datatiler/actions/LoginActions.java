package eu.datatiler.actions;

import com.qatestlab.base.BaseActions;
import com.qatestlab.reporting.Reporter;
import com.qatestlab.testrail.CustomStepResult;
import com.qatestlab.testrail.TestRailAssert;
import eu.datatiler.pages.Pages;

public class LoginActions extends BaseActions{

    public void login(String email, String password){
         Pages.loginPage().waitForLoginFormLoad();

        TestRailAssert.assertTrue(
                (Pages.loginPage().loginFormIsDisplayed()),
                new CustomStepResult(
                        "Страница недоступна",
                        "Отображается открытая страница с формой авторизации")
        );

         Pages.loginPage().typeEmail(email);

        TestRailAssert.assertTrue(
                email.trim().equals(Pages.loginPage().getEmailText().trim()),
                new CustomStepResult(
                        "Введенные данные не отображается в поле в 'E-mail'",
                        "Введенные данные отображается в поле в 'E-mail'"
                )
        );

         Pages.loginPage().typePassword(password);

        TestRailAssert.assertTrue(
                (password.length()) == (Pages.loginPage().getPasswordText().length()),
                new CustomStepResult(
                        "Введенные данные не отображается в поле в 'Password'",
                        "Введенные данные отображается в поле в 'Password'"
                )
        );

         Pages.loginPage().clickOnLoginButton();

        String expectedPageTitle = "DataTile";

        TestRailAssert.assertTrue(
                expectedPageTitle.equals(Actions.loginActions().getPageTitle()),
                new CustomStepResult(
                        "Пользователь залогинился не успешно или тайтл страницы '" + expectedPageTitle +
                                "' во вкладке браузера не отображается",
                        "Пользователь успешно залогинился. Тайтл страницы во вкладке браузера: " +
                                expectedPageTitle + ""
                )
        );
     }

    public String getPageTitle() {
        BaseActions.wait(2);
        String title = driver().getTitle();
        Reporter.log("Page title is " + title);
        return title;
    }
}
