package nacho.nachoplugin.mecanicas;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.StructureType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import nacho.nachoplugin.main;

	public class shulkerRespawn implements Listener {
	    
		
		// Esto sirve para importar variables de otra clase
		private main plugin;
		public shulkerRespawn(main plugin) {
			this.plugin = plugin;
			// Esto sirve para registrar el evento
			plugin.getServer().getPluginManager().registerEvents(this, plugin);
		}
		
		


	    private Random random = new Random();

	    @EventHandler
	    public void onCreatureSpawn(CreatureSpawnEvent event) {
	    	// Asigna el archivo de configuracion
	    	FileConfiguration config = plugin.getConfig();
			
	    	// Comprobar si ShulkerRespawn esta activado o no
	    	Boolean respawnShulkers = config.getBoolean("config.mecanicas.shulkerRespawn");
	    	if(respawnShulkers == true){
		    	    	
	    		// Comprueba si es un enderman
		    	if (event.getEntityType() != EntityType.ENDERMAN)
		            return;
		        // Comprueba si se a spawneado naturalmente
		    	if (event.getSpawnReason() != SpawnReason.NATURAL)
		            return;
		    	
		    	// Asigna variables
		        LivingEntity enderman = event.getEntity();
		        Location location = enderman.getLocation();
		        Location structure = enderman.getWorld().locateNearestStructure(location, StructureType.END_CITY, 0, false);
	
		        // Comprueba esta en una endCitys
		        if (structure == null)
		            return;
		        
		        // Matematicas akka Matemagia
		        if ((Math.abs(structure.getChunk().getX() - location.getChunk().getX()) > 1))
		            return;
		        if ((Math.abs(structure.getChunk().getZ() - location.getChunk().getZ()) > 1))
		            return;
		        
		        // Sacar lista de bloques de config.yml
				List<String> bloques = (ArrayList<String>) config.getStringList("config.mecanicas.bloquesRespawneo");
		        
		        // Comprueba el bloque de debajo
		        String block = location.add(0, -1, 0).getBlock().getType().toString();
		        if (!bloques.contains(block))
		            return;
		        
		        // Cancela aleatoriamente
		        int spawnRate = config.getInt("config.mecanicas.shulkerRespawnRate");
		        if (random.nextInt(10) >= spawnRate)
		            return;
	
		        // Spawnea shulker donde el enderman
		        Entity shulker = enderman.getWorld().spawnEntity(enderman.getLocation(), EntityType.SHULKER);
		        if (shulker != null)
		            event.setCancelled(true);
		    }	    
	    }
	}
	
	
	
	
	
	
	
	
	
	
	
	
	