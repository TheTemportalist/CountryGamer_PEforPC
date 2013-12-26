package CountryGamer_PEforPC.Gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import CountryGamer_PEforPC.Blocks.TileEnt.TileEntityNethergizer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiNethergizer extends GuiContainer {
	private static final ResourceLocation furnaceGuiTextures = new ResourceLocation(
			"textures/gui/container/furnace.png");
	private TileEntityNethergizer inventory;

	public GuiNethergizer(InventoryPlayer invPlayer,
			TileEntityNethergizer tileEnt) {
		super(new ContainerNethergizer(invPlayer, tileEnt));
		this.inventory = tileEnt;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of
	 * the items)
	 */
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		String s = this.inventory.isInvNameLocalized() ? this.inventory
				.getInvName() : I18n.getString(this.inventory
				.getInvName());
		this.fontRenderer.drawString(s,
				this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6,
				4210752);
		this.fontRenderer.drawString(I18n.getString("container.inventory"), 8,
				this.ySize - 96 + 2, 4210752);
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the
	 * items)
	 */
	protected void drawGuiContainerBackgroundLayer(float par1, int par2,
			int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(furnaceGuiTextures);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		int i1;

		

		i1 = this.inventory.getCookProgressScaled(24);
		this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
	}
}
