package live.hisui.classicindustrialization.screen;

import live.hisui.classicindustrialization.ClassicIndustrialization;
import live.hisui.classicindustrialization.menu.GeneratorMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class GeneratorScreen<T extends GeneratorMenu> extends AbstractContainerScreen<T> {
    private static final ResourceLocation TEXTURE = ClassicIndustrialization.modLoc("textures/gui/container/generator.png");

    private static final ResourceLocation LIT_PROGRESS_SPRITE = ResourceLocation.withDefaultNamespace("container/furnace/lit_progress");

    public GeneratorScreen(T menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = this.leftPos;
        int j = this.topPos;
        guiGraphics.blit(TEXTURE, i, j, 0, 0, this.imageWidth, this.imageHeight);
        ClassicIndustrialization.LOGGER.debug("Screen isLit: {}", this.menu.isLit());
        ClassicIndustrialization.LOGGER.debug("Screen litProgress: {}", this.menu.getLitProgress());
        ClassicIndustrialization.LOGGER.debug("Screen energyProgress: {}", this.menu.getEnergyProgress());
        if (this.menu.isLit()) {
            int l = Mth.ceil(this.menu.getLitProgress() * 13.0F) + 1;
            guiGraphics.blitSprite(LIT_PROGRESS_SPRITE, 14, 14, 0, 14 - l, i + 56, j + 36 + 14 - l, 14, l);
        }


        int i1 = 24;
        int j1 = Mth.ceil(this.menu.getEnergyProgress() * 64.0f);
        guiGraphics.blitSprite(TEXTURE, 16, 64, 180, 10, i + 79, j + 34, 16, j1);

    }
}
