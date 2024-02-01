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

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.Command;
import org.incendo.cloud.bean.CommandProperties;
import org.incendo.cloud.caption.CaptionVariable;
import org.incendo.kitchensink.caption.CaptionKeys;
import org.incendo.kitchensink.caption.Formatter;
import org.incendo.kitchensink.command.KitchenSinkCommandBean;
import org.incendo.kitchensink.command.KitchenSinkCommandSender;
import org.incendo.kitchensink.entity.player.GameMode;
import org.incendo.kitchensink.entity.player.KitchenSinkPlayer;

import static org.incendo.kitchensink.command.parser.GameModeParser.gameModeParser;

@Singleton
public final class GameModeCommand extends KitchenSinkCommandBean {

    private final Formatter formatter;

    /**
     * Creates a new instance.
     *
     * @param formatter caption formatter
     */
    @Inject
    public GameModeCommand(final @NonNull Formatter formatter) {
        this.formatter = Objects.requireNonNull(formatter, "formatter");
    }

    @Override
    protected @NonNull CommandProperties properties() {
        return CommandProperties.of("gamemode", "gm");
    }

    @Override
    protected Command.@NonNull Builder<? extends KitchenSinkCommandSender> configureKitchenSinkCommand(
            final Command.@NonNull Builder<KitchenSinkCommandSender> builder
    ) {
        // TODO(City): Make the command take in an optional player argument that defaults
        //  to the executing player (if the sender is a player).
        return builder.required("gameMode", gameModeParser())
                .senderType(KitchenSinkPlayer.class)
                .handler((FutureCommandExecutionHandler<KitchenSinkPlayer>) context -> {
                    final GameMode gameMode = context.get("gameMode");
                    return context.sender().gameMode(gameMode).whenComplete(($, error) -> {
                        context.sender().sendMessage(
                                this.formatter.format(
                                        context.sender(),
                                        CaptionKeys.UTILITY_COMMAND_GAMEMODE_UPDATED,
                                        CaptionVariable.of("gamemode", gameMode.key()) // TODO(City): Use RichVariable
                                )
                        );
                    });
                });
    }
}
