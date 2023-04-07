package io.github.mrvictor42

import java.util.stream.Collectors

fun main() {
    val listRequest : List<Pessoa> = listOf(Pessoa(1, "victor"), Pessoa(2, "tati"), Pessoa(3, "anita"), Pessoa(4, "Dublin"))
    val listDatabase : List<Pessoa> = listOf(Pessoa(1, "victor"), Pessoa(2, "Tati"), Pessoa(3, "Anita"), Pessoa(4, "Belfast"), Pessoa(5, "Italia"), Pessoa(6, "Alemanha"))

    val listNotInclude : List<Pessoa> =
        listDatabase
            .stream()
            .filter { itemDatabase -> listRequest.stream().noneMatch { itemMigration -> itemDatabase.idPessoa == itemMigration.idPessoa } }
            .collect(Collectors.toList())
    listNotInclude.stream().map { it.nome }.forEach (::println)

//    for(item in listDatabase) {
//        if(listRequest.stream().noneMatch { listDatabaseItem -> listDatabaseItem.idPessoa == item.idPessoa }) {
//            println(item.nome)
//        }
//    }
}

class Pessoa(val idPessoa : Int, val nome : String)