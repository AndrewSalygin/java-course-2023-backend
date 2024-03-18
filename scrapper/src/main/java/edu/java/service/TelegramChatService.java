package edu.java.service;


public interface TelegramChatService {

    void registerChat(Long tgChatId);

    void deleteChat(Long tgChatId);
}
