package nacho.nachoplugin.eventos;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import nacho.nachoplugin.main;

public class chat implements Listener{
			
	boolean playerIsOP;
	boolean playerIsDonador;
	
	// Esto sirve para importar variables de otra clase
			private main plugin;
			public chat(main plugin) {
				this.plugin = plugin;
				// Esto sirve para registrar el evento
				plugin.getServer().getPluginManager().registerEvents(this, plugin);
			}	
	
	@EventHandler
	public void modificarChat(AsyncPlayerChatEvent event){
		
		Player jugador = event.getPlayer();
		String message = event.getMessage();
		
		// Asigna el archivo de configuracion
		FileConfiguration config = plugin.getConfig();
				
		// Comprobar si es OP
		List<String> OPplayers = (ArrayList<String>) config.getStringList("OPplayers");
		String nombreJugador = jugador.getName();
		if(OPplayers.contains(nombreJugador)) {
			playerIsOP = true;
		}else {
			playerIsOP = false;
		}		
		// Comprobar si es donador
		List<String> donadoresPlayers = (ArrayList<String>) config.getStringList("donadoresPlayers");
		if(donadoresPlayers.contains(nombreJugador)) {
					playerIsDonador = true;
		}else {
			playerIsDonador = false;
		}
		
		
		if (playerIsOP == true) {
			event.setMessage(message);
			event.setFormat(ChatColor.DARK_RED + jugador.getName() + ChatColor.DARK_GRAY+" >>> " + ChatColor.GRAY+message);
		}
		else if (playerIsDonador) {
			event.setMessage(message);
			event.setFormat(ChatColor.AQUA + jugador.getName() + ChatColor.DARK_GRAY+" >>> " + ChatColor.GRAY+message);
		}
		else {
			event.setMessage(message);
			event.setFormat(ChatColor.GRAY + jugador.getName() + ChatColor.DARK_GRAY+" >>> " + ChatColor.GRAY+message);
		}
		
		
	}
}






























