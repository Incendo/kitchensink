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
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.kitchensink.configuration.StyleConfiguration;
import org.incendo.kitchensink.configuration.StyleConfigurationImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

/**
 * Configuration bindings.
 */
public final class ConfigurationModule extends AbstractModule {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationModule.class);

    private final Path rootPath;

    /**
     * Creates a new configuration module.
     *
     * @param rootPath root path
     */
    public ConfigurationModule(final @NonNull Path rootPath) {
        this.rootPath = Objects.requireNonNull(rootPath, "rootPath");

        if (!Files.exists(this.rootPath)) {
            try {
                Files.createDirectory(this.rootPath);
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Creates the style configuration.
     *
     * @return the style configuration
     */
    @Provides
    public @NonNull StyleConfiguration styleConfiguration() {
        return new StyleConfigurationImpl(this.readConfig("style.yml"));
    }

    private @NonNull CommentedConfigurationNode readConfig(final @NonNull String name) {
        final Path path = this.rootPath.resolve(name);
        if (!Files.exists(path)) {
            try (InputStream inputStream = this.getClass().getResourceAsStream("/default_config/" + name)){
                Files.copy(inputStream, path);
            } catch (final Exception e) {
                LOGGER.error("Failed to copy default config '{}'", name, e);
            }
        }

        final YamlConfigurationLoader loader = YamlConfigurationLoader.builder()
                .path(path)
                .build();

        final CommentedConfigurationNode node;
        try {
            return loader.load();
        } catch (ConfigurateException e) {
            throw new RuntimeException(e);
        }
    }
}
