package CountryGamer_PEforPC.Blocks.TileEnt;

import CountryGamer_PEforPC.PEforPC;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityNethergizer extends TileEntity implements IInventory,
		ISidedInventory {

	public final int maxCookTime = 200 / 4;
	public int furnaceCookTime;

	private ItemStack[] invStacks = new ItemStack[2];

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		this.invStacks[i] = itemstack;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (this.invStacks[i] != null) {
			ItemStack itemstack;
			if (this.invStacks[i].stackSize <= j) {
				itemstack = this.invStacks[i];
				this.invStacks[i] = null;
				return itemstack;
			} else {
				itemstack = this.invStacks[i].splitStack(j);
				if (this.invStacks[i].stackSize == 0)
					this.invStacks[i] = null;
				return itemstack;
			}
		} else
			return null;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return this.invStacks[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if (this.invStacks[i] != null) {
			ItemStack retStack = this.invStacks[i];
			this.invStacks[i] = null;
			return retStack;
		}
		return null;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		int[] slotInput = new int[] { 0 };
		int[] slotOutput = new int[] { 1 };
		if (side == 1)
			return slotInput;
		else if (side == 0)
			return slotOutput;
		else
			return new int[] { 0, 1 };
	}

	@Override
	public int getSizeInventory() {
		return this.invStacks.length;
	}

	@Override
	public String getInvName() {
		return "Nethergizer";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		return true;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return true;
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
		if (slot == 0)
			return itemstack.itemID == PEforPC.energizedQuartz.itemID;
		else
			return itemstack.itemID == PEforPC.netherReactorCore.blockID;
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCom) {
		super.writeToNBT(tagCom);
		tagCom.setShort("CookTime", (short) this.furnaceCookTime);
		NBTTagList tagList = new NBTTagList();

		for (int i = 0; i < this.invStacks.length; ++i) {
			if (this.invStacks[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.invStacks[i].writeToNBT(nbttagcompound1);
				tagList.appendTag(nbttagcompound1);
			}
		}

		tagCom.setTag("Items", tagList);

	}

	@Override
	public void readFromNBT(NBTTagCompound tagCom) {
		super.readFromNBT(tagCom);
		NBTTagList tagList = tagCom.getTagList("Items");
		this.invStacks = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < tagList.tagCount(); ++i) {
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) tagList.tagAt(i);
			byte b0 = nbttagcompound1.getByte("Slot");

			if (b0 >= 0 && b0 < this.invStacks.length)
				this.invStacks[b0] = ItemStack
						.loadItemStackFromNBT(nbttagcompound1);
		}

		this.furnaceCookTime = tagCom.getShort("CookTime");

	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

	private boolean canSmelt() {
		return this.invStacks[0] != null && this.invStacks[1] != null;
	}

	public void smeltItem() {
		if (this.canSmelt()) {
			ItemStack itemstack = this.invStacks[1].copy();

			--this.invStacks[0].stackSize;
			if (this.invStacks[0].stackSize <= 0)
				this.invStacks[0] = null;

			int damage = itemstack.getItemDamage() + 1;
			itemstack.setItemDamage(damage);

			this.invStacks[1] = itemstack;
		}
	}

	public void updateEntity() {
		if (this.canSmelt()) {
			++this.furnaceCookTime;
			if (this.furnaceCookTime == this.maxCookTime) {
				this.smeltItem();
				this.furnaceCookTime = 0;
			}
		}
		this.onInventoryChanged();
	}

	@SideOnly(Side.CLIENT)
	/**
	 * Returns an integer between 0 and the passed value representing how close the current item is to being completely
	 * cooked
	 */
	public int getCookProgressScaled(int par1) {
		return this.furnaceCookTime * par1 / this.maxCookTime;
	}

}
