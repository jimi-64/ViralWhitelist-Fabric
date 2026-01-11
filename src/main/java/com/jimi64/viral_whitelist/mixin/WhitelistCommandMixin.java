package com.jimi64.viral_whitelist.mixin;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.GameProfileArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.PlayerConfigEntry;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.dedicated.command.WhitelistCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Collection;

@Mixin(value = WhitelistCommand.class)
public class WhitelistCommandMixin {

    @Shadow
    private static int executeOn(ServerCommandSource source) {
        throw new AssertionError();
    }

    @Shadow
    private static int executeOff(ServerCommandSource source) {
        throw new AssertionError();
    }

    @Shadow
    private static int executeList(ServerCommandSource source) {
        throw new AssertionError();
    }

    @Shadow
    private static int executeReload(ServerCommandSource source) {
        throw new AssertionError();
    }

    @Shadow
    private static int executeAdd(ServerCommandSource source, Collection<PlayerConfigEntry> targets) {
        throw new AssertionError();
    }

    @Shadow
    private static int executeRemove(ServerCommandSource source, Collection<PlayerConfigEntry> targets) {
        throw new AssertionError();
    }


    /**
     * @author jimi64
     * @reason Have to overwrite this class so that everybody can use /whitelist add <player>
     */
    @Overwrite
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager

                // first argument (always <whitlist>)
                .literal("whitelist") /** .requires(CommandManager.requirePermissionLevel(CommandManager.ADMINS_CHECK)) **/)

                    // second argument (<on>, <off>, <list>, <add>, <remove> or <list>)
                    .then(CommandManager.literal("on").requires(CommandManager.requirePermissionLevel(CommandManager.ADMINS_CHECK)).executes((context) -> executeOn((ServerCommandSource)context.getSource()))))
                    .then(CommandManager.literal("off").requires(CommandManager.requirePermissionLevel(CommandManager.ADMINS_CHECK)).executes((context) -> executeOff((ServerCommandSource)context.getSource()))))
                    .then(CommandManager.literal("list").requires(CommandManager.requirePermissionLevel(CommandManager.ADMINS_CHECK)).executes((context) -> executeList((ServerCommandSource)context.getSource()))))
                    .then(CommandManager.literal("add")

                        // subcommand of <add> (<player>)
                        .then(CommandManager.argument("targets", GameProfileArgumentType.gameProfile()).suggests((context, builder) -> {
                            PlayerManager playerManager = ((ServerCommandSource)context.getSource()).getServer().getPlayerManager();
                            return CommandSource.suggestMatching(playerManager.getPlayerList().stream().map(PlayerEntity::getPlayerConfigEntry).filter((playerConfigEntry) -> !playerManager.getWhitelist().isAllowed(playerConfigEntry)).map(PlayerConfigEntry::name), builder);
                        }).executes((context) -> executeAdd((ServerCommandSource)context.getSource(), GameProfileArgumentType.getProfileArgument(context, "targets"))))))

                    // second argument
                    .then(CommandManager.literal("remove").requires(CommandManager.requirePermissionLevel(CommandManager.ADMINS_CHECK))

                        // subcommand of <remove> (<player>)
                        .then(CommandManager.argument("targets", GameProfileArgumentType.gameProfile()).suggests((context, builder) -> CommandSource.suggestMatching(((ServerCommandSource)context.getSource()).getServer().getPlayerManager().getWhitelistedNames(), builder)).executes((context) -> executeRemove((ServerCommandSource)context.getSource(), GameProfileArgumentType.getProfileArgument(context, "targets"))))))

                    // second argument
                    .then(CommandManager.literal("reload").requires(CommandManager.requirePermissionLevel(CommandManager.ADMINS_CHECK)).executes((context) -> executeReload((ServerCommandSource)context.getSource()))));
    }
}
