package xyz.nsgw.skeyecraft;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.command.CommandSender;

@CommandAlias("rules")
public class RulesCmd extends BaseCommand {

    private final Skeyecraft pl;

    protected RulesCmd(final Skeyecraft sk) {
        this.pl = sk;
    }

    @Default
    public void onRules(CommandSender s) {
        this.pl.rules(s);
    }
}
