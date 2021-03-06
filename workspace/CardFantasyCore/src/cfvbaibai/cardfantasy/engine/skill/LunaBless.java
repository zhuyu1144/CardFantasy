package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.Field;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public final class LunaBless {
    public static void apply(Skill cardSkill, SkillResolver resolver, EntityInfo healer) throws HeroDieSignal {
        if (healer == null) {
            return;
        }

        Field field = healer.getOwner().getField();
        Rainfall.healCards(resolver, healer, cardSkill, HealType.Percentage, field.getAliveCards());
    }
}
