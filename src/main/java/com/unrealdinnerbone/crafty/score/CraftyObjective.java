package com.unrealdinnerbone.crafty.score;

import com.unrealdinnerbone.crafty.api.UpdatedObjective;
import net.minecraft.network.chat.numbers.BlankFormat;
import net.minecraft.network.chat.numbers.NumberFormat;
import org.bukkit.craftbukkit.v1_20_R3.scoreboard.CraftScoreboard;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public record CraftyObjective(Objective score) implements UpdatedObjective {

    @Override
    public void blankFormat() {
        Scoreboard scoreboard = score.getScoreboard();
        CraftScoreboard craftScoreboard = (CraftScoreboard) scoreboard;
        net.minecraft.world.scores.Objective objective = craftScoreboard.getHandle().getObjective(score.getName());
        if(objective.numberFormat() != BlankFormat.INSTANCE) {
            objective.setNumberFormat(BlankFormat.INSTANCE);
        }
    }

    @Override
    public void resetFormat() {
        Scoreboard scoreboard = score.getScoreboard();
        CraftScoreboard craftScoreboard = (CraftScoreboard) scoreboard;
        net.minecraft.world.scores.Objective objective = craftScoreboard.getHandle().getObjective(score.getName());
        if(objective.numberFormat() != null) {
            objective.setNumberFormat(null);
        }
    }
}
