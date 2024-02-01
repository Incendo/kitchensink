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
package org.incendo.kitchensink.command;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.CommandManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service responsible for creating and configuring commands.
 */
@Singleton
public final class CommandService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandService.class);

    private final CommandManager<KitchenSinkCommandSender> commandManager;
    private final Collection<@NonNull KitchenSinkCommandBean> commands;

    /**
     * Creates a new command service.
     *
     * @param commandManager cloud command manager
     * @param commands       recognized commands
     */
    @Inject
    public CommandService(
            final @NonNull CommandManager<KitchenSinkCommandSender> commandManager,
            final @NonNull Set<@NonNull KitchenSinkCommandBean> commands
    ) {
        this.commandManager = Objects.requireNonNull(commandManager, "commandManager");
        this.commands = Objects.requireNonNull(commands, "commands");
    }

    /**
     * Registers all recognized commands.
     *
     * @see #registerCommand(KitchenSinkCommandBean)
     */
    public void registerCommands() {
        LOGGER.info("Registering commands");
        this.commands.forEach(this::registerCommand);
    }

    /**
     * Registers the given {@code command}.
     *
     * @param command command to register
     */
    public void registerCommand(final @NonNull KitchenSinkCommandBean command) {
        Objects.requireNonNull(command, "command");
        // TODO(City): Determine whether the command should be registered or not.
        this.commandManager.command(command);
    }
}
