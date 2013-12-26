package CountryGamer_PEforPC.Blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import CountryGamer_Core.BlockBase;

public class BlockGlowObsidian extends BlockBase {

	public BlockGlowObsidian(int id, Material mat, String modid, String name) {
		super(id, mat, modid, name);
		// glowstone = 1.0F
		this.setLightValue(0.7F);
		this.setHardness(50.0F);
		this.setResistance(2000.0F);
	}

	public int idDropped(int par1, Random par2Random, int par3) {
		return Block.obsidian.blockID;
	}

}
