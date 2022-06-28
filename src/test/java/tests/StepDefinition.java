package tests;

import config.ProjectConfig;
import config.UserConfig;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class StepsToReproduce {

    private WebDriver driver;
    private static final ProjectConfig config = ConfigFactory.create(ProjectConfig.class);
    private static final UserConfig configUsers = ConfigFactory.create(UserConfig.class);
    private static String baseURI = config.baseURI();
    private static String username = configUsers.username();

    @Before
    public void before() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
    }

    @Given("I go to login page")
    public void i_go_to_login_page() {
        driver.get(baseURI + "/admin/login");
    }

    @When("I enter credentials and submit form")
    public void i_enter_credentials_and_submit_form() {
        login();
    }

    @Then("I see admin dashboard")
    public void i_see_admin_dashboard() {
        assertTrue(driver.findElement(By.id("header-logo")).isDisplayed());
    }


    @Given("I'm logged in")
    public void i_am_logged_in() {
        login();
    }

    @When("I go to players page")
    public void i_go_to_players_page() {
        driver.get("http://test-app.d6.dev.devcaz.com/user/player/admin");
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.className("page")));
    }

    @Then("I see a list of players")
    public void i_see_a_list_of_players() {
        assertTrue(driver.findElement(By.id("payment-system-transaction-grid")).isDisplayed());
    }


    @Given("I'm logged on players page")
    public void i_m_logged_on_players_page() {
        login();
        driver.get("http://test-app.d6.dev.devcaz.com/user/player/admin");
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.className("page")));
    }

    @When("I click sort by registration")
    public void i_click_sort_by_registration() throws InterruptedException {
        driver.findElement(By.id("payment-system-transaction-grid_c9")).click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.invisibilityOfElementLocated(By.className("grid-view-loading")));
    }

    @Then("Users sorted right")
    public void users_sorted_right() {
        List<WebElement> rows = driver.findElements(By.xpath("//*[@id=\"payment-system-transaction-grid\"]/table/tbody/tr"));

        DateTimeFormatter f = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
        LocalDateTime currentTime = LocalDateTime.parse("1970-01-01 00:00:00", f);

        for (WebElement row : rows) {
            WebElement cell = row.findElement(By.cssSelector("td:nth-child(10)"));
            LocalDateTime time = LocalDateTime.parse(cell.getText(), f);
            assertTrue(currentTime.isBefore(time));
            currentTime = time;
        }
    }

    private void login() {
        if (!Objects.equals(driver.getCurrentUrl(), "http://test-app.d6.dev.devcaz.com/admin/login")) {
            driver.get("http://test-app.d6.dev.devcaz.com/admin/login");
        }
        driver.findElement(By.id("UserLogin_username")).sendKeys("admin1");
        driver.findElement(By.id("UserLogin_password")).sendKeys("[9k<k8^z!+$$GkuP");
        driver.findElement(By.tagName("form")).submit();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.id("header-logo")));
    }

    @After
    public void after() {
        driver.quit();
    }
}
