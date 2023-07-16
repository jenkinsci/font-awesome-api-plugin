package io.jenkins.plugins.fontawesome;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullSource;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test Class for {@link FontAwesomeIcons}.
 *
 * @author strangelookingnerd
 */
class FontAwesomeIconsTest {

    @BeforeAll
    static void init() {
        assertThat(
                Files.exists(Paths.get("./target/classes/images/symbols/")))
                .as("No Font Awesome Symbols found! Try running 'mvn process-resources' first!")
                .isTrue();
    }

    @Test
    void testGetIconClassName() {
        assertThat(FontAwesomeIcons.getIconClassName("test")).isEqualTo("symbol-test plugin-font-awesome-api");
    }

    @ParameterizedTest
    @NullSource
    @EnumSource(SvgTag.FontAwesomeStyle.class)
    void testGetAvailableIconsFiltered(final SvgTag.FontAwesomeStyle style) throws Exception {
        Map<String, String> availableIcons = style == null ? FontAwesomeIcons.getAvailableIcons() : FontAwesomeIcons.getAvailableIcons(style);

        assertThat(availableIcons).isNotNull().isNotEmpty();

        try (Stream<Path> stream = Files
                .walk(Paths.get("./target/classes/images/symbols/" + (style == null ? "" : style.name().toLowerCase(Locale.ENGLISH))), 2)) {

            Set<String> iconNames = stream
                    .filter(path -> StringUtils.endsWith(path.getFileName().toString(), ".svg"))
                    .map(path -> path.getParent().getFileName().toString() + "/" + StringUtils.removeEnd(path.getFileName().toString(), ".svg"))
                    .collect(Collectors.toSet());

            assertThat(availableIcons.keySet()).isEqualTo(iconNames);

            for (Entry<String, String> icon : availableIcons.entrySet()) {
                assertThat(FontAwesomeIcons.getIconClassName(icon.getKey())).isEqualTo(icon.getValue());
            }
        }
    }

}
