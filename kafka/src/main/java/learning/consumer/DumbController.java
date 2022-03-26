package learning.consumer;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class DumbController {
    RestTemplate template;

    @GetMapping("/hell")
    public String getHelloFromOtherService(){
        String s = template.getForObject("http://TEMP/", String.class);
        return "Wow! Microservices are cool!";
    }
}
