/*
 * Copyright (c) 2014, NTUU KPI, Computer systems department and/or its affiliates. All rights reserved.
 * NTUU KPI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 */
package Lab_2;

import java.time.Year;

/**
 * Модель для Створінь 
 * 
 * @author Artem Marchenko IA-34
 *
 */

public class Creature {
    private String name;
    private CreatureType type;
    private int yearsSinceFirstMention;
    private int attackPower;

    public Creature(String name, CreatureType type, int firstMentionYear, int attackPower) {
        this.name = name;
        this.type = type;
        this.yearsSinceFirstMention = Year.now().getValue() - firstMentionYear;
        this.attackPower = attackPower;
    }

    public enum CreatureType {
        DEMON, SPIRIT, BANSHEE, VAMPIRE, GHOUL, SHADE, WRAITH, WITCH, GIANT, DRAGON
    }

	public String getName() {
		return name;
	}

	public CreatureType getType() {
		return type;
	}

	public int getYearsSinceFirstMention() {
		return yearsSinceFirstMention;
	}

	public int getAttackPower() {
		return attackPower;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(CreatureType type) {
		this.type = type;
	}

	public void setYearsSinceFirstMention(int yearsSinceFirstMention) {
		this.yearsSinceFirstMention = yearsSinceFirstMention;
	}

	public void setAttackPower(int attackPower) {
		this.attackPower = attackPower;
	}

	@Override
	public String toString() {
		return "Creature [name=" + name + ", type=" + type + ", yearsSinceFirstMention=" + yearsSinceFirstMention
				+ ", attackPower=" + attackPower + "]";
	}
	

    
}

