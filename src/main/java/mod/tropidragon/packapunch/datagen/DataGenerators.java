package mod.tropidragon.packapunch.datagen;

import org.antlr.v4.parse.ANTLRParser.labeledAlt_return;

import mod.tropidragon.packapunch.Divinium;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
// import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = Divinium.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();

        if (event.includeServer()) {
            generator.addProvider(false, new ModRecipes(generator));

            // ModBlockTags blockTags = new ModBlockTags(generator,
            // event.getExistingFileHelper());
            // generator.addProvider(blockTags);

            // generator.addProvider(new ModItemTags(generator, blockTags,
            // event.getExistingFileHelper()));
        }

        if (event.includeClient()) {
            // generator.addProvider(new ModBlockStates(generator,
            // event.getExistingFileHelper());
            // generator.addProvider(new ModItemModels(generator,
            // event.getExistingFileHelper());
            // generator.addProvider(new ModLanguageProvider(generator,
            // event.getExistingFileHelper());
        }

    }
}
