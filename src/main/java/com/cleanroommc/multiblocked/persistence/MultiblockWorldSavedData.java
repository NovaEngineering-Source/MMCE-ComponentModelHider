package com.cleanroommc.multiblocked.persistence;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;

public class MultiblockWorldSavedData {

    @SideOnly(Side.CLIENT)
    public static final ThreadLocal<Boolean> isBuildingChunk = ThreadLocal.withInitial(() -> Boolean.FALSE);
    @SideOnly(Side.CLIENT)
    public static final Set<BlockPos> modelDisabled = new HashSet<>();
    @SideOnly(Side.CLIENT)
    public static final Map<BlockPos, Collection<BlockPos>> multiDisabled = new HashMap<>();

    public static void clearDisabled() {
        modelDisabled.clear();
        multiDisabled.clear();
    }

    @SideOnly(Side.CLIENT)
    public static void removeDisableModel(BlockPos controllerPos) {
        Collection<BlockPos> poses = multiDisabled.remove(controllerPos);
        if (poses == null) return;
        modelDisabled.clear();
        multiDisabled.values().forEach(modelDisabled::addAll);
        updateRenderChunk(poses);
    }

    @SideOnly(Side.CLIENT)
    private static void updateRenderChunk(Collection<BlockPos> poses) {
        World world = Minecraft.getMinecraft().world;
        if (world != null) {
            for (BlockPos pos : poses) {
                world.markBlockRangeForRenderUpdate(pos.getX() - 1, pos.getY() - 1, pos.getZ() - 1, pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public static void addDisableModel(BlockPos controllerPos, Collection<BlockPos> poses) {
        multiDisabled.put(controllerPos, poses);
        modelDisabled.addAll(poses);
        updateRenderChunk(poses);
    }

    @SideOnly(Side.CLIENT)
    public static boolean isModelDisabled(BlockPos pos) {
        if (isBuildingChunk.get()) {
            return modelDisabled.contains(pos);
        }
        return false;
    }
}
