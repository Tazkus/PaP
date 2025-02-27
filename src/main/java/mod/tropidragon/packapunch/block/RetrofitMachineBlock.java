package mod.tropidragon.packapunch.block;

import javax.annotation.Nullable;

import mod.tropidragon.packapunch.block.entity.RetrofitMachineBlockEntity;
import mod.tropidragon.packapunch.inventory.RetrofitMachineMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;

// public class RetrofitMachineBlock extends Block implements EntityBlock {
public class RetrofitMachineBlock extends BaseEntityBlock {
    // public static final VoxelShape BLOCK_AABB = Block.box(0, 0, 0, 16, 16, 16);

    private static final VoxelShape RENDER_SHAPE = Shapes.box(0, 0, 0, 1, 1, 1);

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    // public static final EnumProperty<BedPart> PART =
    // BlockStateProperties.BED_PART;

    public static final String SCREEN_RETROFIT_MACHINE = "screen.packapunch.retrofit_machine";

    public RetrofitMachineBlock() {

        super(Properties.of(Material.METAL)
                .sound(SoundType.METAL)
                .strength(2.0F, 3.0F)
                .lightLevel(state -> state.getValue(BlockStateProperties.POWERED) ? 14 : 0)
                .noOcclusion());
        this.registerDefaultState(
                this.stateDefinition.any().setValue(FACING, Direction.NORTH));
        // this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(PART,
        // BedPart.FOOT));
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter reader, BlockPos pos) {
        return RENDER_SHAPE;
    }
    // private static Direction getNeighbourDirection(BedPart bedPart, Direction
    // direction) {
    // return bedPart == BedPart.FOOT ? direction : direction.getOpposite();
    // }

    @Override
    public InteractionResult use(BlockState pState, Level level, BlockPos pos, Player player, InteractionHand pHand,
            BlockHitResult pHit) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof RetrofitMachineBlockEntity machine) {
                // 如果block entity实现了MenuProvider，且不需要复杂数据，可使用该方法
                // player.openMenu(machine);

                MenuProvider containerProvider = new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return new TranslatableComponent(SCREEN_RETROFIT_MACHINE);
                    }

                    @Override
                    public AbstractContainerMenu createMenu(int windowId, Inventory inventory,
                            Player player) {
                        return new RetrofitMachineMenu(windowId, inventory, player, pos);
                    }
                };
                NetworkHooks.openGui((ServerPlayer) player, containerProvider,
                        blockEntity.getBlockPos());
            } else {
                throw new IllegalStateException("container provider is missing!");
            }
            return InteractionResult.CONSUME;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        // builder.add(FACING, PART);
        builder.add(FACING);
        builder.add(BlockStateProperties.POWERED);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState blockState) {
        return new RetrofitMachineBlockEntity(pos, blockState);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getHorizontalDirection();
        BlockPos clickedPos = context.getClickedPos();
        return super.getStateForPlacement(context).setValue(BlockStateProperties.POWERED, false);
        // check if there is 2 block space to place it
        // BlockPos relative = clickedPos.relative(direction);
        // Level level = context.getLevel();
        // if (level.getBlockState(relative).canBeReplaced(context) &&
        // level.getWorldBorder().isWithinBounds(relative)) {
        // return this.defaultBlockState().setValue(FACING, direction);
        // }
        // return null;

    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState blockState, Player player) {
        // if (!level.isClientSide && player.isCreative()) {
        // BedPart bedPart = blockState.getValue(PART);
        // if (bedPart == BedPart.FOOT) {
        // BlockPos blockpos = pos.relative(getNeighbourDirection(bedPart,
        // blockState.getValue(FACING)));
        // BlockState blockstate = level.getBlockState(blockpos);
        // if (blockstate.is(this) && blockstate.getValue(PART) == BedPart.HEAD) {
        // level.setBlock(blockpos, Blocks.AIR.defaultBlockState(),
        // Block.UPDATE_ALL | Block.UPDATE_SUPPRESS_DROPS);
        // level.levelEvent(player, LevelEvent.PARTICLES_DESTROY_BLOCK, blockpos,
        // Block.getId(blockstate));
        // }
        // }
        // }
        super.playerWillDestroy(level, pos, blockState, player);
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state,
            @Nullable LivingEntity placer,
            ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);
        // if (!worldIn.isClientSide) {
        // BlockPos relative = pos.relative(state.getValue(FACING));
        // worldIn.setBlock(relative, state.setValue(PART, BedPart.HEAD),
        // Block.UPDATE_ALL);
        // worldIn.blockUpdated(pos, Blocks.AIR);
        // state.updateNeighbourShapes(worldIn, pos, Block.UPDATE_ALL);
        // }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction,
            BlockState facingState, LevelAccessor level,
            BlockPos currentPos, BlockPos facingPos) {
        // if (direction == getNeighbourDirection(state.getValue(PART),
        // state.getValue(FACING))) {
        // return facingState.is(this) && facingState.getValue(PART) !=
        // state.getValue(PART) ? state
        // : Blocks.AIR.defaultBlockState();
        // } else {
        // return super.updateShape(state, direction, facingState, level, currentPos,
        // facingPos);
        // }
        return super.updateShape(state, direction, facingState, level, currentPos,
                facingPos);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
        // return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.IGNORE;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return RENDER_SHAPE;
    }

    // @Override
    // public void addHoverText()

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {

        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof RetrofitMachineBlockEntity) {

                ((RetrofitMachineBlockEntity) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
            BlockEntityType<T> type) {
        if (level.isClientSide()) {
            return null;
        }
        return (lvl, pos, blockState, t) -> {
            if (t instanceof RetrofitMachineBlockEntity tile) {
                tile.tickServer();
            }
        };
    }
}
