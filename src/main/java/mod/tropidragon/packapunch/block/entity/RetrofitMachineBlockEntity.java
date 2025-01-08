package mod.tropidragon.packapunch.block.entity;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import com.tacz.guns.api.item.IGun;
import com.tacz.guns.item.ModernKineticGunItem;

import mod.tropidragon.packapunch.common.Pap;
import mod.tropidragon.packapunch.common.internal.IMixinModernKineticGunItem;
import mod.tropidragon.packapunch.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class RetrofitMachineBlockEntity extends BlockEntity {

    public static final int POWER_CAPACITY = 50000;
    public static final int POWER_THRESHOLD = 0;
    private int power;

    // Never create lazy optionals in getCapability. Always place them as fields in
    // the tile entity:
    private final ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    public static final BlockEntityType<RetrofitMachineBlockEntity> TYPE = BlockEntityType.Builder
            .of(RetrofitMachineBlockEntity::new, ModBlocks.RETROFIT_MACHINE.get()).build(null);

    public RetrofitMachineBlockEntity(BlockPos pos, BlockState blockstate) {
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

    // @Override
    // public Component getDisplayName() {
    // return new TextComponent("Retrofit Machine");
    // }

    // @Nullable
    // @Override
    // public AbstractContainerMenu createMenu(int id, Inventory inventory, Player
    // player) {
    // return new RetrofitMachineMenu(id, inventory, player);
    // }

    // private final CustomEnergyStorage energyStorage = createEnergy();
    // private final LazyOptional<IEnergyStorage> energy = LazyOptional.of(() ->
    // energyStorage);

    @Override
    public void setRemoved() {
        super.setRemoved();
        handler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        if (tag.contains("inventory")) {
            itemHandler.deserializeNBT(tag.getCompound("inventory"));
        }
        // if (tag.contains("energy")) {
        // itemHandler.deserializeNBT(tag.getCompound("energy"));
        // }
        // if (tag.contains("info")) {
        // tag.getCompound("info").getInt("Counter");
        // }

        super.load(tag);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());

        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(level, worldPosition, inventory);
    }

    // 存储单个tacz枪械
    private ItemStackHandler createHandler() {
        return new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                IGun iGun = IGun.getIGunOrNull(stack);
                if (iGun != null) {
                    return true;
                }
                return false;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                IGun iGun = IGun.getIGunOrNull(stack);
                if (iGun != null) {
                    return super.insertItem(slot, stack, simulate);
                }
                return stack;
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {

        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        // if(cap == CapabilityEnergy.ENERGY){
        // return energy.cast();
        // }
        return super.getCapability(cap, side);
    }

    public void tickServer() {
        BlockState blockState = level.getBlockState(worldPosition);
        if (blockState.getValue(BlockStateProperties.POWERED) != (power >= POWER_THRESHOLD)) {
            level.setBlock(worldPosition,
                    blockState.setValue(BlockStateProperties.POWERED, power >= POWER_THRESHOLD),
                    Block.UPDATE_ALL);
        }
    }

    public static boolean hasGunInWeaponSlot(RetrofitMachineBlockEntity entity) {
        return entity.itemHandler.getStackInSlot(0).getItem() instanceof ModernKineticGunItem;
    }

    public static int getWeaponPapLevel(RetrofitMachineBlockEntity entity) {
        ItemStack gun = entity.itemHandler.getStackInSlot(0);
        IGun iGun = IGun.getIGunOrNull(gun);
        return ((IMixinModernKineticGunItem) (Object) iGun).getPaPLevel(gun);
    }

    public static int getWeaponRarityLevel(RetrofitMachineBlockEntity entity) {
        ItemStack gun = entity.itemHandler.getStackInSlot(0);
        IGun iGun = IGun.getIGunOrNull(gun);
        return ((IMixinModernKineticGunItem) (Object) iGun).getRarityLevel(gun);
    }

    public static void upgradeWeapon(RetrofitMachineBlockEntity entity) {
        Level level = entity.level;
        // SimpleContainer inventory = new
        // SimpleContainer(entity.itemHandler.getSlots());

        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            // inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        // ItemStack gun = entity.itemHandler.getStackInSlot(0);
        ItemStack gun = entity.itemHandler.extractItem(0, 1, false);
        IGun iGun = IGun.getIGunOrNull(gun);

        int curLvl = ((IMixinModernKineticGunItem) (Object) iGun).getPaPLevel(gun);
        ((IMixinModernKineticGunItem) (Object) iGun).setPaPLevel(gun, curLvl + 1);

        // 重新放入升级后的枪械
        entity.itemHandler.insertItem(0, gun, false);
    }

}
