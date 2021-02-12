package com.github.fabricservertools.htm.command;

import com.github.fabricservertools.htm.HTMRegistry;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.server.command.ServerCommandSource;

import java.util.concurrent.CompletableFuture;

public class LockTypeSuggestionProvider implements SuggestionProvider<ServerCommandSource> {
    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) throws CommandSyntaxException {
        String current = builder.getRemaining().toUpperCase();

        for (String type : HTMRegistry.getLockTypes().keySet()) {
            if (type.contains(current.toLowerCase())) {
                builder.suggest(type.toUpperCase());
            }
        }

        return builder.buildFuture();
    }
}
