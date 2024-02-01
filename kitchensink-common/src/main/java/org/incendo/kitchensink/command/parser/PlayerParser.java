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
package org.incendo.kitchensink.command.parser;

import java.util.Objects;
import java.util.UUID;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.caption.Caption;
import org.incendo.cloud.caption.CaptionVariable;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.context.CommandInput;
import org.incendo.cloud.exception.parsing.ParserException;
import org.incendo.cloud.parser.ArgumentParseResult;
import org.incendo.cloud.parser.ArgumentParser;
import org.incendo.cloud.parser.ParserDescriptor;
import org.incendo.cloud.suggestion.BlockingSuggestionProvider;
import org.incendo.cloud.suggestion.Suggestion;
import org.incendo.kitchensink.command.KitchenSinkCommandSender;
import org.incendo.kitchensink.entity.KitchenSinkEntity;
import org.incendo.kitchensink.entity.PlayerRepository;
import org.incendo.kitchensink.entity.player.KitchenSinkPlayer;

/**
 * Parses online players.
 */
public final class PlayerParser implements ArgumentParser<KitchenSinkCommandSender, KitchenSinkPlayer>,
        BlockingSuggestionProvider<KitchenSinkCommandSender> {

    /**
     * Creates a new player parser.
     *
     * @param playerRepository player repository
     * @return the parser
     */
    public static @NonNull ParserDescriptor<KitchenSinkCommandSender, KitchenSinkPlayer> playerParser(
            final @NonNull PlayerRepository<?, ?> playerRepository
    ) {
        return ParserDescriptor.of(new PlayerParser(playerRepository), KitchenSinkPlayer.class);
    }

    private final PlayerRepository<?, ?> playerRepository;

    private PlayerParser(final @NonNull PlayerRepository<?, ?> playerRepository) {
        this.playerRepository = Objects.requireNonNull(playerRepository, "playerRepository");
    }

    @Override
    public @NonNull ArgumentParseResult<@NonNull KitchenSinkPlayer> parse(
            final @NonNull CommandContext<@NonNull KitchenSinkCommandSender> commandContext,
            final @NonNull CommandInput commandInput
    ) {
        // Try to parse a UUID. If that fails, we instead try to parse a player.
        final String input = commandInput.readString();

        KitchenSinkPlayer player = null;

        try {
            final UUID uuid = UUID.fromString(input);
            player = this.playerRepository.findById(uuid).orElse(null);
        } catch (final IllegalArgumentException ignored) {
        }

        for (final KitchenSinkPlayer candidate : this.playerRepository.players()) {
            if (candidate.name().equalsIgnoreCase(input)) {
                player = candidate;
                break;
            }
        }

        if (player != null) {
            return ArgumentParseResult.success(player);
        }
        return ArgumentParseResult.failure(new PlayerParseException(input, commandContext));
    }

    @Override
    public @NonNull Iterable<@NonNull Suggestion> suggestions(
            final @NonNull CommandContext<KitchenSinkCommandSender> context,
            final @NonNull CommandInput input
    ) {
        return this.playerRepository.players().stream().map(KitchenSinkEntity::name).map(Suggestion::simple).toList();
    }


    /**
     * Player parse exception
     */
    public static final class PlayerParseException extends ParserException {

        private final String input;

        /**
         * Construct a new Player parse exception.
         *
         * @param input   string input
         * @param context command context
         */
        public PlayerParseException(
                final @NonNull String input,
                final @NonNull CommandContext<?> context
        ) {
            super(
                    PlayerParser.class,
                    context,
                    Caption.of("argument.parse.failure.player"),
                    CaptionVariable.of("input", input)
            );
            this.input = input;
        }

        /**
         * Returns the supplied input-
         *
         * @return string value
         */
        public @NonNull String input() {
            return this.input;
        }
    }
}
