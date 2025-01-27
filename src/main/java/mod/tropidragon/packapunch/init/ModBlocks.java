package mod.tropidragon.packapunch.init;

import mod.tropidragon.packapunch.Divinium;
import mod.tropidragon.packapunch.block.ArsenalMachineBlock;
import mod.tropidragon.packapunch.block.RetrofitMachineBlock;
import mod.tropidragon.packapunch.block.entity.ArsenalMachineBlockEntity;
import mod.tropidragon.packapunch.block.entity.RetrofitMachineBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
        // Block
        public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
                        Divinium.MODID);

        public static RegistryObject<Block> RETROFIT_MACHINE = BLOCKS.register("retrofit_machine",
                        RetrofitMachineBlock::new);
        public static RegistryObject<Block> ARSENAL_MACHINE = BLOCKS.register("arsenal_machine",
                        ArsenalMachineBlock::new);

        // BlockEntity
        public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
                        .create(ForgeRegistries.BLOCK_ENTITY_TYPES, Divinium.MODID);

        public static RegistryObject<BlockEntityType<RetrofitMachineBlockEntity>> RETROFIT_MACHINE_BE = BLOCK_ENTITIES
                        .register("retrofit_machine_entity", () -> RetrofitMachineBlockEntity.TYPE);
        public static RegistryObject<BlockEntityType<ArsenalMachineBlockEntity>> ARSENAL_MACHINE_BE = BLOCK_ENTITIES
                        .register("arsenal_machine_entity", () -> ArsenalMachineBlockEntity.TYPE);

}
