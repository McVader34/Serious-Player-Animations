package net.mcvader.seriousplayeranimations.mixin;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.moddiscovery.ModFile;
import net.minecraftforge.forgespi.locating.IModFile;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;


public class SeriousPlayerAnimationsMixinPlugin implements IMixinConfigPlugin {


    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    private Supplier<Boolean> NEApresent = () -> {
        boolean result;
        try {
            Class.forName("dev.tr7zw.notenoughanimations.access.PlayerData").getName();
            result = true;
        } catch(ClassNotFoundException e) {
            result = false;
        }

        boolean finalResult = result;
        NEApresent = () -> { return finalResult; };

        return result;
    };

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.contains("NEA") && !NEApresent.get()) {
            return false;
        }
        return true;

    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

}