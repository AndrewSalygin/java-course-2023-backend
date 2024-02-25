package edu.java.supplier.github;

import edu.java.configuration.supplier.GithubConfig;
import edu.java.supplier.api.LinkInfo;
import edu.java.supplier.api.WebClientInfoSupplier;
import java.net.URL;
import java.util.regex.Pattern;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class GithubInfoSupplier extends WebClientInfoSupplier {

    private static final String TYPE_SUPPLIER = "github";

    private final Pattern repositoryPattern;

    public GithubInfoSupplier(
        GithubConfig config
    ) {
        super(config.url());
        repositoryPattern = Pattern.compile(config.patterns().repository());
    }

    @Override
    public LinkInfo fetchInfo(URL url) {
        Pattern pattern = supportedPattern(url);
        if (pattern == null) {
            return null;
        }

        GithubRepoInfo info = null;
        if (pattern == repositoryPattern) {
            info = executeRequestGet("repos" + url.getPath(), GithubRepoInfo.class, GithubRepoInfo.EMPTY);
        }
        if (info == null) {
            return null;
        }
        return new LinkInfo(url, info.title(), info.lastUpdate());
    }

    @Override
    public Pattern supportedPattern(URL url) {
        if (repositoryPattern.matcher(url.toString()).matches()) {
            return repositoryPattern;
        }
        return null;
    }
}
