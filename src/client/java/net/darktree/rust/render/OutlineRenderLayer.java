package net.darktree.rust.render;

import com.mojang.blaze3d.systems.VertexSorter;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.joml.Vector3f;

import java.util.function.Function;

public class OutlineRenderLayer {

	private static final String LAYER_NAME = "rust:outline_translucent";
	private static final Vector3f ORIGIN = new Vector3f(0.0f);
	private static final VertexSorter INVERTED_VERTEX_SORTER = VertexSorter.of(pos -> -ORIGIN.distance(pos));

	private static final Function<Identifier, RenderLayer> OUTLINE_TRANSLUCENT = Util.memoize(texture -> {
		RenderLayer.MultiPhaseParameters multiPhaseParameters = RenderLayer.MultiPhaseParameters.builder().program(RenderLayer.ITEM_ENTITY_TRANSLUCENT_CULL_PROGRAM).texture(new RenderPhase.Texture(texture, false, false)).transparency(RenderLayer.TRANSLUCENT_TRANSPARENCY).target(RenderLayer.ITEM_ENTITY_TARGET).lightmap(RenderLayer.ENABLE_LIGHTMAP).depthTest(RenderPhase.LEQUAL_DEPTH_TEST).overlay(RenderLayer.ENABLE_OVERLAY_COLOR).writeMaskState(RenderPhase.ALL_MASK).build(true);
		return RenderLayer.of("rust:outline_translucent", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, false, true, multiPhaseParameters);
	});

	public static boolean isOutlineLayer(String name) {
		return LAYER_NAME.equals(name);
	}

	public static VertexSorter getInvertedVertexSorter() {
		return INVERTED_VERTEX_SORTER;
	}

	public static RenderLayer getOutlineLayer(Identifier texture) {
		return OUTLINE_TRANSLUCENT.apply(texture);
	}

}
