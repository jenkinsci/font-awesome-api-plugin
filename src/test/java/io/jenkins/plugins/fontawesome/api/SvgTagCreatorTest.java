package io.jenkins.plugins.fontawesome.api;

import org.junit.jupiter.api.Test;

import io.jenkins.plugins.util.JenkinsFacade;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests the class {@link SvgTag}.
 *
 * @author Ullrich Hafner
 */
class SvgTagCreatorTest {
    @Test
    void shouldCreateIcon() {
        JenkinsFacade jenkins = mock(JenkinsFacade.class);
        when(jenkins.getImagePath(anyString())).thenReturn("path");

        assertThat(new SvgTag("hello", jenkins).render())
                .isEqualTo("<svg class=\"fa-svg-icon\"><use href=\"path\"></use></svg>");
    }
}
