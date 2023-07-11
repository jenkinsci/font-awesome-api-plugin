package io.jenkins.plugins.fontawesome;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jenkins.ui.symbol.Symbol;
import org.jenkins.ui.symbol.SymbolRequest.Builder;

import j2html.tags.UnescapedText;

/**
 * Textual SVG tag representation that shows a FontAwesome icon.
 *
 * @author Ullrich Hafner
 */
public class SvgTag {
    private static final String SPACE = " ";
    private final Builder builder;

    /** Available Font Awesome styles. */
    public enum FontAwesomeStyle {
        SOLID,
        REGULAR,
        BRANDS
    }

    private static final String ICON_MD = "icon-md";
    private static final String FONT_AWESOME_API = "font-awesome-api";

    /**
     * Creates a new {@link SvgTag} that renders the specified SVG icon of FontAwesome using
     * {@link FontAwesomeStyle#SOLID}.
     *
     * @param iconName
     *         the name of the icon (without fa- prefix), e.g. {@code chevron-circle-up}.
     *
     * @return the HTML tag
     */
    public static SvgTag fontAwesomeSvgIcon(final String iconName) {
        return new SvgTag(iconName);
    }

    /**
     * Creates a new {@link SvgTag} that renders the specified SVG icon of FontAwesome.
     *
     * @param iconName
     *         the name of the icon (without fa- prefix), e.g. {@code chevron-circle-up}.
     * @param style
     *         Font Awesome style of the icon
     *
     * @return the HTML tag
     */
    public static SvgTag fontAwesomeSvgIcon(final String iconName, final FontAwesomeStyle style) {
        return new SvgTag(iconName, style);
    }

    /**
     * Creates a new {@link SvgTag} that renders the specified SVG icon of FontAwesome using
     * {@link FontAwesomeStyle#SOLID}.
     *
     * @param iconName
     *         the name of the icon (without fa- prefix), e.g. {@code chevron-circle-up}.
     */
    public SvgTag(final String iconName) {
        this(iconName, FontAwesomeStyle.SOLID);
    }

    /**
     * Creates a new {@link SvgTag} that renders the specified SVG icon of FontAwesome.
     *
     * @param iconName
     *         the name of the icon (without fa- prefix), e.g. {@code chevron-circle-up}.
     * @param style
     *         Font Awesome style of the icon
     */
    public SvgTag(final String iconName, final FontAwesomeStyle style) {
        builder = new Builder()
                .withName(style.name().toLowerCase(Locale.ENGLISH) + "/" + iconName)
                .withPluginName(FONT_AWESOME_API)
                .withClasses(ICON_MD);
    }

    /**
     * Uses the specified class names for this SVG tag. Adds the tag {@link #ICON_MD} as well as class.
     *
     * @param classNames
     *         the additional class names, might be empty
     *
     * @return this tag
     */
    public SvgTag withClasses(final String... classNames) {
        builder.withClasses(join(Arrays.asList(classNames)) + SPACE + ICON_MD);

        return this;
    }

    private String join(final List<String> classes) {
        return String.join(SPACE, classes);
    }

    /**
     * Renders this tag into plain HTML String representation.
     *
     * @return the tag as HTML String
     */
    public String render() {
        String symbol = Symbol.get(builder.build());

        return new UnescapedText(symbol).render();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(builder);
    }
}
