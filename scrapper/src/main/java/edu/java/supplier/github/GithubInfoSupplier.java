package edu.java.supplier.github;

import edu.java.configuration.ApplicationConfig;
import edu.java.configuration.supplier.GithubConfig;
import edu.java.supplier.api.LinkInfo;
import edu.java.supplier.api.WebClientInfoSupplier;
import java.net.URL;
import java.util.regex.Pattern;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Getter
@Component
public class GithubInfoSupplier extends WebClientInfoSupplier {

    private static final String TYPE_SUPPLIER = "github";

    private final Pattern repositoryPattern;

    @Autowired
    public GithubInfoSupplier(GithubConfig githubConfig, ApplicationConfig applicationConfig) {
        super(WebClient.builder()
            .baseUrl(githubConfig.url())
            .defaultHeaders(headers -> {
                if (applicationConfig.githubToken() != null) {
                    headers.set("Authorization", "Bearer " + applicationConfig.githubToken());
                }
            })
            .build()
        );
        repositoryPattern = Pattern.compile(githubConfig.patterns().repository());
    }

    public GithubInfoSupplier(
        GithubConfig githubConfig
    ) {
        super(githubConfig.url());
        repositoryPattern = Pattern.compile(githubConfig.patterns().repository());
    }

    public String getTypeSupplier() {
        return TYPE_SUPPLIER;
    }

    @Override
    public LinkInfo fetchInfo(URL url) {
        GithubRepoInfo info = null;
        if (isSupported(url)) {
            info = executeRequestGet("repos" + url.getPath(), GithubRepoInfo.class, GithubRepoInfo.EMPTY);
        }
        if (info == null) {
            return null;
        }
        return new LinkInfo(url, info.title(), info.lastUpdate());
    }

    @Override
    public boolean isSupported(URL url) {
        Pattern pattern = supportedPattern(url);
        return pattern != null;
    }

    private Pattern supportedPattern(URL url) {
        if (repositoryPattern.matcher(url.toString()).matches()) {
            return repositoryPattern;
        }
        return null;
    }

}
