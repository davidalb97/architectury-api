/*
 * Copyright 2020 shedaniel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.shedaniel.architectury.mixin.fabric;

import me.shedaniel.architectury.event.events.EntityEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {LivingEntity.class, Player.class, ServerPlayer.class})
public class LivingDeathInvoker {
    @Inject(method = "die", at = @At("HEAD"), cancellable = true)
    private void die(DamageSource source, CallbackInfo ci) {
        if (EntityEvent.LIVING_DEATH.invoker().die((LivingEntity) (Object) this, source) == InteractionResult.FAIL) {
            ci.cancel();
        }
    }
}