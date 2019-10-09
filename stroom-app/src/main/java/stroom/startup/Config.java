package stroom.startup;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import stroom.annotation.impl.db.AnnotationDbConfig;
import stroom.proxy.guice.ProxyConfig;

public class Config extends Configuration {
    private String mode;
    private AnnotationDbConfig annotationDbConfig;
    private ProxyConfig proxyConfig;
    private String externalConfig = "~/.stroom/stroom.conf";

    @JsonProperty
    public String getMode() {
        return mode;
    }

    @JsonProperty
    public void setMode(final String mode) {
        this.mode = mode;
    }

    @JsonProperty
    public ProxyConfig getProxyConfig() {
        return proxyConfig;
    }

    @JsonProperty
    public void setProxyConfig(final ProxyConfig proxyConfig) {
        this.proxyConfig = proxyConfig;
    }

    @JsonProperty("annotationConfig")
    public AnnotationDbConfig getAnnotationDbConfig() {
        return annotationDbConfig;
    }

    @JsonProperty("annotationConfig")
    public void setAnnotationDbConfig(final AnnotationDbConfig annotationDbConfig) {
        this.annotationDbConfig = annotationDbConfig;
    }

    @JsonProperty
    public String getExternalConfig() {
        return externalConfig;
    }

    @JsonProperty
    public void setExternalConfig(final String externalConfig) {
        this.externalConfig = externalConfig;
    }

}
