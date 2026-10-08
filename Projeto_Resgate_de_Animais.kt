val capacidadeAbrigo = 20

val nomes = mutableListOf<String>()
val especies = mutableListOf<String>()
val idades = mutableListOf<Double?>()
val sexos = mutableListOf<String>()
val chips = mutableListOf<String?>()
val consumos = mutableListOf<Double>()
val chipsUsados = mutableListOf<String>()

var racaoKg = 20.0
var dia = 1

fun main() {
    carregarAnimaisIniciais()
    println("=== SISTEMA DE RESGATE DE ANIMAIS ===")

    var sair = false
    while (!sair) {
        exibirStatus()
        exibirMenu()
        val opcao = lerNumero("Escolha uma opção: ")
        println()

        if (opcao < 0 || opcao > 5) {
            println("Opção inválida! Digite um número do menu.")
        } else {
            when (opcao) {
                1 -> resgatarAnimal()
                2 -> listarAnimais()
                3 -> encerrarDia()
                4 -> comprarRacao()
                5 -> adotarAnimal()
                0 -> {
                    println("Encerrando o sistema. Obrigado por passar o dia conosco!")
                    sair = true
                }
            }
        }

        if (!sair) {
            pausar()
        }
    }
}

fun carregarAnimaisIniciais() {
    adicionarAnimal("Mel", "Cachorro", 2.0, "Fêmea", "125874", consumoDaEspecie("Cachorro"))
    adicionarAnimal("Tibbie", "Gato", 1.5, "Fêmea", "186942", consumoDaEspecie("Gato"))
    adicionarAnimal("Totó", "Cachorro", 4.0, "Macho", null, consumoDaEspecie("Cachorro"))
    adicionarAnimal("Esnupi", "Gato", 3.0, "Macho", null, consumoDaEspecie("Gato"))
    adicionarAnimal("Batman", "Coelho", 2.5, "Macho", "250526", consumoDaEspecie("Coelho"))
}

fun adicionarAnimal(nome: String, especie: String, idade: Double?, sexo: String, chip: String?, consumo: Double) {
    nomes.add(nome)
    especies.add(especie)
    idades.add(idade)
    sexos.add(sexo)
    chips.add(chip)
    consumos.add(consumo)
    if (chip != null) {
        chipsUsados.add(chip)
    }
}

fun exibirMenu() {
    println("1 - Resgatar animal")
    println("2 - Listar animais do abrigo")
    println("3 - Alimentar os animais e encerrar o dia")
    println("4 - Comprar ração")
    println("5 - Adotar animal")
    println("0 - Sair")
}

fun exibirStatus() {
    println()
    println("---------- Dia $dia ----------")
    println("Animais: ${nomes.size}/$capacidadeAbrigo | Ração: ${formatar(racaoKg)} kg")
    println("------------------------------")
}

fun pausar() {
    println()
    lerTexto("Pressione Enter para voltar ao menu...")
}

fun formatar(valor: Double): String {
    return String.format("%.2f", valor)
}

fun formatarIdade(idade: Double?): String {
    if (idade == null) {
        return "idade desconhecida"
    } else if (idade % 1 == 0.0) {
        return "${idade.toInt()} ano(s)"
    } else {
        return String.format("%.1f", idade) + " ano(s)"
    }
}

fun lerTexto(mensagem: String): String {
    print(mensagem)
    val entrada = readlnOrNull()
    return entrada?.trim() ?: ""
}

fun lerNumero(mensagem: String): Int {
    val texto = lerTexto(mensagem)
    return texto.toIntOrNull() ?: -1
}

fun converterDecimal(texto: String): Double {
    val numero = texto.replace(",", ".").toDoubleOrNull()
    return numero ?: -1.0
}

fun somenteNumeros(texto: String): Boolean {
    var valido = true
    for (letra in texto) {
        if (letra < '0' || letra > '9') {
            valido = false
        }
    }
    return valido
}

fun consumoDaEspecie(especie: String): Double {
    return when (especie) {
        "Cachorro" -> 1.0
        "Gato" -> 0.5
        "Coelho" -> 0.25
        else -> 0.02
    }
}

fun resgatarAnimal() {
    if (nomes.size >= capacidadeAbrigo) {
        println("O abrigo está lotado! Adote um animal antes de resgatar outro.")
    } else {
        var nome = lerTexto("Nome do animal: ")
        while (nome == "") {
            println("O nome é obrigatório.")
            nome = lerTexto("Nome do animal: ")
        }

        var especie = ""
        while (especie == "") {
            println("Espécie: 1 - Cachorro | 2 - Gato | 3 - Coelho | 4 - Hamster")
            val opcaoEspecie = lerNumero("Escolha a espécie: ")
            when (opcaoEspecie) {
                1 -> especie = "Cachorro"
                2 -> especie = "Gato"
                3 -> especie = "Coelho"
                4 -> especie = "Hamster"
                else -> println("Espécie inválida. Escolha um número de 1 a 4.")
            }
        }

        var idade: Double? = null
        var idadeOk = false
        while (!idadeOk) {
            val textoIdade = lerTexto("Idade em anos, ex: 2 ou 1,5 (se NÃO souber a idade, apenas aperte ENTER): ")
            if (textoIdade == "") {
                idadeOk = true
            } else {
                val numero = converterDecimal(textoIdade)
                if (numero < 0 || numero > 30) {
                    println("Idade inválida. Digite um número de 0 a 30.")
                } else {
                    idade = numero
                    idadeOk = true
                }
            }
        }

        var sexo = ""
        while (sexo == "") {
            val textoSexo = lerTexto("Sexo (M para macho, F para fêmea): ")
            when (textoSexo) {
                "M", "m" -> sexo = "Macho"
                "F", "f" -> sexo = "Fêmea"
                else -> println("Sexo inválido. Digite M ou F.")
            }
        }

        var chip: String? = null
        var chipOk = false
        while (!chipOk) {
            val textoChip = lerTexto("Número do chip com 6 dígitos (se NÃO tiver chip, apenas aperte ENTER): ")
            if (textoChip == "") {
                chipOk = true
            } else if (!somenteNumeros(textoChip)) {
                println("O chip deve ter apenas números. Tente novamente.")
            } else if (textoChip.length != 6) {
                println("O chip deve ter exatamente 6 dígitos. Tente novamente.")
            } else if (chipsUsados.contains(textoChip)) {
                println("Esse chip já foi usado em outro animal. Tente novamente.")
            } else {
                chip = textoChip
                chipOk = true
            }
        }

        var consumo = -1.0
        while (consumo <= 0) {
            val textoConsumo = lerTexto("Consumo diário de ração em kg (Enter para o padrão da espécie): ")
            if (textoConsumo == "") {
                consumo = consumoDaEspecie(especie)
            } else {
                consumo = converterDecimal(textoConsumo)
                if (consumo <= 0) {
                    println("Digite um valor maior que 0. Exemplo: 0,5")
                }
            }
        }

        adicionarAnimal(nome, especie, idade, sexo, chip, consumo)

        println("Animal resgatado com sucesso! Consumo: ${formatar(consumo)} kg por dia.")
        if (chip == null) {
            println("Atenção: ficha criada SEM chip de identificação.")
        }
    }
}

fun listarAnimais() {
    if (nomes.size == 0) {
        println("Nenhum animal no abrigo.")
    } else {
        println("--- Fichas dos animais ---")
        for (i in 0 until nomes.size) {
            val chip = chips[i] ?: "SEM CHIP"
            println("${i + 1}. ${nomes[i]} (${especies[i]}, ${sexos[i]}, ${formatarIdade(idades[i])}) - Chip: $chip - Come ${formatar(consumos[i])} kg/dia")
        }
    }
}

fun calcularConsumoDiario(): Double {
    var total = 0.0
    for (i in 0 until consumos.size) {
        total += consumos[i]
    }
    return total
}

fun calcularDiasDeRacao(): Int {
    val consumo = calcularConsumoDiario()
    return (racaoKg / consumo).toInt()
}

fun encerrarDia() {
    val consumo = calcularConsumoDiario()

    println("========== FIM DO DIA $dia ==========")

    if (nomes.size == 0) {
        println("\nNão há animais no abrigo para alimentar.")
    } else if (racaoKg >= consumo) {
        println("Alimentação de hoje:")
        for (i in 0 until nomes.size) {
            println("  - ${nomes[i]} comeu ${formatar(consumos[i])} kg")
        }
        racaoKg -= consumo
        println()
        println("Total consumido hoje: ${formatar(consumo)} kg")
        println("Ração que sobrou no estoque: ${formatar(racaoKg)} kg")
        println("Com esses animais, o abrigo gasta ${formatar(consumo * 7)} kg por semana.")
    } else {
        println("Ração insuficiente! Os animais precisam de ${formatar(consumo)} kg e só há ${formatar(racaoKg)} kg.")
        println("Os animais ficaram com fome hoje. Compre ração na opção 4.")
    }

    dia += 1
    println()
    println(">>> Começou o dia $dia")

    if (dia % 7 == 0) {
        racaoKg += 5
        println("Chegou a doação semanal: +5 kg de ração!")
    }

    if (nomes.size > 0) {
        val diasRestantes = calcularDiasDeRacao()
        println("O estoque de ração dá para mais $diasRestantes dia(s).")
        if (diasRestantes < 3) {
            println("\nALERTA: a ração está acabando! Compre mais na opção 4.")
        }
    }
}

fun comprarRacao() {
    val texto = lerTexto("Quantos kg de ração deseja comprar? ")
    val quantidade = converterDecimal(texto)

    if (quantidade > 0) {
        racaoKg += quantidade
        println("Ração atualizada: ${formatar(racaoKg)} kg")
    } else {
        println("Quantidade inválida.")
    }
}

fun adotarAnimal() {
    listarAnimais()

    if (nomes.size > 0) {
        val numero = lerNumero("Número do animal adotado: ")
        val posicao = numero - 1

        if (posicao >= 0 && posicao < nomes.size) {
            var palavra = "adotado"
            if (sexos[posicao] == "Fêmea") {
                palavra = "adotada"
            }
            println("\n" + "${nomes[posicao]} foi $palavra! Parabéns à nova família!")
            nomes.removeAt(posicao)
            especies.removeAt(posicao)
            idades.removeAt(posicao)
            sexos.removeAt(posicao)
            chips.removeAt(posicao)
            consumos.removeAt(posicao)
        } else {
            println("Número inválido.")
        }
    }
}