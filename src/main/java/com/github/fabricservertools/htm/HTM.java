package com.github.fabricservertools.htm;

import com.github.fabricservertools.htm.command.HTMCommand;
import com.github.fabricservertools.htm.command.subcommands.*;
import com.github.fabricservertools.htm.config.HTMConfig;
import com.github.fabricservertools.htm.listeners.PlayerEventListener;
import com.github.fabricservertools.htm.listeners.WorldEventListener;
import com.github.fabricservertools.htm.locks.KeyLock;
import com.github.fabricservertools.htm.locks.PrivateLock;
import com.github.fabricservertools.htm.locks.PublicLock;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.command.ServerCommandSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class HTM implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("HTM");
	public static HTMConfig config;

	@Override
	public void onInitialize() {
		registerLocks();
		registerFlags();

		config = HTMConfig.loadConfig(new File(FabricLoader.getInstance().getConfigDir() + "/htm_config.json"));

		CommandRegistrationCallback.EVENT.register(((dispatcher, dedicated) -> registerCommands(dispatcher)));

		PlayerEventListener.init();
		WorldEventListener.init();
	}

	private void registerLocks() {
		HTMRegistry.registerLockType("private", PrivateLock.class);
		HTMRegistry.registerLockType("public", PublicLock.class);
		HTMRegistry.registerLockType("key", KeyLock.class);
	}

	private void registerFlags() {
		HTMRegistry.registerFlagType("hoppers");
	}

	private void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
		HTMCommand.register(dispatcher);
		HTMCommand.registerSubCommand(new SetCommand().build());
		HTMCommand.registerSubCommand(new RemoveCommand().build());
		HTMCommand.registerSubCommand(new TrustCommand().build());
		HTMCommand.registerSubCommand(new UntrustCommand().build());
		HTMCommand.registerSubCommand(new InfoCommand().build());
		HTMCommand.registerSubCommand(new TransferCommand().build());
		HTMCommand.registerSubCommand(new FlagCommand().build());
		HTMCommand.registerSubCommand(new PersistCommand().build());
	}
}
