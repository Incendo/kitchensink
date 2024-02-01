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
package org.incendo.kitchensink.command.commands;

import jakarta.inject.Singleton;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.Command;
import org.incendo.cloud.bean.CommandProperties;
import org.incendo.cloud.context.CommandContext;
import org.incendo.kitchensink.command.KitchenSinkCommandBean;
import org.incendo.kitchensink.command.KitchenSinkCommandSender;

import static org.incendo.cloud.parser.standard.StringParser.greedyStringParser;

/**
 * Pong?
 */
@Singleton
public final class PingCommand extends KitchenSinkCommandBean {

    @Override
    protected @NonNull CommandProperties properties() {
        return CommandProperties.of("ping");
    }

    @Override
    protected Command.@NonNull Builder<? extends KitchenSinkCommandSender> configureKitchenSinkCommand(
            final Command.@NonNull Builder<KitchenSinkCommandSender> builder
    ) {
        return builder.required("text", greedyStringParser());
    }

    @Override
    public void execute(final @NonNull CommandContext<KitchenSinkCommandSender> commandContext) {
        final String text = commandContext.get("text");
        commandContext.sender().sendMessage(
                Component.text("You said: ", NamedTextColor.GRAY).append(Component.text(text, NamedTextColor.WHITE))
        );
    }
}
