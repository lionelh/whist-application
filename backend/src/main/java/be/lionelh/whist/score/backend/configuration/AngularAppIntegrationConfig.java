package be.lionelh.whist.score.backend.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * This configuration is used to integrate the angular app as a webjar in the spring boot application
 */
@Configuration
public class AngularAppIntegrationConfig implements WebMvcConfigurer {

    @Value("${frontend.version}")
    private String version;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/whist-score-frontend/" + this.version + "/")
                .resourceChain(true);
    }
}
