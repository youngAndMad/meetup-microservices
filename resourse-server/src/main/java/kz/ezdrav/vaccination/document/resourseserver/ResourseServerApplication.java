package kz.ezdrav.vaccination.document.resourseserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@SpringBootApplication
@RestController
public class ResourseServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResourseServerApplication.class, args);
    }

    @GetMapping("sum")
    ResponseEntity<?> isEven(
            @RequestParam Long param1,
            @RequestParam Long param2
    ) {
        var random = new Random().nextInt(10);
        ;

        System.out.printf("random number for param1 %d param2 %d is %d%n", param1, param2, random);
        var sum = param1 + param2 + random;

        if (sum % 2 != 0) {
            throw new IllegalStateException("Sum is not an even number sum=" + sum);
        }

        return ResponseEntity.ok("Cool, sum is even: %d, random number from server  %d".formatted(sum, random));
    }

}
