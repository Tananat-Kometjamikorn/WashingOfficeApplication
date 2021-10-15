package th.ac.ku.app.config;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.client.RestTemplate;
import th.ac.ku.app.models.UserBranch;
import th.ac.ku.app.service.CustomRestTemplateCustomizer;
import th.ac.ku.app.service.WashingOrderServiceAPI;

@Configuration
public class ComponentConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public WashingOrderServiceAPI service (RestTemplate restTemplate ){
        return new WashingOrderServiceAPI(restTemplate);
    }

    @Bean
    public UserBranch userBranch(){
        return new UserBranch();
    }

    @Bean
    public CustomRestTemplateCustomizer customRestTemplateCustomizer() {
        return new CustomRestTemplateCustomizer();
    }

    @Bean
    @DependsOn(value = {"customRestTemplateCustomizer"})
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder(customRestTemplateCustomizer());
    }
}
