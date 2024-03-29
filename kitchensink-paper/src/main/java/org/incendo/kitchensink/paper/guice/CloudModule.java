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
package org.incendo.kitchensink.paper.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import java.util.Objects;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.bukkit.CloudBukkitCapabilities;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.minecraft.extras.AudienceProvider;
import org.incendo.cloud.minecraft.extras.MinecraftExceptionHandler;
import org.incendo.cloud.paper.PaperCommandManager;
import org.incendo.cloud.translations.LocaleExtractor;
import org.incendo.cloud.translations.TranslationBundle;
import org.incendo.cloud.translations.bukkit.BukkitTranslationBundle;
import org.incendo.kitchensink.command.KitchenSinkCommandSender;
import org.incendo.kitchensink.configuration.StyleConfiguration;
import org.incendo.kitchensink.paper.PaperKitchenSink;
import org.incendo.kitchensink.paper.command.KitchenSinkSenderMapper;

/**
 * Module for Cloud bindings.
 */
public final class CloudModule extends AbstractModule {

    private final PaperKitchenSink paperKitchenSink;

    /**
     * Creates a new cloud module instance.
     *
     * @param paperKitchenSink plugin that is creating the instance
     */
    public CloudModule(final @NonNull PaperKitchenSink paperKitchenSink) {
        this.paperKitchenSink = Objects.requireNonNull(paperKitchenSink, "paperKitchenSink");
    }

    /**
     * Provides the command manager.
     *
     * @param kitchenSinkSenderMapper mapper between the Bukkit and KitchenSink command senders
     * @param styleConfiguration      style configuration
     * @return the command manager
     */
    @Provides
    public CommandManager<KitchenSinkCommandSender> commandManager(
            final @NonNull KitchenSinkSenderMapper kitchenSinkSenderMapper,
            final @NonNull StyleConfiguration styleConfiguration
    ) {
        final PaperCommandManager<KitchenSinkCommandSender> commandManager = new PaperCommandManager<>(
                this.paperKitchenSink,
                ExecutionCoordinator.asyncCoordinator(),
                kitchenSinkSenderMapper
        );

        if (commandManager.hasCapability(CloudBukkitCapabilities.NATIVE_BRIGADIER)) {
            commandManager.registerBrigadier();
        } else if (commandManager.hasCapability(CloudBukkitCapabilities.ASYNCHRONOUS_COMPLETION)) {
            commandManager.registerAsynchronousCompletions();
        }

        final LocaleExtractor<KitchenSinkCommandSender> localeExtractor = KitchenSinkCommandSender::locale;
        commandManager.captionRegistry().registerProvider(TranslationBundle.core(localeExtractor));
        commandManager.captionRegistry().registerProvider(TranslationBundle.resourceBundle(
                "org.incendo.kitchensink.common.lang.messages", localeExtractor));
        commandManager.captionRegistry().registerProvider(BukkitTranslationBundle.bukkit(localeExtractor));

        MinecraftExceptionHandler.<KitchenSinkCommandSender>create(AudienceProvider.nativeAudience())
                .defaultHandlers()
                .decorator(component -> MiniMessage.miniMessage().deserialize(styleConfiguration.prefix()).append(component))
                .registerTo(commandManager);

        return commandManager;
    }
}
