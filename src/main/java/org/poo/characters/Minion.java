package org.poo.characters;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CardInput;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class extends the Minion class, having extra fields such as
 * the attack damage or the isFrozen boolean which tells
 * whether the card is frozen or not
 */
public final class Minion extends Card {
    private int attackDamage;
    private boolean isFrozen;
    public Minion(final CardInput card) {
        super(card);
        attackDamage = card.getAttackDamage();
        isFrozen = false;
    }

    public Minion() {
        super();
    }

    public void setIsFrozen(final boolean isFrozen) {
        this.isFrozen = isFrozen;
    }

    public boolean getIsFrozen() {
        return isFrozen;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    /**
     * the printMinion function creates an ObjectNode, adds the minion into the node
     * and adds the node into an ArrayNode
     * @param outputCorrespondent the ArrayNode where you add the node
     * @param mapper the ObjectMapper object
     */
    public void printMinion(final ArrayNode outputCorrespondent, final ObjectMapper mapper) {
        ObjectNode nodeInfo = mapper.createObjectNode();
        addMinionToObjectNode(nodeInfo, mapper);
        outputCorrespondent.add(nodeInfo);
    }

    /**
     * adds Minion to the required ObjectNode received as parameter
     * @param nodeInfo the node where you add the Minion stats
     * @param mapper the ObjectMapper object
     */
    public void addMinionToObjectNode(final ObjectNode nodeInfo, final ObjectMapper mapper) {
        nodeInfo.put("mana", getMana());
        nodeInfo.put("attackDamage", getAttackDamage());
        nodeInfo.put("health", getHealth());
        nodeInfo.put("description", getDescription());
        ArrayNode colors = mapper.createArrayNode();
        for (String s: getColors()) {
            colors.add(s);
        }
        nodeInfo.set("colors", colors);
        nodeInfo.put("name", getName());
    }

    /**
     * uses the special attack of the current Minion on
     * another Minion received as parameter
     * @param attacked the Minion who will be attacked
     */
    public void specialAttack(final Minion attacked) {
        if (getName().equals("The Ripper")) {
            attacked.setAttackDamage(attacked.getAttackDamage() - 2);
            if (attacked.getAttackDamage() < 0) {
                attacked.setAttackDamage(0);
            }
        } else if (getName().equals("Miraj")) {
            int swap = attacked.getHealth();
            attacked.setHealth(getHealth());
            setHealth(swap);
        } else if (getName().equals("The Cursed One")) {
            int swap = attacked.getHealth();
            attacked.setHealth(attacked.getAttackDamage());
            attacked.setAttackDamage(swap);
        } else if (getName().equals("Disciple")) {
            attacked.setHealth(attacked.getHealth() + 2);
        }
    }

    /**
     * tells which row a Minion should be placed on based on its name
     * @return "front" or "back" accordingly
     */
    public String getRow() {
        if (getName().equals("Sentinel") || getName().equals("Berserker")) {
            return "back";
        }
        if (getName().equals("The Cursed One") || getName().equals("Disciple")) {
            return "back";
        }
        return "front";
    }
    /*
    checks whether the current minion is a tank or not
    */
    public boolean isTank() {
        return (getName().equals("Goliath") || getName().equals("Warden"));
    }
}