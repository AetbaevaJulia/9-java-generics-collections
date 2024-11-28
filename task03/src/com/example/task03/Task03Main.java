package com.example.task03;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

public class Task03Main {

    public static void main(String[] args) throws IOException {
        List<Set<String>> anagrams = findAnagrams(new FileInputStream("task03/resources/singular.txt"),
                Charset.forName("windows-1251"));
        for (Set<String> anagram : anagrams) {
            System.out.println(anagram);
        }
    }

    public static List<Set<String>> findAnagrams(InputStream inputStream, Charset charset) {
        InputStreamReader input = new InputStreamReader(inputStream, charset);
        Set<String> uniqueWords = new HashSet<>(); // множество слов, чтобы отсеять повторения
        HashMap<String, List<String>> anagrams = new HashMap<>(); // Ключ: набор букв, значение: набор слов

        // Считываем слова и подгоняем их под условие (нижний регистр, кириллица и тд)
        try (BufferedReader buffer = new BufferedReader(input)){
            String line;
            while ((line = buffer.readLine()) != null) {
                line = line.toLowerCase();
                if (isCyrillic(line) && line.length() > 3){
                    uniqueWords.add(line);
                }
            }
        } catch (Exception e){}

        // Создаем пары для HashMap
        for (String word : uniqueWords){
            String setOfChars = sortChars(word); // сортируем в алфавитный порядок
            if (!anagrams.containsKey(setOfChars)){ // если нет нужного ключа, то добавляем
                anagrams.put(setOfChars, new ArrayList<>());
            }
            anagrams.get(setOfChars).add(word); // и в любом случае добавляем новые слова
        }

        // сортируем слова внутри набора
        List<List<String>> sortedAnagrams = new ArrayList<>();
        for (List<String> list : anagrams.values()){
            if (list.size() > 1){
                Collections.sort(list);
                sortedAnagrams.add(list);
            }
        }

        // сортировка набора наборов по первому слову
        sortedAnagrams.sort((a, b) -> a.get(0).compareTo(b.get(0)));

        // Превращаем Лист листов в лист множеств
        List<Set<String>> res = new ArrayList<>();
        for (List<String> list : sortedAnagrams){
            res.add(new LinkedHashSet<>(list)); // LinkedHashSet чтобы сохранился порядок
        }

        return res;
    }

    // сортировка чаров, чтобы были в алфавитном порядке
    public static String sortChars(String str){
        char[] chars = str.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    // проверка строки на кириллицу
    public static boolean isCyrillic(String s) {
        for (int i = 0; i < s.length(); i++) {
            // если чары в строке не находятся в блоке юникода, то скип
            if (!Character.UnicodeBlock.of(s.charAt(i)).equals(Character.UnicodeBlock.CYRILLIC)) {
                return false;
            }
        }
        return true;
    }
}