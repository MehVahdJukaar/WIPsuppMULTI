package net.mehvahdjukaar.supplementaries.common.world.data.map.markers;

import net.mehvahdjukaar.moonlight.api.map.CustomMapDecoration;
import net.mehvahdjukaar.moonlight.api.map.markers.MapBlockMarker;
import net.mehvahdjukaar.supplementaries.common.world.data.map.CMDreg;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.ChestBlock;

import javax.annotation.Nullable;

public class ChestMarker extends MapBlockMarker<CustomMapDecoration> {

    public ChestMarker() {
        super(CMDreg.CHEST_DECORATION_TYPE);
    }

    public ChestMarker(BlockPos pos) {
        super(CMDreg.CHEST_DECORATION_TYPE, pos);
        this.setPos(pos);
    }

    @Nullable
    public static ChestMarker getFromWorld(BlockGetter world, BlockPos pos) {
        if (world.getBlockState(pos).getBlock() instanceof ChestBlock) {
            return new ChestMarker(pos);
        } else {
            return null;
        }
    }

    @Nullable
    @Override
    public CustomMapDecoration doCreateDecoration(byte mapX, byte mapY, byte rot) {
        return new CustomMapDecoration(this.getType(), mapX, mapY, rot, null);
    }
}
