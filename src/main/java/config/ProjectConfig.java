package config;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:dev_env.properties"})
public interface ProjectConfig extends Config {
    @Key("url")
    String url();

    @Key("OldPHPurl")
    String OldPHPurl();

    @Key("APIUrl")
    String APIUrl();

    @Key("adminUsername")
    String adminUsername();

    @Key("appsmartEmployeeUsername")
    String appsmartEmployeeUsername();

    @Key("password")
    String password();

    @Key("headless")
    Boolean headless();

    @Key("branchIdPizzaEPasta")
    String branchIdPizzaEPasta();

    @Key("keyCloakURL")
    String keyCloakURL();

    @Key("keyCloakClientId")
    String keyCloakClientId();
}