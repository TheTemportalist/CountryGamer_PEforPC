package CountryGamer_PEforPC.Blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import CountryGamer_Core.BlockContainerBase;
import CountryGamer_PEforPC.PEforPC;
import CountryGamer_PEforPC.Blocks.TileEnt.TileEntityReactor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockNetherCore extends BlockContainerBase {

	public static int maximumCharge = 5;// 45;

	public BlockNetherCore(int id, Material mat, String modid, String name) {
		super(id, mat, modid, name);
		this.setResistance(4000.0F);
	}

	@SideOnly(Side.CLIENT)
	private Icon[] icons;

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.icons = new Icon[3];

		String textureName = this.getUnlocalizedName().substring(
				this.getUnlocalizedName().indexOf(".") + 1);
		this.icons[1] = iconRegister.registerIcon(this.modid + ":"
				+ textureName + "_Full");
		this.icons[2] = iconRegister.registerIcon(this.modid + ":"
				+ textureName + "_Active");
		this.icons[0] = iconRegister.registerIcon(this.modid + ":"
				+ textureName + "_Drained");

	}

	@SideOnly(Side.CLIENT)
	/**
	 * @param side
	 * @param metadata
	 */
	public Icon getIcon(int side, int metadata) {
		if (metadata < 0)
			metadata = 0;

		if (metadata == 0) // Drained
			return this.icons[0];
		else if (metadata == BlockNetherCore.maximumCharge + 1) // Active
			return this.icons[2];
		else
			// Between 0 & MaxCharge; Charged
			return this.icons[1];

	}

	public int damageDropped(int meta) {
		return 1;
	}

	public void onBlockDestroyedByPlayer(World world, int x, int y, int z,
			int meta) {
		TileEntity tEnt = world.getBlockTileEntity(x, y, z);
		if (tEnt instanceof TileEntityReactor) {
			TileEntityReactor tileEnt = (TileEntityReactor) tEnt;
			if (meta > BlockNetherCore.maximumCharge) {
				super.onBlockDestroyedByPlayer(world, x, y, x, tileEnt.charge);
			}
		}

	}

	public TileEntity createNewTileEntity(World world) {
		return new TileEntityReactor();
	}

	private void setBlockNew(World world, int coreX, int coreY, int coreZ,
			int x, int y, int z, int blockiD) {
		this.setBlockMetaWithNotifyNew(world, coreX, coreY, coreZ, x, y, z,
				blockiD, 0, 3);
	}

	private void setBlockMetaWithNotifyNew(World world, int coreX, int coreY,
			int coreZ, int x, int y, int z, int blockiD, int meta, int flag) {
		Random rand = new Random();
		boolean degrate = rand.nextInt(100) % 5 == 0;
		if (degrate) {
			TileEntity tEnt = world.getBlockTileEntity(coreX, coreY, coreZ);
			if (tEnt != null) {
				if (tEnt instanceof TileEntityReactor) {
					// PEforPC.log.info("Setting Degration");
					((TileEntityReactor) tEnt).setBlockDegrate(x, y, z);
				}
			} else {
				// PEforPC.log.info("No Tile Ent");
			}
		}

		world.setBlock(x, y, z, blockiD, meta, flag);

	}

	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int side, float par7, float par8, float par9) {
		if (!world.isRemote) {
			if (player.isSneaking())
				return false;

			TileEntity tEnt = world.getBlockTileEntity(x, y, z);
			if (tEnt instanceof TileEntityReactor) {
				TileEntityReactor tileEnt = (TileEntityReactor) tEnt;
				if (world.getBlockMetadata(x, y, z) <= 0)
					tileEnt.isExhausted = true;
				PEforPC.log.info("isActive: " + tileEnt.isActive);
				PEforPC.log.info("isExhausted: " + tileEnt.isExhausted);
				if (!tileEnt.isActive && !tileEnt.isExhausted) {
					if (this.structureCheck(world, x, y, z)) {
						PEforPC.log.info("Making Spire");
						this.createSpire(world, x, y, z);
						tileEnt.isActive = true;

						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean structureCheck(World world, int x, int y, int z) {
		int cobble = Block.cobblestone.blockID;
		int gold = Block.blockGold.blockID;

		boolean bottomGold = world.getBlockId(x - 1, y - 1, z - 1) == gold
				&& world.getBlockId(x + 1, y - 1, z - 1) == gold
				&& world.getBlockId(x - 1, y - 1, z + 1) == gold
				&& world.getBlockId(x + 1, y - 1, z + 1) == gold;
		boolean cobble1 = world.getBlockId(x - 1, y - 1, z - 0) == cobble
				&& world.getBlockId(x - 0, y - 1, z - 1) == cobble
				&& world.getBlockId(x + 1, y - 1, z - 0) == cobble
				&& world.getBlockId(x - 0, y - 1, z + 1) == cobble
				&& world.getBlockId(x - 0, y - 1, z - 0) == cobble;
		boolean cobble2 = world.getBlockId(x - 1, y - 0, z - 1) == cobble
				&& world.getBlockId(x + 1, y - 0, z - 1) == cobble
				&& world.getBlockId(x - 1, y - 0, z + 1) == cobble
				&& world.getBlockId(x + 1, y - 0, z + 1) == cobble;
		boolean cobble3 = world.getBlockId(x - 1, y + 1, z - 0) == cobble
				&& world.getBlockId(x - 0, y + 1, z - 1) == cobble
				&& world.getBlockId(x + 1, y + 1, z - 0) == cobble
				&& world.getBlockId(x - 0, y + 1, z + 1) == cobble
				&& world.getBlockId(x - 0, y + 1, z - 0) == cobble;

		return bottomGold && cobble1 && cobble2 && cobble3;

	}

	private void createSpire(World world, int x, int y, int z) {
		int block = Block.netherrack.blockID;
		int radiusFromCore = 7;
		int baseofSpire = y - 2;
		int spireBoxHeight = 1 + 5 + 1 + 5;// floor+4+floor+4+spire
		// hollow it out
		this.spireHollowInterior(world, x, z, baseofSpire, radiusFromCore, y
				+ spireBoxHeight + 40);
		// floors of spire
		this.spireFloor(world, x, y, z, baseofSpire, block, radiusFromCore);
		this.spireFloor(world, x, y, z, baseofSpire + 6, block, radiusFromCore);
		this.spireFloor(world, x, y, z, baseofSpire + 12, block, radiusFromCore);
		// ~~~~~~~~~~~~~~~
		// Walls
		// +X
		int wallX = x + radiusFromCore + 1;
		for (int j = baseofSpire; j <= baseofSpire + spireBoxHeight; j++) {
			for (int k = z - radiusFromCore - 1; k <= z + radiusFromCore + 1; k++) {
				if (!this.isValidSpireWall(world.getBlockId(wallX, j, k))) {
					// world.destroyBlock(wallX, j, k, true);
					this.setBlockNew(world, x, y, z, wallX, j, k, block);
				}
			}
		}
		// -X
		wallX = x - radiusFromCore - 1;
		for (int j = baseofSpire; j <= baseofSpire + spireBoxHeight; j++) {
			for (int k = z - radiusFromCore - 1; k <= z + radiusFromCore + 1; k++) {
				if (!this.isValidSpireWall(world.getBlockId(wallX, j, k))) {
					// world.destroyBlock(wallX, j, k, true);
					this.setBlockNew(world, x, y, z, wallX, j, k, block);
				}
			}
		}
		// +Z
		int wallZ = z + radiusFromCore + 1;
		for (int j = baseofSpire; j <= baseofSpire + spireBoxHeight; j++) {
			for (int i = x - radiusFromCore - 1; i <= x + radiusFromCore + 1; i++) {
				if (!this.isValidSpireWall(world.getBlockId(i, j, wallZ))) {
					// world.destroyBlock(i, j, wallZ, true);
					this.setBlockNew(world, x, y, z, i, j, wallZ, block);
				}
			}
		}
		// -Z
		wallZ = z - radiusFromCore - 1;
		for (int j = baseofSpire; j <= baseofSpire + spireBoxHeight; j++) {
			for (int i = x - radiusFromCore - 1; i <= x + radiusFromCore + 1; i++) {
				if (!this.isValidSpireWall(world.getBlockId(i, j, wallZ))) {
					// world.destroyBlock(i, j, wallZ, true);
					this.setBlockNew(world, x, y, z, i, j, wallZ, block);
				}
			}
		}
		// ~~~~~~~~~~~~~~~
		// spire
		// this.spire(world, x, z, baseofSpire + 12, block, radiusFromCore);
		// ~~~~~~~~~~~~~~~
		// Lighting
		// this.spireLight(world, x, z, baseofSpire, Block.glowStone.blockID,
		// radiusFromCore);
		block = PEforPC.glowObsidian.blockID;
		for (int i = x - 1; i <= x + 1; i++) {
			for (int k = z - 1; k <= z + 1; k++) {
				world.setBlock(i, y - 1, k, block);
			}
		}
		world.setBlock(x - 1, y - 0, z - 1, block);
		world.setBlock(x + 1, y - 0, z - 1, block);
		world.setBlock(x - 1, y - 0, z + 1, block);
		world.setBlock(x + 1, y - 0, z + 1, block);
		world.setBlock(x + 1, y + 1, z - 0, block);
		world.setBlock(x - 1, y + 1, z - 0, block);
		world.setBlock(x - 0, y + 1, z - 0, block);
		world.setBlock(x - 0, y + 1, z + 1, block);
		world.setBlock(x - 0, y + 1, z - 1, block);
		// ~~~~~~~~~~~~~~~
		// Spawner
		world.setBlock(x, y + 5, z, Block.mobSpawner.blockID, 0, 2);
		TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner) world
				.getBlockTileEntity(x, y + 5, z);

		if (tileentitymobspawner != null) {
			tileentitymobspawner.getSpawnerLogic().setMobID("Blaze");
		}

	}

	private boolean isValidSpireWall(int blockiD) {
		return blockiD == Block.netherrack.blockID
				|| blockiD == Block.netherBrick.blockID
				|| blockiD == Block.bedrock.blockID
				|| blockiD == Block.obsidian.blockID
				|| blockiD == Block.glowStone.blockID;
	}

	private void spireFloor(World world, int coreX, int coreY, int coreZ,
			int floorY, int blockiD, int radiusFromCore) {
		for (int i = coreX - radiusFromCore - 1; i <= coreX + radiusFromCore
				+ 1; i++) {
			for (int k = coreZ - radiusFromCore - 1; k <= coreZ
					+ radiusFromCore + 1; k++) {
				if (!this.isValidSpireWall(world.getBlockId(i, floorY, k))) {
					// world.destroyBlock(i, floorY, k, true);
					this.setBlockNew(world, coreX, coreY, coreZ, i, floorY, k,
							blockiD);
				}
			}
		}
	}

	private void spireLight(World world, int coreX, int coreZ, int floorY,
			int blockiD, int radiusFromCore) {
		for (int layer = floorY; layer <= floorY + 5; layer += 5) {
			world.setBlock(coreX - 3, layer, coreZ - 3, blockiD);
			world.setBlock(coreX + 3, layer, coreZ - 3, blockiD);
			world.setBlock(coreX - 3, layer, coreZ + 3, blockiD);
			world.setBlock(coreX + 3, layer, coreZ + 3, blockiD);
		}

	}

	private void spireHollowInterior(World world, int coreX, int coreZ,
			int spireBase, int radiusFromCore, int height) {
		int minWallY = spireBase;
		int maxWallY = height;
		int minWallX = coreX - radiusFromCore - 1;
		int maxWallX = coreX + radiusFromCore + 1;
		int minWallZ = coreZ - radiusFromCore - 1;
		int maxWallZ = coreZ + radiusFromCore + 1;
		for (int j = maxWallY; j >= minWallY; j--) {
			for (int i = minWallX; i <= maxWallX; i++) {
				for (int k = minWallZ; k <= maxWallZ; k++) {
					if (world.getBlockId(i, j, k) != this.blockID) {
						// world.destroyBlock(i, j, k, true);
						world.setBlock(i, j, k, 0);
					}
				}
			}
		}

	}

	private void spire(World world, int coreX, int coreZ, int floorY,
			int blockiD, int radiusFromCore) {

		int maxWallX = coreX + radiusFromCore + 1;
		int maxWallZ = coreZ + radiusFromCore + 1;
		int minWallX = coreX - radiusFromCore - 1;
		int minWallZ = coreZ - radiusFromCore - 1;
		int yLayer = floorY;
		int i, k;

		k = minWallZ;
		while (k <= maxWallZ - 3) {
			yLayer++;
			k += 2;
			for (int i1 = maxWallX; i1 >= maxWallX - 2; i1--) {
				for (int k1 = k; k1 <= maxWallZ; k1++) {
					world.setBlock(i1, yLayer, k1, blockiD);
				}
			}
		}

		i = maxWallX;
		while (i >= minWallX + 3) {
			yLayer++;
			i -= 2;
			for (int k1 = maxWallZ; k1 >= maxWallZ - 2; k1--) {
				for (int i1 = i; i1 >= minWallX; i1--) {
					world.setBlock(i1, yLayer, k1, blockiD);
				}
			}
		}

	}
}
