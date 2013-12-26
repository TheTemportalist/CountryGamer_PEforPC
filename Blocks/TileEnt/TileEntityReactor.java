package CountryGamer_PEforPC.Blocks.TileEnt;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import CountryGamer_PEforPC.PEforPC;
import CountryGamer_PEforPC.Blocks.BlockNetherCore;

public class TileEntityReactor extends TileEntity {

	public boolean isActive = false;
	public int maxCharge = BlockNetherCore.maximumCharge * 20;
	public int charge;
	public boolean isExhausted = false;
	private static ItemStack[] lootTable = new ItemStack[] {
			new ItemStack(Item.ingotGold), new ItemStack(Item.melonSeeds),
			new ItemStack(Item.painting), new ItemStack(Item.glowstone),
			new ItemStack(Block.doorWood), new ItemStack(Block.mushroomBrown),
			new ItemStack(Block.mushroomRed), new ItemStack(Item.bone),
			new ItemStack(Block.cactus), new ItemStack(Item.arrow),
			new ItemStack(Block.reed), new ItemStack(Item.bowlEmpty),
			new ItemStack(Item.bed), new ItemStack(Item.bow),
			new ItemStack(Item.netherQuartz), new ItemStack(Item.feather) };
	private HashMap<Integer, int[]> blocksToDelete = new HashMap();

	/**
	 * Reads a tile entity from NBT.
	 */
	public void readFromNBT(NBTTagCompound tagCom) {
		super.readFromNBT(tagCom);

		NBTTagList degrateList = tagCom.getTagList("degrateList");
		this.blocksToDelete.clear();
		for (int index = 0; index < degrateList.tagCount(); index++) {
			NBTTagCompound coorTagCom = (NBTTagCompound) degrateList
					.tagAt(index);
			this.blocksToDelete.put(coorTagCom.getInteger("Index"),
					coorTagCom.getIntArray("Coor"));
		}
		this.isExhausted = tagCom.getBoolean("isExhausted");
		this.charge = tagCom.getInteger("currentTimer");
		this.maxCharge = tagCom.getInteger("maxTimer");
		this.isActive = tagCom.getBoolean("isActive");

	}

	/**
	 * Writes a tile entity to NBT.
	 */
	public void writeToNBT(NBTTagCompound tagCom) {
		super.writeToNBT(tagCom);

		tagCom.setBoolean("isActive", this.isActive);
		tagCom.setInteger("maxTimer", this.maxCharge);
		tagCom.setInteger("currentTimer", this.charge);
		tagCom.setBoolean("isExhausted", this.isExhausted);
		NBTTagList degrateList = new NBTTagList();
		for (int index = 0; index < this.blocksToDelete.size(); index++) {
			NBTTagCompound coorTagCom = new NBTTagCompound();
			coorTagCom.setInteger("Index", index);
			coorTagCom.setIntArray("Coor", this.blocksToDelete.get(index));
			degrateList.appendTag(coorTagCom);
		}
		tagCom.setTag("degrateList", degrateList);

	}

	public void setBlockDegrate(int x, int y, int z) {
		this.blocksToDelete.put(this.blocksToDelete.size(),
				new int[] { x, y, z });
	}

	/**
	 * Allows the entity to update its state. Overridden in most subclasses,
	 * e.g. the mob spawner uses this to count ticks and creates a new spawn
	 * inside its implementation.
	 */
	public void updateEntity() {
		if (this.isActive) {
			this.charge--;
			// spawning things
			// this.spawnEntities();
			if (this.charge == 20 * 10)
				this.worldObj.setWorldTime(13500);
			if (this.charge < 0)
				this.charge = 0;
			if (this.charge == 0) {
				this.isActive = false;
				this.isExhausted = true;
				this.makeObsidianCasing(this.worldObj, this.xCoord,
						this.yCoord, this.zCoord);
				// PEforPC.log.info("Degrating");
				for (int index = 0; index < this.blocksToDelete.size(); index++) {
					int x = this.blocksToDelete.get(index)[0];
					int y = this.blocksToDelete.get(index)[1];
					int z = this.blocksToDelete.get(index)[2];
					// PEforPC.log.info("x: " + x + ";" + "y: " + y + ";" +
					// "z: "
					// + z);
					this.worldObj.setBlock(x, y, z, 0);
				}
			}
		}

		// Set Metadata (Texures)
		int meta;
		if (!this.isExhausted) { // charge > 0
			if (!this.isActive) { // charged & not active
				// set meta to charge
				meta = this.charge;
			} else { // charged & active
				// set meta to maxCharge + 1
				meta = BlockNetherCore.maximumCharge + 1;
			}
		} else { // set meta to 0
			meta = 0;
		}
		if (this.worldObj.getBlockMetadata(this.xCoord, this.yCoord,
				this.xCoord) != meta)
			this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord,
					this.zCoord, meta, 3);

		super.updateEntity();
	}

	private void makeObsidianCasing(World world, int x, int y, int z) {
		world.setBlock(x - 1, y - 1, z - 1, Block.obsidian.blockID);
		world.setBlock(x - 1, y - 1, z - 0, Block.obsidian.blockID);
		world.setBlock(x - 1, y - 1, z + 1, Block.obsidian.blockID);
		world.setBlock(x - 0, y - 1, z - 1, Block.obsidian.blockID);
		world.setBlock(x - 0, y - 1, z - 0, Block.obsidian.blockID);
		world.setBlock(x - 0, y - 1, z + 1, Block.obsidian.blockID);
		world.setBlock(x + 1, y - 1, z - 1, Block.obsidian.blockID);
		world.setBlock(x + 1, y - 1, z - 0, Block.obsidian.blockID);
		world.setBlock(x + 1, y - 1, z + 1, Block.obsidian.blockID);
		world.setBlock(x - 1, y - 0, z - 1, Block.obsidian.blockID);
		world.setBlock(x - 1, y - 0, z + 1, Block.obsidian.blockID);
		world.setBlock(x + 1, y - 0, z - 1, Block.obsidian.blockID);
		world.setBlock(x + 1, y - 0, z + 1, Block.obsidian.blockID);
		world.setBlock(x - 0, y + 1, z - 1, Block.obsidian.blockID);
		world.setBlock(x - 0, y + 1, z + 1, Block.obsidian.blockID);
		world.setBlock(x - 1, y + 1, z - 0, Block.obsidian.blockID);
		world.setBlock(x + 1, y + 1, z - 0, Block.obsidian.blockID);
		world.setBlock(x - 0, y + 1, z - 0, Block.obsidian.blockID);
	}

	private void spawnEntities() {
		// lootTable and ZombiePigmen
		List<Entity> zombiesAround = this.worldObj.getEntitiesWithinAABB(
				EntityPigZombie.class, AxisAlignedBB.getBoundingBox(
						this.xCoord - 6, this.yCoord - 2, this.zCoord - 6,
						this.xCoord + 6, this.yCoord + 2, this.zCoord + 6));
		int maxZombies = 0;
		switch (this.worldObj.difficultySetting) {
		case 1:
			maxZombies = 10;
			break;
		case 2:
			maxZombies = 15;
			break;
		case 3:
			maxZombies = 20;
			break;
		default:
			maxZombies = 0;
			break;
		}

		if (zombiesAround.size() <= maxZombies) {
			EntityPigZombie pigman = new EntityPigZombie(this.worldObj);
			Random rand = new Random();
			int randX = rand.nextInt(13) - 5; // (0 to 12) + 6
			int randZ = rand.nextInt(13) - 5; // (0 to 12) + 6
			pigman.setPosition(this.xCoord + randX, this.yCoord, this.zCoord
					+ randZ);
			/*
			 * List<Entity> players = this.worldObj.getEntitiesWithinAABB(
			 * EntityPlayer.class, AxisAlignedBB.getBoundingBox( this.xCoord -
			 * 6, this.yCoord - 2, this.zCoord - 6, this.xCoord + 6, this.yCoord
			 * + 2, this.zCoord + 6)); if(!players.isEmpty()) if (players.get(0)
			 * instanceof EntityLivingBase) {
			 * pigman.setLastAttacker((EntityLivingBase) players.get(0));
			 * pigman.setRevengeTarget((EntityLivingBase) players.get(0)); }
			 */
			if (!this.worldObj.isRemote)
				this.worldObj.spawnEntityInWorld(pigman);

			EntityItem itemEnt = new EntityItem(this.worldObj);
			int randIndex = rand.nextInt(TileEntityReactor.lootTable.length);
			int randQuantity = rand
					.nextInt(TileEntityReactor.lootTable[randIndex]
							.getMaxStackSize());
			ItemStack stack = TileEntityReactor.lootTable[randIndex];
			stack.stackSize = randQuantity / 2;
			if (stack.stackSize <= 0)
				stack.stackSize = 1;
			itemEnt.setEntityItemStack(stack);
			randX = rand.nextInt(13) - 6; // (0 to 12) + 6
			randZ = rand.nextInt(13) - 6; // (0 to 12) + 6
			itemEnt.setPosition(this.xCoord + randX, this.yCoord, this.zCoord
					+ randZ);
			if (!this.worldObj.isRemote)
				this.worldObj.spawnEntityInWorld(itemEnt);

		}

	}
}
