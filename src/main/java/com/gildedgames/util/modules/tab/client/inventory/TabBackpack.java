package com.gildedgames.util.modules.tab.client.inventory;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.modules.tab.common.util.ITab;
import com.gildedgames.util.modules.tab.common.util.ITabClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * The {@link ITab} representation of the Minecraft's vanilla Inventory {@link GuiScreen}
 * @author Brandon Pearce
 */
public class TabBackpack implements ITab
{
	@Override
	public String getUnlocalizedName()
	{
		return "tab.backpack.name";
	}

	@Override
	public void onOpen(EntityPlayer player)
	{
	}

	@Override
	public boolean isEnabled()
	{
		return true;
	}

	@Override
	public boolean isRemembered()
	{
		return true;
	}

	@SideOnly(Side.CLIENT)
	public static class Client extends TabBackpack implements ITabClient
	{
		private static final ResourceLocation ICON = new ResourceLocation(UtilModule.MOD_ID, "textures/gui/tab_icons/backpack.png");

		@Override
		public boolean isTabValid(GuiScreen gui)
		{
			Class<? extends GuiScreen> clazz = gui.getClass();
			return clazz == GuiInventory.class || clazz == GuiContainerCreative.class;
		}

		@Override
		public void onOpen(EntityPlayer player)
		{
			Minecraft.getMinecraft().displayGuiScreen(new GuiInventory(player));
		}

		@Override
		public void onClose(EntityPlayer player)
		{
		}

		@Override
		public ResourceLocation getIcon()
		{
			return TabBackpack.Client.ICON;
		}
	}
}
