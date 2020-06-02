package nacho.nachoplugin.mecanicas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.StructureType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitScheduler;

import nacho.nachoplugin.main;

	public class shulkerRespawn implements Listener {
	    
		
		// Esto sirve para importar variables de otra clase
		int taskID;
		long ticks;
		private main plugin;
		public shulkerRespawn(main plugin, long ticks) {
			this.plugin = plugin;
			this.ticks = ticks;
			// Esto sirve para registrar el evento
			plugin.getServer().getPluginManager().registerEvents(this, plugin);
		}
		
		
		
		public void repetidor() {
			BukkitScheduler sh = Bukkit.getServer().getScheduler();
			taskID =  sh.scheduleSyncRepeatingTask(plugin, new Runnable() {
				
				@Override
				public void run() {
					shulkerRespawner();
					
				}
			}, 0L, ticks);
		}
	   
	
	
	
	private void shulkerRespawner() {
		FileConfiguration config = plugin.getConfig();
		
			
		int radius = 100; // Radio de busqueda de EndCity alrededor del jugador
		radius = config.getInt("config.mecanicas.chunkRadio");
		int chunkRadius = 5; // Radio de chunks en los que se comprueba si estas cerca de endCity 
	
        Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
        Bukkit.getServer().getOnlinePlayers().toArray(players);
        for (int i = 0; i < players.length; i++){
		
        // Asigna variables
	    Location location = (players[i].getLocation());
	    Location structure = (players[i].getWorld().locateNearestStructure(location, StructureType.END_CITY, radius, false));
	    boolean shulkerActive = false;
	    
	    // Comprueba si cerca hay una endCitys
	    if (structure == null) {
	    	shulkerActive = false;
	    	return;
	    }
	    else {
	    	shulkerActive = true;
	    }

	    if (shulkerActive == true) { 
	    	
	    	
	        // Matemagia
		    if ((Math.abs(structure.getChunk().getX() - location.getChunk().getX()) > chunkRadius))
		        return;
		    if ((Math.abs(structure.getChunk().getZ() - location.getChunk().getZ()) > chunkRadius))
		        return;
   
		    // Si llega hasta aqui es que el jugador esta cerca de una endCity (a "chunkRadius" chunks de una endcity) 
		    
		    List<Location> purpurblocks = obtenerListaBloquesSpawneo(structure.getChunk());
				
			Random random = new Random();
			
			int cantidadPorTanda = 5;
			cantidadPorTanda = config.getInt("config.mecanicas.cantidadMaxPorTanda");
			int probabilidadBloqueValido = 10;
			probabilidadBloqueValido = config.getInt("config.mecanicas.probabilidadBloqueValido");
			
			int randTanda = random.nextInt(cantidadPorTanda);
			
			for(int p=0;p<purpurblocks.size(); p++) {
				//players[i].sendMessage(ChatColor.GREEN + "Posible spawn shulker: X" + purpurblocks.get(p).getBlockX() + " Y:" + purpurblocks.get(p).getBlockY()+ " Z:" + purpurblocks.get(p).getBlockZ());			
				
				int randValido = random.nextInt(probabilidadBloqueValido);
				
				// 1 entre 10 de que aparezca shulker
				if(randValido == 0) {
					// Calcular numero para elejir un bloque aleatorio
					int randBlockAElegir = random.nextInt(purpurblocks.size());
					
					boolean shulkerCerca = false;
					List<Entity> entidadesCerca = new ArrayList<Entity>();
					entidadesCerca = (List<Entity>) structure.getWorld().getNearbyEntities(purpurblocks.get(randBlockAElegir), 2, 2, 2);
					
					// Comprobar si hay mas entidades cerca
					if(entidadesCerca.isEmpty() == false) {
						for(int y = 0; y<entidadesCerca.size();y++){
							// Comprobar si hay un shulker a 1 bloque a la redonda
							if(entidadesCerca.get(y).getType() == EntityType.SHULKER){
								shulkerCerca = true;		
							}
						}
					}
					if (shulkerCerca == false) {
						// Spawnear shulkers de poco en poco (de 1 a 10 por tanda)
						if (randTanda < cantidadPorTanda+1) {							
							// Spawn shulker
							players[i].getWorld().spawnEntity(purpurblocks.get(randBlockAElegir), EntityType.SHULKER);
							//players[i].sendMessage(ChatColor.RED + "Shulker spawn X:" + purpurblocks.get(p).getBlockX() + " Y:" + purpurblocks.get(p).getBlockY()+ " Z:" + purpurblocks.get(p).getBlockZ());
							randTanda = randTanda+1;
						}
					}
					else {
						//players[i].sendMessage(ChatColor.YELLOW + "Shulker no spawnea");
					}
				}
			}	            
		}       
	}	
}
	
	// Obtener una lista con todos los bloques en los que puede spawnear un shulker
			public static List<Location> obtenerListaBloquesSpawneo(final Chunk chunk) {
		        List<Location> purpurBlocks = new ArrayList<Location>();
		        // Comprueba todos los bloques del chunk
		        final int maxY = chunk.getWorld().getMaxHeight()-1; 
		        for (int x = 0; x < 16; ++x) {
		            for (int y = 0; y <= maxY; ++y) {
		                for (int z = 0; z < 16; ++z) {
		                	// Comprueba si es del material correcto
		                	if(chunk.getBlock(x, y, z).getType() == Material.PURPUR_BLOCK || chunk.getBlock(x, y, z).getType() == Material.DIRT) {
		                		// Comprueba si ya esta el bloque
		                		if(!(purpurBlocks.contains(chunk.getBlock(x, y, z).getLocation()))) {
		                			// Comprueba si encima hay un bloque de aire
		                			if(chunk.getBlock(x, y+1, z).getType() == Material.AIR || chunk.getBlock(x, y+1, z).getType() == Material.WITHER_ROSE) {
		                				// Comprueba si hay 2 bloques de aire en total
		                				if(chunk.getBlock(x, y+2, z).getType() == Material.AIR || chunk.getBlock(x, y+2, z).getType() == Material.TRIPWIRE) {
		                					// Añade el bloque de encima del bloque elegido
			                				purpurBlocks.add(chunk.getBlock(x, y+1, z).getLocation());
		                				}
			                		}	
		                		}	                				
		                	}
		                }                
		            }        
		        }
		        return purpurBlocks;
		    }
	
	
	
}	
	
	
	
	
	
	
	
	
	/*
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
	 */
	
	
	