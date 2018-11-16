package guru.springfamework.api.v1.config;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                //.pathMapping("/")
                .apiInfo(this.metadata());
    }

    private ApiInfo metadata() {
        Contact contactInfo = new Contact("Carlos", "", "krlosh@gmail.com");

        return  new ApiInfo("Spring Framework Guru",
                "Spring Framework 5: Beginner to Guru",
                "1.0",
                "Terms of Service: blah",contactInfo,"Apache 2.0", "https://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>());
    }
}
