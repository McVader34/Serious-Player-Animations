package net.mcvader.seriousplayeranimations.config;

import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

@Config(name = "seriousplayeranimations")
public class ConfigWrapper extends PartitioningSerializer.GlobalData {
    @ConfigEntry.Category("client")
    @ConfigEntry.Gui.TransitiveObject
    public ClientConfig client = new ClientConfig();
}