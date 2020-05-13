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
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command comando, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(plugin.nombre + ChatColor.RED + "La consola no puede ejecutar este comando");
		}
		else {
			Player jugador = (Player) sender;
			FileConfiguration config = plugin.getConfig();
			
			// Comprobar si es OP
			List<String> OPPlayers = (ArrayList<String>) config.getStringList("OPplayers");
			if(OPPlayers.contains(jugador.getName())) {
				playerIsOP = true;
			}else {
				playerIsOP = false;
			}		
			// Cargar donadores
			List<String> donadoresPlayers = (ArrayList<String>) config.getStringList("donadoresPlayers");
			// Cargar miembros
			List<String> miembrosPlayers = (ArrayList<String>) config.getStringList("miembrosPlayers");

			if(args.length > 0 && playerIsOP == true) {
				// ------------------------------------------------------------------------------------------------
				if(args[0].equalsIgnoreCase("set")) {
					if(args.length > 1) {
						if(args[1].equalsIgnoreCase("op")) {
							if(args.length > 2) {
								if(!(OPPlayers.contains(args[2]))) {
									// Añadir jugador a los OP
									OPPlayers.add(args[2]);
									config.set("OPplayers",OPPlayers );
									plugin.saveConfig();
									jugador.sendMessage(plugin.nombre + ChatColor.GREEN + "Rango OP dado correctamente");
									jugador.sendMessage(plugin.nombre + ChatColor.GREEN + "Ejecuta /tech reload para aplicar cambios");
									return true;
								}
								else {
									jugador.sendMessage(plugin.nombre + ChatColor.RED + "El jugador " + args[2] + " ya tiene rango OP");
									return true;
								}
							}
							else {
								jugador.sendMessage(plugin.nombre + ChatColor.RED + "Falta jugador");
								return false;
							}
						}
						// ------------------------------------------------------------------------------------------------
						else if(args[1].equalsIgnoreCase("donador")) {
							if(args.length > 2) {
								if(!(donadoresPlayers.contains(args[2]))) {
									// Añadir jugador a donador
									donadoresPlayers.add(args[2]);
									config.set("donadoresPlayers",donadoresPlayers );
									plugin.saveConfig();
									jugador.sendMessage(plugin.nombre + ChatColor.GREEN + "Rango donador dado correctamente");
									jugador.sendMessage(plugin.nombre + ChatColor.GREEN + "Ejecuta /tech reload para aplicar cambios");
									return true;
								}
								else {
									jugador.sendMessage(plugin.nombre + ChatColor.RED + "El jugador " + args[2] + " ya tiene rango donador");
									return true;
								}
							}
							else {
								jugador.sendMessage(plugin.nombre + ChatColor.RED + "Falta jugador");
								return false;
							}
						}
						// ------------------------------------------------------------------------------------------------
						else if(args[1].equalsIgnoreCase("miembro")) {
							if(args.length > 2) {
								if(!(miembrosPlayers.contains(args[2]))) {
									// Añadir jugador a miembro
									miembrosPlayers.add(args[2]);
									config.set("miembrosPlayers",miembrosPlayers );
									plugin.saveConfig();
									jugador.sendMessage(plugin.nombre + ChatColor.GREEN + "Rango miembro dado correctamente");
									jugador.sendMessage(plugin.nombre + ChatColor.GREEN + "Ejecuta /tech reload para aplicar cambios");
									return true;
								}
								else {
									jugador.sendMessage(plugin.nombre + ChatColor.RED + "El jugador " + args[2] + " ya tiene rango miembro");
									return true;
								}
							}
							else {
								jugador.sendMessage(plugin.nombre + ChatColor.RED + "Falta jugador");
								return false;
							}
						}
						else {
							jugador.sendMessage(plugin.nombre + ChatColor.RED + "Rango no reconocido");
							jugador.sendMessage(plugin.nombre + ChatColor.RED	+ "Rangos disponibles op/donador/miembro");
							return false;
						}
					}
					else {
						jugador.sendMessage(plugin.nombre + ChatColor.RED + "Faltan argumentos");
						return false;
					}
				}
				// ------------------------------------------------------------------------------------------------
				else if(args[0].equalsIgnoreCase("remove")) {
					if(args.length > 1) {
						if(args[1].equalsIgnoreCase("op")) {
							if(args.length > 2) {
								// Quitar jugador de OP
								OPPlayers.remove(args[2]);
								config.set("OPplayers",OPPlayers );
								plugin.saveConfig();
								jugador.sendMessage(plugin.nombre + ChatColor.GREEN + "Rango OP quitado correctamente");
								jugador.sendMessage(plugin.nombre + ChatColor.GREEN + "Ejecuta /tech reload para aplicar cambios");
								return true;
							}
							else {
								jugador.sendMessage(plugin.nombre + ChatColor.RED + "Falta jugador");
								return false;
							}
						}
						// ------------------------------------------------------------------------------------------------
						else if(args[1].equalsIgnoreCase("donador")) {
							if(args.length > 2) {
								// Quitar jugador de donador
								donadoresPlayers.remove(args[2]);
								config.set("donadoresPlayers",donadoresPlayers );
								plugin.saveConfig();
								jugador.sendMessage(plugin.nombre + ChatColor.GREEN + "Rango donador quitado correctamente");
								jugador.sendMessage(plugin.nombre + ChatColor.GREEN + "Ejecuta /tech reload para aplicar cambios");
								return true;
							}
							else {
								jugador.sendMessage(plugin.nombre + ChatColor.RED + "Falta jugador");
								return false;
							}	
						}
						// ------------------------------------------------------------------------------------------------
						else if(args[1].equalsIgnoreCase("miembro")) {
							if(args.length > 2) {
								// Quitar jugador de miembro
								miembrosPlayers.remove(args[2]);
								config.set("miembrosPlayers",miembrosPlayers );
								plugin.saveConfig();
								jugador.sendMessage(plugin.nombre + ChatColor.GREEN + "Rango miembro quitado correctamente");
								jugador.sendMessage(plugin.nombre + ChatColor.GREEN + "Ejecuta /tech reload para aplicar cambios");
								return true;
							}
							else {
								jugador.sendMessage(plugin.nombre + ChatColor.RED + "Falta jugador");
								return false;
							}
						}
						else {
							jugador.sendMessage(plugin.nombre + ChatColor.RED + "Rango no reconocido");
							jugador.sendMessage(plugin.nombre + ChatColor.RED	+ "Rangos disponibles op/donador/miembro");
							return false;
						}
					}
					else {
						jugador.sendMessage(plugin.nombre + ChatColor.RED + "Faltan argumentos");
						return false;
					}
				}
				// ------------------------------------------------------------------------------------------------
				else if(args[0].equalsIgnoreCase("list")) {
					if(args.length > 1) {
						if(args[1].equalsIgnoreCase("op")) {
							// Mostrar jugadores OP
							jugador.sendMessage(plugin.nombre + ChatColor.LIGHT_PURPLE + "-----OP-----");
							for(int i=0;i<OPPlayers.size(); i++) {
								jugador.sendMessage(plugin.nombre + ChatColor.RED + OPPlayers.get(i));
							}
							jugador.sendMessage(plugin.nombre + ChatColor.LIGHT_PURPLE + "------------");
							return true;
						}
						// ------------------------------------------------------------------------------------------------
						else if(args[1].equalsIgnoreCase("donador")) {
							// Mostrar jugadores donadores
							jugador.sendMessage(plugin.nombre + ChatColor.LIGHT_PURPLE + "-----DONADORES-----");
							for(int i=0;i<donadoresPlayers.size(); i++) {
								jugador.sendMessage(plugin.nombre + ChatColor.AQUA + donadoresPlayers.get(i));
							}
							jugador.sendMessage(plugin.nombre + ChatColor.LIGHT_PURPLE + "-------------------");
							return true;
						}
						// ------------------------------------------------------------------------------------------------
						else if(args[1].equalsIgnoreCase("miembro")) {
							// Mostrar jugadores donadores
							jugador.sendMessage(plugin.nombre + ChatColor.LIGHT_PURPLE + "-----MIEMBROS-----");
							for(int i=0;i<miembrosPlayers.size(); i++) {
								jugador.sendMessage(plugin.nombre + ChatColor.GREEN + miembrosPlayers.get(i));
							}
							jugador.sendMessage(plugin.nombre + ChatColor.LIGHT_PURPLE + "------------------");
							return true;
						}
						else {
							jugador.sendMessage(plugin.nombre + ChatColor.RED + "Rango no reconocido");
							jugador.sendMessage(plugin.nombre + ChatColor.RED	+ "Rangos disponibles op/donador/miembro");
							return false;
						}
					}
					else {
						jugador.sendMessage(plugin.nombre + ChatColor.RED + "Faltan argumentos");
						return false;
					}
				}
				// ------------------------------------------------------------------------------------------------
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
					return false;
				}			
			}
			else{
				if(playerIsOP == true) {
					jugador.sendMessage(plugin.nombre + ChatColor.RED + "Faltan argumentos");
					return false;
				}
				else {
					jugador.sendMessage(plugin.nombre + ChatColor.RED + "No tienes permiso para ejecutar ese comando");
					return true;
				}	
			}
		}
		return false;
	}	



	// Autocompletar con TAB
	@Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1){
            List<String> arguments1 = new ArrayList<>();
            arguments1.add("set");
            arguments1.add("remove");
            arguments1.add("list");
            arguments1.add("help");
            return arguments1;
        }
        else if (args.length == 2){
            List<String> arguments2 = new ArrayList<>();
            arguments2.add("op");
            arguments2.add("donador");
            arguments2.add("miembro");
 
            return arguments2;
        }
        // Lista con todos los jugadores online
        else if (args.length == 3){
            List<String> onlinePlayerNames = new ArrayList<>();
            Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
            Bukkit.getServer().getOnlinePlayers().toArray(players);
            for (int i = 0; i < players.length; i++){
            	onlinePlayerNames.add(players[i].getName());
            }
 
            return onlinePlayerNames;
        } 
        return null;
    }
}




























