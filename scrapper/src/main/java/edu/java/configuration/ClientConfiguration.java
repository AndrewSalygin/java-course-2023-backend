package edu.java.configuration;

import edu.java.configuration.supplier.GithubConfig;
import edu.java.configuration.supplier.StackOverflowConfig;
import edu.java.supplier.github.GithubInfoSupplier;
import edu.java.supplier.stackoverflow.StackOverflowInfoSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class ClientConfiguration {
    @Bean
    public GithubInfoSupplier githubInfoSupplier(GithubConfig config) {
        return new GithubInfoSupplier(config);
    }

    @Bean
    public StackOverflowInfoSupplier stackOverflowInfoSupplier(StackOverflowConfig config) {
        return new StackOverflowInfoSupplier(config);
    }
}
