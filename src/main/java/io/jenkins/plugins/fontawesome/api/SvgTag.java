package io.jenkins.plugins.fontawesome.api;

import java.util.Arrays;

import j2html.tags.ContainerTag;

import io.jenkins.plugins.util.JenkinsFacade;

/**
 * Textual SVG tag representation that shows a FontAwesome icon.
 *
 * @author Ullrich Hafner
 */
public class SvgTag {
    private static final String SVG_ICON = "fa-svg-icon";
    private static final String ICON_PREFIX = "/plugin/font-awesome-api/webjars/sprites/solid.svg#";

    private ContainerTag container;

    /**
     * Creates a new {@link SvgTag} that renders the specified SVG icon of FontAwesome.
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
     */
    public SvgTag(final String iconName) {
        this(iconName, new JenkinsFacade());
    }

    /**
     * Creates a new {@link SvgTag} that renders the specified SVG icon of FontAwesome.
     *
     * @param iconName
     *         the name of the icon (without fa- prefix), e.g. {@code chevron-circle-up}.
     * @param jenkinsFacade
     *         Jenkins facade to replaced with a stub during unit tests
     */
    public SvgTag(final String iconName, final JenkinsFacade jenkinsFacade) {
        container = new ContainerTag("svg")
                .withClasses(SVG_ICON)
                .with(use().withHref(jenkinsFacade.getImagePath(ICON_PREFIX + iconName)));
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
        String[] actualClasses = Arrays.copyOf(classNames, classNames.length + 1);
        actualClasses[classNames.length] = "svg-icon";
        container.withClasses(actualClasses);

        return this;
    }

    private static ContainerTag use() {
        return new ContainerTag("use");
    }

    /**
     * Renders this tag into plain HTML String representation.
     *
     * @return the tag as HTML String
     */
    public String render() {
        return container.render();
    }
}
