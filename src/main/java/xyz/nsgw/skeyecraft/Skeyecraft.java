package xyz.nsgw.skeyecraft;

import co.aikar.commands.PaperCommandManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Skeyecraft extends JavaPlugin implements Listener {

    private PaperCommandManager manager;

    @Override
    public void onEnable() {
        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();

        this.manager = new PaperCommandManager(this);

        manager.registerCommand(new RulesCmd(this));

        Bukkit.getPluginManager().registerEvents(this,this);
    }

    public void rules(final CommandSender s) {
        s.sendMessage(ChatColor.GOLD + "Our Rules\n" + ChatColor.GREEN +
                String.join("\n"+ChatColor.GREEN,this.getConfig().getStringList("lang.rules")));
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
        if(!this.getConfig().getBoolean("rules.join")) return;
        if(e.getPlayer().hasPlayedBefore()) {
            e.getPlayer().sendMessage(ChatColor.WHITE+""+ChatColor.BOLD+"Welcome back to "+
                    ChatColor.AQUA + "Skeye"+ChatColor.DARK_AQUA+"craft"+ChatColor.WHITE+ChatColor.BOLD+"!");
        } else {
            ConfigurationSection loc = getConfig().getConfigurationSection("rules.spawnpoint");
            assert loc != null;
            e.getPlayer().teleport(new Location(Bukkit.getWorld(Objects.requireNonNull(loc.getString("world"))),
                    loc.getDouble("x"),
                    loc.getDouble("y"),
                    loc.getDouble("z"),
                    Float.parseFloat(Objects.requireNonNull(loc.getString("yaw"))),
                    Float.parseFloat(Objects.requireNonNull(loc.getString("pitch")))));
            e.getPlayer().sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "Welcome to " +
                    ChatColor.AQUA + "Skeye" + ChatColor.DARK_AQUA + "craft" + ChatColor.WHITE + ChatColor.BOLD + "!\n"
                    + ChatColor.RESET + ChatColor.GREEN + "Please take a moment to read our rules by using " +
                    ChatColor.BOLD + ChatColor.AQUA + "/rules"
                    + ChatColor.RESET + ChatColor.GREEN + ".\n" +
                    "Type " + ChatColor.BOLD + "/wild " + ChatColor.RESET + ChatColor.GREEN + "to start your adventure!");
        }
    }

    @Override
    public void onDisable() {
        manager.unregisterCommands();
    }
}
