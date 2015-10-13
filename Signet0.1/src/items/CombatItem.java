package items;

import java.util.ArrayList;

/**
 * This interface is used for weapons, and items, that can be used in the middle of combat.
 * This is anything from longswords, to hand grenades, to healing draughts.
 * @author bonattt
 *
 */
public interface CombatItem {
	public String getName();
	public boolean isExpendible();
	public boolean canBeUsedInCombat();
	public boolean isWeapon();
	public boolean isRangedWeapon();
	public boolean isMeleeWeapon();
	public boolean canBeUsedInExplore();
	public Weapon asWeapon();
	public int getWeight();
}
