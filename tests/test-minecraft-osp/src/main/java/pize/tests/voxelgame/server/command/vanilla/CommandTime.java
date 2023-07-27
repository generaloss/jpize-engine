package pize.tests.voxelgame.server.command.vanilla;

import pize.math.Maths;
import pize.tests.voxelgame.main.text.Component;
import pize.tests.voxelgame.main.time.GameTime;
import pize.tests.voxelgame.main.command.CommandContext;
import pize.tests.voxelgame.server.command.CommandDispatcher;
import pize.tests.voxelgame.main.command.argument.CommandArg;
import pize.tests.voxelgame.main.command.argument.CommandArgTime;
import pize.tests.voxelgame.main.command.builder.Commands;

public class CommandTime{
    
    public static void registerTo(CommandDispatcher dispatcher){
        dispatcher.newCommand(Commands.literal("time")
        
            .then(Commands.literal("set")
                .then(Commands.argument("time", CommandArg.time()).executes( CommandTime::setTime ))
        
                .then(Commands.literal("day")
                    .executes( CommandTime::setDay )
                    
                    .then(Commands.argument("time", CommandArg.time()).executes( CommandTime::setDayTime ))
                )
                .then(Commands.literal("midnight").executes( CommandTime::setMidnight ))
                .then(Commands.literal("night").executes( CommandTime::setNight ))
                .then(Commands.literal("noon").executes( CommandTime::setNoon ))
            )
        
            .then(Commands.literal("get")
                .executes( CommandTime::getTicks )
                .then(Commands.literal("seconds").executes( CommandTime::getSeconds ))
                .then(Commands.literal("minutes").executes( CommandTime::getMinutes ))
                .then(Commands.literal("day")
                    .executes( CommandTime::getDays )

                    .then(Commands.literal("ticks").executes( CommandTime::getDayTicks ))
                    .then(Commands.literal("seconds").executes( CommandTime::getDaySeconds ))
                    .then(Commands.literal("minutes").executes( CommandTime::getDayMinutes ))
                )
            )
        
        );
    }
    
    
    private static void setTime(CommandContext context){
        final CommandArgTime time = context.getArg(0).asTime();
        // Set time
        context.getServer().getGameTime()
            .setTime(time.getTime(), time.getUnit());
        // Send msg
        context.getSource().sendMessage(new Component().text("Set the gametime to " + time.getTime() + " " + time.getUnit().getLiteral()));
    }
    
    
    private static void setDayTime(CommandContext context){
        final CommandArgTime time = context.getArg(0).asTime();
        // Set day time
        context.getServer().getGameTime()
            .setDayTime(time.getTime(), time.getUnit());
        // Send msg
        context.getSource().sendMessage(new Component().text("Set the daytime to " + time.getTime() + " " + time.getUnit().getLiteral()));
    }
    
    
    private static void setDay(CommandContext context){
        context.getServer().getGameTime().setDay();
        // Send msg
        context.getSource().sendMessage(new Component().text("Set the time to Day (" + GameTime.TIME_DAY + ")"));
    }
    
    private static void setMidnight(CommandContext context){
        context.getServer().getGameTime().setMidnight();
        // Send msg
        context.getSource().sendMessage(new Component().text("Set the time to Midnight (" + GameTime.TIME_MIDNIGHT + ")"));
    }
    
    private static void setNight(CommandContext context){
        context.getServer().getGameTime().setNight();
        // Send msg
        context.getSource().sendMessage(new Component().text("Set the time to Night (" + GameTime.TIME_NIGHT + ")"));
    }
    
    private static void setNoon(CommandContext context){
        context.getServer().getGameTime().setNoon();
        // Send msg
        context.getSource().sendMessage(new Component().text("Set the time to Noon (" + GameTime.TIME_NOON + ")"));
    }
    
    
    private static void getTicks(CommandContext context){
        context.getSource().sendMessage(new Component().text("Gametime is " +
            context.getServer().getGameTime().getTicks()
        ));
    }
    
    private static void getSeconds(CommandContext context){
        context.getSource().sendMessage(new Component().text("Gametime is " +
            context.getServer().getGameTime().getSeconds() + " seconds"
        ));
    }
    
    private static void getMinutes(CommandContext context){
        context.getSource().sendMessage(new Component().text("Gametime is " +
            context.getServer().getGameTime().getMinutes() + " minutes"
        ));
    }
    
    private static void getDays(CommandContext context){
        context.getSource().sendMessage(new Component().text("Day is " +
            Maths.floor( context.getServer().getGameTime().getDays() + 1 )
        ));
    }
    
    
    private static void getDayTicks(CommandContext context){
        context.getSource().sendMessage(new Component().text("Daytime is " +
            context.getServer().getGameTime().getDayTicks()
        ));
    }
    
    private static void getDaySeconds(CommandContext context){
        context.getSource().sendMessage(new Component().text("Daytime is " +
            context.getServer().getGameTime().getDaySeconds() + " seconds"
        ));
    }
    
    private static void getDayMinutes(CommandContext context){
        context.getSource().sendMessage(new Component().text("Daytime is " +
            context.getServer().getGameTime().getDayMinutes() + " minutes"
        ));
    }
    
}
