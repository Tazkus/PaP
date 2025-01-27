package mod.tropidragon.packapunch.init;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import com.google.common.collect.Maps;

import mod.tropidragon.packapunch.Divinium;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("all")
public class ModCreativeTab {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB,
            Divinium.MODID);

    public static RegistryObject<CreativeModeTab> PAP_TAB = TABS.register("other", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.tab.tacz.other"))
            .icon(() -> ModItems.RETROFIT_MACHINE.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ModItems.RETROFIT_MACHINE.get());
                output.accept(ModItems.ARSENAL_MACHINE.get());
                output.accept(ModItems.ARTIFICIAL_115.get());
                output.accept(ModItems.ELEMENT_115.get());
                output.accept(ModItems.LIQUID_115.get());
                output.accept(ModItems.ARSENAL_MACHINE.get());
                output.accept(ModItems.COLA_DEADSHOT.get());
                output.accept(ModItems.COLA_PHD.get());
            }).build());

    // private static class DiviniumTab extends CreativeModeTab {
    // private Component displayName;
    // private Supplier<ItemStack> iconStack;
    // private Consumer<NonNullList<ItemStack>> tabConsumer;

    // public DiviniumTab(String label, Component displayName, Supplier<ItemStack>
    // iconStack,
    // Consumer<NonNullList<ItemStack>> tabConsumer) {
    // super(label);
    // this.displayName = displayName;
    // this.iconStack = iconStack;
    // this.tabConsumer = tabConsumer;
    // }

    // @Override
    // public @Nonnull ItemStack makeIcon() {
    // return iconStack.get();
    // }

    // @Override
    // public Component getDisplayName() {
    // return displayName;
    // }

    // @Override
    // public void fillItemList(NonNullList<ItemStack> items) {
    // tabConsumer.accept(items);
    // }
    // }

    // private static void addCreativeTabs(String label, Component displayName,
    // Supplier<ItemStack> iconStack, Consumer<NonNullList<ItemStack>> tabConsumer)
    // {
    // DiviniumTab tab = new DiviniumTab(label, displayName, iconStack,
    // tabConsumer);
    // TABS.put(new ResourceLocation(Divinium.MODID, label), tab);
    // }

    // public static void initCreativeTabs() {
    // addCreativeTabs("other",
    // Component.translatable("itemGroup.tab.packapunch.other"),
    // () -> ModItems.RETROFIT_MACHINE.get().getDefaultInstance(), output -> {
    // output.add(ModItems.RETROFIT_MACHINE.get().getDefaultInstance());
    // output.add(ModItems.ARSENAL_MACHINE.get().getDefaultInstance());
    // output.add(ModItems.ARTIFICIAL_115.get().getDefaultInstance());
    // output.add(ModItems.ELEMENT_115.get().getDefaultInstance());
    // output.add(ModItems.COLA_DEADSHOT.get().getDefaultInstance());
    // output.add(ModItems.COLA_PHD.get().getDefaultInstance());
    // });
    // }
}
