package mod.tropidragon.packapunch.init;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import com.google.common.collect.Maps;
import mod.tropidragon.packapunch.Divinium;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

@SuppressWarnings("all")
public class ModCreativeTab {
    private static Map<ResourceLocation, CreativeModeTab> TABS = Maps.newHashMap();

    public static void initCreativeTabs() {
        addCreativeTabs("other", Component.translatable("itemGroup.tab.packapunch.other"),
                () -> ModItems.RETROFIT_MACHINE.get().getDefaultInstance(), output -> {
                    output.add(ModItems.RETROFIT_MACHINE.get().getDefaultInstance());
                    output.add(ModItems.ARSENAL_MACHINE.get().getDefaultInstance());
                    output.add(ModItems.ARTIFICIAL_115.get().getDefaultInstance());
                    output.add(ModItems.ELEMENT_115.get().getDefaultInstance());
                    output.add(ModItems.COLA_DEADSHOT.get().getDefaultInstance());
                    output.add(ModItems.COLA_PHD.get().getDefaultInstance());
                });
    }

    private static void addCreativeTabs(String label, Component displayName,
            Supplier<ItemStack> iconStack, Consumer<NonNullList<ItemStack>> tabConsumer) {
        DiviniumTab tab = new DiviniumTab(label, displayName, iconStack, tabConsumer);
        TABS.put(new ResourceLocation(Divinium.MODID, label), tab);
    }

    private static class DiviniumTab extends CreativeModeTab {
        private Component displayName;
        private Supplier<ItemStack> iconStack;
        private Consumer<NonNullList<ItemStack>> tabConsumer;

        public DiviniumTab(String label, Component displayName, Supplier<ItemStack> iconStack,
                Consumer<NonNullList<ItemStack>> tabConsumer) {
            super(label);
            this.displayName = displayName;
            this.iconStack = iconStack;
            this.tabConsumer = tabConsumer;
        }

        @Override
        public @Nonnull ItemStack makeIcon() {
            return iconStack.get();
        }

        @Override
        public Component getDisplayName() {
            return displayName;
        }

        @Override
        public void fillItemList(NonNullList<ItemStack> items) {
            tabConsumer.accept(items);
        }
    }
}
