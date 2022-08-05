package com.theswirlingvoid.polarmachinery.item.utility.applicator.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;

public interface SectorSelectCallback {
	Event<SectorSelectCallback> EVENT = EventFactory.createArrayBacked(SectorSelectCallback.class,
        (listeners) -> (sectorIndex) -> {
            for (SectorSelectCallback listener : listeners) {
                ActionResult result = listener.onSectorChange(sectorIndex);
 
                if(result != ActionResult.PASS) {
                    return result;
                }
            }
 
        return ActionResult.PASS;
    });
 
    ActionResult onSectorChange(int selectedArcIndex);
}
