package CountryGamer_PEforPC;

import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import CountryGamer_PEforPC.Blocks.BlockBeetroot;
import CountryGamer_PEforPC.Blocks.BlockGlowObsidian;
import CountryGamer_PEforPC.Blocks.BlockNetherCore;
import CountryGamer_PEforPC.Blocks.BlockNethergizer;
import CountryGamer_PEforPC.Handler.PEforPCHandler;
import CountryGamer_PEforPC.Items.ItemBeetroot;
import CountryGamer_PEforPC.Items.ItemBeetrootSeed;
import CountryGamer_PEforPC.Items.ItemBeetrootSoup;
import CountryGamer_PEforPC.Items.ItemEQuartz;
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
import cpw.mods.fml.common.network.NetworkRegistry;
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
	public static Item energizedQuartz;
	public static int energizedQuartziD;
	public static String energizedQuartzName = "Energized Quartz";
	
	// Blocks
	public static Block beetrootPlant;
	public static int	beetrootPlantiD;
	public static String beetrootPlantName = "Beetroot";
	public static Block netherReactorCore;
	public static int	netherReactorCoreiD;
	public static String netherReactorCoreName = "Nether Reactor Core";
	public static Block glowObsidian;
	public static int	glowObsidianiD;
	public static String glowObsidianName = "Glowing Obsidian";
	public static Block	nethergizer;
	public static int	nethergizeriD;
	public static String nethergizerName = "Nethergizer";
	

	@EventHandler
	public void preinit(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(
				event.getSuggestedConfigurationFile());
		String itemCate = "Item IDs", blockCate = "Block IDs", csCate = "Crafting and Smelting", general = config.CATEGORY_GENERAL;
		config.load();

		PEforPC.beetrootPlantiD = config.get(blockCate,
				PEforPC.beetrootPlantName, 700).getInt();
		PEforPC.netherReactorCoreiD = config.get(blockCate,
				PEforPC.netherReactorCoreName, 701).getInt();
		PEforPC.glowObsidianiD = config.get(blockCate,
				PEforPC.glowObsidianName, 702).getInt();
		PEforPC.nethergizeriD = config.get(blockCate,
				PEforPC.nethergizerName, 703).getInt();

		PEforPC.beetrootiD = config.get(itemCate, PEforPC.beetrootName, 704)
				.getInt();
		PEforPC.beetrootSeediD = config.get(itemCate, PEforPC.beetrootSeedName,
				705).getInt();
		PEforPC.beetrootSoupiD = config.get(itemCate, PEforPC.beetrootSoupName,
				706).getInt();
		PEforPC.energizedQuartziD = config.get(itemCate, PEforPC.energizedQuartzName,
				707).getInt();

		config.save();
		
		NetworkRegistry.instance().registerGuiHandler(PEforPC.instance,
				new PEforPCHandler());
		MinecraftForge.EVENT_BUS.register(new PEforPCHandler());
		//GameRegistry.registerFuelHandler(new PEforPCHandler());
		
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
		
		PEforPC.energizedQuartz = new ItemEQuartz(PEforPC.energizedQuartziD,
				Reference.MOD_ID, PEforPC.energizedQuartzName);
		PEforPC.energizedQuartz.setCreativeTab(CreativeTabs.tabMaterials);

	}

	public static void blocks() {
		PEforPC.beetrootPlant = new BlockBeetroot(PEforPC.beetrootPlantiD,
				Reference.MOD_ID, PEforPC.beetrootPlantName);

		PEforPC.netherReactorCore = new BlockNetherCore(
				PEforPC.netherReactorCoreiD, Material.rock, Reference.MOD_ID,
				PEforPC.netherReactorCoreName);
		PEforPC.netherReactorCore.setCreativeTab(CreativeTabs.tabBlock);
		PEforPC.glowObsidian = new BlockGlowObsidian(PEforPC.glowObsidianiD,
				Material.rock, Reference.MOD_ID, PEforPC.glowObsidianName);
		PEforPC.glowObsidian.setCreativeTab(CreativeTabs.tabBlock);
		PEforPC.nethergizer = new BlockNethergizer(
				PEforPC.nethergizeriD, Material.rock, Reference.MOD_ID,
				PEforPC.nethergizerName);
		PEforPC.nethergizer.setCreativeTab(CreativeTabs.tabBlock);
		
	}

	public static void craftSmelt() {
		GameRegistry.addShapelessRecipe(new ItemStack(PEforPC.beetroot),
				new Object[] { Item.spiderEye, Item.carrot });
		GameRegistry.addShapelessRecipe(new ItemStack(PEforPC.beetrootSeed),
				new Object[] { PEforPC.beetroot });
		GameRegistry.addShapelessRecipe(new ItemStack(PEforPC.beetrootSoup),
				new Object[] { PEforPC.beetroot, PEforPC.beetroot,
						PEforPC.beetroot, PEforPC.beetroot, Item.bowlEmpty });
		GameRegistry.addSmelting(PEforPC.beetroot.itemID, new ItemStack(
				Item.dyePowder.itemID, 1, 1), 5F);
		
		GameRegistry.addRecipe(new ItemStack(PEforPC.netherReactorCore),
				new Object[] { "idi", "dcd", "idi", 'i', Item.ingotIron, 'd',
						Item.diamond, 'c', Block.blockNetherQuartz });
		GameRegistry.addShapelessRecipe(new ItemStack(PEforPC.energizedQuartz), new Object[] {
			Item.netherQuartz, Item.glowstone
		});
		
		
	}

	public static void entity() {

	}

}
