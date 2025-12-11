/*
 * Copyright (c) 2014, NTUU KPI, Computer systems department and/or its affiliates. All rights reserved.
 * NTUU KPI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 */
package Lab_2;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import Lab_2.Creature.CreatureType;

/**
 * Lab_2 Main point
 * 
 * @author Artem Marchenko IA-34
 *
 */
public class Main {

	public static void main(String[] args) {
			// Кількість об'єктів, які потрібно пропустити
		  	int N = 50; 
		  	// Ім'я істоти, яку потрібно пропускати на початку
	        String skipName = "Morgul"; 
	        // Константа для фільтрації за роками появи в літературі
	        int constYear=300;
	        

	        /*
	         * Генеруємо нескінченний стрім Creatures,
	         * використовуємо власний Gatherer,
	         * щоб пропустити перші N істот з ім’ям skipName
	         * і зібрати рівно 500 елементів.
	         */
			List<Creature> creatures = Generator.infiniteStream()
					.gather(SkipAndLimitGatherer.create(a -> a.getName() == skipName,  N, 500 )).toList();
			System.out.println(creatures);
			
			/*
	         * Фільтруємо список за умовою:
	         * брати лише істот, які існують більше ніж constYear років
	         * з моменту першої згадки.
	         */
	        List<Creature> filtered = creatures.stream()
	                .filter(b -> b.getYearsSinceFirstMention()>constYear)
	                .toList();
	        System.out.println(filtered);
	        
	        /*
	         * Групуємо істот за їхнім типом 
	         */
	        Map<CreatureType, List<Creature>> grouped =
	                filtered.stream()
	                        .collect(Collectors.groupingBy(Creature::getType));
	        System.out.println(grouped);
	        
	        /*
	         * Збираємо статистику по силі атаки
	         * Використовується власний Collector 
	         * - додаємо кожне значення
	         * - комбінуємо результати
	         */
			CreatureStats stats = creatures.stream().collect(Collector.of(CreatureStats::new,
					(acc, c) -> acc.add(c.getAttackPower()),CreatureStats::combine, Collector.Characteristics.IDENTITY_FINISH));
			System.out.println(stats);
			
			/*
	         * Обчислюємо квартилі та межі для пошуку викидів 
	         * Метод: Interquartile Range 
	         */
			double[] sortedAttacks = creatures.stream()
	                .mapToDouble(Creature::getAttackPower) 
	                .sorted()
	                .toArray();

	        double q1 = sortedAttacks[(int) (sortedAttacks.length * 0.25)];
	        double q3 = sortedAttacks[(int) (sortedAttacks.length * 0.75)];
	        double iqr = q3 - q1;

	        // нижня межа нормальних значень
	        double lowerBound = q1 - 1.5 * iqr;
	        // верхня межа нормальних значень
	        double upperBound = q3 + 1.5 * iqr;

	        System.out.printf("Q1: "+q1+", Q3: "+ q3+ ", IQR: "+iqr+"\n");
	        System.out.printf("Межі нормальних значень: ["+lowerBound+" "+upperBound+"]\n");

	        /*
	         * Підрахунок статистики:
	         * - скільки даних належать до нормальних значень
	         * - скільки є викидами 
	         */
			Map<String, Long> outlierStats = creatures.stream().collect(Collectors.groupingBy(c -> {
				double val = c.getAttackPower();
				if (val < lowerBound || val > upperBound) {
					return "outliers";
				} else {
					return "data";
				}
			}, Collectors.counting()));

			System.out.println(outlierStats);

		}

}
