package nacho.nachoplugin.comandos;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;


import nacho.nachoplugin.main;

public class comandoPrincipal implements CommandExecutor,TabExecutor{

	// Esto sirve para importar variables de otra clase
	private main plugin;
	public comandoPrincipal(main plugin) {
		this.plugin = plugin;
	}
	
	boolean playerIsOp;
	
	@Override
	public boolean onCommand(CommandSender sender, Command comando, String label, String[] args) {
		// Si la consola pone un comando
		if(!(sender instanceof Player)) {	//Si se ejecuta desde la consola
			Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + plugin.nombre + " Consola no puede ejecutar este comando");
			return false;
		}
		// Comando reload se puede ejecutar desde la consola
		else if (!(sender instanceof Player) && args[0].equalsIgnoreCase("reload")) {
			plugin.saveConfig();
			plugin.reloadConfig();
			Bukkit.getConsoleSender().sendMessage(plugin.nombre + ChatColor.BLUE + "Plugin recargado exitosamente");
			return true;
		}
		// Si un jugador pone un comando
		else {
			Player jugador = (Player) sender;
			FileConfiguration config = plugin.getConfig();
			List<String> OPplayers = (ArrayList<String>) config.getStringList("OPplayers");
			String nombreJugador = jugador.getName();
			
			if(OPplayers.contains(nombreJugador)) {
				playerIsOp = true;
			}
			else {
				playerIsOp = false;
			}
			

			if(args.length > 0) {
				// -----------------------------------------------------------------------------------
				if(args[0].equalsIgnoreCase("version")) {
					jugador.sendMessage(plugin.nombre + ChatColor.GREEN + "Version " + ChatColor.DARK_GREEN + plugin.version);
					return true;
				}
				// -----------------------------------------------------------------------------------
				else if (args[0].equalsIgnoreCase("help")) {
					jugador.sendMessage(plugin.nombre + ChatColor.LIGHT_PURPLE + "-----------");
					jugador.sendMessage(plugin.nombre + ChatColor.DARK_PURPLE + "/tech version" + ChatColor.LIGHT_PURPLE + " - Muestra la version del plugin");
					jugador.sendMessage(plugin.nombre + ChatColor.DARK_PURPLE + "/tech info" + ChatColor.LIGHT_PURPLE + " - Muestra informacion del plugin");
					jugador.sendMessage(plugin.nombre + ChatColor.DARK_RED + "/tech reload" + ChatColor.LIGHT_PURPLE + " - Recarga el plugin");
					jugador.sendMessage(plugin.nombre + ChatColor.LIGHT_PURPLE + "-----------");
					return true;
				}
				// -----------------------------------------------------------------------------------
				else if (args[0].equalsIgnoreCase("reload") && playerIsOp == true) {
					plugin.saveConfig();
					plugin.reloadConfig();
					jugador.sendMessage(plugin.nombre + ChatColor.BLUE + "Plugin recargado exitosamente");
					return true;
				}
				else if (args[0].equalsIgnoreCase("reload") && playerIsOp == false) {
					jugador.sendMessage(plugin.nombre + ChatColor.RED + "No tienes permiso para ejecutar ese comando");
					return true;
				}
				// -----------------------------------------------------------------------------------
				else if (args[0].equalsIgnoreCase("info")) {
					jugador.sendMessage(plugin.nombre + ChatColor.LIGHT_PURPLE + "-----------");
					jugador.sendMessage(plugin.nombre + ChatColor.DARK_PURPLE + "Plugin creado por" + ChatColor.DARK_RED + " Nachogamer1000");
					jugador.sendMessage(plugin.nombre + ChatColor.GREEN + "Version " + ChatColor.DARK_GREEN + plugin.version);
					jugador.sendMessage(plugin.nombre + ChatColor.LIGHT_PURPLE + "-----------");
					return true;
				}
				// -----------------------------------------------------------------------------------
				if(args[0].equalsIgnoreCase("fakeKill")) {
					Bukkit.getServer().broadcastMessage(args[1] + " fue asesinado por EnderDragon");
					return true;
				}
				// -----------------------------------------------------------------------------------
				else {
					jugador.sendMessage(plugin.nombre + ChatColor.RED + "Ese comando no existe. Usa /tech help");
					return true;
				}
			}
			else {
				jugador.sendMessage(plugin.nombre + ChatColor.GREEN + "Usa" + ChatColor.GRAY +"/tech help" + ChatColor.WHITE + "para ver los comandos disponibles");
				return true;
			}
		}			
	}
	
	@Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 2){
            List<String> playerNames = new ArrayList<>();
            Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
            Bukkit.getServer().getOnlinePlayers().toArray(players);
            for (int i = 0; i < players.length; i++){
                playerNames.add(players[i].getName());
            }
 
            return playerNames;
        }
        else if (args.length == 1){
            List<String> arguments1 = new ArrayList<>();
            arguments1.add("reload");
            arguments1.add("info");
            arguments1.add("version");
            arguments1.add("help");
            return arguments1;
        }   
        return null;
    }


}

















