package edu.java.scrapper.supplier.github;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.configuration.supplier.GithubConfig;
import edu.java.configuration.supplier.GithubPatternConfig;
import edu.java.supplier.api.LinkInfo;
import edu.java.supplier.github.GithubInfoSupplier;
import java.net.URI;
import java.time.OffsetDateTime;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class GithubInfoSupplierTest {
    private static WireMockServer server;

    @BeforeAll
    public static void setUp() {
        server = new WireMockServer(wireMockConfig().dynamicPort());
        server.stubFor(get(urlPathMatching("/repos/AndrewSalygin/java-course-2023-backend"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("""
                    {
                        "full_name": "AndrewSalygin/java-course-2023-backend",
                        "updated_at": "2024-02-25T07:35:50Z"
                    }
                    """)));
        server.stubFor(get(urlPathMatching("/repos/test/test"))
            .willReturn(aResponse()
                .withStatus(404)));
        server.start();
    }

    @SneakyThrows
    @Test
    public void returnRightInformationTest() {
        GithubPatternConfig githubPatternConfig = Mockito.mock(GithubPatternConfig.class);
        Mockito.when(githubPatternConfig.repository()).thenReturn("https://github.com/(.+)/(.+)");
        GithubConfig config = new GithubConfig(server.baseUrl(), githubPatternConfig);


        GithubInfoSupplier supplier = new GithubInfoSupplier(config);
        LinkInfo info = supplier.fetchInfo(
            new URI("https://github.com/AndrewSalygin/java-course-2023-backend").toURL()
        );
        Assertions.assertThat(info)
            .extracting(LinkInfo::url, LinkInfo::title, LinkInfo::lastUpdate)
            .contains(
                new URI("https://github.com/AndrewSalygin/java-course-2023-backend").toURL(),
                "AndrewSalygin/java-course-2023-backend",
                OffsetDateTime.parse("2024-02-25T07:35:50Z")
            );
    }

    @SneakyThrows
    @Test
    public void returnNullInformationWhenRepositoryWrongTest() {
        GithubPatternConfig githubPatternConfig = Mockito.mock(GithubPatternConfig.class);
        Mockito.when(githubPatternConfig.repository()).thenReturn("https://github.com/wrongURL");
        GithubConfig config = new GithubConfig(server.baseUrl(), githubPatternConfig);


        GithubInfoSupplier supplier = new GithubInfoSupplier(config);
        LinkInfo info = supplier.fetchInfo(
            new URI("https://github.com/AndrewSalygin/java-course-2023-backend").toURL()
        );
        Assertions.assertThat(info).isNull();
    }

    @SneakyThrows
    @Test
    public void returnNullInformationWithWrongUrlTest() {
        GithubPatternConfig githubPatternConfig = Mockito.mock(GithubPatternConfig.class);
        Mockito.when(githubPatternConfig.repository()).thenReturn("https://github.com/(.+)/(.+)");
        GithubConfig config = new GithubConfig(server.baseUrl(), githubPatternConfig);


        GithubInfoSupplier supplier = new GithubInfoSupplier(config);
        LinkInfo info = supplier.fetchInfo(
            new URI("https://github.com/AndrewSalygin/test/test").toURL()
        );
        Assertions.assertThat(info)
            .extracting(LinkInfo::url, LinkInfo::title, LinkInfo::lastUpdate)
            .contains(
                new URI("https://github.com/AndrewSalygin/test/test").toURL(),
                null,
                null
            );
    }
}
