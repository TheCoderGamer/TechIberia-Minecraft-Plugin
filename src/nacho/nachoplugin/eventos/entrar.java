package nacho.nachoplugin.eventos;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

import nacho.nachoplugin.main;

public class entrar implements Listener{

	// Esto sirve para importar variables de otra clase
	private main plugin;
	public entrar(main plugin) {
		this.plugin = plugin;
		// Esto sirve para registrar el evento
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
		
	boolean playerIsOp;
	boolean playerIsDonador;
	
	// Se ejecuta al entrar un jugador
	@EventHandler
	public void alEntrar(PlayerJoinEvent event) {
		// Asigna el jugador que ha entrado
		Player jugador = event.getPlayer();
		// Asigna el archivo de configuracion
		FileConfiguration config = plugin.getConfig();
		
		// Comprobar si es OP
		List<String> OPplayers = (ArrayList<String>) config.getStringList("OPplayers");
		String nombreJugador = jugador.getName();
		if(OPplayers.contains(nombreJugador)) {
			playerIsOp = true;
		}else {
			playerIsOp = false;
		}
		
		// Comprobar si es donador
		List<String> donadoresPlayers = (ArrayList<String>) config.getStringList("donadoresPlayers");
		if(donadoresPlayers.contains(nombreJugador)) {
			playerIsDonador = true;
		}else {
			playerIsDonador = false;
		}
		
		
		
		
		// --- Mensaje de bienvenida ---
		Boolean showMensajeBienvenida = config.getBoolean("config.mensaje-bienvenida");	
		if(showMensajeBienvenida == true) {
			String texto = "config.mensaje-bienvenida-texto";
			String textoOP = "config.mensaje-bienvenida-texto-OP";
			String textoDonador = "config.mensaje-bienvenida-texto-donador";
			
			if (playerIsOp == true) {
				event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', config.getString(textoOP).replaceAll("%player%", jugador.getName())));
			}
			else if (playerIsDonador == true) {
				event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', config.getString(textoDonador).replaceAll("%player%", jugador.getName())));
			}
			else {
				event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', config.getString(texto).replaceAll("%player%", jugador.getName())));
			}
		}		
	}






}
