package io.jenkins.plugins.fontawesome;

import edu.umd.cs.findbugs.annotations.CheckForNull;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Utility to work with icons provided by the font-awesome-api-plugin.
 *
 * @author strangelookingnerd
 */
public final class FontAwesomeIcons {

    private static final Logger LOGGER = Logger.getLogger(FontAwesomeIcons.class.getName());
    private static final String SVG_FILE_ENDING = ".svg";
    private static final String IMAGES_SYMBOLS_PATH = "images/symbols/";
    private static final String FONT_AWESOME_API_PLUGIN = "font-awesome-api";
    private static final String ICON_CLASS_NAME_PATTERN = "symbol-%s plugin-" + FONT_AWESOME_API_PLUGIN;

    /**
     * Takes an icon name and generates an icon class name from it.
     *
     * @param icon the icon name
     * @return the icon class name
     */
    public static String getIconClassName(final String icon) {
        return String.format(ICON_CLASS_NAME_PATTERN, icon);
    }

    /**
     * Get all available icons provided by the font-awesome-api-plugin.
     *
     * @return a sorted map of available icons with icon name as key and the icon
     * class name as value.
     */
    public static Map<String, String> getAvailableIcons() {
        return getAvailableIcons(null);
    }

    /**
     * Get all available icons provided by the font-awesome-api-plugin filtered by their style.
     *
     * @param filter the style to filter
     * @return a sorted map of available icons with icon name as key and the icon
     * class name as value filtered by the given style.
     */
    public static Map<String, String> getAvailableIcons(final SvgTag.FontAwesomeStyle filter) {
        return getIconsFromClasspath(filter);
    }

    private static Map<String, String> getIconsFromClasspath(final SvgTag.FontAwesomeStyle filter) {
        try {
            Enumeration<URL> urls = FontAwesomeIcons.class.getClassLoader().getResources(IMAGES_SYMBOLS_PATH);

            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();

                if (StringUtils.contains(url.toExternalForm(), FONT_AWESOME_API_PLUGIN)) {
                    URI uri = url.toURI();

                    if (StringUtils.equals(uri.getScheme(), "jar")) {
                        try (FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap())) {
                            return collectIcons(fileSystem.getPath(IMAGES_SYMBOLS_PATH), filter);
                        }
                    }
                    else {
                        return collectIcons(Paths.get(uri), filter);
                    }
                }
            }
        }
        catch (IOException | URISyntaxException ex) {
            LOGGER.log(Level.WARNING, "Unable to read available icons: Resource unavailable.", ex);
        }

        return new LinkedHashMap<>();
    }

    private static Map<String, String> collectIcons(final Path path,  @CheckForNull final SvgTag.FontAwesomeStyle filter) throws IOException {
        Map<String, String> icons = new LinkedHashMap<>();

        if (path != null) {
            try (Stream<Path> stream = filter == null ? Files.walk(path, 2) : Files.walk(path.resolve(filter.name().toLowerCase(Locale.ENGLISH)), 1)) {
                stream.filter(icon -> icon != null && icon.getFileName() != null && StringUtils.endsWith(icon.getFileName().toString(), SVG_FILE_ENDING))
                        .sorted().forEach(icon -> {
                            if (icon.getParent() != null && icon.getParent().getFileName() != null && icon.getFileName() != null) {
                                String iconName = icon.getParent().getFileName() + "/" + StringUtils.removeEnd(icon.getFileName().toString(), SVG_FILE_ENDING);
                                icons.put(iconName, getIconClassName(iconName));
                            }
                        }
                        );
            }
        }

        return icons;
    }

    private FontAwesomeIcons() {
        // hidden
    }

}
