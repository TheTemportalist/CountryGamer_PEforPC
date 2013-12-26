package CountryGamer_PEforPC.Blocks;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import CountryGamer_Core.BlockContainerBase;
import CountryGamer_PEforPC.PEforPC;
import CountryGamer_PEforPC.Blocks.TileEnt.TileEntityNethergizer;

public class BlockNethergizer extends BlockContainerBase {

	public BlockNethergizer(int id, Material mat, String modid, String name) {
		super(id, mat, modid, name);
		this.tileEnt = new TileEntityNethergizer();
	}

	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int side, float par7, float par8, float par9) {
		if (!player.isSneaking()) {
			player.openGui(PEforPC.instance, 0, world, x, y, z);
			return true;
		}
		return false;
	}



}
