package CountryGamer_PEforPC.Items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import CountryGamer_Core.ItemFoodBase;

public class ItemBeetrootSoup extends ItemFoodBase {

	public ItemBeetrootSoup(int id, String modid, String name) {
		super(id, modid, name, 8, 0.8F, false);
	}
	
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        super.onEaten(par1ItemStack, par2World, par3EntityPlayer);
        return new ItemStack(Item.bowlEmpty);
    }

}
