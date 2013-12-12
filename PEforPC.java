package CountryGamer_PEforPC;

import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import CountryGamer_PEforPC.Blocks.BlockBeetroot;
import CountryGamer_PEforPC.Items.ItemBeetroot;
import CountryGamer_PEforPC.Items.ItemBeetrootSeed;
import CountryGamer_PEforPC.Items.ItemBeetrootSoup;
import CountryGamer_PEforPC.Proxy.ServerProxy;
import CountryGamer_PEforPC.lib.Reference;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = true)
public class PEforPC {

	public static final Logger log = Logger.getLogger(Reference.MOD_ID);

	@Instance(Reference.MOD_ID)
	public static PEforPC instance;
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static ServerProxy proxy;

	// ~~~~~~~~~~~~~~~~~~~~~~~~
	// Items
	public static Item beetroot;
	public static int beetrootiD;
	public static String beetrootName = "Beetroot";
	public static Item beetrootSeed;
	public static int beetrootSeediD;
	public static String beetrootSeedName = "Beetroot Seeds";
	public static Item beetrootSoup;
	public static int beetrootSoupiD;
	public static String beetrootSoupName = "Beetroot Soup";

	// Blocks
	public static Block beetrootPlant;
	public static int beetrootPlantiD;
	public static String beetrootPlantName = "Beetroot";

	@EventHandler
	public void preinit(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(
				event.getSuggestedConfigurationFile());
		String itemCate = "Item IDs", blockCate = "Block IDs", csCate = "Crafting and Smelting", general = config.CATEGORY_GENERAL;
		config.load();

		PEforPC.beetrootPlantiD = config.get(blockCate,
				PEforPC.beetrootPlantName, 700).getInt();
		PEforPC.beetrootiD = config.get(itemCate, PEforPC.beetrootName, 701)
				.getInt();
		PEforPC.beetrootSeediD = config.get(itemCate, PEforPC.beetrootSeedName,
				702).getInt();
		PEforPC.beetrootSoupiD = config.get(itemCate, PEforPC.beetrootSoupName,
				703).getInt();

		config.save();

		// Physical Stuff
		proxy.registerThings();
		proxy.registerServerTickHandler();

		PEforPC.items();
		PEforPC.blocks();
		PEforPC.craftSmelt();
		PEforPC.entity();

	}

	@EventHandler
	public void init(FMLInitializationEvent event) {

	}

	@EventHandler
	public void postinit(FMLPostInitializationEvent event) {

	}

	public static void items() {
		PEforPC.beetroot = new ItemBeetroot(PEforPC.beetrootiD,
				Reference.MOD_ID, PEforPC.beetrootName);
		PEforPC.beetroot.setCreativeTab(CreativeTabs.tabFood);

		PEforPC.beetrootSeed = new ItemBeetrootSeed(PEforPC.beetrootSeediD,
				Reference.MOD_ID, PEforPC.beetrootSeedName);
		PEforPC.beetrootSeed.setCreativeTab(CreativeTabs.tabMaterials);

		PEforPC.beetrootSoup = new ItemBeetrootSoup(PEforPC.beetrootSoupiD,
				Reference.MOD_ID, PEforPC.beetrootSoupName);
		PEforPC.beetrootSoup.setCreativeTab(CreativeTabs.tabFood);
		
	}

	public static void blocks() {
		PEforPC.beetrootPlant = new BlockBeetroot(PEforPC.beetrootPlantiD,
				Reference.MOD_ID, PEforPC.beetrootPlantName);

	}

	public static void craftSmelt() {
		GameRegistry.addShapelessRecipe(new ItemStack(Item.dyePowder.itemID, 1,
				1), new Object[] { PEforPC.beetroot });

	}

	public static void entity() {

	}

}
