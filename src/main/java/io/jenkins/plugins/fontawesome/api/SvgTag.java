package io.jenkins.plugins.fontawesome.api;

import java.util.Arrays;
import java.util.Locale;

import org.jenkins.ui.symbol.Symbol;
import org.jenkins.ui.symbol.SymbolRequest.Builder;

import j2html.tags.UnescapedText;

import io.jenkins.plugins.util.JenkinsFacade;

/**
 * Textual SVG tag representation that shows a FontAwesome icon.
 *
 * @author Ullrich Hafner
 */
public class SvgTag {
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
     * Creates a new {@link SvgTag} that renders the specified SVG icon of FontAwesome.
     *
     * @param iconName
     *         the name of the icon (without fa- prefix), e.g. {@code chevron-circle-up}.
     * @param jenkinsFacade
     *         Jenkins facade to replaced with a stub during unit tests
     * @deprecated use {@link #SvgTag(String)} instead
     */
    @Deprecated
    public SvgTag(final String iconName, @SuppressWarnings("unused") final JenkinsFacade jenkinsFacade) {
        this(iconName);
    }

    /**
     * Creates a new {@link SvgTag} that renders the specified SVG icon of FontAwesome.
     *
     * @param iconName
     *         the name of the icon (without fa- prefix), e.g. {@code chevron-circle-up}.
     * @param jenkinsFacade
     *         Jenkins facade to replaced with a stub during unit tests
     * @param style
     *         Font Awesome style of the icon
     * @deprecated use {@link #SvgTag(String, FontAwesomeStyle)} instead
     */
    @Deprecated
    public SvgTag(final String iconName, @SuppressWarnings("unused") final JenkinsFacade jenkinsFacade, final FontAwesomeStyle style) {
        this(iconName, style);
    }

    /**
     * Uses the specified class names for this SVG tag. Adds the tag {@code svg-icon} as well as class.
     *
     * @param classNames
     *         the additional class names, might be empty
     *
     * @return this tag
     */
    public SvgTag withClasses(final String... classNames) {
        builder.withClasses(String.join(" ", Arrays.asList(classNames)));

        return this;
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
}
