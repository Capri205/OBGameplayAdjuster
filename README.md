# OBGameplayAdjuster
Minecraft bukkit/spigot plugin to enforce various gameplay settings.<br>

Use the /gpa to get the usage message

Useful for PVP servers where you want to enforce particular rules such as auto deop of op'd players.<br>
This was a requested feature for some of our PlayerServers users who want to PVP and don't trust each other :-)<br>
More to come!

Currently supports:
deop - deop players so there's a level playing field in an smp server. Obviously use with caution!<br>
alldamage - toggle all damge to players on/off<br>
damage - toggle specific damage to all players on and off. See this URL for specifc damage causes:<br>
[Spigot Damage Causes](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/event/entity/EntityDamageEvent.DamageCause.html)<br>
eg. /gpa damage FALL - would toggle fall damage for all players in all worlds<br>

Compiled for 1.20 and Java 17.
