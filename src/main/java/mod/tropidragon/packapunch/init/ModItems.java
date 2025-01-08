package mod.tropidragon.packapunch.init;

import mod.tropidragon.packapunch.Divinium;
import mod.tropidragon.packapunch.item.ArsenalMachineItem;
import mod.tropidragon.packapunch.item.RetrofitMachineItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItems {
        public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
                        Divinium.MODID);

        public static RegistryObject<Item> RETROFIT_MACHINE = ITEMS.register("retrofit_machine",
                        RetrofitMachineItem::new);
        public static RegistryObject<Item> ARSENAL_MACHINE = ITEMS.register("arsenal_machine", ArsenalMachineItem::new);
        // public static RegistryObject<Item> ARSENAL_MACHINE =
        // ITEMS.register("arsenal_machine", new
        // BlockItem(ModBlocks.ARSENAL_MACHINE.get(), new Item.Properties()));

        public static RegistryObject<Item> ARTIFICIAL_115 = ITEMS.register("artificial_115",
                        () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
        public static RegistryObject<Item> ELEMENT_115 = ITEMS.register("element_115",
                        () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

        @SubscribeEvent
        public static void onItemRegister(RegistryEvent.Register<Item> event) {

        }
}
