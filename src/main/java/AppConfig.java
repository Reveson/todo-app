import org.aeonbits.owner.Config;

import java.util.Locale;

//@Config.Sources({"../resources/AppConfig.properties"})
public interface AppConfig extends Config {
    @DefaultValue("800")
    int windowWidth();
    @DefaultValue("600")
    int windowHeight();
    @DefaultValue("en")
    String language();
    @DefaultValue("US")
    String country();
}
