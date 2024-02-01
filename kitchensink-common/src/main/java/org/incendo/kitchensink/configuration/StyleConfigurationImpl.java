//
// MIT License
//
// Copyright (c) 2024 Incendo
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
//
package org.incendo.kitchensink.configuration;

import java.util.Locale;
import java.util.Objects;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;

public final class StyleConfigurationImpl implements StyleConfiguration {

    private final ConfigurationNode configurationNode;

    /**
     * Creates a new style configuration instance.
     *
     * @param configurationNode node which contains the configured values
     */
    public StyleConfigurationImpl(final @NonNull ConfigurationNode configurationNode) {
        this.configurationNode = Objects.requireNonNull(configurationNode, "configurationNode");
    }

    @Override
    public @NonNull String prefix() {
        return Objects.requireNonNull(this.configurationNode.node("prefix").getString(), "prefix");
    }

    @Override
    public @NonNull TextColor primaryColor() {
        return Objects.requireNonNull(readColor(this.configurationNode.node("primary")), "primary");
    }

    @Override
    public @NonNull TextColor accentColor() {
        return Objects.requireNonNull(readColor(this.configurationNode.node("accent")), "accent");
    }

    @Override
    public @NonNull TextColor highlightColor() {
        return Objects.requireNonNull(readColor(this.configurationNode.node("highlight")), "highlight");
    }

    @Override
    public @NonNull TextColor errorColor() {
        return Objects.requireNonNull(readColor(this.configurationNode.node("error")), "error");
    }

    @Override
    public @NonNull TextColor warningColor() {
        return Objects.requireNonNull(readColor(this.configurationNode.node("warning")), "warning");
    }

    private static @Nullable TextColor readColor(final @NonNull ConfigurationNode node) {
        final String color = node.getString();
        if (color == null) {
            return null;
        }
        if (color.startsWith("#")) {
            return TextColor.fromCSSHexString(color);
        }
        return NamedTextColor.NAMES.value(color.toLowerCase(Locale.ROOT));
    }
}
