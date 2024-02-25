package edu.java.bot.configuration;

import edu.java.bot.wrapper.TelegramBotWrapper;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class BeansConfiguration {
    @Bean
    public TelegramBotWrapper telegramBot(ApplicationConfig config) {
        return new TelegramBotWrapper(config.telegramToken());
    }

    @Bean
    public ResourceBundleMessageSource messageSourceResourceBundle() {
        ResourceBundleMessageSource messageSourceResourceBundle = new ResourceBundleMessageSource();
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        yamlPropertiesFactoryBean.setResources(new ClassPathResource("messages.yml"));
        messageSourceResourceBundle.setCommonMessages(yamlPropertiesFactoryBean.getObject());
        return messageSourceResourceBundle;
    }
}
