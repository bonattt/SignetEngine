package items;

import java.util.ArrayList;

import combat.Combat;
import combat.CombatAction;

/**
 * This interface is used for weapons, and items, that can be used in the middle of combat.
 * This is anything from longswords, to hand grenades, to healing draughts.
 * @author bonattt
 *
 */
public interface CombatItem {
	public String getName();
	public boolean needsToBeHeld();
	public ArrayList<CombatAction> getActions();
	public boolean isExpendible();
	public Weapon asWeapon();
	public Expendable asExpendable();
	public int getWeight();
}
