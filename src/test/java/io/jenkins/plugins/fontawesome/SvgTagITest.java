package io.jenkins.plugins.fontawesome;

import org.junit.jupiter.api.Test;

import io.jenkins.plugins.util.IntegrationTestWithJenkinsPerSuite;

import static org.assertj.core.api.Assertions.*;

class SvgTagITest extends IntegrationTestWithJenkinsPerSuite {
    @Test
    void shouldCreateTag() {
        var tag = new SvgTag("tag");

        assertThat(tag.toString()).contains("classes=icon-md", "name=solid/tag", "pluginName=font-awesome-api");

        tag.withClasses("additional");
        assertThat(tag.toString()).contains("classes=additional icon-md");

        tag.withClasses("one two");
        assertThat(tag.toString()).contains("classes=one two icon-md");

        tag.withClasses("one two");
        assertThat(tag.render()).contains("class=\"one two icon-md\"", "xmlns=\"http://www.w3.org/2000/svg\"",
                "https://fontawesome.com ", "<svg ", "</svg>");
    }
}
