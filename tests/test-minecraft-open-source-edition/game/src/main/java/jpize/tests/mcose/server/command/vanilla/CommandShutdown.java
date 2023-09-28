package jpize.tests.mcose.server.command.vanilla;

import jpize.tests.mcose.main.text.Component;
import jpize.tests.mcose.main.text.TextColor;
import jpize.tests.mcose.main.command.CommandContext;
import jpize.tests.mcose.server.command.CommandDispatcher;
import jpize.tests.mcose.main.command.builder.Commands;
import jpize.util.time.JpizeRunnable;

import java.util.concurrent.atomic.AtomicInteger;

public class CommandShutdown{

    public static void registerTo(CommandDispatcher dispatcher){
        dispatcher.newCommand(Commands.literal("shutdown")
            .requiresPlayer()
            .requires(source -> source.asPlayer().getName().equals("GeneralPashon") || source.asPlayer().getName().equals("Herobrine"))

            .executes( CommandShutdown::shutdown)
            .then(Commands.literal("now").executes( CommandShutdown::shutdownNow ) )
        );
    }

    private static void shutdown(CommandContext context){
        context.getServer().broadcast(new Component().color(TextColor.DARK_RED).text("Shutting down the server..."));

        final AtomicInteger counterInt = new AtomicInteger(3);
        new JpizeRunnable(() -> {
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
