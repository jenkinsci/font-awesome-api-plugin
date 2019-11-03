import java.io.IOException;
import java.util.logging.Level;

import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.JenkinsRule.WebClient;
import org.xml.sax.SAXException;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.TextPage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import io.jenkins.plugins.fontawesome.api.FontAwesomeApi;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests the class {@link FontAwesomeApi}.
 *
 * @author Ullrich Hafner
 */
public class FontAwesomeApiITest {
    /** Jenkins rule per test. */
    @Rule
    public final JenkinsRule jenkins = new JenkinsRule();

    @SuppressFBWarnings(value = "LG", justification = "Setting the logger here helps to clean up the console log for tests")
    JenkinsRule.WebClient createWebClient() {
        WebClient webClient = jenkins.createWebClient();
        webClient.setCssErrorHandler(new SilentCssErrorHandler());
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.SEVERE);
        webClient.setIncorrectnessListener((s, o) -> {
        });

        webClient.setJavaScriptEnabled(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getCookieManager().setCookiesEnabled(false);
        webClient.getOptions().setCssEnabled(false);

        webClient.getOptions().setDownloadImages(false);
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setPrintContentOnFailingStatusCode(false);

        return webClient;
    }

    /**
     * Returns the HTML page content of the specified URL.
     *
     * @param url
     *         the relative URL within the job
     *
     * @return the HTML page
     */
    private HtmlPage getWebPage(final String url) {
        try {
            return createWebClient().goTo(url);
        }
        catch (SAXException | IOException e) {
            throw new AssertionError(e);
        }
    }

    @Test
    public void shouldProvideCssFiles() throws Exception {
        TextPage result = (TextPage) createWebClient().getPage(jenkins.getURL().toString() + "/plugin/font-awesome-api/font-awesome/css/solid.css");

        assertThat(result.getContent()).isEqualTo("bla");
    }
}
