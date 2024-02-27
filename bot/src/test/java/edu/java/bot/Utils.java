package edu.java.bot;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;

public class Utils {
//
//    public static UpdateWrapper createMockUpdateWrapper() {
//        UpdateWrapper update = Mockito.mock(UpdateWrapper.class);
//        MessageWrapper message = Mockito.mock(MessageWrapper.class);
//
//        Mockito.when(update.message()).thenReturn(message);
//        return update;
//    }
//
//    public static UpdateWrapper createMockUpdateWrapper(String text) {
//        UpdateWrapper update = Mockito.mock(UpdateWrapper.class);
//        MessageWrapper message = Mockito.mock(MessageWrapper.class);
//
//        Mockito.when(message.text()).thenReturn(text);
//        Mockito.when(update.message()).thenReturn(message);
//        return update;
//    }
//
//    public static UpdateWrapper createMockUpdateWrapper(String text, Long chatId) {
//        UpdateWrapper update = Mockito.mock(UpdateWrapper.class);
//        MessageWrapper message = Mockito.mock(MessageWrapper.class);
//        ChatWrapper chat = Mockito.mock(ChatWrapper.class);
//
//        Mockito.when(message.text()).thenReturn(text);
//        Mockito.when(message.chat()).thenReturn(chat);
//        Mockito.when(message.chat().id()).thenReturn(chatId);
//        Mockito.when(update.message()).thenReturn(message);
//        return update;
//    }
//
    public static ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSourceResourceBundle = new ResourceBundleMessageSource();
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        yamlPropertiesFactoryBean.setResources(new ClassPathResource("messagesTest.yml"));
        messageSourceResourceBundle.setCommonMessages(yamlPropertiesFactoryBean.getObject());
        return messageSourceResourceBundle;
    }
}
