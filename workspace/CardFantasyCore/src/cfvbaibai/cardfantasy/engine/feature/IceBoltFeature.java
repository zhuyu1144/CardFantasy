package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.GameUI;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;
import cfvbaibai.cardfantasy.engine.Player;

public final class IceBoltFeature {

    public static void apply(FeatureInfo feature, FeatureResolver resolver, CardInfo attacker, Player defender) {
        int damage = feature.getImpact();
        List <CardInfo> victims = defender.getField().pickRandom(1, true);
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, victims, feature);
        for (CardInfo victim : victims) {
            OnAttackBlockingResult result = resolver.resolveAttackBlockingFeature(attacker, victim, feature);
            if (!result.isAttackable()) {
                continue;
            }
            damage = result.getDamage(); 
            ui.attackCard(attacker, victim, feature, damage);
            boolean cardDead = resolver.applyDamage(victim, damage).cardDead;
            resolver.resolveCounterAttackFeature(attacker, victim, feature);
            if (cardDead){
                resolver.resolveDeathFeature(attacker, victim, feature);
            } else if (Randomizer.roll100() <= 45) {
                CardStatusItem status = CardStatusItem.frozen(feature);
                ui.addCardStatus(attacker, victim, feature, status);
                victim.addStatus(status);
            }
        }
    }

}
