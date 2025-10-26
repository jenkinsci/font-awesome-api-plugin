package io.jenkins.plugins.fontawesome;

import org.apache.commons.lang3.Strings;

import edu.umd.cs.findbugs.annotations.CheckForNull;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.jenkins.plugins.fontawesome.SvgTag.FontAwesomeStyle;

/**
 * Utility to work with icons provided by the font-awesome-api-plugin.
 *
 * @author strangelookingnerd
 */
public final class FontAwesomeIcons {
    private static final String SVG_FILE_ENDING = ".svg";
    private static final String IMAGES_SYMBOLS_PATH = "images/symbols/";
    private static final String FONT_AWESOME_API_PLUGIN = "font-awesome-api";
    private static final String ICON_CLASS_NAME_PATTERN = "symbol-%s plugin-" + FONT_AWESOME_API_PLUGIN;

    /**
     * Takes an icon name and generates an icon class name from it.
     *
     * @param icon
     *         the icon name
     *
     * @return the icon class name
     */
    public static String getIconClassName(final String icon) {
        return String.format(ICON_CLASS_NAME_PATTERN, icon);
    }

    /**
     * Get all available icons provided by the font-awesome-api-plugin.
     *
     * @return a sorted map of available icons with icon name as key and the icon class name as value.
     */
    public static Map<String, String> getAvailableIcons() {
        return getIconsFromClasspath(null);
    }

    /**
     * Get all available icons provided by the font-awesome-api-plugin filtered by their style.
     *
     * @param filter
     *         the style to filter
     *
     * @return a sorted map of available icons with icon name as key and the icon class name as value filtered by the
     *         given style.
     */
    public static Map<String, String> getAvailableIcons(@CheckForNull final FontAwesomeStyle filter) {
        return getIconsFromClasspath(filter);
    }

    private static Map<String, String> getIconsFromClasspath(@CheckForNull final FontAwesomeStyle filter) {
        try {
            Enumeration<URL> urls = FontAwesomeIcons.class.getClassLoader().getResources(IMAGES_SYMBOLS_PATH);

            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();

                if (Strings.CS.contains(url.toExternalForm(), FONT_AWESOME_API_PLUGIN)) {
                    URI uri = url.toURI();

                    if (Strings.CS.contains(uri.getScheme(), "jar")) {
                        try (FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap())) {
                            return filterIcons(fileSystem.getPath(IMAGES_SYMBOLS_PATH), filter);
                        }
                    }
                    return filterIcons(Paths.get(uri), filter);
                }
            }
        }
        catch (IOException | URISyntaxException exception) {
            throw new IllegalStateException("Unable to read available icons: Resource unavailable.", exception);
        }
        throw new IllegalStateException("Unable to find icons: Resource unavailable.");
    }

    private static Map<String, String> filterIcons(final Path iconFolder, @CheckForNull final FontAwesomeStyle filter) throws IOException {
        return createIconStream(iconFolder, filter)
                .filter(Objects::nonNull)
                .filter(icon -> icon.getFileName() != null)
                .filter(FontAwesomeIcons::isSvgImage)
                .filter(icon -> icon.getParent() != null)
                .filter(icon -> icon.getParent().getFileName() != null)
                .sorted()
                .map(FontAwesomeIcons::createFileName)
                .collect(Collectors.toMap(icon -> icon, FontAwesomeIcons::getIconClassName));
    }

    private static Stream<Path> createIconStream(final Path iconFolder, @CheckForNull final FontAwesomeStyle filter) throws IOException {
        if (filter == null) {
            return Files.walk(iconFolder, 2);
        }
        return Files.walk(iconFolder.resolve(filter.name().toLowerCase(Locale.ENGLISH)), 1);
    }

    private static String createFileName(final Path icon) {
        return icon.getParent().getFileName() + "/"
                + Strings.CS.removeEnd(icon.getFileName().toString(), SVG_FILE_ENDING);
    }

    private static boolean isSvgImage(final Path path) {
        return Strings.CS.endsWith(path.getFileName().toString(), SVG_FILE_ENDING);
    }

    private FontAwesomeIcons() {
        // hidden
    }
}
