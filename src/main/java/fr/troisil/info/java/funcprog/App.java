package fr.troisil.info.java.funcprog;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        log.info("Better than System.out.println..");

        // Résumé de l'exercice :
        // J'ai suivi l'exercice et ai adapté mon code en conséquence
        // J'ai défini des blocs pour les Function, les Supplier et les Consumer afin d'avoir un code plus propre et organisé
        // Il aurait peut-être été plus judicieux de les stocker dans des fichiers distincts pour améliorer la lisibilité mais tout à été fait ici lors de la refacto
        // Les normes de nommage sont en anglais (sauf la classe Personne car j'ai suivi ce qui était demandé dans l'exercice)

        Path filePath = Paths.get("src/main/resources/personnes.csv");

        Supplier<Stream<String>> lineSupplier = () -> {
            try {
                return Files.lines(filePath).skip(1);
            } catch (IOException e) {
                e.printStackTrace();
                return Stream.empty();
            }
        };

        Function<String, Personne> lineToPerson = line -> {
            String[] elements = line.split(",");
            if (elements.length == 2) {
                String firstName = elements[0].trim();
                int age = Integer.valueOf(elements[1].trim());
                return new Personne(firstName, age);
            } else {
                return null;
            }
        };

        Supplier<Comparator<Personne>> comparatorSupplier = () ->
                Comparator.comparingInt(Personne::getAge).reversed()
                        .thenComparing(Comparator.comparing(Personne::getPrenom));

        Consumer<Personne> printPerson = person -> log.info(person.toString());

        try {
            List<Personne> people = lineSupplier.get()
                    .map(lineToPerson)
                    .filter(person -> person != null)
                    .sorted(comparatorSupplier.get())
                    .collect(Collectors.toList());

            people.forEach(printPerson);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}