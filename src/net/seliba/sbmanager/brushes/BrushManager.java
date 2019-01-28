package net.seliba.sbmanager.brushes;

/*
SchematicBrushManager created by Seliba
*/

import java.util.List;
import net.seliba.sbmanager.SBManager;
import net.seliba.sbmanager.files.BrushFile;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BrushManager {

  private static BrushFile brushFile;

  public static void createBrush(Player player) {
    String uuid = player.getUniqueId().toString();
    brushFile = SBManager.getBrushFile();
    ItemStack brushItem = BrushDataManager.getBrushItem(player);
    String brushName = BrushDataManager.getBrushName(player);
    int nextKey = getNextKey(uuid);

    List<String> brushList = brushFile.getStringList(uuid + ".brushes-list");
    brushList.add(brushName);
    //TODO
    brushFile.set(uuid + ".brushes-list", brushList);
    brushFile.set(uuid + ".brushes." + nextKey + ".name", brushName);
    brushFile.set(uuid + ".brushes." + nextKey + ".command", brushItem.getItemMeta().getLore().get(3).split("\"")[3]);
    brushFile.set(uuid + ".brushes." + nextKey + ".material", brushItem.getType().name());
    brushFile.set(uuid + ".brushes." + nextKey + ".lore", brushItem.getItemMeta().getLore());
    brushFile.save();

    BrushDataManager.removeBrushItem(player);
    BrushDataManager.removeBrushName(player);

    player.sendMessage("§aDu hast erfolgreich den Brush erstellt!");
  }

  private static int getNextKey(String uuid) {
    return brushFile.getStringList(uuid + ".brushes-list").size();
  }

}
