package de.acktstudios.forceitem.commands;

import de.acktstudios.forceitem.Main;
import de.acktstudios.forceitem.Timer.Timer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TimerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String prefix = Main.getTimerPrefix();
        Timer timer = Main.getInstance().getTimer();

        if (args.length == 0) {
            sender.sendMessage(prefix + "§7Usage§8: §9/timer start, /timer stop, /timer reset");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "start": {

                if (args.length != 2) {
                    sender.sendMessage(prefix + "§7Usage§8: §9/timer start <time>");
                    return true;
                }

                if (timer.isRunning()) {
                    sender.sendMessage(prefix + "§cDer Timer läuft bereits!");
                    break;
                }

                try {
                    int time = Integer.parseInt(args[1]);
                    timer.setTime(time);
                    timer.setRunning(true);
                    sender.sendMessage(prefix + "Der Timer wurde §agestartet§b!");
                } catch (NumberFormatException e) {
                    sender.sendMessage(prefix + "§cDer zweite Parameter muss eine Zahl sein.");
                }
                break;
            }
            case "stop": {
                if (!timer.isRunning()) {
                    sender.sendMessage(prefix + "§cDer Timer läuft bereits!");
                    break;
                }

                timer.setRunning(false);
                sender.sendMessage(prefix + "Der Timer wurde §agestoppt§b!");
                break;
            }
            case "reset": {
                timer.setRunning(false);
                timer.setTime(0);

                sender.sendMessage(prefix + "Der Timer wurde §azurückgesetzt§b!");
                break;
            }
            default:
                sender.sendMessage(prefix + "§7Usage§8: §9/timer start, /timer stop, /timer reset");
                break;
        }

        return true;
    }
}
