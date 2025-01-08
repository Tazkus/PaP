package mod.tropidragon.packapunch.block.entity;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import javax.annotation.Nullable;

import mod.tropidragon.packapunch.init.ModBlocks;
import mod.tropidragon.packapunch.inventory.ArsenalMachineMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class ArsenalMachineBlockEntity extends BlockEntity implements MenuProvider {
    public static final BlockEntityType<ArsenalMachineBlockEntity> TYPE = BlockEntityType.Builder
            .of(ArsenalMachineBlockEntity::new, ModBlocks.ARSENAL_MACHINE.get()).build(null);

    public ArsenalMachineBlockEntity(BlockPos pos, BlockState blockstate) {
        super(TYPE, pos, blockstate);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    // @Override
    // @OnlyIn(Dist.CLIENT)
    // public AABB getRenderBoundingBox() {
    // return new AABB(worldPosition.offset(-2, 0, -2), worldPosition.offset(2, 1,
    // 2));
    // }

    @Override
    public Component getDisplayName() {
        return new TextComponent("Arsenal Machine");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new ArsenalMachineMenu(id, inventory);
    }

}
