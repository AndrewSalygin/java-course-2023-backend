package edu.java.bot;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;

public class Utils {
    public static ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSourceResourceBundle = new ResourceBundleMessageSource();
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        yamlPropertiesFactoryBean.setResources(new ClassPathResource("messagesTest.yml"));
        messageSourceResourceBundle.setCommonMessages(yamlPropertiesFactoryBean.getObject());
        return messageSourceResourceBundle;
    }
}
