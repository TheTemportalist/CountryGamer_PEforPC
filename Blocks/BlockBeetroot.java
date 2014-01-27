package CountryGamer_PEforPC.Blocks;

import net.minecraft.item.Item;
import CountryGamer_Core.Blocks.BlockCropBase;
import CountryGamer_PEforPC.PEforPC;

public class BlockBeetroot extends BlockCropBase {

	public BlockBeetroot(int id, String modid, String name) {
		super(id, modid, name, PEforPC.beetrootSeed, PEforPC.beetroot);
	}

}
