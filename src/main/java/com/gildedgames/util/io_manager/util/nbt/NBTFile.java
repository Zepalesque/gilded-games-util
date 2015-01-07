package com.gildedgames.util.io_manager.util.nbt;

import java.io.File;
import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.io_manager.IOManager;
import com.gildedgames.util.io_manager.io.IOFile;
import com.gildedgames.util.io_manager.io.NBT;

public class NBTFile implements IOFile<NBTTagCompound, NBTTagCompound>
{
	
	protected File directory;
	
	protected NBT nbt;
	
	protected Class dataClass;
	
	public NBTFile(File directory, NBT nbt, Class dataClass)
	{
		this.directory = directory;
		this.nbt = nbt;
		this.dataClass = dataClass;
	}

	@Override
	public void readFromFile(IOManager manager, NBTTagCompound reader) throws IOException
	{
		this.nbt.read(reader);
	}

	@Override
	public void writeToFile(IOManager manager, NBTTagCompound writer) throws IOException
	{
		this.nbt.write(writer);
	}

	@Override
	public String getFileExtension()
	{
		return "dat";
	}

	@Override
	public String getDirectoryName()
	{
		return this.directory.getName();
	}

	@Override
	public Class getDataClass()
	{
		return this.dataClass;
	}

}