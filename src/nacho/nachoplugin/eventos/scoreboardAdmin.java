package nacho.nachoplugin.eventos;



import java.text.NumberFormat;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import nacho.nachoplugin.main;

public class scoreboardAdmin {
	// Esto sirve para importar variables de otra clase
	private main plugin;
	int taskID;	
	public scoreboardAdmin(main plugin) {
		this.plugin = plugin;
	}
	
	public void crearScoreboard(int ticks) {
		BukkitScheduler schedule = Bukkit.getServer().getScheduler();
		taskID = schedule.scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
				FileConfiguration config = plugin.getConfig();
				for(Player player : Bukkit.getOnlinePlayers()) {
					actualizarScoreboard(player, config);
				}
			}
		}, 0, 20);
	}
	

	@SuppressWarnings("deprecation")
	private void actualizarScoreboard(Player jugador, FileConfiguration config){
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard scoreboard = manager.getNewScoreboard();
		Objective objetivo = scoreboard.registerNewObjective("TechIberia", "dummy");
		objetivo.setDisplaySlot(DisplaySlot.SIDEBAR);
		objetivo.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString("Scoreboard.title")));
		List<String> lineas = config.getStringList("Scoreboard.text");
		double x = jugador.getLocation().getX();
		double y = jugador.getLocation().getY();
		double z = jugador.getLocation().getZ();
		NumberFormat nf = NumberFormat.getInstance();
		
		for(int i=0; i<lineas.size();i++) {
			Score score = objetivo.getScore(ChatColor.translateAlternateColorCodes('&', lineas.get(i).replace("%player%", jugador.getName()).replace("%posX%", nf.format(x)).replace("%posY%", nf.format(y)).replace("%posZ%", nf.format(z))));
			score.setScore(lineas.size()-(i));
			jugador.setScoreboard(scoreboard);
		}
	}
}
