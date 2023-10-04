package fr.troisil.info.java.funcprog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Setter
@Getter
class Personne {
    private String prenom;
    private int age;
}
