/*
 * Copyright (c) 2014, NTUU KPI, Computer systems department and/or its affiliates. All rights reserved.
 * NTUU KPI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 */
package Lab_2;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import Lab_2.Creature.CreatureType;

/**
 * Generator random values
 * 
 * @author Artem Marchenko IA-34
 *
 */
public class Generator {
	 private static final Random RANDOM = new Random();

	    private static final List<String> NAMES = List.of(
	            "Astaroth", "Morgul", "Lilith", "Baal", "Morana", "Chort",
	            "Zmey", "Veles", "Striga", "Mara", "Koschei", "Oni", "Asura"
	    );

	    public static Stream<Creature> infiniteStream() {

	        return Stream.generate(() -> {
	            String name = NAMES.get(RANDOM.nextInt(NAMES.size()));

	            CreatureType type = CreatureType.values()
	                    [RANDOM.nextInt(CreatureType.values().length)];
	            int firstMention = 1000 + RANDOM.nextInt(900);  


	            int attack = switch (type) {
	                case DEMON, DRAGON -> 1000 + RANDOM.nextInt(40);  
	                case VAMPIRE, GIANT -> 80 + RANDOM.nextInt(30); 
	                case BANSHEE, WRAITH -> 30 + RANDOM.nextInt(30); 
	                default -> 10 + RANDOM.nextInt(40); 
	            };

	            return new Creature(name, type, firstMention, attack);
	        });
	    }

}
