package CountryGamer_PEforPC.Gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import CountryGamer_PEforPC.Blocks.TileEnt.TileEntityNethergizer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerNethergizer extends Container {
	private TileEntityNethergizer furnace;
	private int lastCookTime;

	// private int lastBurnTime;
	// private int lastItemBurnTime;

	public ContainerNethergizer(InventoryPlayer par1InventoryPlayer,
			TileEntityNethergizer par2TileEntityNethergizer) {
		this.furnace = par2TileEntityNethergizer;
		this.addSlotToContainer(new Slot(par2TileEntityNethergizer, 0, 56, 17));
		this.addSlotToContainer(new Slot(par2TileEntityNethergizer, 1, 116, 35));
		int i;

		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9
						+ 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(par1InventoryPlayer, i,
					8 + i * 18, 142));
		}
	}

	public void addCraftingToCrafters(ICrafting par1ICrafting) {
		super.addCraftingToCrafters(par1ICrafting);
		par1ICrafting.sendProgressBarUpdate(this, 0,
				this.furnace.furnaceCookTime);
		// par1ICrafting.sendProgressBarUpdate(this, 1,
		// this.furnace.furnaceBurnTime);
		// par1ICrafting.sendProgressBarUpdate(this, 2,
		// this.furnace.currentItemBurnTime);
	}

	/**
	 * Looks for changes made in the container, sends them to every listener.
	 */
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.crafters.size(); ++i) {
			ICrafting icrafting = (ICrafting) this.crafters.get(i);

			if (this.lastCookTime != this.furnace.furnaceCookTime) {
				icrafting.sendProgressBarUpdate(this, 0,
						this.furnace.furnaceCookTime);
			}

			/*
			 * if (this.lastBurnTime != this.furnace.furnaceBurnTime) {
			 * icrafting.sendProgressBarUpdate(this, 1,
			 * this.furnace.furnaceBurnTime); }
			 * 
			 * if (this.lastItemBurnTime != this.furnace.currentItemBurnTime) {
			 * icrafting.sendProgressBarUpdate(this, 2,
			 * this.furnace.currentItemBurnTime); }
			 */
		}

		this.lastCookTime = this.furnace.furnaceCookTime;
		// this.lastBurnTime = this.furnace.furnaceBurnTime;
		// this.lastItemBurnTime = this.furnace.currentItemBurnTime;
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2) {
		if (par1 == 0) {
			this.furnace.furnaceCookTime = par2;
		}
		/*
		 * if (par1 == 1) { this.furnace.furnaceBurnTime = par2; }
		 * 
		 * if (par1 == 2) { this.furnace.currentItemBurnTime = par2; }
		 */
	}

	public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
		return this.furnace.isUseableByPlayer(par1EntityPlayer);
	}

	/**
	 * Called when a player shift-clicks on a slot. You must override this or
	 * you will crash when someone does that.
	 */
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer,
			int slotiD) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(slotiD);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (slotiD == 1) {
				if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (slotiD != 0) {
				if (FurnaceRecipes.smelting().getSmeltingResult(itemstack1) != null) {
					if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
						return null;
					}
				} else if (slotiD >= 3 && slotiD < 30) {
					if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
						return null;
					}
				} else if (slotiD >= 30 && slotiD < 39
						&& !this.mergeItemStack(itemstack1, 3, 30, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
				return null;
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
		}

		return itemstack;
	}
}
