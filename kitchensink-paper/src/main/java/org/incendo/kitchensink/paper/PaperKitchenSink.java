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
package org.incendo.kitchensink.paper;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.kitchensink.command.CommandService;
import org.incendo.kitchensink.guice.CaptionModule;
import org.incendo.kitchensink.guice.CommandModule;
import org.incendo.kitchensink.guice.ConfigurationModule;
import org.incendo.kitchensink.paper.entity.PaperPlayerRepository;
import org.incendo.kitchensink.paper.guice.CloudModule;
import org.incendo.kitchensink.paper.guice.PaperModule;

/**
 * Paper implementation of KitchenSink.
 */
public final class PaperKitchenSink extends JavaPlugin {

    private Injector injector;

    @Override
    public void onLoad() {
        this.injector = Guice.createInjector(
                new ConfigurationModule(),
                new CaptionModule(),
                new PaperModule(this),
                new CloudModule(this),
                new CommandModule()
        );
    }

    @Override
    public void onEnable() {
        final PaperPlayerRepository playerRepository = this.injector.getInstance(PaperPlayerRepository.class);
        this.getServer().getPluginManager().registerEvents(playerRepository, this);

        final CommandService commandService = this.injector.getInstance(CommandService.class);
        commandService.registerCommands();
    }
}
