package io.jenkins.plugins.fontawesome;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SvgTagTest {
    @Test
    void shouldCreateTag() {
        var tag = new SvgTag("left");

        assertThat(tag.toString()).contains("classes=icon-md", "name=solid/left", "pluginName=font-awesome-api");

        tag.withClasses("additional");
        assertThat(tag.toString()).contains("classes=additional icon-md");

        tag.withClasses("one two");
        assertThat(tag.toString()).contains("classes=one two icon-md");
    }
}
