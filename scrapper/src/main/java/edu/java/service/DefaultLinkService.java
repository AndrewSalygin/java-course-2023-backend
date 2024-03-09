package edu.java.service;

import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinksResponse;
import edu.java.util.URLCreator;
import java.net.URL;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class DefaultLinkService implements LinkService {
    @Override
    public ListLinksResponse getTrackedLinks(Long tgChatId) {
        log.info("Tracked links for telegram chat id: {}", tgChatId);
        return new ListLinksResponse(
            List.of(new LinkResponse(tgChatId, URLCreator.createURL("https://google.com"))),
            1
        );
    }

    @Override
    public LinkResponse addTrackingLink(URL link, Long tgChatId) {
        log.info("Adding link {} for telegram chat id: {}", link, tgChatId);
        return new LinkResponse(tgChatId, link);
    }

    @Override
    public LinkResponse deleteTrackingLink(URL link, Long tgChatId) {
        log.info("Deleting link {} for telegram chat id: {}", link, tgChatId);
        return new LinkResponse(tgChatId, link);
    }
}
