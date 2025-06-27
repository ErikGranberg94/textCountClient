package org.example;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class WordCount {
    private final Map<String, Integer> wordCount;
    private final ReentrantLock lock;

    public WordCount() {
        wordCount = new HashMap<>();
        lock = new ReentrantLock(true);
    }

    void countWords(String text) {
        for (var word : getWordsInString(text)) {
            word = removePunctuation(word);

            if (!word.isEmpty()) {
                lock.lock();
                int count = wordCount.getOrDefault(word, 0);
                wordCount.put(word, count + 1);
                lock.unlock();
            }
        }
    }

    static List<String> getWordsInString(String text) {
        return List.of(text.split(" +"));
    }

    static String removePunctuation(String word) {
        return word.replaceAll("([^a-zA-Z0-9'])$", "").toLowerCase();
    }

    void getCount(int limit) {
        wordCount.entrySet()
                .stream()
                .sorted((word1, word2) -> word2.getValue().compareTo(word1.getValue()))
                .limit(limit)
                .forEach(word -> System.out.println(word.getKey() + " " + word.getValue()));
    }
}
