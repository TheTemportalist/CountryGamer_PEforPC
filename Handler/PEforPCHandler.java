package CountryGamer_PEforPC.Handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import CountryGamer_Misc.Blocks.TileEnt.TileEntityIncubator;
import CountryGamer_PEforPC.Blocks.TileEnt.TileEntityNethergizer;
import CountryGamer_PEforPC.Gui.ContainerNethergizer;
import CountryGamer_PEforPC.Gui.GuiNethergizer;
import cpw.mods.fml.common.network.IGuiHandler;

public class PEforPCHandler implements IGuiHandler {
	
	
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tileEnt = world.getBlockTileEntity(x, y, z);
		if (tileEnt instanceof TileEntityNethergizer)
			return new ContainerNethergizer(player.inventory,
					(TileEntityNethergizer) tileEnt);
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tileEnt = world.getBlockTileEntity(x, y, z);
		if (tileEnt instanceof TileEntityNethergizer) {
			return new GuiNethergizer(player.inventory,
					(TileEntityNethergizer) tileEnt);
		}
		return null;
	}

}
