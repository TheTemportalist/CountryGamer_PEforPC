package CountryGamer_PEforPC.Proxy;

import CountryGamer_PEforPC.Blocks.TileEnt.TileEntityNethergizer;
import CountryGamer_PEforPC.Blocks.TileEnt.TileEntityReactor;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends ServerProxy {

	@Override
	public void registerThings() {
		GameRegistry.registerTileEntity(TileEntityReactor.class, "TileEntityReactor");
		GameRegistry.registerTileEntity(TileEntityNethergizer.class, "TileEntityNethergizer");
	}

	@Override
	public int addArmor(String armor) {
		return RenderingRegistry.addNewArmourRendererPrefix(armor);
	}

}
