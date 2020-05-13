package nacho.nachoplugin.comandos;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import nacho.nachoplugin.main;

public class comandoDiscord implements CommandExecutor{

	// Esto sirve para importar variables de otra clase
	private main plugin;
	public comandoDiscord(main plugin) {
		this.plugin = plugin;
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command comando, String label, String[] args) {
		
		if(!(sender instanceof Player)) {	//Si se ejecuta desde la consola
			Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + plugin.nombre + " Consola no puede ejecutar este comando");
			return false;
		}
		else {
			Player jugador = (Player) sender;
			jugador.sendMessage("");
			jugador.sendMessage(ChatColor.DARK_BLUE + "-------------------------------------");
			jugador.sendMessage(ChatColor.BLUE + "Discord oficial: " + ChatColor.YELLOW + "https://discord.gg/f8jk9SU");
			jugador.sendMessage(ChatColor.DARK_BLUE + "-------------------------------------");
			
			return true;
		}	
	}	
}