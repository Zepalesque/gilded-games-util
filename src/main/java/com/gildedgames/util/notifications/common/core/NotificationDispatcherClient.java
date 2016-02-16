package com.gildedgames.util.notifications.common.core;

import java.util.UUID;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.notifications.NotificationModule;
import com.gildedgames.util.notifications.common.networking.messages.PacketNotification;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class NotificationDispatcherClient extends NotificationDispatcher
{

	@Override
	public void sendNotification(INotification notification)
	{
		//If the notification contains no message and is meant for the player,
		//don't send it to the server for a check. 
		UUID player = notification.getReceiver();
		EntityPlayer thePlayer = Minecraft.getMinecraft().thePlayer;
		if (notification.getMessage() == null && player.equals(thePlayer.getGameProfile().getId()))
		{
			NotificationModule.locate().queueNotificationForDisplay(notification);
		}
		else
		{
			UtilModule.NETWORK.sendToServer(new PacketNotification(notification, NotificationModule.getPlayerNotifications(player)));
		}
	}

}
