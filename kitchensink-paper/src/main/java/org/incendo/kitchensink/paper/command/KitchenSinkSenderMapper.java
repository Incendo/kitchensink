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
package org.incendo.kitchensink.paper.command;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.SenderMapper;
import org.incendo.kitchensink.command.KitchenSinkCommandSender;
import org.incendo.kitchensink.entity.PlayerRepository;
import org.incendo.kitchensink.paper.entity.PaperPlayer;

@Singleton
public final class KitchenSinkSenderMapper implements SenderMapper<CommandSender, KitchenSinkCommandSender> {

    private final PaperConsole paperConsole = new PaperConsole();
    private final PlayerRepository<PaperPlayer, Player> playerRepository;

    /**
     * Creates a new sender mapper.
     *
     * @param playerRepository repository used to find player instances
     */
    @Inject
    public KitchenSinkSenderMapper(final @NonNull PlayerRepository<PaperPlayer, Player> playerRepository) {
        this.playerRepository = Objects.requireNonNull(playerRepository, "playerRepository");
    }

    @Override
    public @NonNull KitchenSinkCommandSender map(final @NonNull CommandSender base) {
        if (base instanceof Player player) {
            return this.playerRepository.findByPlatformPlayer(player);
        }
        return this.paperConsole;
    }

    @Override
    public @NonNull CommandSender reverse(final @NonNull KitchenSinkCommandSender mapped) {
        if (mapped instanceof PaperPlayer player) {
            return player.paperPlayer();
        }
        return Bukkit.getConsoleSender();
    }
}
