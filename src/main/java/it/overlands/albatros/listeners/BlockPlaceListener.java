package it.overlands.albatros.listeners;


import it.overlands.albatros.Albatros;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.json.JSONObject;

import javax.swing.*;
import java.util.ArrayList;

import static java.lang.Math.abs;

public class BlockPlaceListener implements Listener {

    /**
    * Un listener per confermare la posa delle chest
    **/

    // all'evento del blocco piazzato
    @EventHandler
    public void BlockPlaceEvent(BlockPlaceEvent e) {
        //System.out.println(e.getBlockPlaced().getLocation());

        //JSONObject json = new JSONObject();
        //json.put("",e.getBlockPlaced().getType())

        Block pb = e.getBlock();
        Player issuer = e.getPlayer();
        ArrayList<Player> ep = Albatros.getExecutingPlayers();

        /*se il player che ha piazzato la cassa non ha attivato il comando l'evento è ignorato */
        if(!ep.contains(issuer)){return;}

        if(pb.getType().equals(Material.CHEST)){
            Chest placed_block= (Chest) pb;
            //se è nel mondo giusto
            //TODO mettere il nome del mondo giusto
            if(e.getPlayer().getWorld().getName().equals("world")){
                //ha piazzato una chest
                if(!distanceCheck(placed_block,issuer)){
                    issuer.sendMessage("Solo casse singole! piazzale ad un blocco di distanza"
                    +"l'una dall'altra!!");
                    e.setCancelled(true);
                    return;
                }

                int aux = Albatros.addChest2Player(issuer.getDisplayName(),placed_block);
                /** piazzo la chest nell'arraylist del player in questione
                 * aux mi ritorna -1 se ho superato il limite, altrimenti il numero di chest
                 * attualmente piazzate.
                 */

                int _MAXNUMCHEST = Albatros.get_MAXNUMCHEST();

                if(aux>0 && aux< _MAXNUMCHEST){
                    String message = "Chest confermata, te ne mancano: " + (_MAXNUMCHEST - aux);
                    issuer.sendMessage(message);
                }
                if (aux == -1){
                    e.setCancelled(true);
                    issuer.sendMessage("Limite chest raggiunto operazione terminata");
                }
            }

        }
    }

    private boolean distanceCheck(Chest chest, Player player) {
        Location loc = chest.getLocation();
        double nx = abs(loc.getX());
        double nz = abs(loc.getZ());

        ArrayList<Chest> listablocchi = Albatros.getOnePlayerChestMap(player);
        if(listablocchi == null) { return true;}

        for(Chest r : listablocchi){
            //TODO
        }
        return true;
    }
}
