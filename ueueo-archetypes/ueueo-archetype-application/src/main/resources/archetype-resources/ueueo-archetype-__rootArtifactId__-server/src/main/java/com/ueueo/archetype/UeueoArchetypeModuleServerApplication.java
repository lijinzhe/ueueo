#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.archetype;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UeueoArchetypeModuleServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UeueoArchetypeModuleServerApplication.class, args);
    }
}
