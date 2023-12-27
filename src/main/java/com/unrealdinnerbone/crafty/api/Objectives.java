package com.unrealdinnerbone.crafty.api;

import com.unrealdinnerbone.crafty.score.CraftyObjective;
import org.bukkit.scoreboard.Objective;

public class Objectives {
    public static UpdatedObjective getObjective(Objective score) {
        return new CraftyObjective(score);
    }
}
