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
import java.util.stream.Collectors;
import org.apiguardian.api.API;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.caption.CaptionVariable;
import org.incendo.cloud.caption.StandardCaptionKeys;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.context.CommandInput;
import org.incendo.cloud.exception.parsing.ParserException;
import org.incendo.cloud.parser.ArgumentParseResult;
import org.incendo.cloud.parser.ArgumentParser;
import org.incendo.cloud.parser.ParserDescriptor;
import org.incendo.cloud.suggestion.BlockingSuggestionProvider;
import org.incendo.cloud.suggestion.Suggestion;
import org.incendo.cloud.type.range.Range;
import org.incendo.kitchensink.command.KitchenSinkCommandSender;
import org.incendo.kitchensink.entity.player.GameMode;

public final class GameModeParser implements ArgumentParser<KitchenSinkCommandSender, GameMode>,
        BlockingSuggestionProvider<KitchenSinkCommandSender> {

    /**
     * Creates a new game mode parser.
     *
     * @return the parser
     */
    public static ParserDescriptor<KitchenSinkCommandSender, GameMode> gameModeParser() {
        return ParserDescriptor.of(new GameModeParser(), GameMode.class);
    }

    @Override
    public @NonNull ArgumentParseResult<@NonNull GameMode> parse(
            final @NonNull CommandContext<@NonNull KitchenSinkCommandSender> commandContext,
            final @NonNull CommandInput commandInput
    ) {
        if (commandInput.isValidInteger(Range.intRange(0, 3))) {
            return ArgumentParseResult.success(GameMode.fromId(commandInput.readInteger()));
        }

        final String input = commandInput.readString();
        try {
            return ArgumentParseResult.success(GameMode.fromKey(input));
        } catch (final IllegalArgumentException ignored) {
            return ArgumentParseResult.failure(new GameModeParseException(input, commandContext));
        }
    }

    @Override
    public @NonNull Iterable<@NonNull Suggestion> suggestions(
            final @NonNull CommandContext<KitchenSinkCommandSender> context,
            final @NonNull CommandInput input
    ) {
        return GameMode.gameModes()
                .stream()
                .map(GameMode::key)
                .map(Suggestion::simple)
                .toList();
    }


    @API(status = API.Status.STABLE)
    public static final class GameModeParseException extends ParserException {

        private final String input;

        /**
         * Construct a new game mode parse exception.
         *
         * @param input   input
         * @param context command context
         */
        public GameModeParseException(
                final @NonNull String input,
                final @NonNull CommandContext<?> context
        ) {
            super(
                    GameModeParser.class,
                    context,
                    StandardCaptionKeys.ARGUMENT_PARSE_FAILURE_ENUM,
                    CaptionVariable.of("input", input),
                    CaptionVariable.of("acceptableValues", GameMode.gameModes()
                            .stream()
                            .map(GameMode::key)
                            .collect(Collectors.joining(", "))
                    )
            );
            this.input = input;
        }

        /**
         * Returns the input provided by the sender.
         *
         * @return input
         */
        public @NonNull String input() {
            return this.input;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            final GameModeParseException that = (GameModeParseException) o;
            return this.input.equals(that.input);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.input);
        }
    }
}
