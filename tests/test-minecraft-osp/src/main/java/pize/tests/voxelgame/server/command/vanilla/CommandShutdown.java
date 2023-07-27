package pize.tests.voxelgame.server.command.vanilla;

import pize.tests.voxelgame.main.text.Component;
import pize.tests.voxelgame.main.text.TextColor;
import pize.tests.voxelgame.main.command.CommandContext;
import pize.tests.voxelgame.server.command.CommandDispatcher;
import pize.tests.voxelgame.main.command.builder.Commands;
import pize.util.time.PizeRunnable;

import java.util.concurrent.atomic.AtomicInteger;

public class CommandShutdown{

    public static void registerTo(CommandDispatcher dispatcher){
        dispatcher.newCommand(Commands.literal("shutdown")
            .requiresPlayer()
            .requires(source -> source.asPlayer().getName().equals("GeneralPashon"))

            .executes( CommandShutdown::shutdown)
            .then(Commands.literal("now").executes( CommandShutdown::shutdownNow ) )
        );
    }

    private static void shutdown(CommandContext context){
        context.getServer().broadcast(new Component().color(TextColor.DARK_RED).text("Shutting down the server..."));

        final AtomicInteger counterInt = new AtomicInteger(3);
        new PizeRunnable(() -> {
            if(counterInt.get() == 0)
                context.getServer().stop();
            else{
                context.getServer().broadcast(new Component().color(TextColor.DARK_RED).text(counterInt));
                counterInt.set(counterInt.get() - 1);
            }
        }).runTimerAsync(1000, 1000);
    }

    private static void shutdownNow(CommandContext context){
        context.getServer().stop();
    }

}
