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
package org.incendo.kitchensink.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.minecraft.extras.ComponentCaptionFormatter;
import org.incendo.kitchensink.command.KitchenSinkCommandSender;
import org.incendo.kitchensink.configuration.StyleConfiguration;

/**
 * Caption bindings.
 */
public final class CaptionModule extends AbstractModule {

    /**
     * Creates a new MiniMessage instance.
     *
     * @param styleConfiguration style configuration
     * @return the instance
     */
    @Provides
    public @NonNull MiniMessage miniMessage(final StyleConfiguration styleConfiguration) {
        return MiniMessage.builder()
                .tags(TagResolver.builder()
                        .tag("color_primary", Tag.styling(builder -> builder.color(styleConfiguration.primaryColor())))
                        .tag("color_accent", Tag.styling(builder -> builder.color(styleConfiguration.accentColor())))
                        .tag("color_highlight", Tag.styling(builder -> builder.color(styleConfiguration.highlightColor())))
                        .tag("color_error", Tag.styling(builder -> builder.color(styleConfiguration.errorColor())))
                        .tag("color_warning", Tag.styling(builder -> builder.color(styleConfiguration.warningColor())))
                        .tag("prefix", Tag.inserting(MiniMessage.miniMessage().deserialize(styleConfiguration.prefix())))
                        .build())
                .build();
    }

    /**
     * Creates a new caption formatter.
     *
     * @param miniMessage MiniMessage instance
     * @return the formatter
     */
    @Provides
    public @NonNull ComponentCaptionFormatter<KitchenSinkCommandSender> captionFormatter(final @NonNull MiniMessage miniMessage) {
        return ComponentCaptionFormatter.miniMessage(miniMessage);
    }
}
