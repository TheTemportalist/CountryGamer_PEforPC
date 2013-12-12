package CountryGamer_PEforPC.Items;

import net.minecraft.block.Block;
import CountryGamer_Core.ItemSeedBase;
import CountryGamer_PEforPC.PEforPC;

public class ItemBeetrootSeed extends ItemSeedBase {

	public ItemBeetrootSeed(int id, String modid, String name) {
		super(id, modid, name, PEforPC.beetrootPlantiD, Block.tilledField.blockID);
	}
	
	

}
