package com.gildedgames.util.modules.instances;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.core.nbt.NBTHelper;
import com.gildedgames.util.core.util.BlockPosDimension;
import com.gildedgames.util.modules.instances.networking.packet.PacketRegisterInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class PlayerInstances implements IPlayerInstances
{
	private Instance activeInstance;

	private BlockPosDimension outside;

	private EntityPlayer player;

	public PlayerInstances(EntityPlayer player)
	{
		this.player = player;
	}

	public Instance getInstance()
	{
		return this.activeInstance;
	}

	public void setInstance(Instance instance)
	{
		this.activeInstance = instance;

		if (!this.player.worldObj.isRemote)
		{
			//UtilModule.NETWORK.sendTo(new PacketRegisterInstance(this.activeInstance), (EntityPlayerMP) this.player);
		}
	}

	public BlockPosDimension outside()
	{
		return this.outside;
	}

	public void setOutside(BlockPosDimension pos)
	{
		this.outside = pos;
	}

	public static class Storage implements Capability.IStorage<IPlayerInstances>
	{

		@Override
		public NBTBase writeNBT(Capability<IPlayerInstances> capability, IPlayerInstances instance, EnumFacing side)
		{
			NBTTagCompound compound = new NBTTagCompound();

			compound.setTag("outside", NBTHelper.serializeBlockPosDimension(instance.outside()));

			NBTHelper.fullySerialize("activeInstance", instance.getInstance(), compound);

			return compound;
		}

		@Override
		public void readNBT(Capability<IPlayerInstances> capability, IPlayerInstances instance, EnumFacing side, NBTBase nbt)
		{
			NBTTagCompound compound = (NBTTagCompound) nbt;

			instance.setOutside(NBTHelper.getBlockPosDimension(compound.getCompoundTag("outside")));
			instance.setInstance((Instance) NBTHelper.fullyDeserialize("activeInstance", compound));
		}
	}

}
