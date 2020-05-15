package nacho.nachoplugin.comandos;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import nacho.nachoplugin.main;

public class comandoTest implements CommandExecutor {

	public comandoTest(main plugin) {
	}
	
	
	@Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		
		Player jugador = (Player) sender;
		List<Location> purpurblocks = obtenerListaBloquesSpawneo(jugador.getLocation().getChunk());
		
		Random random = new Random();
		int cantidadPorTanda = 5;
		
		int rand2 = random.nextInt(cantidadPorTanda);
		for(int p=0;p<purpurblocks.size(); p++) {
			jugador.sendMessage(ChatColor.GREEN + "Posible spawn shulker: X" + purpurblocks.get(p).getBlockX() + " Y:" + purpurblocks.get(p).getBlockY()+ " Z:" + purpurblocks.get(p).getBlockZ());			
			
			int rand = random.nextInt(10);
			
			// 1 entre 10 de que aparezca shulker
			if(rand == 0) {
				boolean shulkerCerca = false;
				List<Entity> entidadesCerca = new ArrayList<Entity>();
				entidadesCerca = (List<Entity>) jugador.getWorld().getNearbyEntities(purpurblocks.get(p), 2, 2, 2);
				
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
					if (rand2 < cantidadPorTanda+1) {
						// Spawn shulker
						jugador.getWorld().spawnEntity(purpurblocks.get(p), EntityType.SHULKER);
						jugador.sendMessage(ChatColor.RED + "Shulker spawn X:" + purpurblocks.get(p).getBlockX() + " Y:" + purpurblocks.get(p).getBlockY()+ " Z:" + purpurblocks.get(p).getBlockZ());
					}
				}
				else {
					jugador.sendMessage(ChatColor.YELLOW + "Shulker no spawnea");
				}
				
			}
		}
		
		return true;
    }
	
	/*public static List<Entity> nearbyEntities(Location location, double radius) {
        List<Entity> entities = new ArrayList<Entity>();

        for (Entity entity : location.getWorld().getEntities()) {
            if (entity.getWorld() != location.getWorld()) {
                continue;
            } else if (entity instanceof Player && ((Player) entity).getGameMode().equals(GameMode.SPECTATOR)) {
                continue; //Don't want spectators
            } else if (entity.getLocation().distanceSquared(location) <= radius * radius) {
                entities.add(entity);
            }
        }
        return entities;
    }*/
	
		// Obtener una lista con todos los bloques en los que puede spawnear un shulker
		public static List<Location> obtenerListaBloquesSpawneo(final Chunk chunk) {
	        List<Location> purpurBlocks = new ArrayList<Location>();
	        // Comprueba todos los bloques del chunk
	        final int maxY = chunk.getWorld().getMaxHeight()-1; 
	        for (int x = 0; x < 16; ++x) {
	            for (int y = 0; y <= maxY; ++y) {
	                for (int z = 0; z < 16; ++z) {
	                	// Comprueba si es del material correcto
	                	if(chunk.getBlock(x, y, z).getType() == Material.PURPUR_BLOCK) {
	                		// Comprueba si ya esta el bloque
	                		if(!(purpurBlocks.contains(chunk.getBlock(x, y, z).getLocation()))) {
	                			// Comprueba si encima hay un bloque de aire
	                			if(chunk.getBlock(x, y+1, z).getType() == Material.AIR) {
	                				// Comprueba si hay 2 bloques de aire en total
	                				if(chunk.getBlock(x, y+2, z).getType() == Material.AIR) {
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
