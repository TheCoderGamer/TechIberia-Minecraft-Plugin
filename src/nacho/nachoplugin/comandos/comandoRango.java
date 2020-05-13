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

public class comandoRango implements CommandExecutor,TabExecutor{

	// Esto sirve para importar variables de otra clase
	private main plugin;
	public comandoRango(main plugin) {
		this.plugin = plugin;
	}
	
	boolean playerIsOP;
	boolean playerIsDonador;
	
	@Override
	public boolean onCommand(CommandSender sender, Command comando, String label, String[] args) {
		
		Player jugador = (Player) sender;
		FileConfiguration config = plugin.getConfig();
		
		// Comprobar si es OP
		List<String> OPPlayers = (ArrayList<String>) config.getStringList("OPplayers");
		String nombreJugador = jugador.getName();
		if(OPPlayers.contains(nombreJugador)) {
			playerIsOP = true;
		}else {
			playerIsOP = false;
		}		
		// Cargar donadores
		List<String> donadoresPlayers = (ArrayList<String>) config.getStringList("donadoresPlayers");	

		if((args.length > 0 && playerIsOP == true) || !(sender instanceof Player)) {
			// -----------------------------------------------------------------------------------
			if(args[0].equalsIgnoreCase("set")) {
				if(args.length > 1) {
					if(args[1].equalsIgnoreCase("op")) {
						// Añadir jugador a los OP
						OPPlayers.add(args[2]);
						config.set("OPplayers",OPPlayers );
						plugin.saveConfig();
						jugador.sendMessage(plugin.nombre + ChatColor.GREEN + "Rango OP dado correctamente");
						return true;
					}
					else if(args[1].equalsIgnoreCase("donador")) {
						// Añadir jugador a donador
						donadoresPlayers.add(args[2]);
						config.set("donadoresPlayers",donadoresPlayers );
						plugin.saveConfig();
						jugador.sendMessage(plugin.nombre + ChatColor.GREEN + "Rango donador dado correctamente");
						return true;
					}
					else {
						jugador.sendMessage(plugin.nombre + ChatColor.RED + "Rango no reconocido");
						jugador.sendMessage(plugin.nombre + ChatColor.RED	+ "Rangos disponibles op/donador");
						return true;
					}
				}
				else {
					jugador.sendMessage(plugin.nombre + ChatColor.RED + "Faltan argumentos");
					return true;
				}
			}
			else if(args[0].equalsIgnoreCase("remove")) {
				if(args.length > 1) {
					if(args[1].equalsIgnoreCase("op")) {
						// Quitar jugador de OP
						OPPlayers.remove(args[2]);
						config.set("OPplayers",OPPlayers );
						plugin.saveConfig();
						jugador.sendMessage(plugin.nombre + ChatColor.GREEN + "Rango OP quitado correctamente");
						return true;
					}
					else if(args[1].equalsIgnoreCase("donador")) {
						// Quitar jugador de donador
						donadoresPlayers.remove(args[2]);
						config.set("donadoresPlayers",donadoresPlayers );
						plugin.saveConfig();
						jugador.sendMessage(plugin.nombre + ChatColor.GREEN + "Rango donador quitado correctamente");
						return true;
					}
					else {
						jugador.sendMessage(plugin.nombre + ChatColor.RED + "Rango no reconocido");
						jugador.sendMessage(plugin.nombre + ChatColor.RED	+ "Rangos disponibles op/donador");
						return true;
					}
				}
				else {
					jugador.sendMessage(plugin.nombre + ChatColor.RED + "Faltan argumentos");
					return true;
				}
			}
			else if(args[0].equalsIgnoreCase("list")) {
				if(args.length > 1) {
					if(args[1].equalsIgnoreCase("op")) {
						// Mostrar jugadores OP
						jugador.sendMessage(plugin.nombre + ChatColor.LIGHT_PURPLE + "-----------");
						for(int i=0;i<OPPlayers.size(); i++) {
							jugador.sendMessage(plugin.nombre + ChatColor.GREEN + OPPlayers.get(i));
						}
						jugador.sendMessage(plugin.nombre + ChatColor.LIGHT_PURPLE + "-----------");
						return true;
					}
					else if(args[1].equalsIgnoreCase("donador")) {
						// Mostrar jugadores donadores
						jugador.sendMessage(plugin.nombre + ChatColor.LIGHT_PURPLE + "-----------");
						for(int i=0;i<donadoresPlayers.size(); i++) {
							jugador.sendMessage(plugin.nombre + ChatColor.GREEN + donadoresPlayers.get(i));
						}
						jugador.sendMessage(plugin.nombre + ChatColor.LIGHT_PURPLE + "-----------");
						return true;
					}
					else {
						jugador.sendMessage(plugin.nombre + ChatColor.RED + "Rango no reconocido");
						jugador.sendMessage(plugin.nombre + ChatColor.RED	+ "Rangos disponibles op/donador");
						return true;
					}
				}
				else {
					jugador.sendMessage(plugin.nombre + ChatColor.RED + "Faltan argumentos");
					return true;
				}
			}
			else if(args[0].equalsIgnoreCase("help")) {
				jugador.sendMessage(plugin.nombre + ChatColor.LIGHT_PURPLE + "-----------");
				jugador.sendMessage(plugin.nombre + ChatColor.DARK_PURPLE + "/rango set" + ChatColor.LIGHT_PURPLE + " - Muestra la version del plugin");
				jugador.sendMessage(plugin.nombre + ChatColor.DARK_PURPLE + "/rango remove" + ChatColor.LIGHT_PURPLE + " - Muestra informacion del plugin");
				jugador.sendMessage(plugin.nombre + ChatColor.DARK_PURPLE + "/rango list" + ChatColor.LIGHT_PURPLE + " - Recarga el plugin");
				jugador.sendMessage(plugin.nombre + ChatColor.LIGHT_PURPLE + "-----------");
				return true;
			}
			else {
				jugador.sendMessage(plugin.nombre + ChatColor.RED + "Comando no reconocido");
				return true;
			}			
		}
		else{
			jugador.sendMessage(plugin.nombre + ChatColor.RED + "No tienes permiso para ejecutar ese comando");
			return true;
		}
	}	




	@Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 3){
            List<String> playerNames = new ArrayList<>();
            Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
            Bukkit.getServer().getOnlinePlayers().toArray(players);
            for (int i = 0; i < players.length; i++){
                playerNames.add(players[i].getName());
            }
 
            return playerNames;
        }
        else if (args.length == 2){
            List<String> arguments2 = new ArrayList<>();
            arguments2.add("op");
            arguments2.add("donador");
 
            return arguments2;
        }
        else if (args.length == 1){
            List<String> arguments1 = new ArrayList<>();
            arguments1.add("set");
            arguments1.add("remove");
            arguments1.add("list");
            arguments1.add("help");
            return arguments1;
        }   
        return null;
    }



}




























