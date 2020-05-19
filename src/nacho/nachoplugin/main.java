package nacho.nachoplugin;
import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

// Clases
import nacho.nachoplugin.comandos.comandoDiscord;
import nacho.nachoplugin.comandos.comandoPrincipal;
import nacho.nachoplugin.comandos.comandoRango;
import nacho.nachoplugin.comandos.comandoWeb;
import nacho.nachoplugin.eventos.chat;
import nacho.nachoplugin.eventos.entrar;
import nacho.nachoplugin.eventos.scoreboardAdmin;
import nacho.nachoplugin.mecanicas.shulkerRespawn;


public class main extends JavaPlugin{
	PluginDescriptionFile pdffile = getDescription();
	public String version = pdffile.getVersion();
	public String nombre = ChatColor.YELLOW + "[" + ChatColor.BLUE + pdffile.getName() + ChatColor.YELLOW + "] ";
	public String rutaConfig;
	
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "<----------------------------->");
		Bukkit.getConsoleSender().sendMessage(nombre + ChatColor.GREEN + "ha sido activado" + ChatColor.DARK_GREEN +"(v " + version + ")");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "<----------------------------->");
		registrarComandos();
		registrarEventos();
		registrarConfig();
		registrarRepeticiones();
		
		// Scoreboard
		if(getConfig().getBoolean("Scoreboard.active") == true) {
			scoreboardAdmin scoreboard = new scoreboardAdmin(this);
			scoreboard.crearScoreboard(Integer.valueOf(getConfig().getInt("Scoreboard.ticks")));
		}
	}
	
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "<----------------------------->");
		Bukkit.getConsoleSender().sendMessage(nombre + ChatColor.GREEN + "ha sido desactivado" + ChatColor.DARK_GREEN +"(v " + version + ")");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "<----------------------------->");
	}
	
	public void registrarComandos() {
		this.getCommand("web").setExecutor(new comandoWeb(this));
		this.getCommand("discord").setExecutor(new comandoDiscord(this));
		this.getCommand("tech").setExecutor(new comandoPrincipal(this));
		this.getCommand("rango").setExecutor(new comandoRango(this));
		//this.getCommand("test").setExecutor(new comandoTest(this));
	}
	
	public void registrarEventos() {
		new entrar(this);
		//new shulkerRespawn(this);
		new chat(this);
	}
	
	public void registrarConfig() {
		File config = new File(this.getDataFolder(),"config.yml");
		rutaConfig = config.getPath();
		if(!config.exists()) {
			this.getConfig().options().copyDefaults(true);
			saveConfig();
		}
	}
	
	
	public void registrarRepeticiones() {
		shulkerRespawn shulkerRespawn1 = new shulkerRespawn(this, 600);
		shulkerRespawn1.repetidor();
	}
	
	
}
